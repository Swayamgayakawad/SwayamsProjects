package com.example.jadhavworkforce.ui.theme

import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.jadhavworkforce.R
import com.example.jadhavworkforce.ui.theme.Repository.Employees
import com.example.jadhavworkforce.ui.theme.viewmodel.HomeViweModel
import java.util.Date
import java.util.Locale

val formattedDate: String = SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date())
@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeViweModel = viewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getLabList()
    }

    var showMenu by remember {
        mutableStateOf(false)
    }

    Scaffold(
        floatingActionButton = {

            FloatingActionButton(
                onClick = {
                    navController.navigate("add")
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Employee",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    ) { paddingValues ->

        var offsetX by remember {
            mutableFloatStateOf(0f)
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    MaterialTheme.colorScheme.background
                )
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .pointerInput(Unit) {

                        detectHorizontalDragGestures(

                            onHorizontalDrag = { _, dragAmount ->
                                offsetX += dragAmount
                            },

                            onDragEnd = {

                                if (offsetX < -150f) {
                                    navController.navigate("labersStatus")
                                }

                                offsetX = 0f
                            },

                            onDragCancel = {
                                offsetX = 0f
                            }
                        )
                    }
            ) {

                TopBar(
                    onMenuClick = {
                        showMenu = true
                    }
                )

                DateCard(navController)

                EmployeeList(
                    viewModel = viewModel,
                    modifier = Modifier.weight(1f),
                    navController = navController
                )
            }

            // RIGHT SIDE MENU
            if (showMenu) {

                MenuCard(
                    navController = navController,
                    onDismiss = {
                        showMenu = false
                    }
                )
            }
        }
    }
}
@Composable
fun TopBar(
    onMenuClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 12.dp,
                vertical = 8.dp
            ),

        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(
            onClick = {
                onMenuClick()
            }
        ) {

            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = "Jadhav WorkForce",

            modifier = Modifier.weight(1f),

            textAlign = TextAlign.Center,

            fontSize = 24.sp,

            fontWeight = FontWeight.Bold,

            color = MaterialTheme.colorScheme.onBackground
        )
    }
}


@Composable
fun DateCard(navController: NavHostController) {

    Card(
        onClick = {
           navController.navigate("labersStatus")
        },

        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 8.dp),
        shape = RoundedCornerShape(22.dp),

        colors = CardDefaults.cardColors(
                containerColor = MaterialTheme
                        .colorScheme
                        .surface
        ),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)

    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),

            verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Date",
                tint = MaterialTheme.colorScheme.primary
            )


            Spacer(modifier = Modifier.width(10.dp))
            val formattedDate = remember {
                SimpleDateFormat(
                    "dd MMM yyyy",
                    Locale.getDefault()
                ).format(Date())
            }

            Text(
                text = formattedDate,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.primary
            )


            Icon(

                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = "Select date",
                tint = MaterialTheme
                        .colorScheme
                        .primary
            )
        }
    }
}


@Composable
fun EmployeeList(
    viewModel: HomeViweModel,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    var selectedStatus by remember {
        mutableStateOf<Map<Int, Boolean>>(emptyMap())
    }

    val formattedDate = SimpleDateFormat(
        "yyyy-MM-dd",
        LocalLocale.current.platformLocale
    ).format(Date())

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            horizontal = 16.dp,
            vertical = 8.dp
        ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        items(
            items = viewModel.employees,
            key = { employee ->
                employee.id
            }
        ) { employee ->

            // false when no status has been selected yet
            val isPresent = selectedStatus[employee.id] ?: false

            EmployeeCard(
                employee = employee,
                isPresent = isPresent,

                onClick = {
                    navController.navigate(
                        "paymentStatus/${employee.id}/${Uri.encode(employee.name)}"
                    )
                },

                onPresentClick = {

                    // Update UI
                    selectedStatus = selectedStatus.toMutableMap().apply {
                        this[employee.id] = true
                    }

                    // Save Present + Salary
                    viewModel.addStatus(
                        employeeId = employee.id,
                        date = formattedDate,
                        status = true,
                        salary = employee.salary.toFloat()
                    )
                },

                onAbsentClick = {
                    selectedStatus = selectedStatus.toMutableMap().apply {
                        this[employee.id] = false
                    }
                    viewModel.addAbsentStatus(
                        employeeId = employee.id,
                        date = formattedDate
                    )
                }
            )
        }
    }
}

