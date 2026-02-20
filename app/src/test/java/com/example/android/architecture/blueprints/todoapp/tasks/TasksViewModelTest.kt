package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var remoteTasks: MutableList<Task>
    lateinit var localTasks: MutableList<Task>
    lateinit var tasksViewModel: TasksViewModel

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

        //Given a view model
        val repo: DefaultTasksRepository = mockk()

        every { repo.observeTasks() } returns MutableLiveData()
        coEvery { repo.getTasks() } returns Result.Success(localTasks)

        tasksViewModel = TasksViewModel(repo)
    }

    @Test
    fun addNewTask_updateNewTaskEvent() {
        //when adding new task
        tasksViewModel.addNewTask()

        //then update newTask LiveData
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertNotNull(value)
    }

    @Test
    fun setFilter_allTasks_UpdateLiveData() {
        //when filtering with all tasks
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        //then these data are updated
        val currentFilteringLabel = tasksViewModel.currentFilteringLabel.getOrAwaitValue()
        val noTasksLabel = tasksViewModel.noTasksLabel.getOrAwaitValue()
        val noTaskIconRes = tasksViewModel.noTaskIconRes.getOrAwaitValue()
        val tasksAddViewVisible = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()

        assertEquals(R.string.label_all, currentFilteringLabel)
        assertEquals(R.string.no_tasks_all, noTasksLabel)
        assertEquals(R.drawable.logo_no_fill, noTaskIconRes)
        assertEquals(true, tasksAddViewVisible)
    }

    @Test
    fun setFilter_activeTasks_updateLiveData() {
        //when filtering with only active tasks
        tasksViewModel.setFiltering(TasksFilterType.ACTIVE_TASKS)

        //then these data are updated
        val currentFilteringLabel = tasksViewModel.currentFilteringLabel.getOrAwaitValue()
        val noTasksLabel = tasksViewModel.noTasksLabel.getOrAwaitValue()
        val noTaskIconRes = tasksViewModel.noTaskIconRes.getOrAwaitValue()
        val tasksAddViewVisible = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()

        assertEquals(R.string.label_active, currentFilteringLabel)
        assertEquals(R.string.no_tasks_active, noTasksLabel)
        assertEquals(R.drawable.ic_check_circle_96dp, noTaskIconRes)
        assertEquals(false, tasksAddViewVisible)
    }

    @Test
    fun setFilter_completedTasks_updateLiveData() {
        //when filtering with completed tasks
        tasksViewModel.setFiltering(TasksFilterType.COMPLETED_TASKS)

        //then these data are updated
        val currentFilteringLabel = tasksViewModel.currentFilteringLabel.getOrAwaitValue()
        val noTasksLabel = tasksViewModel.noTasksLabel.getOrAwaitValue()
        val noTaskIconRes = tasksViewModel.noTaskIconRes.getOrAwaitValue()
        val tasksAddViewVisible = tasksViewModel.tasksAddViewVisible.getOrAwaitValue()

        assertEquals(R.string.label_completed, currentFilteringLabel)
        assertEquals(R.string.no_tasks_completed, noTasksLabel)
        assertEquals(R.drawable.ic_verified_user_96dp, noTaskIconRes)
        assertEquals(false, tasksAddViewVisible)
    }
}