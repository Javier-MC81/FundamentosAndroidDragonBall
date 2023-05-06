package com.jmoreno.dragonballandroid

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class SecondActivityViewModel: ViewModel() {

    private val _uiListState = MutableStateFlow<SecondActivityViewModel.UiListState>(SecondActivityViewModel.UiListState.Idle)
    val uiListState : StateFlow<SecondActivityViewModel.UiListState> = _uiListState
    var listaPersonajes: List<Personaje> = listOf()

    fun changeDetail(personaje: Personaje) {
        _uiListState.value = UiListState.OnHeroReceived(personaje)
    }
    fun showList() {
        _uiListState.value = UiListState.OnListReceived(listaPersonajes)
    }

    fun downloadListOfHeroes(token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "https://dragonball.keepcoding.education/api/heros/all"
            val body = FormBody.Builder()
                .add("name", "")
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer $token")
                .post(body)
                .build()
            val call = client.newCall(request)
            val response = call.execute()

            response.body?.let { responseBody ->
                val gson = Gson()
                try {
                    val personajeDtoArray = gson.fromJson(responseBody.string(), Array<PersonajeDTO>::class.java)
                    //_uiState.value = UiState.OnListReceived(personajeDtoArray.toList().map { PersonajeDTO(it.favorite,it.name,it.id,it.photo,it.description) })
                    listaPersonajes = personajeDtoArray.toList().map { Personaje(it.favorite,it.name,it.id,it.photo,it.description, vidaMaxima = 100, vidaActual = 100) }
                    _uiListState.value = UiListState.OnListReceived(listaPersonajes)
                } catch(ex: Exception ) {
                    _uiListState.value = UiListState.Error("Something went wrong in the response")
                }
            } ?: run { _uiListState.value = UiListState.Error("Something went wrong in the request") }
        }
    }
    sealed class UiListState {
        object Idle : UiListState()
        object Empty: UiListState()
        data class Error(val error: String) : UiListState()
        data class OnListReceived(val heroeList: List<Personaje>) : UiListState()
        data class OnHeroReceived(val personaje: Personaje): UiListState()
    }


    sealed class UiHeroState {
        object Idle : UiHeroState()

        data class OnHeroReceived(val personaje: Personaje): UiHeroState()
    }
}
