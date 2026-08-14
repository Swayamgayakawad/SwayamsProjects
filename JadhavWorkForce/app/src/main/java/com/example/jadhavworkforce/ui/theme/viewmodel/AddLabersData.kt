package com.example.jadhavworkforce.ui.theme.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.jadhavworkforce.ui.theme.Repository.AppDatabase
import com.example.jadhavworkforce.ui.theme.Repository.Employees


class AddLabersData(
    private val context: Context
) : ViewModel() {


    suspend fun saveEmployee(
        name: String,
        phone: String,
        salary: String
    ) {

        val employee = Employees(
            name = name,
            phone = phone,
            salary = salary
        )


        val db = AppDatabase.getDatabase(
            context
        )


        db.employeeDao().insertEmployee(
            employee
        )
    }
}