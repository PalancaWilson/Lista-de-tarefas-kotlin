package com.palancawilson.listatarefaskotlin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TarefaUtilsTest {

    @Test
    fun tituloVazioNaoEValido() {
        assertFalse(tituloValido("   "))
    }

    @Test
    fun tituloComTextoEValido() {
        assertTrue(tituloValido("Comprar leite"))
    }

    @Test
    fun adicionaTarefaValida() {
        val lista = mutableListOf<Task>()

        assertTrue(adicionarTarefa(lista, "Comprar leite"))

        assertEquals(1, lista.size)
        assertEquals("Comprar leite", lista[0].title)
        assertFalse(lista[0].isDone)
    }

    @Test
    fun naoAdicionaDuplicadosIgnorandoMaiusculas() {
        val lista = mutableListOf(Task("Comprar leite"))

        assertFalse(adicionarTarefa(lista, "  comprar LEITE "))

        assertEquals(1, lista.size)
    }

    @Test
    fun naoAdicionaTituloVazio() {
        val lista = mutableListOf<Task>()

        assertFalse(adicionarTarefa(lista, "  "))

        assertTrue(lista.isEmpty())
    }

    @Test
    fun alternaConclusao() {
        val lista = mutableListOf(Task("Estudar"))

        alternarConclusao(lista, 0)
        assertTrue(lista[0].isDone)

        alternarConclusao(lista, 0)
        assertFalse(lista[0].isDone)
    }

    @Test
    fun removeTarefa() {
        val lista = mutableListOf(Task("A"), Task("B"))

        removerTarefa(lista, 0)

        assertEquals(listOf(Task("B")), lista)
    }

    @Test
    fun ignoraIndiceInvalido() {
        val lista = mutableListOf(Task("A"))

        removerTarefa(lista, 5)
        alternarConclusao(lista, -1)

        assertEquals(1, lista.size)
        assertFalse(lista[0].isDone)
    }
}
