package com.example.jadhavworkforce.ui.theme.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.example.jadhavworkforce.ui.theme.Repository.AppDatabase
import com.example.jadhavworkforce.ui.theme.Repository.Employees
import com.example.jadhavworkforce.ui.theme.Repository.EmployeesCalender
import com.example.jadhavworkforce.ui.theme.Repository.Payments
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch

class HomeViweModel(
    application: Application
) : AndroidViewModel(application) {

    private val database =
        AppDatabase.getDatabase(application)

    private val _paymentHistory = MutableStateFlow<List<Payments>>(emptyList())
    val paymentHistory: StateFlow<List<Payments>> = _paymentHistory


    private val employeeDao =
        database.employeeDao()

    private val employeeCalenderDao =
        database.employeeCalenderDao()


    var employees by mutableStateOf<List<Employees>>(
        emptyList()
    )
        private set


    var presentCount by mutableStateOf(0)
        private set

    var absentCount by mutableStateOf(0)
        private set


    fun getLabList() {

        viewModelScope.launch {

            employees =
                employeeDao.getAllEmployees()

            updateSummary()
        }
    }


    fun addStatus(
        employeeId: Int,
        date: String,
        status: Boolean,
        salary: Float
    ) {

        viewModelScope.launch {

            val existingStatus =
                employeeCalenderDao.getStatusForDate(
                    employeeId = employeeId,
                    date = date
                )


            if (existingStatus == null) {

                // First click for this employee/date
                employeeCalenderDao.insertStatus(

                    EmployeesCalender(
                        employeeId = employeeId,
                        date = date,
                        status = status,
                        totalsalary = salary
                    )
                )

            } else {

                // Already exists → UPDATE
                employeeCalenderDao.updateStatus(

                    employeeId = employeeId,
                    date = date,
                    status = status,
                    salary = salary
                )
            }


            updateSummary()
        }
    }


    private suspend fun updateSummary() {

        var present = 0
        var absent = 0

        val today = getTodayDate()


        employees.forEach { employee ->

            val status =
                employeeCalenderDao.getStatusForDate(
                    employeeId = employee.id,
                    date = today
                )


            if (status != null) {

                if (status.status) {

                    present++

                } else {

                    absent++
                }
            }
        }


        presentCount = present
        absentCount = absent
    }


    fun getEmployeeStatus(
        employeeId: Int,
        onResult: (List<EmployeesCalender>) -> Unit
    ) {

        viewModelScope.launch {

            val result =
                employeeCalenderDao.getStatus(
                    employeeId
                )

            onResult(result)
        }
    }


    fun getTodayDate(): String {

        return java.text.SimpleDateFormat(
            "yyyy-MM-dd",
            java.util.Locale.getDefault()
        ).format(
            java.util.Date()
        )
    }

    fun paySalary(employeeId: Int) {
        viewModelScope.launch {

            employeeCalenderDao.deleteEmployeeAttendance(
                employeeId = employeeId
            )

            updateSummary()
        }
    }
    fun addAbsentStatus(
        employeeId: Int,
        date: String
    ) {

        viewModelScope.launch {

            val existingStatus =
                employeeCalenderDao.getStatusForDate(
                    employeeId = employeeId,
                    date = date
                )

            if (existingStatus == null) {

                // First click for this employee/date
                employeeCalenderDao.insertStatus(

                    EmployeesCalender(
                        employeeId = employeeId,
                        date = date,
                        status = false,
                        totalsalary = 0f
                    )
                )

            } else {

                // Already exists → UPDATE
                employeeCalenderDao.updateStatus(

                    employeeId = employeeId,
                    date = date,
                    status = false,
                    salary = 0f
                )
            }

            updateSummary()
        }
    }
    fun Insertpayment(EmployeeId: Int,date: String,amount: Int) {
        viewModelScope.launch {
            val payment = Payments(
                employeeId = EmployeeId,
                date = date,
                paymentAmounts = amount
            )
            database.paymentsDao().insertPayments(payment)
        }
    }
    fun getPaymentHistory(employeeId: Int) {

        viewModelScope.launch {

            val payment = database
                    .paymentsDao()
                    .getPaymentHistoryForEmployee(employeeId)

            _paymentHistory.value = payment
        }
    }

    fun updateSalary(employeeId: Int, newSalary: Float) {
     viewModelScope.launch {
         employeeDao.UpdateSalary(employeeId,newSalary)
     }
    }

}


