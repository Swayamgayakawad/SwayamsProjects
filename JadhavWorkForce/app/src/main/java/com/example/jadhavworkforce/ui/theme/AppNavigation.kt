package com.example.jadhavworkforce.ui.theme

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jadhavworkforce.AddLabers
import com.example.jadhavworkforce.ui.theme.viewmodel.HomeViweModel

@Composable
fun appNav() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "homeScreen"
    ) {

        // HOME
        composable("homeScreen") {

            HomeScreen(
                navController = navController
            )
        }


        // ADD EMPLOYEE
        composable("add") {

            AddLabers(
                navController = navController
            )
        }


        // LABOR STATUS
        composable("labersStatus") {

            LabersStatus(
                navController = navController
            )
        }
        composable("updateSalary") {
            UpdateSalary(
                navController = navController,
                viewModel = viewModel<HomeViweModel>()
            )
        }

        // PAYMENT STATUS
        composable(
            route = "paymentStatus/{employeeId}/{employeeName}"
        ) { backStackEntry ->

            val employeeId =
                backStackEntry.arguments
                    ?.getString("employeeId")
                    ?.toIntOrNull()

            val employeeName =
                backStackEntry.arguments
                    ?.getString("employeeName")

            // Get HomeViweModel
            val viewModel: HomeViweModel = viewModel()

            if (
                employeeId != null &&
                employeeName != null
            ) {

                PaymentStatus(

                    navController = navController,

                    employeeId = employeeId,

                    employeeName = employeeName,

                    viewModel = viewModel
                )
            }
        }
    }
}