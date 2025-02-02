package com.example.caricovid

import android.content.Intent
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caricovid.databinding.ActivityMainBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var dataCovidAdapter: DataCovidAdapter
    private var dataCovidList: List<DataCovid> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)
        loadData()

        binding.fab.setOnClickListener {
            val intent = Intent(this, TambahDataActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadData() {
        lifecycleScope.launch {
            dataCovidList = withContext(Dispatchers.IO) {
                databaseHelper.bacaSemuaData()
            }
            updateUI(dataCovidList)

            val mapFragment = supportFragmentManager.findFragmentById(
                R.id.map_fragment
            ) as? SupportMapFragment
            mapFragment?.getMapAsync { map ->
                if (dataCovidList.isNotEmpty()) {
                    addMarkers(map)
                }
            }

        }
    }

    private fun updateUI(dataCovidList: List<DataCovid>) {
        dataCovidAdapter = DataCovidAdapter(dataCovidList) { dataCovid ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("DATA_COVID", dataCovid)
            startActivity(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = dataCovidAdapter
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }
    private fun addMarkers(googleMap: GoogleMap) {
        val geocoder = Geocoder(this, Locale.getDefault()) // Initialize Geocoder

        if (!dataCovidList.isNullOrEmpty()){
            for (dataCovid in dataCovidList){
                try {
                    val alamatList =  geocoder.getFromLocationName(dataCovid.alamat , 1)

                    if (!alamatList.isNullOrEmpty()) {
                        val location = alamatList[0]
                        val latLng = LatLng(location.latitude, location.longitude) // Extract LatLng

                        googleMap.addMarker(
                            MarkerOptions()
                                .title(dataCovid.alamat)
                                .position(latLng)
                        )
                    } else {
                        println("City not found: ${dataCovid.alamat}") // Debugging
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}