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
        val passEt = findViewById<TextInputEditText>(R.id.password)
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()

        signup.setOnClickListener {
            Toast.makeText(this, "button clicked", Toast.LENGTH_SHORT).show()

            val name = nameEt.text.toString().trim()
            val phone = phoneEt.text.toString().trim()
            val pass = passEt.text.toString().trim()

            if (name.isEmpty() || phone.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                ReadData(phone,name , pass)
                lifecycleScope.launch {
                    roomData(phone, name, pass)
                }
            }
        }
    }

    fun ReadData(name: String, phone: String, pass: String) {

        val data = DatabaseSignUp(name, phone, pass)

        database.child(phone).setValue(data)
            .addOnSuccessListener {
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
    suspend fun roomData(phone : String, name: String, pass: String){
        val id :Int=0
        val RD = User( id,phone, name, pass)
        db.userDao().insert(RD)

        runOnUiThread {
            Toast.makeText(this@SignUp, "Signup Saved Locally", Toast.LENGTH_SHORT).show()
        }
    }
    suspend fun getUser(uid: String): User? {
        return UserDao.getUserByUid(uid)
    }
}



