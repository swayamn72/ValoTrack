package com.example.valotrack.data

import com.google.gson.annotations.SerializedName

data class PlayerMmrResponse(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: MmrData?
)

// Add the 'images' property to this class
data class MmrData(
    @SerializedName("name") val name: String,
    @SerializedName("tag") val tag: String,
    @SerializedName("currenttierpatched") val currentTierPatched: String?,
    @SerializedName("ranking_in_tier") val rankingInTier: Int?,
    @SerializedName("images") val images: RankImages? // Add this line
)

// Create this new class for the nested 'images' object
data class RankImages(
    @SerializedName("small") val small: String?,
    @SerializedName("large") val large: String?
)