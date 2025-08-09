package com.example.valotrack.data

import com.google.gson.annotations.SerializedName

// This class remains the same
data class PlayerMmrResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: MmrData?
)

// We move the rank properties directly into this class
data class MmrData(
    @SerializedName("name") val name: String,
    @SerializedName("tag") val tag: String,
    @SerializedName("currenttierpatched") val currentTierPatched: String?, // Make nullable for unranked
    @SerializedName("ranking_in_tier") val rankingInTier: Int? // Make nullable for unranked
)