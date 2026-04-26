package com.example.studchats
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE uid = :uid")
    suspend fun getUser(uid: String): User?
    suspend fun getUserByUid(uid: String): User?
}

