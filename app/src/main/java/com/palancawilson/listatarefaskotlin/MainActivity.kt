package com.palancawilson.listatarefaskotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    TodoApp()
                }
            }
        }
    }
}

@Composable
fun TodoApp() {
    val tarefas = remember { mutableStateListOf<Task>() }
    var texto by remember { mutableStateOf("") }
    var aviso by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Lista de Tarefas",
            fontSize = 24.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = texto,
                onValueChange = {
                    texto = it
                    aviso = null
                },
                label = { Text("Nova tarefa") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    aviso = when {
                        !tituloValido(texto) -> "Escreve uma tarefa antes de adicionar."
                        tarefaJaExiste(tarefas, texto) -> "Essa tarefa já existe."
                        else -> {
                            adicionarTarefa(tarefas, texto)
                            texto = ""
                            null
                        }
                    }
                }
            ) {
                Text("Add Task")
            }
        }

        aviso?.let { mensagem ->
            Text(
                text = mensagem,
                color = MaterialTheme.colorScheme.error
            )
        }

        if (tarefas.isEmpty()) {
            Text(
                text = "Ainda não há tarefas. Adiciona a primeira!",
                modifier = Modifier.padding(top = 8.dp)
            )
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemsIndexed(tarefas) { indice, tarefa ->
                    TarefaItem(
                        tarefa = tarefa,
                        onToggle = { alternarConclusao(tarefas, indice) },
                        onDelete = { removerTarefa(tarefas, indice) }
                    )
                }
            }
        }
    }
}

@Composable
private fun TarefaItem(
    tarefa: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = tarefa.isDone,
                onCheckedChange = { onToggle() }
            )
            Text(
                text = tarefa.title,
                textDecoration = if (tarefa.isDone) {
                    TextDecoration.LineThrough
                } else {
                    TextDecoration.None
                },
                modifier = Modifier.weight(1f)
            )
            TextButton(onClick = onDelete) {
                Text("Apagar")
            }
        }
    }
}
