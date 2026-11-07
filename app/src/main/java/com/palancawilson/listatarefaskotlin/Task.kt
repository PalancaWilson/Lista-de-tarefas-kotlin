package com.palancawilson.listatarefaskotlin

// 4.2: representa uma tarefa e o seu estado.
data class Task(
    val title: String,
    val isDone: Boolean = false
)
