package com.example.valotrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.valotrack.api.RetrofitInstance
import kotlinx.coroutines.launch
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val regionSpinner = findViewById<Spinner>(R.id.spinnerRegion)
        val playerNameEditText = findViewById<EditText>(R.id.etPlayerName)
        val tagEditText = findViewById<EditText>(R.id.etTag)
        val searchButton = findViewById<Button>(R.id.btnSearch)
        val resultTextView = findViewById<TextView>(R.id.tvResult)

        // Create an adapter to populate the spinner with the regions array
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
            // BUG FIX 1: Removed the .replace(" ", "%20")
            val playerName = playerNameEditText.text.toString().trim()
            val tag = tagEditText.text.toString().trim()

            if (playerName.isNotEmpty() && tag.isNotEmpty()) {
                lifecycleScope.launch {
                    try {
                        val response = RetrofitInstance.api.getPlayerMmr(region, playerName, tag)

                        // BUG FIX 2: Access data from the new, flatter structure
                        val rank = response.data?.currentTierPatched ?: "Unranked"
                        val rr = response.data?.rankingInTier ?: 0

                        resultTextView.text = "Rank: $rank\nRR: $rr"

                    } catch (e: Exception) {
                        resultTextView.text = "Error: ${e.message}"
                        Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter player name and tag", Toast.LENGTH_SHORT).show()
            }
        }
    }
}