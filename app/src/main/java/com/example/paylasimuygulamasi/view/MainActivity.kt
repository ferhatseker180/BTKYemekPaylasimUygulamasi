package com.example.paylasimuygulamasi.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.paylasimuygulamasi.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val guncelKullanici = auth.currentUser
        if (guncelKullanici != null) {
            val intent = Intent(this, AkisActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun girisYap(view: View) {

        val gmail = editTextGmail.text.toString()
        val password = editTextPassword.text.toString()
        auth.signInWithEmailAndPassword(gmail,password
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                val guncelKullanici = auth.currentUser?.email.toString()
                Snackbar.make(view,"Hoşgeldiniz : ${guncelKullanici}",Snackbar.LENGTH_SHORT).show()
                val intent = Intent(this, AkisActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener {exception ->
            Toast.makeText(this,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }

    }
    fun kayitOl(view: View) {

        val gmail = editTextGmail.text.toString()
        val password = editTextPassword.text.toString()

        auth.createUserWithEmailAndPassword(gmail,password).addOnCompleteListener { task ->
            // Asenkron
            if (task.isSuccessful) {
                // Diğer aktiviteye geçiş
                val intent = Intent(this, AkisActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()
        }
        // Kullanıcı oluşturma işlemi yukarıdaki gibi basittir.

    }
}