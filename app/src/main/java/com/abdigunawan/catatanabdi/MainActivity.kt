package com.abdigunawan.catatanabdi

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val api by lazy {ApiRetrofit().endpoint}
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var listNote: RecyclerView
    private lateinit var fabCreate: FloatingActionButton
    private lateinit var swipeReresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupList()
        setupListener()
        netConnection()
    }

    override fun onStart() {
        super.onStart()
        getNote()
    }

    private fun setupView() {
        listNote = findViewById(R.id.listNotes)
        fabCreate = findViewById(R.id.fabCrete)
        swipeReresh = findViewById(R.id.swipeRefresh)
    }

    private fun setupListener() {
        fabCreate.setOnClickListener {
            startActivity(Intent(this, InsertActivity::class.java))
        }

        swipeReresh.setOnRefreshListener {
            getNote()
            swipeReresh.isRefreshing = false
        }
    }

    private fun setupList() {
        notesAdapter = NotesAdapter(arrayListOf(), object : NotesAdapter.OnAdapterListener{
            override fun onUpdate(note: NotesModel.Data) {
                startActivity(Intent(this@MainActivity, EditActivity::class.java)
                    .putExtra("note", note)
                )
            }

        })
        listNote.adapter = notesAdapter
    }

    private fun getNote() {
        api.data().enqueue(object : Callback<NotesModel>{
            override fun onResponse(call: Call<NotesModel>, response: Response<NotesModel>) {
                if (response.isSuccessful){
                    val listData = response.body()!!.notes
                    shimmerLayout.stopShimmerAnimation()
                    shimmerLayout.visibility = View.GONE
                    listNotes.visibility = View.VISIBLE
                    notesAdapter.setData( listData )
//                    listData.forEach{
//                        Log.e("MainActivity",response.toString())
//                    }
                }
            }

            override fun onFailure(call: Call<NotesModel>, t: Throwable) {
                shimmerLayout.visibility = View.GONE
                Log.e("MainActivity",t.toString())
            }
        })
    }

    private fun netConnection(){
        val networkConnection = NetworkConnection(applicationContext)
        networkConnection.observe(this, Observer { isConnected ->

            if (isConnected) {
                layoutDisconnected.visibility = View.GONE
                layoutConnected.visibility = View.VISIBLE
            }else {
                layoutDisconnected.visibility = View.VISIBLE
                layoutConnected.visibility = View.GONE
            }

        })
    }

    override fun onResume() {
        super.onResume()
        shimmerLayout.startShimmerAnimation()
    }

    override fun onPause() {
        shimmerLayout.stopShimmerAnimation()
        super.onPause()
    }
}