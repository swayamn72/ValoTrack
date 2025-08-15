package com.example.valotrack.api

import com.example.valotrack.data.PlayerMmrResponse
import com.example.valotrack.data.MatchHistoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ValorantApiService {

    @GET("valorant/v1/mmr/{region}/{name}/{tag}")
    suspend fun getPlayerMmr(
        @Path("region") region: String,
        @Path("name") name: String,
        @Path("tag") tag: String
    ): PlayerMmrResponse

    @GET("valorant/v3/matches/{region}/{name}/{tag}")
    suspend fun getMatchHistory(
        @Path("region") region: String,
        @Path("name") name: String,
        @Path("tag") tag: String
    ): MatchHistoryResponse
}