package com.example.jadhavworkforce

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.jadhavworkforce.ui.theme.JadhavWorkForceTheme
import com.example.jadhavworkforce.ui.theme.viewmodel.AddLabersData
import kotlinx.coroutines.launch


@Composable
fun AddLabers(
    navController: NavHostController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .safeDrawingPadding()
    ) {

        TitleBar(
            navController = navController
        )

        DetailsAdder(
            navController = navController
        )
    }
}


@Composable
fun TitleBar(
    navController: NavHostController
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
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
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back"
            )
        }


        Text(
            text = "Add Employee",

            modifier = Modifier
                .weight(1f),

            textAlign = TextAlign.Center,

            fontSize = 24.sp,

            fontWeight = FontWeight.Bold,

            color = MaterialTheme.colorScheme.onBackground
        )


        Spacer(
            modifier = Modifier
                .padding(24.dp)
        )
    }
}


@Composable
fun DetailsAdder(
    navController: NavHostController
) {

    var name by remember {
        mutableStateOf("")
    }

    var phone by remember {
        mutableStateOf("")
    }

    var salary by remember {
        mutableStateOf("")
    }


    val context = LocalContext.current

    val scope = rememberCoroutineScope()


    /*
     * Create your ViewModel/Data class.
     */
    val viewModel = remember {
        AddLabersData(
            context = context
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(
                horizontal = 20.dp
            ),

        verticalArrangement = Arrangement.spacedBy(
            16.dp
        )
    ) {


        Spacer(
            modifier = Modifier
                .padding(4.dp)
        )


        Text(
            text = "Employee Details",

            fontSize = 20.sp,

            fontWeight = FontWeight.Bold,

            color = MaterialTheme.colorScheme.onBackground
        )


        Text(
            text = "Enter the employee information below.",

            fontSize = 14.sp,

            color = MaterialTheme.colorScheme.onSurfaceVariant
        )


        // Employee Name

        OutlinedTextField(
            value = name,

            onValueChange = {
                name = it
            },

            label = {
                Text("Employee Name")
            },


            placeholder = {
                Text("Enter employee name")
            },

            singleLine = true,

            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(16.dp)
        )


        // Phone Number

        OutlinedTextField(
            value = phone,

            onValueChange = {
                phone = it
            },

            label = {
                Text("Phone Number")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            placeholder = {
                Text("Enter phone number")
            },

            singleLine = true,

            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(16.dp)
        )


        // Salary

        OutlinedTextField(
            value = salary,

            onValueChange = {
                salary = it
            },

            label = {
                Text("Salary")
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),

            placeholder = {
                Text("Enter salary")
            },

            singleLine = true,

            modifier = Modifier
                .fillMaxWidth(),

            shape = RoundedCornerShape(16.dp)
        )


        Spacer(
            modifier = Modifier
                .padding(2.dp)
        )


        // Save Button

        Button(
            onClick = {

                scope.launch {

                    viewModel.saveEmployee(
                        name = name,
                        phone = phone,
                        salary = salary
                    )


                    navController.popBackStack()
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 4.dp
                ),

            shape = RoundedCornerShape(16.dp)
        ) {

            Text(
                text = "Save Employee",

                fontSize = 16.sp,

                fontWeight = FontWeight.SemiBold,

                modifier = Modifier
                    .padding(
                        vertical = 6.dp
                    )
            )
        }


        Spacer(
            modifier = Modifier
                .padding(
                    bottom = 20.dp
                )
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddEmployeePreview() {

    JadhavWorkForceTheme {

        val navController = rememberNavController()

        AddLabers(
            navController = navController
        )
    }
}