package com.example.valotrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView // Import ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import coil.load // Import the Coil 'load' function
import com.example.valotrack.api.RetrofitInstance
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find the new ImageView
        val rankImageView = findViewById<ImageView>(R.id.ivRankImage)

        val regionSpinner = findViewById<Spinner>(R.id.spinnerRegion)
        val playerNameEditText = findViewById<EditText>(R.id.etPlayerName)
        val tagEditText = findViewById<EditText>(R.id.etTag)
        val searchButton = findViewById<Button>(R.id.btnSearch)
        val resultTextView = findViewById<TextView>(R.id.tvResult)

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
                    try {
                        val response = RetrofitInstance.api.getPlayerMmr(region, playerName, tag)
                        val rank = response.data?.currentTierPatched ?: "Unranked"
                        val rr = response.data?.rankingInTier ?: 0

                        // Get the image URL
                        val rankImageUrl = response.data?.images?.large

                        // Use Coil to load the image
                        if (rankImageUrl != null) {
                            rankImageView.load(rankImageUrl) {
                                crossfade(true) // Optional: for a fade-in effect
                                placeholder(R.drawable.ic_launcher_background) // Optional: shows while loading
                                error(R.drawable.ic_launcher_foreground) // Optional: shows if image fails to load
                            }
                        } else {
                            // If there's no image URL (e.g., unranked), clear the ImageView
                            rankImageView.setImageResource(android.R.color.transparent)
                        }

                        resultTextView.text = "Rank: $rank\nRR: $rr"

                    } catch (e: Exception) {
                        resultTextView.text = "Error: ${e.message}"
                        rankImageView.setImageResource(android.R.color.transparent) // Clear image on error too
                        Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter player name and tag", Toast.LENGTH_SHORT).show()
            }
        }
    }
}