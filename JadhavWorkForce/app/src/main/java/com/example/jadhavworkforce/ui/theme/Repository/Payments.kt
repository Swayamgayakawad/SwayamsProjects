package com.example.jadhavworkforce.ui.theme.Repository

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "payments",
    foreignKeys = [
        ForeignKey(
            entity = Employees::class,
            parentColumns = ["id"],
            childColumns = ["employeeId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["employeeId"]),
        Index(
            value = ["employeeId", "date"],
            unique = false
        )
    ]
)
data class Payments(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val employeeId: Int,

    val date: String,

    val paymentAmounts: Int
)