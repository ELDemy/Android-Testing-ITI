package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test

class DefaultTasksRepositoryTest {
    lateinit var tasksRepository: DefaultTasksRepository
    lateinit var remoteTasks: MutableList<Task>
    lateinit var localTasks: MutableList<Task>
    lateinit var tasksLocalDataSource: TasksLocalDataSource

    @Before
    fun setup() {
        remoteTasks = mutableListOf(
            Task("Remote task 1", "Description 1"),
            Task("Remote task 2", "Description 2")
        )

        localTasks = mutableListOf(
            Task("Local task 1", "Description 1"),
            Task("Local task 2", "Description 2")
        )


//        val tasksRemoteDataSource = FakeDataSource(remoteTasks)
//        val tasksLocalDataSource = FakeDataSource(localTasks)
//        tasksRepository = DefaultTasksRepository(tasksRemoteDataSource, tasksLocalDataSource)

        val tasksRemoteDataSource = mockk<TasksDataSource>()
        tasksLocalDataSource = mockk<TasksLocalDataSource>()
        coEvery { tasksRemoteDataSource.getTasks() } returns Result.Success(remoteTasks)
        coEvery { tasksLocalDataSource.getTasks() } returns Result.Success(localTasks)

        tasksRepository = DefaultTasksRepository(
            tasksRemoteDataSource,
            tasksLocalDataSource,
            ioDispatcher = Dispatchers.Unconfined
        )
    }

    @Test
    fun getTasks_requestsAllTasksFromRemoteDataSource() = runTest {
        coEvery { tasksLocalDataSource.deleteAllTasks() } returns Unit
        coEvery { tasksLocalDataSource.saveTask(any()) } returns Unit
        
        val tasks = tasksRepository.getTasks(true) as Result.Success
//        assertThat(tasks is Result.Success, IsEqual(true))
        assertThat(tasks.data, IsEqual(localTasks))
    }
}