package com.example.todomvvm.data

import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val db: TodoDatabase): TodoRepository {

    override suspend fun insertTodo(todo: Todo) {
        db.dao.insertTodo(todo = todo)
    }

    override suspend fun deleteTodo(todo: Todo) {
        db.dao.deleteTodo(todo = todo)
    }

    override fun getAllTodos(): Flow<List<Todo>> {
        return db.dao.getAllTodos()
    }

    override suspend fun getTodoById(id: Int): Todo? {
        return db.dao.getTodoById(id = id)
    }
}