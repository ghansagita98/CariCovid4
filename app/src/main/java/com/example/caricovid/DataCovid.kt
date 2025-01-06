package com.example.caricovid

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataCovid(
    val id: Int,
    val nama: String,
    val alamat: String,
    val usia: Int,
    val jenisKelamin: String,
    val gejala: String,
    val suhuTubuh: Double,
    val tanggalPemeriksaan: String,
    val status: String
) : Parcelable