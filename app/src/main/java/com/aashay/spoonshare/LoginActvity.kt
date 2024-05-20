package com.aashay.spoonshare

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActvity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_actvity)

        auth = FirebaseAuth.getInstance()

        val emailEditText = findViewById<EditText>(R.id.et_email)
        val passwordEditText = findViewById<EditText>(R.id.et_pass)
        val loginButton = findViewById<Button>(R.id.login_button)
        val registerNavigationButton = findViewById<Button>(R.id.register_navigation_button)

//        loginButton.setOnClickListener {
//            val email = emailEditText.text.toString()
//            val password = passwordEditText.text.toString()
//
//            if (email.isNotEmpty() && password.isNotEmpty()) {
//                loginUser(email, password)
//            } else {
//                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
//            }
//        }

        loginUser("aashay@ss.com", "aashay")

        registerNavigationButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    Toast.makeText(this, "Authentication successful.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        // Do not call super.onBackPressed() to disable the back button
    }
}
