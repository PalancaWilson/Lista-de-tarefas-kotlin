package com.palancawilson.listatarefaskotlin

// Verifica se o título não está vazio.
fun tituloValido(texto: String): Boolean = texto.isNotBlank()

// Verifica duplicados (ignora maiúsculas e espaços).
fun tarefaJaExiste(tarefas: List<Task>, titulo: String): Boolean =
    tarefas.any { it.title.equals(titulo.trim(), ignoreCase = true) }

// Só adiciona se o título for válido e não duplicado.
fun podeAdicionarTarefa(tarefas: List<Task>, texto: String): Boolean =
    tituloValido(texto) && !tarefaJaExiste(tarefas, texto)

// Adiciona a tarefa e devolve true se conseguiu.
fun adicionarTarefa(tarefas: MutableList<Task>, texto: String): Boolean {
    if (!podeAdicionarTarefa(tarefas, texto)) return false
    tarefas.add(Task(texto.trim()))
    return true
}

// Alterna o estado de conclusão.
fun alternarConclusao(tarefas: MutableList<Task>, indice: Int) {
    val tarefa = tarefas.getOrNull(indice) ?: return
    tarefas[indice] = tarefa.copy(isDone = !tarefa.isDone)
}

// Remove a tarefa se o índice for válido.
fun removerTarefa(tarefas: MutableList<Task>, indice: Int) {
    if (indice in tarefas.indices) tarefas.removeAt(indice)
}
