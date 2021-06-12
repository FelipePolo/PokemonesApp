package com.felipepolo.pokedexchallengue.Sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context): SQLiteOpenHelper(context,
    PokemonsContract.Companion.Entrada.TABLE_NAME,null,
    PokemonsContract.Companion.Entrada.VERSION
) {

    companion object {
        val CREATE_POKEMONS_TABLE = "CREATE TABLE "+ PokemonsContract.Companion.Entrada.TABLE_NAME + " (" + PokemonsContract.Companion.Entrada.COLUMNA_NAME +" TEXT PRIMARY KEY )"
        val REMOVE_POKEMONS_TABLE = "DROP TABLE IF EXISTS" + PokemonsContract.Companion.Entrada.TABLE_NAME
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(CREATE_POKEMONS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(REMOVE_POKEMONS_TABLE)
        onCreate(db)
    }

}