package com.example.jadhavworkforce.ui.theme.Repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PaymentsDao {

    @Insert
    suspend fun insertPayments(payment: Payments)

    @Query("SELECT * FROM payments")
    suspend fun getPaymentHistory(): List<Payments>

    @Query(
        "SELECT * FROM payments " +
                "WHERE employeeId = :employeeId " +
                "ORDER BY date DESC"
    )
    suspend fun getPaymentHistoryForEmployee(employeeId: Int): List<Payments>
}