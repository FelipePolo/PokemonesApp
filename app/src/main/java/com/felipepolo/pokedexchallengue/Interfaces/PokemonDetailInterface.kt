package com.felipepolo.pokedexchallengue.Interfaces

import android.content.Context
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.felipepolo.pokedexchallengue.PokeApi.PokeStruct

interface PokemonDetailInterface {

    interface  View {
        fun showTitle(title:String)
        fun showImagePokemon(url:String)
        fun showNamePokemon(name:String)
        fun showExperiencePokemon(xp:Int)
        fun showTypesPokemon(types:ArrayList<String>)
        fun showPokedexNumberPokemon(number:Int)
        fun showWeightAndHeightPokemon(w:Int, h:Int)
        fun showMoves(moves:ArrayList<String>)
        fun showDefaultLikeAnimation(animation:Int)
        fun showToast(msj:String)
    }

    interface Presenter{
        fun getPokemonByName(name:String)
        fun getPokemonByNameReady(pokemon:PokeStruct.Pokemon)
        fun setLikeAction(view: LottieAnimationView)
    }

    interface Model {
        fun getPokemonByName(context: Context,name: String)
        fun newLocalPokemon(context: Context,name: String)
        fun existLocalPokemon(context: Context,name: String): Boolean
        fun removeLocalPokemon(context: Context,name: String)
    }
}