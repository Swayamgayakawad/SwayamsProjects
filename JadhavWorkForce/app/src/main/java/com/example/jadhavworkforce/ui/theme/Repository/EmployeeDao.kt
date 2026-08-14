package com.example.jadhavworkforce.ui.theme.Repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface EmployeeDao {
    @Insert
    suspend fun insertEmployee(employee: Employees)
    @Query("SELECT * FROM employees")
    suspend fun getAllEmployees(): List<Employees>
    @Query("UPDATE employees SET salary = :newSalary WHERE id = :employeeId")
    suspend fun UpdateSalary(employeeId: Int, newSalary: Float)
}