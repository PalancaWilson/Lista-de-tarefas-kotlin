package com.palancawilson.listatarefaskotlin

fun tituloValido(texto: String): Boolean = texto.isNotBlank()

fun tarefaJaExiste(tarefas: List<Task>, titulo: String): Boolean =
    tarefas.any { it.title.equals(titulo.trim(), ignoreCase = true) }

fun podeAdicionarTarefa(tarefas: List<Task>, texto: String): Boolean =
    tituloValido(texto) && !tarefaJaExiste(tarefas, texto)

fun adicionarTarefa(tarefas: MutableList<Task>, texto: String): Boolean {
    if (!podeAdicionarTarefa(tarefas, texto)) return false
    tarefas.add(Task(texto.trim()))
    return true
}

fun alternarConclusao(tarefas: MutableList<Task>, indice: Int) {
    val tarefa = tarefas.getOrNull(indice) ?: return
    tarefas[indice] = tarefa.copy(isDone = !tarefa.isDone)
}

fun removerTarefa(tarefas: MutableList<Task>, indice: Int) {
    if (indice in tarefas.indices) tarefas.removeAt(indice)
}
