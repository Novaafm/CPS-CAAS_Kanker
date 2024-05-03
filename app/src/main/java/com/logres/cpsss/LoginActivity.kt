package com.logres.cpsss

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var btnRegister: Button
    lateinit var btnLogin: Button
    lateinit var progressDialog: ProgressDialog

    var firebaseAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        editEmail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        btnRegister = findViewById(R.id.btnregister)
        btnLogin = findViewById(R.id.btnlogin)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan Tunggu...")

        btnLogin.setOnClickListener {
            if (editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                proccesLogin()
            }else {
                Toast.makeText(
                    this,
                    "Silahkan isi email dan password terlebih dahulu",
                    LENGTH_SHORT).show()
            }
        }
        btnRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
    private fun proccesLogin(){
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
            .addOnCompleteListener {
                progressDialog.dismiss()
            }
    }
}
