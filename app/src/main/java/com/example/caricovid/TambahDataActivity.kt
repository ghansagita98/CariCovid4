package com.example.caricovid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caricovid.databinding.ActivityTambahDataBinding

class TambahDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTambahDataBinding
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DatabaseHelper(this)

        binding.btnSimpan.setOnClickListener {
            val nama = binding.etNama.text.toString()
            val alamat = binding.etAlamat.text.toString()
            val usia = binding.etUsia.text.toString().toIntOrNull() ?: 0
            val jenisKelamin = binding.etJenisKelamin.text.toString()
            val gejala = binding.etGejala.text.toString()
            val suhuTubuh = binding.etSuhuTubuh.text.toString().toDoubleOrNull() ?: 0.0
            val tanggalPemeriksaan = binding.etTanggalPemeriksaan.text.toString()
            val status = binding.etStatus.text.toString()

            if (nama.isEmpty() || alamat.isEmpty() || jenisKelamin.isEmpty() || gejala.isEmpty() || tanggalPemeriksaan.isEmpty() || status.isEmpty()) {
                Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val dataCovid = DataCovid(
                id = 0,
                nama = nama,
                alamat = alamat,
                usia = usia,
                jenisKelamin = jenisKelamin,
                gejala = gejala,
                suhuTubuh = suhuTubuh,
                tanggalPemeriksaan = tanggalPemeriksaan,
                status = status
            )

            val result = dbHelper.tambahData(dataCovid)
            if (result != -1L) {
                Toast.makeText(this, "Data berhasil disimpan!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman utama
            } else {
                Toast.makeText(this, "Gagal menyimpan data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}