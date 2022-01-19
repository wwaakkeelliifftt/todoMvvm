package com.example.todomvvm.data

import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    fun getAllTodos(): Flow<List<Todo>>

    suspend fun getTodoById(id: Int): Todo?
}