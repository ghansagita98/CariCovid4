package com.example.caricovid

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.caricovid.databinding.ActivityEditDataBinding

class EditDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDataBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var dataCovid: DataCovid

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbHelper = DatabaseHelper(this)

        dataCovid = intent.getParcelableExtra("DATA_COVID")!!

        binding.etNama.setText(dataCovid.nama)
        binding.etAlamat.setText(dataCovid.alamat)
        binding.etUsia.setText(dataCovid.usia.toString())
        binding.etJenisKelamin.setText(dataCovid.jenisKelamin)
        binding.etGejala.setText(dataCovid.gejala)
        binding.etSuhuTubuh.setText(dataCovid.suhuTubuh.toString())
        binding.etTanggalPemeriksaan.setText(dataCovid.tanggalPemeriksaan)
        binding.etStatus.setText(dataCovid.status)

        binding.btnUpdate.setOnClickListener {
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

            val updatedDataCovid = dataCovid.copy(
                nama = nama,
                alamat = alamat,
                usia = usia,
                jenisKelamin = jenisKelamin,
                gejala = gejala,
                suhuTubuh = suhuTubuh,
                tanggalPemeriksaan = tanggalPemeriksaan,
                status = status
            )

            val result = dbHelper.updateData(updatedDataCovid)
            if (result > 0) {
                Toast.makeText(this, "Data berhasil diperbarui!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman detail
            } else {
                Toast.makeText(this, "Gagal memperbarui data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}