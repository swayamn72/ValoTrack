package com.example.valotrack.api

import com.example.valotrack.data.PlayerMmrResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ValorantApiService {

    @GET("valorant/v1/mmr/{region}/{name}/{tag}")
    suspend fun getPlayerMmr(
        @Path("region") region: String,
        @Path("name") name: String,
        @Path("tag") tag: String
    ): PlayerMmrResponse
}