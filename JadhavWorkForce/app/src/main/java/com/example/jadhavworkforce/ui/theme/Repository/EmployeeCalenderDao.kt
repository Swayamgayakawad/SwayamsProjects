package com.example.jadhavworkforce.ui.theme.Repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EmployeeCalenderDao {

    @Insert
    suspend fun insertStatus(
        employeeCalender: EmployeesCalender
    )

    @Query("""
        SELECT * 
        FROM employeesCalender
        WHERE employeeId = :employeeId
        AND date = :date
        LIMIT 1
    """)
    suspend fun getStatusForDate(
        employeeId: Int,
        date: String
    ): EmployeesCalender?

    @Query("""
        UPDATE employeesCalender
        SET status = :status,
            totalsalary = :salary
        WHERE employeeId = :employeeId
        AND date = :date
    """)
    suspend fun updateStatus(
        employeeId: Int,
        date: String,
        status: Boolean,
        salary: Float
    )

    @Query("""
        SELECT *
        FROM employeesCalender
        WHERE employeeId = :employeeId
        ORDER BY date
    """)
    suspend fun getStatus(
        employeeId: Int
    ): List<EmployeesCalender>

    @Query("""
        SELECT *
        FROM employeesCalender
        WHERE date = :date
    """)
    suspend fun getStatusByDate(
        date: String
    ): List<EmployeesCalender>
    @Query("""
    DELETE FROM employeesCalender
    WHERE employeeId = :employeeId
""")
    suspend fun deleteEmployeeAttendance(employeeId: Int)
}