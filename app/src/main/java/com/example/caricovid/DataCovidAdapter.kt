package com.example.caricovid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataCovidAdapter(
    private val dataList: List<DataCovid>,
    private val onItemClick: (DataCovid) -> Unit
) : RecyclerView.Adapter<DataCovidAdapter.DataCovidViewHolder>() {

    inner class DataCovidViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvAlamat: TextView = itemView.findViewById(R.id.tvAlamat)
        val tvUsia: TextView = itemView.findViewById(R.id.tvUsia)
        val tvJenisKelamin: TextView = itemView.findViewById(R.id.tvJenisKelamin)
        val tvGejala: TextView = itemView.findViewById(R.id.tvGejala)
        val tvSuhuTubuh: TextView = itemView.findViewById(R.id.tvSuhuTubuh)
        val tvTanggalPemeriksaan: TextView = itemView.findViewById(R.id.tvTanggalPemeriksaan)
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataCovidViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_data_covid, parent, false)
        return DataCovidViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataCovidViewHolder, position: Int) {
        val data = dataList[position]
        holder.tvNama.text = data.nama
        holder.tvAlamat.text = data.alamat
        holder.tvUsia.text = data.usia.toString()
        holder.tvJenisKelamin.text = data.jenisKelamin
        holder.tvGejala.text = data.gejala
        holder.tvSuhuTubuh.text = data.suhuTubuh.toString()
        holder.tvTanggalPemeriksaan.text = data.tanggalPemeriksaan
        holder.tvStatus.text = data.status

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("DATA_COVID", data)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = dataList.size
}