package com.example.todomvvm.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todomvvm.data.Todo
import com.example.todomvvm.data.TodoRepository
import com.example.todomvvm.util.Routes
import com.example.todomvvm.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val repository: TodoRepository
): ViewModel() {

    val todos = repository.getAllTodos()
    private var deletedTodo: Todo? = null

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnAddTodoClick ->
                sendUiEvent(UiEvent.Navigate(route = Routes.ADD_EDIT_TODO))
            is TodoListEvent.OnTodoClick ->
                sendUiEvent(UiEvent.Navigate(route = Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            is TodoListEvent.DeleteTodo ->
                doInScope {
                    deletedTodo = event.todo
                    repository.deleteTodo(event.todo)
                    sendUiEvent(UiEvent.ShowSnackBar(message = "Todo deleted", action = "Undo"))
                }
            TodoListEvent.OnUndoDeleteClick ->
                deletedTodo?.let { todo ->
                    doInScope { repository.insertTodo(todo = todo) }
                }
            is TodoListEvent.OnDoneChange ->
                doInScope {
                    repository.insertTodo(event.todo.copy(isDone = event.isDone))
                }
        }
    }

    private inline fun doInScope(crossinline block: suspend () -> Unit) {
        viewModelScope.launch { block.invoke() }
    }

    private fun sendUiEvent(event: UiEvent) {
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}
