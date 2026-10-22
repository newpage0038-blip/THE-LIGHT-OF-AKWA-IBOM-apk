package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.UserProfile
import com.example.ui.theme.AkwaIbomGold
import com.example.ui.theme.AkwaIbomGreen
import com.example.ui.theme.AkwaIbomSun

val AkwaIbomLGAs = listOf(
    "Abak", "Eastern Obolo", "Eket", "Esit Eket", "Essien Udim", "Etim Ekpo", "Etinan",
    "Ibeno", "Ibesikpo Asutan", "Ibiono Ibom", "Ika", "Ikono", "Ikot Abasi", "Ikot Ekpene",
    "Ini", "Itu", "Mbo", "Mkpat Enin", "Nsit Atai", "Nsit Ibom", "Nsit Ubium", "Obot Akara",
    "Okobo", "Onna", "Oron", "Oruk Anam", "Udung Uko", "Ukanafun", "Uruan", "Urue-Offong/Oruko", "Uyo"
)

@Composable
fun MembershipScreen(
    profile: UserProfile?,
    onSaveProfile: (name: String, lga: String, phone: String, email: String, occupation: String, talent: String, bio: String) -> Unit
) {
    var showEditDialog by remember { mutableStateOf(false) }
    val currentProfile = profile ?: UserProfile()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA)),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column {
                Text(
                    text = "Membership & Profile",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = AkwaIbomGreen
                    )
                )
                Text(
                    text = "Your official Light of Akwa Ibom digital member identification",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Digital Membership Card
        item {
            DigitalMemberCard(profile = currentProfile)
        }

        // Action: Edit Profile
        item {
            OutlinedButton(
                onClick = { showEditDialog = true },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = AkwaIbomGreen)
            ) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Update My Profile Details", fontWeight = FontWeight.Bold)
            }
        }

        // Profile Details Breakdown
        item {
            ElevatedCard(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Member Information",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    ProfileDetailRow(icon = Icons.Default.Person, label = "Full Name", value = currentProfile.fullName)
                    ProfileDetailRow(icon = Icons.Default.LocationOn, label = "LGA of Origin", value = "${currentProfile.lga} Local Government Area")
                    ProfileDetailRow(icon = Icons.Default.Work, label = "Occupation / Enterprise", value = currentProfile.occupation)
                    ProfileDetailRow(icon = Icons.Default.Phone, label = "Phone", value = currentProfile.phoneNumber)
                    ProfileDetailRow(icon = Icons.Default.Email, label = "Email", value = currentProfile.email)

                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFF8FAFC),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = "Talents & Key Interests:",
                                style = MaterialTheme.typography.labelSmall.copy(color = AkwaIbomGreen, fontWeight = FontWeight.Bold)
                            )
                            Text(text = currentProfile.talentOrInterest, style = MaterialTheme.typography.bodySmall)

                            Spacer(modifier = Modifier.height(6.dp))

                            Text(
                                text = "Personal Bio:",
                                style = MaterialTheme.typography.labelSmall.copy(color = AkwaIbomGold, fontWeight = FontWeight.Bold)
                            )
                            Text(text = currentProfile.bio, style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF475569)))
                        }
                    }
                }
            }
        }

        // Community Badge Privileges
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC8E6C9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Verified, contentDescription = null, tint = AkwaIbomGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Verified Member Privileges",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AkwaIbomGreen
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "• Priority registration for scholarships, grants, and youth training slots.\n• Free business showcase listings in the Business Directory.\n• Voting rights in community decision polls and annual summits.\n• Access to mentorship circles with Akwa Ibom leaders.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF1B5E20), lineHeight = 20.sp)
                    )
                }
            }
        }
    }

    if (showEditDialog) {
        EditProfileDialog(
            current = currentProfile,
            onDismiss = { showEditDialog = false },
            onSave = { name, lga, phone, email, occ, talent, bio ->
                onSaveProfile(name, lga, phone, email, occ, talent, bio)
                showEditDialog = false
            }
        )
    }
}

@Composable
fun DigitalMemberCard(profile: UserProfile) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(220.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF0A582E),
                            AkwaIbomGreen,
                            Color(0xFF15803D)
                        )
                    )
                )
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Top row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "THE LIGHT OF AKWA IBOM",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White,
                                letterSpacing = 1.sp
                            )
                        )
                        Text(
                            text = "BRIGHTER WE SHINE!",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = AkwaIbomSun,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White.copy(alpha = 0.2f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = AkwaIbomSun,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "VERIFIED",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }
                }

                // Member Name and LGA
                Column {
                    Text(
                        text = profile.fullName,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = Color.White,
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = "Origin: ${profile.lga} LGA • Member since ${profile.joinDate}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    )
                }

                // Bottom row with QR code & ID
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Column {
                        Text(
                            text = "MEMBER ID NUMBER",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 9.sp
                            )
                        )
                        Text(
                            text = profile.memberIdNumber,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = AkwaIbomSun,
                                letterSpacing = 1.sp
                            )
                        )
                    }

                    // QR Visual simulation
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = Color.White,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = "Verification QR",
                                tint = Color.Black,
                                modifier = Modifier.size(38.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileDetailRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(imageVector = icon, contentDescription = null, tint = AkwaIbomGreen, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(10.dp))
        Column {
            Text(text = label, style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
            Text(text = value, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileDialog(
    current: UserProfile,
    onDismiss: () -> Unit,
    onSave: (String, String, String, String, String, String, String) -> Unit
) {
    var name by remember { mutableStateOf(current.fullName) }
    var selectedLga by remember { mutableStateOf(current.lga) }
    var phone by remember { mutableStateOf(current.phoneNumber) }
    var email by remember { mutableStateOf(current.email) }
    var occ by remember { mutableStateOf(current.occupation) }
    var talent by remember { mutableStateOf(current.talentOrInterest) }
    var bio by remember { mutableStateOf(current.bio) }
    var lgaExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Edit Member Profile",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = lgaExpanded,
                    onExpandedChange = { lgaExpanded = !lgaExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedLga,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("LGA of Origin (31 LGAs)") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = lgaExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = lgaExpanded,
                        onDismissRequest = { lgaExpanded = false }
                    ) {
                        AkwaIbomLGAs.forEach { lga ->
                            DropdownMenuItem(
                                text = { Text(lga) },
                                onClick = {
                                    selectedLga = lga
                                    lgaExpanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = occ,
                    onValueChange = { occ = it },
                    label = { Text("Occupation / Enterprise") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = talent,
                    onValueChange = { talent = it },
                    label = { Text("Talent, Skill or Interest") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Personal Bio") },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(name, selectedLga, phone, email, occ, talent, bio)
                },
                colors = ButtonDefaults.buttonColors(containerColor = AkwaIbomGreen)
            ) {
                Text("Save Profile")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
