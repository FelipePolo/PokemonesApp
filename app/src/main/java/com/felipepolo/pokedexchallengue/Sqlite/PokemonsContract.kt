package com.felipepolo.pokedexchallengue.Sqlite

import android.provider.BaseColumns

class PokemonsContract {

    companion object {
        class  Entrada: BaseColumns {
            companion object {
                val TABLE_NAME = "myFavPokemons"
                val VERSION = 1

                val COLUMNA_NAME = "pokemons"
            }
        }
    }
}