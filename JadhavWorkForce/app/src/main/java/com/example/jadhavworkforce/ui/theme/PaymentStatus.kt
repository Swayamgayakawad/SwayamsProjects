package com.example.jadhavworkforce.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.jadhavworkforce.ui.theme.viewmodel.HomeViweModel

@Composable
fun PaymentStatus(
    navController: NavController,
    employeeId: Int,
    employeeName: String,
    viewModel: HomeViweModel
) {

    // Get payment list from ViewModel
    val paymentHistory by
    viewModel.paymentHistory.collectAsState()

    // Load payments for this employee
    LaunchedEffect(employeeId) {

        viewModel.getPaymentHistory(employeeId)
    }

    Scaffold(

        topBar = {

            PaymentHeader(
                employeeId = employeeId,
                employeeName = employeeName,

                onBackClick = {
                    navController.popBackStack()
                }
            )
        },

        contentWindowInsets =
            WindowInsets.navigationBars

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Text(
                text = "Payment History",

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 18.dp,
                        bottom = 10.dp
                    ),

                fontSize = 20.sp,

                fontWeight =
                    FontWeight.Bold,

                color =
                    MaterialTheme
                        .colorScheme
                        .onBackground
            )


            if (paymentHistory.isEmpty()) {

                // No payment records

                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "No payment history",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,

                        color = MaterialTheme
                                .colorScheme
                                .onSurface
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Payment records will appear here.",
                        fontSize = 14.sp,
                        color = MaterialTheme
                                .colorScheme
                                .onSurfaceVariant
                    )
                }

            } else {

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = paymentHistory,
                        key = { payment ->
                            payment.id
                        }

                    ) { payment ->

                        HistoryCard(date = payment.date, amount = payment.paymentAmounts)
                    }
                }
            }
        }
    }
}


@Composable
fun PaymentHeader(
    employeeId: Int,
    employeeName: String,
    onBackClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(
                    start = 8.dp,
                    end = 20.dp
                ),

            verticalAlignment =
                Alignment.CenterVertically
        ) {


            IconButton(
                onClick = onBackClick,
                modifier = Modifier.size(48.dp)
            ) {

                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme
                            .colorScheme
                            .onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }


            Spacer(
                modifier = Modifier.width(8.dp)
            )


            Column(
                modifier = Modifier.weight(1f),

                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = employeeName,

                    fontSize = 19.sp,

                    fontWeight =
                        FontWeight.Bold,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurface,

                    maxLines = 1,

                    overflow =
                        TextOverflow.Ellipsis
                )

                Spacer(
                    modifier = Modifier.height(2.dp)
                )

                Text(
                    text = "ID: $employeeId",

                    fontSize = 13.sp,

                    fontWeight =
                        FontWeight.Medium,

                    color =
                        MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
            }
        }


        HorizontalDivider(
            color = MaterialTheme
                    .colorScheme
                    .outlineVariant
        )
    }
}


@Composable
fun HistoryCard(
    date: String,
    amount: Int
) {

    Card(

        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
                containerColor = MaterialTheme
                        .colorScheme
                        .surface
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {


            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Payment Date",
                    fontSize = 12.sp,
                    color = MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = date,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme
                            .colorScheme
                            .onSurface
                )
            }


            Column(
                horizontalAlignment =
                    Alignment.End
            ) {
                Text(
                    text = "Amount",
                    fontSize = 12.sp,
                    color = MaterialTheme
                            .colorScheme
                            .onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "₹$amount",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme
                            .colorScheme
                            .primary
                )
            }
        }
    }
}