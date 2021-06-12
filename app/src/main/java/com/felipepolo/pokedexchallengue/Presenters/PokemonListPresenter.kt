package com.felipepolo.pokedexchallengue.Presenters

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.felipepolo.pokedexchallengue.Adapters.AdapterViewPager
import com.felipepolo.pokedexchallengue.Interfaces.PokemonListInterface
import com.felipepolo.pokedexchallengue.Models.PokemonListModel
import com.felipepolo.pokedexchallengue.PokeApi.PokeStruct
import com.felipepolo.pokedexchallengue.Views.Fragments.FragmentPokemonListView

class PokemonListPresenter: PokemonListInterface.Presenter {

    var context: Context? = null
    var initialsize:Int? = null

    var view:PokemonListInterface.View? = null
    var fragmentView: PokemonListInterface.View.FragmentListPokemon? = null
    var model:PokemonListInterface.Model? = null

    var operationInProcess: Boolean = false

    var pokemonsNameList: ArrayList<PokeStruct.PokemonNames>? = null
    var currentIndexPokemon:Int = 0
    var generationId:Int? = null

    init {
        this.model = PokemonListModel(this)
    }

    constructor(view: PokemonListInterface.View){
        this.context = view as Context
        this.view = view
    }

    constructor(fragmentView: PokemonListInterface.View.FragmentListPokemon, context: Context){
        this.fragmentView = fragmentView
        this.context = context
    }


    override fun setTablayout(viewPager: ViewPager,fragmentManager: FragmentManager) {
        val adaptador = AdapterViewPager(fragmentManager)
        var fragmentoprincipal = FragmentPokemonListView();

        for(item in 1..6){

            val bundle = Bundle()
            bundle.putInt("generation",item)

            fragmentoprincipal.arguments = bundle

            if(item == 6){
                adaptador.addFragment(fragmentoprincipal,"My Fav")
            }else{
                adaptador.addFragment(fragmentoprincipal,"Gen $item")
            }

            fragmentoprincipal = FragmentPokemonListView()
        }
        viewPager.adapter = adaptador
        view!!.setTabViewpager(viewPager)
    }


    override fun requestPokemonNameList(generationId:Int) {
        model!!.getPokemonNameList(context!!,generationId)
    }

    override fun requestPokemonNameListReady(PokemonNameArray: ArrayList<PokeStruct.PokemonNames>) {
        this.pokemonsNameList = PokemonNameArray
        this.getNewPokemonsByGeneration(this.generationId!!)
    }

    override fun getPokemons(size:Int) {
        Log.d("index","tamaño a pedir =" + size.toString())
        if(!operationInProcess){
            var namesPokemon = ArrayList<String>()
            Log.d("index","pokemon actual = " + this.currentIndexPokemon.toString())
            if(size + currentIndexPokemon < this.pokemonsNameList!!.size){
                namesPokemon = getPokemonNames(size)
                Log.d("index","tamaño de namespokemon en if = " + namesPokemon.size.toString())
                this.currentIndexPokemon += size
                model!!.getPokemonsByArray(this.context!!,namesPokemon)
            }else{
                namesPokemon = getPokemonNames(this.pokemonsNameList!!.size - currentIndexPokemon)
                this.currentIndexPokemon += this.pokemonsNameList!!.size - currentIndexPokemon
                Log.d("index","tamaño de namespokemon en else = " + namesPokemon.size.toString())
                if(namesPokemon.size > 0){model!!.getPokemonsByArray(this.context!!,namesPokemon)}else{this.getPokemonsReady(null)}
            }
            this.operationInProcess = true
        }
    }

    override fun getPokemonsReady(pokemons: ArrayList<PokeStruct.Pokemon>?) {
        Log.d("index","se obtuvieron los pokemones")
        this.operationInProcess = false
        if(pokemons!= null){fragmentView!!.showPokemons(pokemons)}else{fragmentView!!.limitpokemonListReached()}
    }

    fun getPokemonNames(limit:Int):ArrayList<String>{
        val names = ArrayList<String>()
        if(limit > 0){
            Log.d("index", "index$currentIndexPokemon size$limit")
            if(currentIndexPokemon+limit <= this.pokemonsNameList!!.size){
                for(item in currentIndexPokemon until currentIndexPokemon+limit){
                    names.add(this.pokemonsNameList!!.get(item).name.toString())
                }
            }else{
                return names
            }
        }
        return names
    }

    override fun getNewPokemonsByGeneration(generationId: Int) {
        Log.d("index", "generation =" + generationId.toString())
        if(generationId != 6){
            if (this.generationId == null || this.generationId != generationId){
                this.generationId = generationId
                this.pokemonsNameList = null
                this.currentIndexPokemon = 0
                this.requestPokemonNameList(generationId)
            }else{
                this.getPokemons(initialsize?: 10)
            }
        }else{
            var pokemonsnames = model!!.getAllLocalPokemons(context!!)
            if(pokemonsnames != null){
                this.pokemonsNameList = ArrayList()
                this.currentIndexPokemon = 0
                for(name in pokemonsnames){
                    val pokemon = PokeStruct.PokemonNames()
                    pokemon.name = name
                    this.pokemonsNameList?.add(pokemon)
                }
                this.getPokemons(initialsize?: 10)
            }else{
                fragmentView!!.limitpokemonListReached()
            }
        }
    }

}