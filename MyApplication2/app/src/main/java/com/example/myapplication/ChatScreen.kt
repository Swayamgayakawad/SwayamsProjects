package com.example.myapplication

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.database.*

data class Message(
    val name: String = "",
    val text: String = ""
)

@Composable
fun ChatScreen() {

    val messages = remember { mutableStateListOf<Message>() }

    var userName by remember { mutableStateOf("") }
    var messageText by remember { mutableStateOf("") }

    val database = FirebaseDatabase.getInstance()
    val messagesRef = database.getReference("Messages")

    DisposableEffect(Unit) {

        val listener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                messages.clear()

                for (child in snapshot.children) {
                    val message =
                        child.getValue(Message::class.java)

                    if (message != null) {
                        messages.add(message)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }

        messagesRef.addValueEventListener(listener)

        onDispose {
            messagesRef.removeEventListener(listener)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {

        OutlinedTextField(
            value = userName,
            onValueChange = {
                userName = it
            },
            label = {
                Text("Your Name")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {

            items(messages) { msg ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {

                        Text(
                            text = msg.name,
                            style = MaterialTheme.typography.titleSmall
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(text = msg.text)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            TextField(
                value = messageText,
                onValueChange = {
                    messageText = it
                },
                placeholder = {
                    Text("Type message...")
                },
                modifier = Modifier.weight(1f)
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Button(
                onClick = {

                    if (userName.isNotBlank() &&
                        messageText.isNotBlank()
                    ) {

                        val message = Message(
                            name = userName,
                            text = messageText
                        )

                        messagesRef.push()
                            .setValue(message)

                        messageText = ""
                    }
                }
            ) {
                Text("Send")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    Text("Run the app to test Firebase chat")
}