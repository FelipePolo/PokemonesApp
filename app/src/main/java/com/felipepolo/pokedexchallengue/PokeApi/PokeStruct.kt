package com.felipepolo.pokedexchallengue.PokeApi

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PokeStruct {

    class Generation{
        var id:Int? = null
        var pokemon_species: ArrayList<PokemonNames>? = null
    }

    class PokemonNames {
        var name:String? = null
    }

    class Pokemon: Serializable{
        var id:Int? = null
        var base_experience:Int? = null
        var height:Int? = null
        var weight:Int? = null
        var moves: ArrayList<Moveindex>? = null
        var name:String? = null
        var sprites: Sprites? = null
        var types: ArrayList<Type>? = null
        var stats: ArrayList<StatsItems>? = null
    }

    class Moveindex: Serializable {
        var move: Move? = null
    }

    class Move: Serializable {
        var name: String? = null
    }

    class Sprites: Serializable {
        var front_default:String? = null

        var other: Other? = null
        var versions: Versions? = null
    }

    class Other: Serializable {
        @SerializedName("official-artwork")
        var official_artwork: OfficialArtwork? = null
    }
    class OfficialArtwork: Serializable {
        var front_default: String? = null
    }

    class Versions: Serializable {
        @SerializedName("generation-i")
        var generation1: Generationgame? = null
        @SerializedName("generation-ii")
        var generation2: Generationgame? = null
        @SerializedName("generation-iii")
        var generation3: Generationgame? = null
        @SerializedName("generation-iv")
        var generation4: Generationgame? = null
        @SerializedName("generation-v")
        var generation5: Generationgame? = null
    }

    class Generationgame: Serializable {
        @SerializedName("red-blue")
        var redblue: Spritegames? = null
        var yellow:Spritegames? = null
        var crystal:Spritegames? = null
        var gold:Spritegames? = null
        var silver:Spritegames? = null
        var emerald:Spritegames? = null
    }

    class Spritegames: Serializable {
        var back_default:String? = null
        var front_gray:String? = null
    }

    class Type:Serializable {
        var type: Type? = null
        var name:String ? = null
    }

    class StatsItems: Serializable {
        var base_stat: Int? = null
        var stat: Stat? = null
    }

    class Stat: Serializable {
        var name:String? = null
    }
}