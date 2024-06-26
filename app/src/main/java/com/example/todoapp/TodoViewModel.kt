package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.util.Date

class TodoViewModel : ViewModel() {
    val todoDao = MainApplication.todoDatabase
        .getTodoDao()
    val todoList : LiveData<List<Todo>> = todoDao.getAllTodo()


    fun addTodo(title: String){
        viewModelScope.launch(Dispatchers.IO){ //put this function on different thread
            todoDao.addTodo(Todo(title=title, createdAt = Date.from(Instant.now())))
        }

    }
    fun deleteTodo(id : Int){

        viewModelScope.launch(Dispatchers.IO){ //put this function on different thread
            todoDao.deleteTodo(id)
        }
    }
}
/*
* view database: view > tool windows > app inspection > open the tables and select live updates
* */