package com.jmoreno.dragonballandroid

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.jmoreno.dragonballandroid.databinding.ActivityMainBinding


import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlin.random.Random

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












