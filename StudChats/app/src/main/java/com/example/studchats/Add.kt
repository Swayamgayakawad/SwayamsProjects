package com.example.studchats

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Add : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)

        val IDtophone = findViewById<TextInputEditText>(R.id.etSearchUid)
        val card = findViewById<androidx.cardview.widget.CardView>(R.id.namesCard)
        val searchBtn = findViewById<Button>(R.id.Searchbtn)
        val nameTv = findViewById<TextView>(R.id.Nametv)
        val addbtn = findViewById<Button>(R.id.AddtoChat)

        var frindName = ""
        var frindPhone = ""

        database = FirebaseDatabase.getInstance().reference

        // ✅ FIXED DB INIT
        db = AppDatabase.getDatabase(applicationContext)

        card.visibility = View.GONE

        // 🔍 SEARCH USER
        searchBtn.setOnClickListener {
            val uid = IDtophone.text.toString().trim()

            if (uid.isEmpty()) {
                Toast.makeText(this, "Enter UID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            database.child("Users").child(uid).get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {

                        frindName = snapshot.child("name").value.toString()
                        frindPhone = snapshot.child("phone").value.toString()

                        nameTv.text = frindName
                        card.visibility = View.VISIBLE

                        // ✅ Set click AFTER data loaded
                        addbtn.setOnClickListener {
                            addChatRoom(frindName, frindPhone)
                        }

                    } else {
                        card.visibility = View.GONE
                        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Error: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // ✅ Separate function (clean)
    private fun addChatRoom(fName: String, fPhone: String) {

        lifecycleScope.launch(Dispatchers.IO) {

            val currentUser = db.userDao().getFirstUser()

            if (currentUser == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Add, "Local User Profile Not Found", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val myName = currentUser.name
            val myPhone = currentUser.Phone   // ✅ correct field

            val roomId = "${myPhone}_${fPhone}"

            val chatRoom = ChatRoom(roomId, myName, fName)

            withContext(Dispatchers.Main) {
                createChatRoom(chatRoom)
                Toast.makeText(this@Add, "Chatting with $fName", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createChatRoom(chatRoom: ChatRoom) {
        database.child("ChatRooms").child(chatRoom.RoomId).setValue(chatRoom)
            .addOnSuccessListener {
                Toast.makeText(this, "Chat room created!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to create room", Toast.LENGTH_SHORT).show()
            }
    }
}