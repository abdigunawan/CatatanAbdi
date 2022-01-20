package com.abdigunawan.catatanabdi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InsertActivity : AppCompatActivity() {

    private val api by lazy {ApiRetrofit().endpoint}
    private lateinit var editJudul: EditText
    private lateinit var editCatatan: EditText
    private lateinit var btnSave: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        setupView()
        setupListener()
    }

    private fun setupView(){
        editJudul = findViewById(R.id.etJudul)
        editCatatan = findViewById(R.id.etCatatan)
        btnSave = findViewById(R.id.btnSave)
    }

    private fun setupListener() {
        btnSave.setOnClickListener {
            when {
                editJudul.text.toString().isEmpty() -> {
                    editJudul.setError("Judul Tidak Boleh Kosong")
                }
                editCatatan.text.toString().isEmpty() -> {
                    editCatatan.setError("Catatan Tidak Boleh Kosong")
                }
                else -> {
                    api.create(editJudul.text.toString(),editCatatan.text.toString())
                        .enqueue(object : Callback<SubmitModel> {
                            override fun onResponse(
                                call: Call<SubmitModel>,
                                response: Response<SubmitModel>
                            ) {
                                if (response.isSuccessful) {
                                    val submit = response.body()
                                    Toast.makeText(applicationContext, submit!!.message, Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }

                            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })
                }
            }
        }
    }
}