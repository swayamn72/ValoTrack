package com.example.valotrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.valotrack.adapter.MatchHistoryAdapter
import com.example.valotrack.api.RetrofitInstance
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find all UI elements
        val regionSpinner = findViewById<Spinner>(R.id.spinnerRegion)
        val playerNameEditText = findViewById<EditText>(R.id.etPlayerName)
        val tagEditText = findViewById<EditText>(R.id.etTag)
        val searchButton = findViewById<Button>(R.id.btnSearch)
        val loadingProgressBar = findViewById<ProgressBar>(R.id.loadingProgressBar)
        val rankImageView = findViewById<ImageView>(R.id.ivRankImage)
        val rankTextView = findViewById<TextView>(R.id.tvRankText)
        val rrProgressBar = findViewById<ProgressBar>(R.id.rrProgressBar)
        val rrTextView = findViewById<TextView>(R.id.tvRrText)
        val matchHistoryRecyclerView = findViewById<RecyclerView>(R.id.rvMatchHistory) // Find the RecyclerView

        // Set up the RecyclerView's LayoutManager. This tells it to arrange items in a vertical list.
        matchHistoryRecyclerView.layoutManager = LinearLayoutManager(this)

        // ArrayAdapter setup (no changes needed)
        ArrayAdapter.createFromResource(
            this,
            R.array.regions_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            regionSpinner.adapter = adapter
        }

        searchButton.setOnClickListener {
            val region = regionSpinner.selectedItem.toString().lowercase(Locale.ROOT)
            val playerName = playerNameEditText.text.toString().trim()
            val tag = tagEditText.text.toString().trim()

            if (playerName.isNotEmpty() && tag.isNotEmpty()) {
                lifecycleScope.launch {
                    // --- Start of search ---
                    // 1. Show loading circle and hide all previous results
                    loadingProgressBar.visibility = View.VISIBLE
                    rankImageView.visibility = View.GONE
                    rankTextView.visibility = View.GONE
                    rrProgressBar.visibility = View.GONE
                    rrTextView.visibility = View.GONE
                    matchHistoryRecyclerView.visibility = View.GONE // Hide the list too

                    try {
                        // --- First API Call: Get Rank ---
                        val rankResponse = RetrofitInstance.api.getPlayerMmr(region, playerName, tag)
                        val rank = rankResponse.data?.currentTierPatched ?: "Unranked"
                        val rr = rankResponse.data?.rankingInTier ?: 0
                        val rankImageUrl = rankResponse.data?.images?.large

                        // 2. Show the rank result views
                        rankImageView.visibility = View.VISIBLE
                        rankTextView.visibility = View.VISIBLE
                        rankTextView.text = rank

                        if (rank != "Unranked") {
                            rrProgressBar.visibility = View.VISIBLE
                            rrTextView.visibility = View.VISIBLE
                            rrTextView.text = "$rr / 100 RR"
                            rrProgressBar.progress = rr
                        }

                        if (rankImageUrl != null) {
                            rankImageView.load(rankImageUrl)
                        } else {
                            rankImageView.setImageResource(android.R.color.transparent)
                        }

                        // --- Second API Call: Get Match History ---
                        val matchHistoryResponse = RetrofitInstance.api.getMatchHistory(region, playerName, tag)
                        if (matchHistoryResponse.data.isNotEmpty()) {
                            matchHistoryRecyclerView.visibility = View.VISIBLE // Show the list
                            // Create and set the adapter with the match data
                            val adapter = MatchHistoryAdapter(matchHistoryResponse.data, playerName, tag)
                            matchHistoryRecyclerView.adapter = adapter
                        }

                    } catch (e: Exception) {
                        // On error, just show the error text
                        rankTextView.visibility = View.VISIBLE
                        rankTextView.text = "Error: ${e.message}"
                        Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    } finally {
                        // 3. This always runs: Hide the loading circle
                        loadingProgressBar.visibility = View.GONE
                    }
                }
            } else {
                Toast.makeText(this, "Please enter player name and tag", Toast.LENGTH_SHORT).show()
            }
        }
    }
}