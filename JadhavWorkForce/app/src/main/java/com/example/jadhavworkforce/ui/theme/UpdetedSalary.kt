package com.example.jadhavworkforce.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CurrencyRupee
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavController
import com.example.jadhavworkforce.ui.theme.viewmodel.HomeViweModel
import androidx.compose.foundation.layout.width

@Composable
fun UpdateSalary(
    navController: NavHostController,
    viewModel: HomeViweModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .safeDrawingPadding()
    ) {

        SalaryUpdateTop(navController)

        EditSalary(
            viewModel = viewModel,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun SalaryUpdateTop(
    navController: NavController
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(
                horizontal = 8.dp,
                vertical = 8.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ) {

            Icon(
                imageVector =
                    Icons.AutoMirrored.Filled.ArrowBack,

                contentDescription = "Back",

                tint =
                    MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Update Salary",

            modifier = Modifier
                .weight(1f),

            textAlign = TextAlign.Center,

            fontSize = 22.sp,

            fontWeight = FontWeight.Bold,

            color =
                MaterialTheme.colorScheme.onBackground
        )

        // Keeps title centered
        Spacer(
            modifier = Modifier
                .padding(24.dp)
        )
    }
}


@Composable
fun EditSalary(
    viewModel: HomeViweModel,
    modifier: Modifier = Modifier
) {

    var id by remember {
        mutableStateOf("")
    }

    var salary by remember {
        mutableStateOf("")
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 20.dp,
                vertical = 12.dp
            ),

        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Employee Details",

            fontSize = 24.sp,

            fontWeight = FontWeight.Bold,

            color =
                MaterialTheme.colorScheme.onBackground
        )

        Text(
            text =
                "Enter the employee ID and new salary below.",

            fontSize = 14.sp,

            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(22.dp),

            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),

                verticalArrangement =
                    Arrangement.spacedBy(16.dp)
            ) {

                // EMPLOYEE ID

                OutlinedTextField(

                    value = id,

                    onValueChange = {
                        id = it
                    },

                    modifier = Modifier
                        .fillMaxWidth(),

                    label = {
                        Text("Employee ID")
                    },

                    placeholder = {
                        Text("Enter employee ID")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default.Badge,

                            contentDescription =
                                "Employee ID"
                        )
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Number
                        ),

                    singleLine = true,

                    shape =
                        RoundedCornerShape(16.dp)
                )


                // SALARY

                OutlinedTextField(

                    value = salary,

                    onValueChange = {
                        salary = it
                    },

                    modifier = Modifier
                        .fillMaxWidth(),

                    label = {
                        Text("New Salary")
                    },

                    placeholder = {
                        Text("Enter new salary")
                    },

                    leadingIcon = {

                        Icon(
                            imageVector =
                                Icons.Default.CurrencyRupee,

                            contentDescription =
                                "Salary"
                        )
                    },

                    keyboardOptions =
                        KeyboardOptions(
                            keyboardType =
                                KeyboardType.Decimal
                        ),

                    singleLine = true,

                    shape =
                        RoundedCornerShape(16.dp)
                )
            }
        }


        // --------------------------------
        // INFORMATION
        // --------------------------------

        Card(

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(18.dp),

            colors = CardDefaults.cardColors(

                containerColor =
                    MaterialTheme.colorScheme.primaryContainer
            )
        ) {

            Text(

                text =
                    "Enter the correct employee ID before updating the salary.",

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),

                fontSize = 13.sp,

                color =
                    MaterialTheme.colorScheme.onPrimaryContainer
            )
        }


        Spacer(
            modifier = Modifier.height(4.dp)
        )


        // --------------------------------
        // UPDATE BUTTON
        // --------------------------------

        Button(

            onClick = {

                val employeeId =
                    id.toIntOrNull()

                val newSalary =
                    salary.toFloatOrNull()

                if (
                    employeeId != null &&
                    newSalary != null
                ) {

                    viewModel.updateSalary(

                        employeeId = employeeId,

                        newSalary = newSalary
                    )
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),

            shape = RoundedCornerShape(16.dp),

            colors = ButtonDefaults.buttonColors(

                containerColor =
                    MaterialTheme.colorScheme.primary,

                contentColor =
                    MaterialTheme.colorScheme.onPrimary
            )
        ) {

            Icon(
                imageVector =
                    Icons.Default.Save,

                contentDescription =
                    "Update Salary"
            )

            Spacer(
                modifier = Modifier.width(10.dp)
            )

            Text(
                text = "Update Salary",

                fontSize = 16.sp,

                fontWeight =
                    FontWeight.SemiBold
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )
    }
}