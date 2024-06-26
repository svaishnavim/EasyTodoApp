package com.example.todoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoapp.Todo


@Dao
interface TodoDao {

    @Query("SELECT * FROM TODO ORDER BY createdAt DESC") //order by latest one first
    fun getAllTodo():LiveData<List<Todo>>

    @Insert
    fun addTodo(todo:Todo)

    @Query("DELETE FROM TODO WHERE id=:id")
    fun deleteTodo(id : Int)
}