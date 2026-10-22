package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Announcement
import com.example.data.model.CommunityPost
import com.example.ui.theme.AkwaIbomGold
import com.example.ui.theme.AkwaIbomGreen

@Composable
fun AdminScreen(
    isAuthenticated: Boolean,
    announcements: List<Announcement>,
    allPosts: List<CommunityPost>,
    onAuthenticate: (String) -> Boolean,
    onLogout: () -> Unit,
    onAddAnnouncement: (title: String, content: String, category: String, isImportant: Boolean) -> Unit,
    onDeleteAnnouncement: (Long) -> Unit,
    onApprovePost: (Long) -> Unit,
    onDeletePost: (Long) -> Unit,
    onAddEvent: (title: String, date: String, time: String, venue: String, lga: String, cat: String, desc: String) -> Unit,
    onAddOpportunity: (title: String, org: String, cat: String, loc: String, deadline: String, desc: String, elig: String, apply: String) -> Unit
) {
    if (!isAuthenticated) {
        AdminLoginScreen(onLogin = onAuthenticate)
    } else {
        AdminDashboardContent(
            announcements = announcements,
            allPosts = allPosts,
            onLogout = onLogout,
            onAddAnnouncement = onAddAnnouncement,
            onDeleteAnnouncement = onDeleteAnnouncement,
            onApprovePost = onApprovePost,
            onDeletePost = onDeletePost,
            onAddEvent = onAddEvent,
            onAddOpportunity = onAddOpportunity
        )
    }
}

@Composable
fun AdminLoginScreen(
    onLogin: (String) -> Boolean
) {
    var pin by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA))
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    shape = CircleShape,
                    color = AkwaIbomGreen.copy(alpha = 0.12f),
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AdminPanelSettings,
                            contentDescription = null,
                            tint = AkwaIbomGreen,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Administrator Access",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = AkwaIbomGreen)
                )

                Text(
                    text = "Enter secure administrator PIN to manage announcements, events, and moderation.",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
                )

                OutlinedTextField(
                    value = pin,
                    onValueChange = {
                        pin = it
                        errorMessage = null
                    },
                    label = { Text("Admin PIN") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = null
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("admin_pin_input")
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val success = onLogin(pin)
                        if (!success) {
                            errorMessage = "Incorrect PIN. (Default demo PIN is 2026)"
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AkwaIbomGreen),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("admin_login_submit_btn")
                ) {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Authenticate", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Demo PIN: 2026",
                    style = MaterialTheme.typography.labelSmall.copy(color = AkwaIbomGold, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun AdminDashboardContent(
    announcements: List<Announcement>,
    allPosts: List<CommunityPost>,
    onLogout: () -> Unit,
    onAddAnnouncement: (String, String, String, Boolean) -> Unit,
    onDeleteAnnouncement: (Long) -> Unit,
    onApprovePost: (Long) -> Unit,
    onDeletePost: (Long) -> Unit,
    onAddEvent: (String, String, String, String, String, String, String) -> Unit,
    onAddOpportunity: (String, String, String, String, String, String, String, String) -> Unit
) {
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }
    var newCategory by remember { mutableStateOf("General") }
    var isUrgent by remember { mutableStateOf(false) }

    val reportedPosts = remember(allPosts) { allPosts.filter { it.isReported } }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA)),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Admin Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Admin Dashboard",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = AkwaIbomGreen
                        )
                    )
                    Text(
                        text = "Control center & moderation queue",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                    )
                }

                OutlinedButton(
                    onClick = onLogout,
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                ) {
                    Icon(imageVector = Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Logout", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Stats Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AdminStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Announcements",
                    value = "${announcements.size}",
                    color = AkwaIbomGreen
                )
                AdminStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Flagged Posts",
                    value = "${reportedPosts.size}",
                    color = if (reportedPosts.isNotEmpty()) Color.Red else AkwaIbomGold
                )
                AdminStatCard(
                    modifier = Modifier.weight(1f),
                    title = "Active Posts",
                    value = "${allPosts.size}",
                    color = Color(0xFF00796B)
                )
            }
        }

        // Moderation Queue
        if (reportedPosts.isNotEmpty()) {
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFCDD2)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Report, contentDescription = null, tint = Color.Red)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Flagged Posts Awaiting Review (${reportedPosts.size})",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFB71C1C)
                                )
                            )
                        }
                    }
                }
            }

            items(reportedPosts) { post ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF9A9A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text(text = post.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text(text = "By ${post.authorName} (${post.authorLga} LGA)", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = post.content, style = MaterialTheme.typography.bodySmall)

                        Spacer(modifier = Modifier.height(10.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { onApprovePost(post.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = AkwaIbomGreen),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Approve Post")
                            }

                            Button(
                                onClick = { onDeletePost(post.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                                shape = RoundedCornerShape(8.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(imageVector = Icons.Default.Delete, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }

        // Publish Announcement Form
        item {
            ElevatedCard(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Campaign, contentDescription = null, tint = AkwaIbomGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Publish Official Announcement",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                    }

                    OutlinedTextField(
                        value = newTitle,
                        onValueChange = { newTitle = it },
                        label = { Text("Announcement Headline") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newCategory,
                        onValueChange = { newCategory = it },
                        label = { Text("Category (General, Youth, Agriculture, Culture)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = newContent,
                        onValueChange = { newContent = it },
                        label = { Text("Announcement Message Body") },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isUrgent,
                            onCheckedChange = { isUrgent = it }
                        )
                        Text(text = "Mark as Urgent / Important Banner", style = MaterialTheme.typography.bodySmall)
                    }

                    Button(
                        onClick = {
                            if (newTitle.isNotBlank() && newContent.isNotBlank()) {
                                onAddAnnouncement(newTitle, newContent, newCategory, isUrgent)
                                newTitle = ""
                                newContent = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AkwaIbomGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Publish Announcement", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // Manage Existing Announcements
        item {
            Text(
                text = "Manage Existing Announcements",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(announcements) { ann ->
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = ann.title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Text(text = "${ann.category} • ${ann.date}", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                    }

                    IconButton(onClick = { onDeleteAnnouncement(ann.id) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }
            }
        }
    }
}

@Composable
fun AdminStatCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    color: Color
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.3f)),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Black,
                    color = color
                )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray),
                maxLines = 1
            )
        }
    }
}
