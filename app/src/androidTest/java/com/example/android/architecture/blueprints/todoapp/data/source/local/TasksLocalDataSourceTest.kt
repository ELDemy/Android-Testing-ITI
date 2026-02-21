package com.example.android.architecture.blueprints.todoapp.data.source.local

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.MediumTest
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class TasksLocalDataSourceTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: ToDoDatabase
    private lateinit var tasksDao: TasksDao
    private lateinit var localDataSource: TasksLocalDataSource

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Application>(),
            ToDoDatabase::class.java
        ).build()
        tasksDao = db.taskDao()
        localDataSource = TasksLocalDataSource(tasksDao, Dispatchers.Unconfined)
    }

    @After
    fun close() {
        db.close()
    }

    @Test
    fun saveTask_getTasks() = runTest {
        //Given the task
        val task = Task("title", "description")

        //when Saving task and getting the task with id
        localDataSource.saveTask(task)
        val tasksResponse = localDataSource.getTasks() as Result.Success
        val tasksRetrieved = tasksResponse.data

        //then the retrieved task must be the same as saved
        assertThat(1, `is`(tasksRetrieved.size))
        assertNotNull(tasksRetrieved.firstOrNull())
        assertThat(task, `is`(tasksRetrieved.first()))
    }

    @Test
    fun saveTask_getTaskById() = runTest {
        //Given the task
        val task = Task("title", "description")

        //when Saving task and getting the task with id
        localDataSource.saveTask(task)
        val taskRetrieved = localDataSource.getTask(task.id) as Result.Success

        //then the retrieved task must be the same as saved
        assertThat(task, `is`(taskRetrieved.data))
    }
}