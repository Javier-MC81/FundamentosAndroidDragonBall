package com.jmoreno.dragonballandroid.models

import kotlinx.coroutines.flow.StateFlow

data class Personaje (

    var favorite: Boolean ,
    var name: String,
    var id: String,
    var photo: String,
    var description:String,
    var vidaMaxima: Int,
    var vidaActual: Int
        )
