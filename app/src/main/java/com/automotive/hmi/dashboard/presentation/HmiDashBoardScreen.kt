package com.automotive.hmi.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel



@Composable
fun HmiDashboardScreen(
    viewModel: HmiViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Automotive Dark Palette
    val darkBackground = Color(0xFF090A0F)
    val cardBackground = Color(0xFF161B22)
    val neonCyan = Color(0xFF00E5FF)
    val emeraldGreen = Color(0xFF10B981)
    val alertRed = Color(0xFFEF4444)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBackground)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "AAOS DIGITAL COCKPIT",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                )

                Surface(
                    color = cardBackground,
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.border(1.dp, neonCyan.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                ) {
                    Text(
                        text = "MODE: ${uiState.rideMode.name}",
                        color = neonCyan,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                Text(
                    text = "10:22 PM",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            if (uiState.errorMessage != null) {
                Surface(
                    color = alertRed.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = uiState.errorMessage ?: "",
                        color = alertRed,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${uiState.speedKmh.toInt()}",
                    fontSize = 104.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.White,
                    letterSpacing = (-2).sp
                )
                Text(
                    text = "km/h",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 3.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                DashboardStatusCard(
                    title = "MOTOR STATUS",
                    statusText = if (uiState.isMotorOn) "MOTOR ON" else "MOTOR OFF",
                    isActive = uiState.isMotorOn,
                    activeColor = emeraldGreen,
                    cardBg = cardBackground
                )

                DashboardStatusCard(
                    title = "KILL-SWITCH",
                    statusText = if (uiState.isKillSwitchActive) "ACTIVE" else "INACTIVE",
                    isActive = uiState.isKillSwitchActive,
                    activeColor = alertRed,
                    cardBg = cardBackground
                )
            }
        }
    }
}

@Composable
fun DashboardStatusCard(
    title: String,
    statusText: String,
    isActive: Boolean,
    activeColor: Color,
    cardBg: Color
) {
    val bg = if (isActive) activeColor.copy(alpha = 0.15f) else cardBg
    val textCol = if (isActive) activeColor else Color.Gray
    val borderColor = if (isActive) activeColor else Color.Transparent

    Surface(
        color = bg,
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier
            .width(240.dp)
            .border(1.dp, borderColor, RoundedCornerShape(14.dp))
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = Color.Gray.copy(alpha = 0.8f),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(textCol, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = statusText,
                    color = textCol,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}