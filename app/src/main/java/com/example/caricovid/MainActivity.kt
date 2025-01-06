package com.example.caricovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.caricovid.databinding.ActivityMainBinding
import androidx.recyclerview.widget.DividerItemDecoration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var dataCovidAdapter: DataCovidAdapter

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
            val dataCovidList = withContext(Dispatchers.IO) {
                databaseHelper.bacaSemuaData()
            }
            updateUI(dataCovidList)
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
}