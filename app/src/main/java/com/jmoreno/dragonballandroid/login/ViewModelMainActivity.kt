package com.jmoreno.dragonballandroid.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class ViewModelMainActivity: ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState : StateFlow<UiState> = _uiState

    fun login(user: String, pass: String){
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()
            val url = "https://dragonball.keepcoding.education/api/auth/login"
            val credential = Credentials.basic("$user", "$pass")
            //val credential = Credentials.basic("jmorenocarrero@hotmail.com", "Realmadrid14")
            val body = FormBody.Builder()
                .build()
            val request = Request.Builder()
                .url(url)
                .addHeader("Authorization", credential)
                .post(body)
                .build()
            val call = client.newCall(request)
            val response = call.execute()
            //Recogemos la respuesta y la utilizamos para cambiar el estado del StateFlow y que la actividad reaccione
            response.body?.let { responseBody ->
                _uiState.value = UiState.OnTokenReceived(responseBody.string())
            } ?: run { _uiState.value = UiState.Error("Something went wrong in the request") }
        }

    }
    sealed class UiState {
        object Idle : UiState()
        data class Error(val error: String) : UiState()
        data class OnTokenReceived(val text: String) : UiState()

    }



}