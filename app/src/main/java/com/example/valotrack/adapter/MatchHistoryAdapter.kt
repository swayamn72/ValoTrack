package com.example.valotrack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.valotrack.R
import com.example.valotrack.data.MatchData

class MatchHistoryAdapter(private val matches: List<MatchData>, private val playerName: String, private val playerTag: String) :
    RecyclerView.Adapter<MatchHistoryAdapter.MatchViewHolder>() {

    // This class holds the views for a single row
    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mapTextView: TextView = itemView.findViewById(R.id.tvMap)
        val gameModeTextView: TextView = itemView.findViewById(R.id.tvGameMode)
        val kdaTextView: TextView = itemView.findViewById(R.id.tvKda)
    }

    // This function is called when a new row needs to be created
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        // We use our item_match_history.xml blueprint to create the view
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match_history, parent, false)
        return MatchViewHolder(view)
    }

    // This function returns the total number of items in our list
    override fun getItemCount(): Int {
        return matches.size
    }

    // This function is called to bind the data to a specific row
    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matches[position]

        // Set map and game mode
        holder.mapTextView.text = match.metadata.map ?: "Unknown Map"
        holder.gameModeTextView.text = match.metadata.game_mode ?: "Unknown Mode"

        // Find the searched player in the list of all players for that match
        val searchedPlayer = match.players.all_players.find {
            it.name?.equals(playerName, ignoreCase = true) == true &&
                    it.tag?.equals(playerTag, ignoreCase = true) == true
        }

        // Set the KDA text if the player is found
        if (searchedPlayer?.stats != null) {
            val stats = searchedPlayer.stats
            holder.kdaTextView.text = "${stats.kills} / ${stats.deaths} / ${stats.assists}"
        } else {
            holder.kdaTextView.text = "- / - / -"
        }
    }
}