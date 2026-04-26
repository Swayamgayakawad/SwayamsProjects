package com.example.studchat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize with your specific database URL from the screenshot
        database = FirebaseDatabase.getInstance("https://studchat-54643-default-rtdb.firebaseio.com/")
        auth = FirebaseAuth.getInstance()

        val signup = findViewById<Button>(R.id.SingUp)
        val nameEt = findViewById<TextInputEditText>(R.id.name)
        val phoneEt = findViewById<TextInputEditText>(R.id.PhoneNumber)
        val passEt = findViewById<TextInputEditText>(R.id.password)

        signup.setOnClickListener {
            val name = nameEt.text.toString().trim()
            val phone = phoneEt.text.toString().trim()
            val pass = passEt.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            if (pass.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Creating a dummy email for Firebase Auth
            val email = "$phone@studchat.com"

            auth.createUserWithEmailAndPassword(email, pass)
                .addOnSuccessListener {
                    val uid = auth.currentUser!!.uid
                    val user = Singdata(phone, name, pass)

                    database.getReference("Users")
                        .child(uid)
                        .setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "User Saved Successfully", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Database Error: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Auth Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
        }
    }
}