package com.example.app.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    @SerialName("id") val id: Int,
    @SerialName("name") val name: String,
    @SerialName("abilities") val abilities: List<Ability>,
    @SerialName("base_experience") val baseExperience: Int,
    @SerialName("forms") val forms: List<Value>,
    @SerialName("game_indices") val gameIndices: List<GameIndex>,
    @SerialName("height") val height: Int,
    @SerialName("is_default") val isDefault: Boolean,
    @SerialName("location_area_encounters") val locationAreaEncounters: String,
    // hold item ?
    @SerialName("moves") val moves: List<Move>,
    @SerialName("order") val order: Int,
    // past_abilities ?
    // past_types ?
    @SerialName("species") val species: Value,
    @SerialName("sprites") val sprites: Sprites,
    @SerialName("stats") val stats: List<Stat>,
    @SerialName("types") val types: List<Type>,
    @SerialName("weight") val weight: Int
)

@Serializable
data class Value(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)

@Serializable
data class Ability(
    @SerialName("ability") val ability: Value,
    @SerialName("is_hidden") val isHidden: Boolean,
    @SerialName("slot") val slot: Int
)

@Serializable
data class GameIndex(
    @SerialName("game_index") val gameIndex: Int,
    @SerialName("version") val version: Value
)

@Serializable
data class Move(
    @SerialName("move") val move: Value,
    @SerialName("version_group_details") val versionGroupDetails: List<VersionGroupDetail>
)

@Serializable
data class VersionGroupDetail(
    @SerialName("level_learned_at") val levelLearnedAt: Int,
    @SerialName("move_learn_method") val moveLearnMethod: Value,
    @SerialName("version_group") val versionGroup: Value
)

@Serializable
data class Sprites(
    @SerialName("back_default") val backDefault: String?,
    @SerialName("back_female") val backFemale: String?,
    @SerialName("back_shiny") val backShiny: String?,
    @SerialName("back_shiny_female") val backShinyFemale: String?,
    @SerialName("front_default") val frontDefault: String?,
    @SerialName("front_female") val frontFemale: String?,
    @SerialName("front_shiny") val frontShiny: String?,
    @SerialName("front_shiny_female") val frontShinyFemale: String?,
    // Other
    // versions
)

@Serializable
data class Stat(
    @SerialName("base_stat") val baseStat: Int,
    @SerialName("effort") val effort: Int,
    @SerialName("stat") val stat: Value
)

@Serializable
data class Type(
    @SerialName("slot") val slot: Int,
    @SerialName("type") val type: Value
)
