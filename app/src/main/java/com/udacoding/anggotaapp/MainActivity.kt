package com.udacoding.anggotaapp

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import com.udacoding.anggotaapp.Adapter.AnggotaAdapter
import com.udacoding.anggotaapp.Config.NetworkModule
import com.udacoding.anggotaapp.Model.action.ResponseAction
import com.udacoding.anggotaapp.Model.getdata.DataItem
import com.udacoding.anggotaapp.Model.getdata.ResponseGetData
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        fab.setOnClickListener {
            val intent = Intent(this, InputActivity::class.java)
            startActivity(intent)
        }

        showData()

    }

    private fun showData(){

            val listAnggota = NetworkModule.service().getData()
            listAnggota.enqueue(object : Callback<ResponseGetData> {
                override fun onResponse(
                    call: Call<ResponseGetData>,
                    response: Response<ResponseGetData>
                ) {

                    Log.d("Data response",response.message())
                    if (response.isSuccessful) {
                        val item = response.body()?.data
                        val adapter = AnggotaAdapter(item, object : AnggotaAdapter.OnClickListener {
                            override fun detail(item: DataItem?) {
                                val intent = Intent(applicationContext, InputActivity::class.java)
                                intent.putExtra("data", item)
                                startActivity(intent)
                            }

                            override fun hapus(item: DataItem?) {

                                AlertDialog.Builder(this@MainActivity).apply {
                                    setTitle("Hapus data..?")
                                    setMessage("Yakin data akan dihapus..?")
                                    setPositiveButton("Hapus"){ dialog, which ->
                                        hapusData(item?.id)
                                        //Toast.makeText(applicationContext,"Tekan tombol hapus",Toast.LENGTH_SHORT)
                                        dialog.dismiss()
                                    }

                                    setNegativeButton("Batal"){ dialog, which ->
                                        dialog.dismiss()
                                    }
                                }.show()

                                //hapusData(item?.id)

                            }
                        })

                        list.adapter = adapter
                    }
                }

                override fun onFailure(call: Call<ResponseGetData>, t: Throwable) {
                    Log.d("Error Connection", t.message)
                }
            })


    }

    private fun hapusData(id: String?) {
        val hapus = NetworkModule.service().deleteData(id ?: "")
        hapus.enqueue(object : Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext,"Data sukses dihapus",Toast.LENGTH_SHORT)
                showData()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        showData()
    }

    fun isConnect() : Boolean{
        val connect : ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connect.activeNetworkInfo != null && connect.activeNetworkInfo.isConnected
    }
}