package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

import org.junit.Test

class StatisticsUtilsTest {

    @Test
    fun getActiveAndCompletedStats_EmptyList_ReturnSuccess() {
        //Given empty tasks list
        val tasks = emptyList<Task>()

        //when calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)

        //then result is 0,0
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_Null_ReturnSuccess() {

        //when calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(null)

        //then result is 0,0
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_NoCompleted_ReturnSuccess() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false),
        )
        //when calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)

        //then result is 0,100
        assertThat(result.completedTasksPercent, `is`(0f))
        assertThat(result.activeTasksPercent, `is`(100f))
    }

    @Test
    fun getActiveAndCompletedStats_AllCompleted_ReturnSuccess() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = true),
        )
        //when calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)

        //then result is 100,0
        assertThat(result.completedTasksPercent, `is`(100f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }

    @Test
    fun getActiveAndCompletedStats_2Completed3Active_ReturnSuccess() {
        val tasks = listOf(
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = false),
            Task("title", "desc", isCompleted = true),
            Task("title", "desc", isCompleted = false),
        )
        //when calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)

        //then result is 40,60
        assertThat(result.completedTasksPercent, `is`(40f))
        assertThat(result.activeTasksPercent, `is`(60f))
    }

}