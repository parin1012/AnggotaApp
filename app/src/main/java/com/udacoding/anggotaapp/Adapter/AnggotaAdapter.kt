package com.udacoding.anggotaapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.udacoding.anggotaapp.Model.getdata.DataItem
import com.udacoding.anggotaapp.R
import kotlinx.android.synthetic.main.item_anggota.view.*

class AnggotaAdapter(val data: List<DataItem>?, val itemClick : OnClickListener) : RecyclerView.Adapter<AnggotaAdapter.AnggotaHolder>() {


    class AnggotaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tnama = itemView.tvNama
        val tnobp = itemView.tvNohp
        val talamat = itemView.tvAlamat
        val btnHapus = itemView.btnHapus
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnggotaHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anggota,parent,false)
        val holder = AnggotaHolder(view)
        return holder
    }

    override fun getItemCount(): Int {
        return data?.size ?:0
    }

    override fun onBindViewHolder(holder: AnggotaHolder, position: Int) {
        val item = data?.get(position)
        holder.tnama.text = data?.get(position)?.nama
        holder.tnobp.text = data?.get(position)?.nohp
        holder.talamat.text = data?.get(position)?.alamat

        holder.itemView.setOnClickListener {
            itemClick.detail(item)
        }

        holder.btnHapus.setOnClickListener {
            itemClick.hapus(item)
        }
    }

    interface OnClickListener{
        fun detail(item: DataItem?)
        fun hapus(item: DataItem?)
    }
}