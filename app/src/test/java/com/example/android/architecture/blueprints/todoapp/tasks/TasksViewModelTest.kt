package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addNewTask_updateNewTaskEvent() {
        //Given a view model
        val application: Application = ApplicationProvider.getApplicationContext()
        val tasksViewModel = TasksViewModel(application)

        //when adding new task
        tasksViewModel.addNewTask()

        //then update newTask LiveData
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()

        assertNotNull(value)
    }

    @Test
    fun setFilter_allTasks_UpdateLiveData() {
        //Given a view model
        val application: Application = ApplicationProvider.getApplicationContext()
        val tasksViewModel = TasksViewModel(application)

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
        //Given a view model
        val application: Application = ApplicationProvider.getApplicationContext()
        val tasksViewModel = TasksViewModel(application)

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
        //Given a view model
        val application: Application = ApplicationProvider.getApplicationContext()
        val tasksViewModel = TasksViewModel(application)

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