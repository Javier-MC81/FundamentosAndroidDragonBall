package com.jmoreno.dragonballandroid

import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@OptIn(ExperimentalCoroutinesApi::class)
class SecondActivityViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    val viewModel = SecondActivityViewModel()
    val token = "eyJraWQiOiJwcml2YXRlIiwiYWxnIjoiSFMyNTYiLCJ0eXAiOiJKV1QifQ.eyJleHBpcmF0aW9uIjo2NDA5MjIxMTIwMCwiZW1haWwiOiJqbW9yZW5vY2FycmVyb0Bob3RtYWlsLmNvbSIsImlkZW50aWZ5IjoiMTZFQjNCQjMtQzFFNi00NjJGLUIzQUQtQUFFMzA4QjEwQThBIn0.OJ0GuAjKi3sdg8AOH5VOm4AvLMIe4J7Lmtz0P-bAWN8"
    @Test
    fun `comprobar descarga heroes`() = runTest {

        launch {
            viewModel.uiListState.collect{

                var listaPersonajes: List<Personaje> = listOf()
                when(it) {
                    is SecondActivityViewModel.UiListState.Idle -> {
                        assertEquals(listaPersonajes.count(), 0)
                    }

                    is SecondActivityViewModel.UiListState.OnListReceived -> {
                        listaPersonajes = it.heroeList
                        assertNotNull(listaPersonajes)
                        assertNotNull(listaPersonajes[1].name)
                        cancel()

                    }
                    is SecondActivityViewModel.UiListState.Error -> {
                        assertEquals(it, "Something went wrong in the response")
                        cancel()
                    }
                    is SecondActivityViewModel.UiListState.OnHeroReceived -> {
                         assertNotNull(it.personaje.name)
                    }

                }
            }
        }

        viewModel.downloadListOfHeroes(token)

    }
}
