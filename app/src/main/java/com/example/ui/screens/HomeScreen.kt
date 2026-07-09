package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.CyanAccent
import com.example.ui.theme.PurplePrimary
import com.example.ui.theme.PurpleSecondary
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun HomeScreen() {
    var isConnected by remember { mutableStateOf(true) } // Default to connected to show the design
    var isConnecting by remember { mutableStateOf(false) }

    // Mock connection logic
    LaunchedEffect(isConnecting) {
        if (isConnecting) {
            delay(2000) // Simulate connection delay
            isConnected = true
            isConnecting = false
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            Brush.linearGradient(listOf(PurplePrimary, PurpleSecondary))
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Bolt, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)) {
                            append("POWER ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = PurplePrimary)) {
                            append("VPN")
                        }
                    },
                    fontSize = 20.sp,
                    letterSpacing = (-0.5).sp
                )
            }
            
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.WorkspacePremium, contentDescription = "Premium", tint = PurplePrimary)
            }
        }

        // Connection Status
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top = 16.dp)) {
            Text(
                text = when {
                    isConnecting -> "CONNECTING"
                    isConnected -> "CONNECTED"
                    else -> "DISCONNECTED"
                },
                color = if (isConnected) CyanAccent else MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                letterSpacing = 2.sp
            )
            Text(
                text = if (isConnected) "01:42:58" else "--:--:--",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 32.sp,
                fontWeight = FontWeight.Light,
                letterSpacing = 1.sp
            )
        }

        // Connect Button
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            ConnectButton(
                isConnected = isConnected,
                isConnecting = isConnecting,
                onClick = {
                    if (isConnected) {
                        isConnected = false
                    } else if (!isConnecting) {
                        isConnecting = true
                    }
                }
            )
        }

        // Real Time Graph (Mini)
        if (isConnected) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 4.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                val heights = listOf(0.3f, 0.45f, 0.2f, 0.6f, 0.85f, 0.5f, 0.7f, 0.4f, 0.55f, 0.9f)
                heights.forEach { h ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(h)
                            .clip(RoundedCornerShape(topStart = 2.dp, topEnd = 2.dp))
                            .background(CyanAccent.copy(alpha = h * 0.8f))
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        // Stats Grid
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(title = "PING", value = "24", unit = "ms", modifier = Modifier.weight(1f))
            StatCard(title = "DOWNLOAD", value = "142.5", unit = "Mb", modifier = Modifier.weight(1f))
            StatCard(title = "UPLOAD", value = "89.2", unit = "Mb", modifier = Modifier.weight(1f))
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Server Card
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(PurplePrimary.copy(alpha = 0.2f))
                        .border(1.dp, PurplePrimary.copy(alpha = 0.4f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Public, contentDescription = null, tint = PurplePrimary.copy(alpha = 0.8f))
                }
                Column {
                    Text(
                        text = "United States",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "New York #4 • 104.28.13.4",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
            Icon(Icons.Filled.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
fun StatCard(title: String, value: String, unit: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.5).sp
        )
        Row(verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                text = value,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = unit,
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                fontSize = 10.sp,
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}

@Composable
fun ConnectButton(isConnected: Boolean, isConnecting: Boolean, onClick: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition()
    
    val pulse1 by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = if (isConnected || isConnecting) 1.2f else 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    val alpha1 by infiniteTransition.animateFloat(
        initialValue = 0.5f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Box(
        modifier = Modifier.size(256.dp),
        contentAlignment = Alignment.Center
    ) {
        // Outer pulsing ring
        if (isConnected || isConnecting) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .scale(pulse1)
                    .border(1.dp, PurplePrimary.copy(alpha = alpha1), CircleShape)
            )
        }
        
        // Inner fixed ring
        Box(
            modifier = Modifier
                .size(208.dp)
                .border(1.dp, PurplePrimary.copy(alpha = 0.4f), CircleShape)
        )
        
        // Main button
        Box(
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(PurplePrimary, PurpleSecondary)
                    )
                )
                .clickable(onClick = onClick)
                .padding(6.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.PowerSettingsNew,
                    contentDescription = "Connect",
                    modifier = Modifier.size(48.dp),
                    tint = if (isConnected) CyanAccent else Color.White
                )
            }
        }
    }
}

