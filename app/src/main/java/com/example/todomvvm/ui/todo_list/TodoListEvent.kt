package com.example.todomvvm.ui.todo_list

import com.example.todomvvm.data.Todo

sealed class TodoListEvent {
    object OnAddTodoClick: TodoListEvent()
    data class OnTodoClick(val todo: Todo): TodoListEvent()
    data class DeleteTodo(val todo: Todo): TodoListEvent()
    object OnUndoDeleteClick: TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone: Boolean): TodoListEvent()
}