@Composable
fun EmployeeCard(
    employee: Employees,
    isPresent: Boolean?,
    onClick: () -> Unit,
    onPresentClick: () -> Unit,
    onAbsentClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth()
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface

        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),

            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        color = MaterialTheme
                            .colorScheme
                            .primary
                            .copy(alpha = 0.10f),
                        shape = RoundedCornerShape(12.dp)
                    ),

                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "%02d".format(employee.id),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Text(
                text = employee.name,
                modifier = Modifier.weight(1f),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            // PRESENT
            Button(
                onClick = {
                    onPresentClick()
                },

                modifier = Modifier.height(40.dp),

                contentPadding = PaddingValues(
                    horizontal = 10.dp
                ),

                shape = RoundedCornerShape(12.dp),

                colors = ButtonDefaults.buttonColors(

                    containerColor =
                        if (isPresent == true) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },

                    contentColor =
                        if (isPresent == true) {
                            MaterialTheme.colorScheme.onPrimary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Present",
                    modifier = Modifier.size(17.dp)
                )

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Text(
                    text = "Present",
                    fontSize = 12.sp
                )
            }

            Spacer(
                modifier = Modifier.width(6.dp)
            )

            // ABSENT
            Button(
                onClick = {
                    onAbsentClick()
                },

                modifier = Modifier.height(40.dp),

                contentPadding = PaddingValues(
                    horizontal = 10.dp
                ),

                shape = RoundedCornerShape(12.dp),

                colors = ButtonDefaults.buttonColors(

                    containerColor =
                        if (isPresent == false) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.surfaceVariant
                        },

                    contentColor =
                        if (isPresent == false) {
                            MaterialTheme.colorScheme.onError
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Absent",
                    modifier = Modifier.size(17.dp)
                )

                Spacer(
                    modifier = Modifier.width(4.dp)
                )

                Text(
                    text = "Absent",
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun MenuCard(
    navController: NavHostController,
    onDismiss: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Black.copy(alpha = 0.35f)
                )
                .clickable {
                    onDismiss()
                }
        )

        // LEFT SIDE MENU
        Card(
            modifier = Modifier
                .fillMaxHeight()
                .width(300.dp)
                .align(Alignment.CenterStart),

            shape = RoundedCornerShape(
                topEnd = 24.dp,
                bottomEnd = 24.dp
            ),

            colors = CardDefaults.cardColors(
                containerColor =
                    MaterialTheme.colorScheme.surface
            ),

            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            )
        ) {

            var offsetX by remember {
                mutableFloatStateOf(0f)
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .pointerInput(Unit) {

                        detectHorizontalDragGestures(

                            onHorizontalDrag = { _, dragAmount ->

                                offsetX += dragAmount
                            },

                            onDragEnd = {

                                if (offsetX < -150f) {

                                    navController.popBackStack()
                                }

                                offsetX = 0f
                            },

                            onDragCancel = {

                                offsetX = 0f
                            }
                        )
                    }
            ) {

                // HEADER
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    // APP LOGO
                    Image(
                        painter = painterResource(
                            id = R.drawable.app_logo
                        ),

                        contentDescription =
                            "App Logo",

                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                    )

                    Spacer(
                        modifier = Modifier.width(10.dp)
                    )

                    Text(
                        text = "Jadhav WorkForce",

                        style =
                            MaterialTheme.typography.titleMedium,

                        fontWeight =
                            FontWeight.Bold,

                        modifier =
                            Modifier.weight(1f)
                    )

                    // CLOSE BUTTON
                    IconButton(
                        onClick = {
                            onDismiss()
                        }
                    ) {

                        Icon(
                            imageVector =
                                Icons.Default.Close,

                            contentDescription =
                                "Close Menu"
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                )

                // UPDATE SALARY
                Button(
                    onClick = {

                        onDismiss()

                        navController.navigate(
                            "updateSalary"
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape = RoundedCornerShape(14.dp)
                ) {

                    Text("Update Salary")
                }

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // DELETE EMPLOYEES
                Button(
                    onClick = {

                        onDismiss()

                        // Delete employee logic
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),

                    shape = RoundedCornerShape(14.dp)
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.Delete,

                        contentDescription =
                            "Delete"
                    )

                    Spacer(
                        modifier =
                            Modifier.width(8.dp)
                    )

                    Text("Delete Employees")
                }
            }
        }
    }
}