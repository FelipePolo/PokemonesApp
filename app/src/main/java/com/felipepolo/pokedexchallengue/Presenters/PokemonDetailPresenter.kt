package com.felipepolo.pokedexchallengue.Presenters

import android.content.Context
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.felipepolo.pokedexchallengue.Interfaces.PokemonDetailInterface
import com.felipepolo.pokedexchallengue.Interfaces.PokemonListInterface
import com.felipepolo.pokedexchallengue.Models.PokemonDetailModel
import com.felipepolo.pokedexchallengue.PokeApi.PokeStruct
import com.felipepolo.pokedexchallengue.R

class PokemonDetailPresenter(view: PokemonDetailInterface.View): PokemonDetailInterface.Presenter {

    var context: Context? = null
    var currentPokemonName: String? = null
    var userLikeThisPokemon = false

    var view: PokemonDetailInterface.View? = null
    var model: PokemonDetailInterface.Model? = null

    init {
        this.context = view as Context
        this.view = view
        this.model = PokemonDetailModel(this)
    }

    override fun getPokemonByName(name: String) {
        this.model!!.getPokemonByName(this.context!!,name)
    }

    override fun getPokemonByNameReady(pokemon: PokeStruct.Pokemon) {
        val view = this.view!!
        this.currentPokemonName = pokemon.name!!

        view.showTitle(pokemon.name!!)
        view.showNamePokemon(pokemon.name!!)
        view.showImagePokemon(pokemon.sprites!!.other!!.official_artwork!!.front_default!!)
        view.showPokedexNumberPokemon(pokemon.id!!)
        view.showExperiencePokemon(pokemon.base_experience!!)
        view.showTypesPokemon(getTypesNames(pokemon.types!!))
        view.showWeightAndHeightPokemon(pokemon.weight!!,pokemon.weight!!)
        view.showMoves(getMovesNames(pokemon.moves!!))

        if(model!!.existLocalPokemon(context!!,this.currentPokemonName!!)){
            this.userLikeThisPokemon = true
            view.showDefaultLikeAnimation(R.raw.like_animation)
        }

    }

    fun getMovesNames(moves: ArrayList<PokeStruct.Moveindex>):ArrayList<String>{
        val movesNames = ArrayList<String>()
        for(move in moves){
            movesNames.add(move.move!!.name!!)
        }
        return movesNames
    }

    fun getTypesNames(types: ArrayList<PokeStruct.Type>):ArrayList<String>{
        val typesNames = ArrayList<String>()
        for(type in types){
            typesNames.add(type.type!!.name!!)
        }
        return typesNames
    }

    override fun setLikeAction(view: LottieAnimationView) {
        userLikeThisPokemon = makeAnimationLikeButton(view,userLikeThisPokemon)

        if(userLikeThisPokemon) {
            model!!.newLocalPokemon(context!!,this.currentPokemonName!!)
            this.view!!.showToast("${this.currentPokemonName} added to your favorite list")
        }else{
            model!!.removeLocalPokemon(context!!,this.currentPokemonName!!)
            this.view!!.showToast("${this.currentPokemonName} removed to your favorite list")
        }
    }

    fun makeAnimationLikeButton(imageview: LottieAnimationView, state:Boolean):Boolean {
        if(!state){
            imageview.setAnimation(R.raw.like_animation)
        }else{
            imageview.setAnimation(R.raw.dislike_animation)
        }
        imageview.playAnimation()
        return !state
    }
}