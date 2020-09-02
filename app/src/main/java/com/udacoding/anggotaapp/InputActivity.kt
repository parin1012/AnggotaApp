package com.udacoding.anggotaapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import com.udacoding.anggotaapp.Config.NetworkModule
import com.udacoding.anggotaapp.Model.action.ResponseAction
import com.udacoding.anggotaapp.Model.getdata.DataItem
import kotlinx.android.synthetic.main.activity_input.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InputActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)


        val getData = intent.getParcelableExtra<DataItem>("data")
        if(getData != null){
            etNama.setText(getData.nama)
            etNohp.setText(getData.nohp)
            etAlamat.setText(getData.alamat)

            btn1.text ="Update"
        }


        when(btn1.text){
            "Update" -> {
                btn1.setOnClickListener {
                    updateData(getData?.id, etNama.text.toString(), etNohp.text.toString(), etAlamat.text.toString())
                }
            }else -> {
                btn1.setOnClickListener {
                    inputData(etNama.text.toString(), etNohp.text.toString(), etAlamat.text.toString())
                }
            }
        }



        btn2.setOnClickListener {
            finish()
        }
    }

    private fun inputData(nama : String? , nohp : String? , alamat : String?){
        val input = NetworkModule.service().insertData(nama ?: "", nohp ?: "", alamat ?: "")
        input.enqueue(object : Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext,"Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateData(id : String? , nama : String? , nohp : String? , alamat : String?){
        val input = NetworkModule.service().updateData(id ?: "" ,nama ?: "", nohp ?: "", alamat ?: "")
        input.enqueue(object : Callback<ResponseAction>{
            override fun onResponse(
                call: Call<ResponseAction>,
                response: Response<ResponseAction>
            ) {
                Toast.makeText(applicationContext,"Data berhasil diupdate", Toast.LENGTH_SHORT).show()
                finish()
            }

            override fun onFailure(call: Call<ResponseAction>, t: Throwable) {
                Toast.makeText(applicationContext,t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}