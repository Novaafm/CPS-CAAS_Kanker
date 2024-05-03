package com.logres.cpsss

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var textName: TextView
    lateinit var textEmail: TextView

    val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        textName = findViewById(R.id.name)
        textEmail = findViewById(R.id.email)

        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser!=null) {
            textName.text = firebaseUser.displayName
            textEmail.text = firebaseUser.email
        }else{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }
    }
}