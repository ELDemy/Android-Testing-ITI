package com.example.android.architecture.blueprints.todoapp.data.source.local

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.android.architecture.blueprints.todoapp.data.Task
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TasksDaoTest {

    private lateinit var tasksDao: TasksDao
    private lateinit var db: ToDoDatabase

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Application>(),
            ToDoDatabase::class.java
        ).build()

        tasksDao = db.taskDao()
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun insertAndGetAllTasks() = runTest {
        //Given the task
        val task = Task("title", "description")

        //when inserting task
        tasksDao.insertTask(task)

        //then task is inserted in the tasks table
        val tasks = tasksDao.getTasks()
        assertNotNull(tasks.firstOrNull())
        assertThat(tasks.size, `is`(1))
        assertThat(tasks[0], `is`(task.copy()))
        assertThat(tasks[0], IsEqual(task))
    }

    @Test
    fun insertAndGetTask() = runTest {
        //Given the task
        val task = Task("title", "description")

        //when inserting task
        tasksDao.insertTask(task)

        //then task is inserted in the tasks table
        val taskRetrieved = tasksDao.getTaskById(task.id)
        assertNotNull(taskRetrieved)
        assertEquals(taskRetrieved, task)
    }
}