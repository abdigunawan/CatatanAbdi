package com.abdigunawan.catatanabdi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditActivity : AppCompatActivity() {

    private val api by lazy {ApiRetrofit().endpoint}
    private val note by lazy {intent.getSerializableExtra("note") as NotesModel.Data }
    private lateinit var editJudul: EditText
    private lateinit var editCatatan: EditText
    private lateinit var btnEdit: Button
    private lateinit var btnHapus: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        setupView()
        setupListener()
        val networkConnection = NetworkConnection(applicationContext)
    }

    private fun setupView(){
        editJudul = findViewById(R.id.editJudul)
        editCatatan = findViewById(R.id.editCatatan)
        btnEdit = findViewById(R.id.btnEdit)
        btnHapus = findViewById(R.id.btnHapus)

        editJudul.setText( note.judul )
        editCatatan.setText( note.catatan )

    }

    private fun setupListener() {
        btnEdit.setOnClickListener {
            api.update(note.id!!, editJudul.text.toString(), editCatatan.text.toString())
                .enqueue( object : Callback<SubmitModel>{
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

        btnHapus.setOnClickListener {
            api.dalete(note.id!!)
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