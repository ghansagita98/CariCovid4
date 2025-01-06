package com.example.caricovid

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "covid_database"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "data_covid"
        private const val ID = "id"
        private const val NAMA = "nama"
        private const val ALAMAT = "alamat"
        private const val USIA = "usia"
        private const val JENIS_KELAMIN = "jenis_kelamin"
        private const val GEJALA = "gejala"
        private const val SUHU_TUBUH = "suhu_tubuh"
        private const val TANGGAL_PEMERIKSAAN = "tanggal_pemeriksaan"
        private const val STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$NAMA TEXT," +
                "$ALAMAT TEXT," +
                "$USIA INTEGER," +
                "$JENIS_KELAMIN TEXT," +
                "$GEJALA TEXT," +
                "$SUHU_TUBUH REAL," +
                "$TANGGAL_PEMERIKSAAN TEXT," +
                "$STATUS TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun tambahData(data: DataCovid): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NAMA, data.nama)
            put(ALAMAT, data.alamat)
            put(USIA, data.usia)
            put(JENIS_KELAMIN, data.jenisKelamin)
            put(GEJALA, data.gejala)
            put(SUHU_TUBUH, data.suhuTubuh)
            put(TANGGAL_PEMERIKSAAN, data.tanggalPemeriksaan)
            put(STATUS, data.status)
        }
        return try {
            db.insert(TABLE_NAME, null, values)
        } catch (e: Exception) {
            -1 // Return -1 to indicate an error
        }
    }

    fun bacaSemuaData(): List<DataCovid> {
        val dataCovidList = mutableListOf<DataCovid>()
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        db.rawQuery(selectQuery, null).use { cursor ->
            if (cursor.moveToFirst()) {
                do {
                    val dataCovid = cursorToDataCovid(cursor)
                    dataCovidList.add(dataCovid)
                } while (cursor.moveToNext())
            }
        }
        return dataCovidList
    }

    fun bacaData(id: Int): DataCovid? {
        val db = this.readableDatabase
        val selectQuery = "SELECT * FROM $TABLE_NAME WHERE $ID = ?"
        db.rawQuery(selectQuery, arrayOf(id.toString())).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursorToDataCovid(cursor)
            }
        }
        return null
    }

    fun updateData(data: DataCovid): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NAMA, data.nama)
            put(ALAMAT, data.alamat)
            put(USIA, data.usia)
            put(JENIS_KELAMIN, data.jenisKelamin)
            put(GEJALA, data.gejala)
            put(SUHU_TUBUH, data.suhuTubuh)
            put(TANGGAL_PEMERIKSAAN, data.tanggalPemeriksaan)
            put(STATUS, data.status)
        }
        return db.update(TABLE_NAME, values, "$ID=?", arrayOf(data.id.toString()))
    }

    fun hapusData(id: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString()))
    }

    private fun cursorToDataCovid(cursor: Cursor): DataCovid {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
        val nama = cursor.getString(cursor.getColumnIndexOrThrow(NAMA))
        val alamat = cursor.getString(cursor.getColumnIndexOrThrow(ALAMAT))
        val usia = cursor.getInt(cursor.getColumnIndexOrThrow(USIA))
        val jenisKelamin = cursor.getString(cursor.getColumnIndexOrThrow(JENIS_KELAMIN))
        val gejala = cursor.getString(cursor.getColumnIndexOrThrow(GEJALA))
        val suhuTubuh = cursor.getDouble(cursor.getColumnIndexOrThrow(SUHU_TUBUH))
        val tanggalPemeriksaan = cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL_PEMERIKSAAN))
        val status = cursor.getString(cursor.getColumnIndexOrThrow(STATUS))
        return DataCovid(id, nama, alamat, usia, jenisKelamin, gejala, suhuTubuh, tanggalPemeriksaan, status)
    }
}