package com.example.valotrack.data

import com.google.gson.annotations.SerializedName

// The top-level response, which contains a list of matches
data class MatchHistoryResponse(
    @SerializedName("data") val data: List<MatchData>
)

// Represents a single match in the list
data class MatchData(
    @SerializedName("metadata") val metadata: MatchMetadata,
    @SerializedName("players") val players: MatchPlayers
)

// Holds the map and game mode
data class MatchMetadata(
    @SerializedName("map") val map: String?,
    @SerializedName("mode") val game_mode: String?
)

// Holds the list of all players in the match
data class MatchPlayers(
    @SerializedName("all_players") val all_players: List<Player>
)

// Represents a single player in a match
data class Player(
    @SerializedName("name") val name: String?,
    @SerializedName("tag") val tag: String?,
    @SerializedName("stats") val stats: PlayerStats?
)

// Holds the Kills, Deaths, and Assists for a player
data class PlayerStats(
    @SerializedName("kills") val kills: Int,
    @SerializedName("deaths") val deaths: Int,
    @SerializedName("assists") val assists: Int
)