package com.example.android.architecture.blueprints.todoapp.data

import androidx.lifecycle.LiveData
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource

class FakeDataSource(private val tasks: MutableList<Task>?) : TasksDataSource {

    override suspend fun getTasks(): Result<List<Task>> {
        if (tasks != null) {
            return Result.Success(tasks)
        } else {
            return Result.Error(Exception("Tasks not found"))
        }
    }

    override suspend fun saveTask(task: Task) {
        tasks?.add(task)
    }

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        tasks?.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        tasks?.removeIf { it.id == taskId }
    }

    override suspend fun refreshTasks() {
        TODO("Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        TODO("Not yet implemented")
    }

    override suspend fun refreshTask(taskId: String) {
        TODO("Not yet implemented")
    }


    override suspend fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTasks() {
        TODO("Not yet implemented")
    }


}