package com.example.jadhavworkforce.ui.theme

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jadhavworkforce.ui.theme.Repository.Employees
import com.example.jadhavworkforce.ui.theme.Repository.EmployeesCalender
import com.example.jadhavworkforce.ui.theme.viewmodel.HomeViweModel


@Composable
fun LabersStatus(navController: NavHostController) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .safeDrawingPadding()
    ) {

        Statustopbar(navController)
        StatusLabersList()
    }
}


@Composable
fun Statustopbar(navController: NavHostController) {

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
            text = "Labers Status",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.size(48.dp))
    }
}
@Composable
fun StatusLabersList(
    viewModel: HomeViweModel = viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getLabList()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {

        items(
            items = viewModel.employees,
            key = { employee ->
                employee.id
            }
        ) { employee ->

            StatusCard(employee = employee)
        }
    }
}



@Composable
fun StatusCard(
    employee: Employees,
    viewModel: HomeViweModel = viewModel()
) {

    var statusList by remember {
        mutableStateOf<List<EmployeesCalender>>(emptyList())
    }

    LaunchedEffect(employee.id) {

        viewModel.getEmployeeStatus(
            employeeId = employee.id
        ) { result ->
            statusList = result
        }
    }
    var totalSalary by remember {
        mutableStateOf(0.0)
    }

    totalSalary = statusList.sumOf {
        it.totalsalary.toDouble()
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),

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
                .padding(16.dp)
        ) {


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(
                            color = MaterialTheme
                                .colorScheme
                                .primary
                                .copy(alpha = 0.12f),

                            shape = RoundedCornerShape(14.dp)
                        ),

                    contentAlignment = Alignment.Center
                ) {

                    Text(
                        text = "%02d".format(employee.id),

                        fontSize = 14.sp,

                        fontWeight = FontWeight.Bold,

                        color = MaterialTheme
                            .colorScheme
                            .primary
                    )
                }

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = employee.name,

                        fontSize = 17.sp,

                        fontWeight = FontWeight.Bold,

                        color = MaterialTheme
                            .colorScheme
                            .onSurface
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = "Attendance history",

                        fontSize = 12.sp,

                        color = MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                StatusLegend(
                    color = MaterialTheme
                        .colorScheme
                        .primary,

                    text = "Present"
                )

                Spacer(modifier = Modifier.width(14.dp))

                StatusLegend(
                    color = MaterialTheme
                        .colorScheme
                        .error,

                    text = "Absent"
                )
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                items(
                    items = statusList,
                    key = { it.id }
                ) { attendance ->

                    DateBox(
                        date = attendance.date,
                        isPresent = attendance.status
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(16.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Total Salary",

                        fontSize = 12.sp,

                        color = MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                    )

                    Spacer(
                        modifier = Modifier.height(3.dp)
                    )

                    Text(
                        text = "₹ %.2f".format(totalSalary),

                        fontSize = 18.sp,

                        fontWeight = FontWeight.Bold,

                        color = MaterialTheme
                            .colorScheme
                            .primary
                    )
                }
                val context = LocalContext.current

                Button(
                    onClick = {
                        if (totalSalary == 0.0) {

                            Toast.makeText(
                                context,
                                "Salary is 0. Nothing to pay",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {

                            viewModel.paySalary(employee.id)

                            viewModel.Insertpayment(
                                employee.id,
                                viewModel.getTodayDate(),
                                totalSalary.toInt()
                            )

                            Toast.makeText(
                                context,
                                "Salary updated successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Pay Salary")
                }
            }
        }
    }
}

@Composable
fun Tost() {
    TODO("Not yet implemented")
}


@Composable
fun DateBox(
    date: String,
    isPresent: Boolean
) {

    val backgroundColor =
        if (isPresent) {
            MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
        } else {
            MaterialTheme.colorScheme.error.copy(alpha = 0.12f)
        }

    val textColor =
        if (isPresent) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.error
        }

    Column(
        modifier = Modifier.width(70.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(
            modifier = Modifier
                .width(70.dp)
                .height(42.dp)
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(13.dp)
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = date,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = textColor,
                maxLines = 1
            )
        }

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Box(
            modifier = Modifier
                .size(6.dp)
                .background(
                    color = textColor,
                    shape = CircleShape
                )
        )
    }
}
@Composable
fun StatusLegend(
    color: Color,
    text: String
) {

    Row(
        verticalAlignment =
            Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier
                .size(8.dp)
                .background(
                    color = color,
                    shape = RoundedCornerShape(50)
                )
        )

        Spacer(
            modifier =
                Modifier.width(5.dp)
        )

        Text(
            text = text,

            fontSize = 11.sp,

            fontWeight =
                FontWeight.Medium,

            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun labersStatusPreview() {

    JadhavWorkForceTheme {

//        val navController = rememberNavController()
//
//        labersStatus(navController)
        DateBox(
           "12/08/2026",
            true
        )

    }
}

