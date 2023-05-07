package com.jmoreno.dragonballandroid.heroHome

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.jmoreno.dragonballandroid.heroHome.heroBattle.HeroeFragment
import com.jmoreno.dragonballandroid.heroHome.list.ListFragment
import com.jmoreno.dragonballandroid.models.Personaje
import com.jmoreno.dragonballandroid.PersonajeClicked
import com.jmoreno.dragonballandroid.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(), PersonajeClicked {

    private lateinit var binding : ActivitySecondBinding
    companion object {
        const val TOKEN = "TOKEN"
    }
    private val viewModel: SecondActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val token = intent.getStringExtra("TOKEN")

        viewModel.downloadListOfHeroes(token.toString())// Hago la llamada
        addFragment(ListFragment())//Añado el fragment para la lista de heroes
    }

    fun getIntent(context: Context,token: String): Intent {
        val intent = Intent(context, SecondActivity::class.java)
        intent.putExtra(TOKEN, token)
        return intent

    }
    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fFragmentList.id,fragment)
            .commitNow()
    }
    override fun personajeClicked(personaje: Personaje) {

    }
}