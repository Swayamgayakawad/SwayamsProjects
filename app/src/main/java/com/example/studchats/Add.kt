
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.studchats.AppDatabase
import com.example.studchats.ChatRoom
import com.example.studchats.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.launch

class Add : AppCompatActivity() {


    private lateinit var database: DatabaseReference
    private lateinit var db: AppDatabase

    // 1. We will initialize this inside onCreate
    private lateinit var addbtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)

        // Initialize Views
        val IDtophone = findViewById<TextInputEditText>(R.id.etSearchUid)
        val card = findViewById<androidx.cardview.widget.CardView>(R.id.namesCard)
        val searchBtn = findViewById<Button>(R.id.Searchbtn)
        val nameTv = findViewById<TextView>(R.id.Nametv)

        // 2. IMPORTANT: Find your Add Button ID here!
        // Replace 'btnAddToChat' with the actual ID from your activity_add.xml
        addbtn = findViewById<Button>(R.id.AddtoChat)

        var frindName = ""
        var frindPhone = ""

        database = FirebaseDatabase.getInstance().reference
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()

        card.visibility = View.GONE

        // SEARCH BUTTON LOGIC
        searchBtn.setOnClickListener {
            val uid = IDtophone.text.toString().trim()

            if (uid.isEmpty()) {
                Toast.makeText(this, "Enter UID", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Note: Ensure "Users" matches your SignUp database path (Case Sensitive!)
            database.child("Users").child(uid).get()
                .addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        frindName = snapshot.child("name").value.toString()
                        frindPhone = snapshot.child("phone").value.toString()

                        nameTv.text = frindName
                        card.visibility = View.VISIBLE

                        // 3. Set the click listener ONLY after we find a friend
                        setupAddButton(frindName, frindPhone)

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

    // 4. Renamed and moved logic here to be safer
    private fun setupAddButton(fName: String, fPhone: String) {
        addbtn.setOnClickListener {
            lifecycleScope.launch {
                // Get current logged-in user from Room
                val currentUser = db.userDao().getUserByUid("U001")

                if (currentUser != null) {
                    val myName = currentUser.name
                    val myPhone = currentUser.uid
                    val roomId = "${myPhone}_${fPhone}"

                    val chatRoom = ChatRoom(roomId, myName, fName)
                    createChatRoom(chatRoom)

                    Toast.makeText(this@Add, "Chatting with $fName", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@Add, "Local User Profile Not Found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun createChatRoom(chatRoom: ChatRoom) {
        database.child("ChatRooms").child(chatRoom.RoomId).setValue(chatRoom)
            .addOnSuccessListener {
                Toast.makeText(this, "Chat room created!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to create room", Toast.LENGTH_SHORT).show()
            }
    }
}