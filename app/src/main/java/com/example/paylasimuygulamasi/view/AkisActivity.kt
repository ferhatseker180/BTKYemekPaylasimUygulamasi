package com.example.paylasimuygulamasi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.paylasimuygulamasi.model.Post
import com.example.paylasimuygulamasi.R
import com.example.paylasimuygulamasi.adapter.AkisRecyclerAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_akis.*

class AkisActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var recyclerViewAdapter : AkisRecyclerAdapter
    var postListesi = ArrayList<Post>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_akis)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()

        verileriAl()

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = AkisRecyclerAdapter(postListesi)
        recyclerView.adapter = recyclerViewAdapter


    }
    fun verileriAl() {
        database.collection("Post").orderBy("tarih", Query.Direction.DESCENDING).addSnapshotListener { value, error ->
            if (error != null) { // Hata var
                Toast.makeText(applicationContext,error.localizedMessage,Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (value.isEmpty == false) {
                        val documents = value.documents
                        postListesi.clear()

                        for (document in documents) {
                        val kullaniciEmail = document.get("kullaniciemail") as String
                            val kullaniciYorumu = document.get("kullaniciyorum") as String
                            val gorselUrl = document.get("gorselurl") as String

                            val indirilenPost = Post(kullaniciEmail,kullaniciYorumu,gorselUrl)
                            postListesi.add(indirilenPost)

                        }

                        recyclerViewAdapter.notifyDataSetChanged()  // Yeni veri gelince kendini yenile
                    }
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.secenekler_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.action_ekle) {
            // Fotoğraf ve yazı eklenecek.
            val intent = Intent(this, SharePictureActivity::class.java)
            startActivity(intent)
        }
        if (item.itemId == R.id.action_cikis) {
            auth.signOut()
            // Main Activity'e dönüp login ekranına gidilecek.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}