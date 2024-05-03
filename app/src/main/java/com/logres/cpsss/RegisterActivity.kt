package com.logres.cpsss

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import java.util.jar.Attributes.Name

class RegisterActivity : AppCompatActivity() {
    lateinit var editName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var editPasswordConf: EditText
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
        setContentView(R.layout.activity_register)
        editName = findViewById(R.id.name)
        editEmail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        editPasswordConf = findViewById(R.id.Password_conf)
        btnRegister = findViewById(R.id.btnregister)
        btnLogin = findViewById(R.id.btnlogin)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan Tunggu...")

        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        btnRegister.setOnClickListener {
            if (editName.text.isNotEmpty() && editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()) {
                if (editPassword.text.toString() == editPasswordConf.text.toString()) {
                   processRegister()
                } else {
                    Toast.makeText(this, "Konfirmasi kata sandi harus sama", LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Silahkan isi dulu semua data", LENGTH_SHORT).show()
            }
        }
    }

    private fun processRegister(){
        val name = editName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = name
                    }
                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfile)
                        .addOnCompleteListener {
                            progressDialog.dismiss()
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        .addOnFailureListener { error2 ->
                            Toast.makeText(this, error2.localizedMessage, LENGTH_SHORT).show()

                        }
                }else{
                    progressDialog.dismiss()
                }
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
    }

}