package com.example.caricovid

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caricovid.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var dataCovid: DataCovid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataCovid = intent.getParcelableExtra("DATA_COVID")!!

        if (dataCovid != null) {
            Log.d("DetailActivity", "Data received: $dataCovid")
            binding.tvNamaDetail.text = dataCovid.nama
            binding.tvAlamatDetail.text = dataCovid.alamat
            binding.tvUsiaDetail.text = dataCovid.usia.toString()
            binding.tvJenisKelaminDetail.text = dataCovid.jenisKelamin
            binding.tvGejalaDetail.text = dataCovid.gejala
            binding.tvSuhuTubuhDetail.text = dataCovid.suhuTubuh.toString()
            binding.tvTanggalPemeriksaanDetail.text = dataCovid.tanggalPemeriksaan
            binding.tvStatusDetail.text = dataCovid.status

            Toast.makeText(this, "Data pasien berhasil ditampilkan!", Toast.LENGTH_SHORT).show()
        } else {
            Log.e("DetailActivity", "DataCovid is null")
            Toast.makeText(this, "Gagal menampilkan data pasien!", Toast.LENGTH_SHORT).show()
            finish() // Tutup aktivitas jika data null
        }

        binding.btnEdit.setOnClickListener {
            val intent = Intent(this, EditDataActivity::class.java)
            intent.putExtra("DATA_COVID", dataCovid)
            startActivity(intent)
        }

        binding.btnHapus.setOnClickListener {
            hapusData()
        }
    }

    private fun hapusData() {
        val dbHelper = DatabaseHelper(this)
        val result = dbHelper.hapusData(dataCovid.id)
        if (result > 0) {
            Toast.makeText(this, "Data berhasil dihapus!", Toast.LENGTH_SHORT).show()
            finish() // Kembali ke halaman utama setelah menghapus data
        } else {
            Toast.makeText(this, "Gagal menghapus data!", Toast.LENGTH_SHORT).show()
        }
    }
}