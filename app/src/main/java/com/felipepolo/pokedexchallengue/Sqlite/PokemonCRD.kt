package com.felipepolo.pokedexchallengue.Sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.util.Log

class PokemonCRD(context: Context) {

    private var helper: DatabaseHelper? = null

    init {
        helper = DatabaseHelper(context)
    }

    fun newPokemon(item: Pokemon){

        val db = helper?.writableDatabase

        val values = ContentValues()
        values.put(PokemonsContract.Companion.Entrada.COLUMNA_NAME, item.name)

        val row = db?.insert(PokemonsContract.Companion.Entrada.TABLE_NAME,null,values)
        db?.close()
    }

    fun getPokemons(): ArrayList<Pokemon>{
        val db = helper?.readableDatabase
        val items = java.util.ArrayList<Pokemon>()

        val columnas = arrayOf(
            PokemonsContract.Companion.Entrada.COLUMNA_NAME
        )

        val c:Cursor = db!!.query(
            PokemonsContract.Companion.Entrada.TABLE_NAME,
            columnas,
            null,
            null,
            null,
            null,
            null,
            null
        )

        while (c.moveToNext()){
            items.add(
                Pokemon(
                    c.getString(c.getColumnIndexOrThrow(PokemonsContract.Companion.Entrada.COLUMNA_NAME))
                )
            )
        }

        // cerrar db
        db.close()
        return items
    }

    fun getPokemon(name:String): Pokemon? {
        var item: Pokemon? = null

        val db = helper?.readableDatabase

        var columnas = arrayOf(
            PokemonsContract.Companion.Entrada.COLUMNA_NAME
        )

        var cursor:Cursor = db!!.query(
            PokemonsContract.Companion.Entrada.TABLE_NAME,
            columnas,
            "pokemons = ?",
            arrayOf(name),
            null,
            null,
            null,
            null
        )

        while (cursor.moveToNext()){
            item = Pokemon(
                cursor.getString(cursor.getColumnIndexOrThrow(PokemonsContract.Companion.Entrada.COLUMNA_NAME))
            )
        }
        db.close()

        return item
    }




    fun deletePokemon(pokemon: String){
        val db = helper?.writableDatabase
        db?.delete(PokemonsContract.Companion.Entrada.TABLE_NAME,"pokemons = ?", arrayOf(pokemon))
        db?.close()
    }
}