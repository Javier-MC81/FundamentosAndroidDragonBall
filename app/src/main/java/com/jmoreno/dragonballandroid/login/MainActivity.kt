package com.jmoreno.dragonballandroid.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jmoreno.dragonballandroid.heroHome.SecondActivity
import com.jmoreno.dragonballandroid.databinding.ActivityMainBinding


import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: ViewModelMainActivity by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var token: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var token: String = ""
        binding.bLogin?.setOnClickListener {
            val user = binding.etUser?.text.toString()
            val password = binding.etPassword?.text.toString()
            //Escuchamos el cambio del StateFlow para que cuando se reciba un token, haga el cambio de actividad
            lifecycleScope.launch {
                viewModel.login(user, password)
                viewModel.uiState.collect{
                    when (it){
                        is ViewModelMainActivity.UiState.OnTokenReceived ->
                            startActivity(SecondActivity().getIntent(this@MainActivity,it.text))
                        is ViewModelMainActivity.UiState.Error -> {}
                        else -> Unit

                    }
                }
            }

        }

    }
}












