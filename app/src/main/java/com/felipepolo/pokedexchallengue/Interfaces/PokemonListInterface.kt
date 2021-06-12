package com.felipepolo.pokedexchallengue.Interfaces

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import com.felipepolo.pokedexchallengue.PokeApi.PokeStruct

interface PokemonListInterface {

    interface View{
        fun setTabViewpager(viewPager: ViewPager)
        fun showToast(msj:String)
        interface FragmentListPokemon {
            fun showPokemons(pokemons: ArrayList<PokeStruct.Pokemon>)
            fun limitpokemonListReached()
            fun resetList()
        }
    }

    interface Presenter{
        fun setTablayout(viewPager: ViewPager, fragmentManager: FragmentManager)
        fun requestPokemonNameList(generationId:Int)
        fun requestPokemonNameListReady(PokemonNameArray:ArrayList<PokeStruct.PokemonNames>)
        fun getNewPokemonsByGeneration(generationId: Int)
        fun getPokemons(size:Int)
        fun getPokemonsReady(pokemons:ArrayList<PokeStruct.Pokemon>?)
    }

    interface Model{
        fun getPokemonNameList(context: Context, generationId:Int)
        fun getPokemonsByArray(context: Context,nameArray:ArrayList<String>)
        fun getAllLocalPokemons(context: Context): ArrayList<String>?
    }
}