package com.example.studchats

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch
import kotlin.jvm.java


class SignUp : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance().getReference("Users")

        val signup = findViewById<Button>(R.id.SingUp)
        val nameEt = findViewById<TextInputEditText>(R.id.name)
        val phoneEt = findViewById<TextInputEditText>(R.id.PhoneNumber)

        // ✅ FIXED
        db = AppDatabase.getDatabase(applicationContext)

        signup.setOnClickListener {

            val name = nameEt.text.toString().trim()
            val phone = phoneEt.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    saveUser(name, phone)
                }
            }
        }
    }

    // ✅ Clean function
    suspend fun saveUser(name: String, phone: String) {

        // Firebase
        val data = DatabaseSignUp(phone, name)

        database.child(phone).setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Saved to Firebase", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Firebase Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }

        // Room
        val user = User(
            id = 0,
            uid = phone,
            Phone = phone,
            name = name,
            pass = "pass"
        )

        db.userDao().insertUser(user)

        runOnUiThread {
            Toast.makeText(this@SignUp, "Saved locally", Toast.LENGTH_SHORT).show()
        }
    }
}



