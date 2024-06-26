package com.example.todoapp

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun TodoListPage(viewModel: TodoViewModel){
    val todoList by viewModel.todoList.observeAsState()
    var inputText by remember {
        mutableStateOf("")
    }
   
    //Text(text=todoList.toString())

    Column (
        modifier = Modifier
            .fillMaxHeight()
            .padding(65.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            OutlinedTextField(modifier = Modifier.weight(1f), value = inputText, onValueChange = {
                inputText = it
            } )
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                viewModel.addTodo(inputText)
                inputText=""
            }) {
                Text(text = "Add")
            }
        }


        todoList?.let{
            LazyColumn (
                content = {
                    itemsIndexed(it){
                            index: Int, item: Todo ->
                        TodoItem(item = item, onDelete = {
                            viewModel.deleteTodo(item.id)
                        })
                    }
                }
            )
        }?: Text(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            textAlign = TextAlign.Center,
            text = "No items yet",
            fontSize = 20.sp,
            )
    }
}

@Composable
fun TodoItem(item:Todo, onDelete : ()->Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier.weight(1f)
        ){
            Text(text=SimpleDateFormat("HH:mm:aaa, dd/mm", Locale.ENGLISH).format(item.createdAt),
                fontSize = 15.sp, color = Color.LightGray)
            Text(text=item.title,
                fontSize = 25.sp,
                color = Color.White)
        }
        IconButton(onClick = onDelete) {
            Icon(painter = painterResource(id = R.drawable.baseline_delete_24), contentDescription = "Delete",
                tint=Color.White)
        }
    }
}