package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AkwaIbomGold
import com.example.ui.theme.AkwaIbomGreen

data class FaqItem(val question: String, val answer: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    onSendMessage: (category: String, message: String) -> Unit
) {
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("Suggestion") }
    var submittedNotice by remember { mutableStateOf(false) }

    val categories = listOf("Suggestion", "Report Inappropriate Content", "Ask Question", "Partnership")

    val faqs = remember {
        listOf(
            FaqItem(
                question = "How does this app work completely offline?",
                answer = "All community data, language guides, recipes, and resources are securely stored in your device's local database. You can read, participate, and bookmark opportunities without using any mobile data."
            ),
            FaqItem(
                question = "How do I verify my membership status?",
                answer = "Visit the 'Membership' tab, click 'Update My Profile Details', select your LGA in Akwa Ibom, and save. Your verified member ID card and QR badge will generate instantly."
            ),
            FaqItem(
                question = "How are businesses verified?",
                answer = "Administrators verify local businesses based on registered community physical presence and trade honesty to protect buyers and foster mutual trust."
            ),
            FaqItem(
                question = "How do I report inappropriate discussions?",
                answer = "Tap the flag icon on any discussion post or submit a report directly on this page. Our moderation team reviews all flagged posts."
            )
        )
    }

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
                    text = "Contact & Community Support",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = AkwaIbomGreen
                    )
                )
                Text(
                    text = "We are here to assist, listen to your suggestions, and maintain a safe platform",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }

        // Contact Channels
        item {
            ElevatedCard(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "Official Support Desks",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = AkwaIbomGreen.copy(alpha = 0.12f), modifier = Modifier.size(36.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = AkwaIbomGreen, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Secretariat Liaison Office", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                            Text(text = "E-Library Complex, IBB Way, Uyo, Akwa Ibom State", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = AkwaIbomGreen.copy(alpha = 0.12f), modifier = Modifier.size(36.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = AkwaIbomGreen, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Community Helpline & Inquiries", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                            Text(text = "+234 (0) 803 123 4567 • Mon - Fri (8am - 5pm)", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(shape = CircleShape, color = AkwaIbomGreen.copy(alpha = 0.12f), modifier = Modifier.size(36.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Default.Email, contentDescription = null, tint = AkwaIbomGreen, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(text = "Official Email Desk", style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray))
                            Text(text = "contact@lightofakwaibom.org", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold))
                        }
                    }
                }
            }
        }

        // Feedback / Report Form
        item {
            ElevatedCard(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "Submit Suggestion or Report",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        categories.forEach { cat ->
                            val isSelected = selectedCategory == cat
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (isSelected) AkwaIbomGreen else Color(0xFFF1F5F9),
                                modifier = Modifier
                                    .clickable { selectedCategory = cat }
                                    .weight(1f)
                            ) {
                                Text(
                                    text = if (cat == "Report Inappropriate Content") "Report" else cat,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) Color.White else Color(0xFF475569),
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        fontSize = 10.sp
                                    ),
                                    modifier = Modifier.padding(vertical = 6.dp, horizontal = 4.dp),
                                    maxLines = 1,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = subject,
                        onValueChange = { subject = it },
                        label = { Text("Subject / Topic") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        label = { Text("Details of your message, suggestion, or complaint") },
                        minLines = 3,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (submittedNotice) {
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = Color(0xFFE8F5E9),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "✓ Thank you! Your submission has been securely recorded for the administrators.",
                                style = MaterialTheme.typography.bodySmall.copy(color = AkwaIbomGreen, fontWeight = FontWeight.Bold),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }

                    Button(
                        onClick = {
                            if (message.isNotBlank()) {
                                onSendMessage(selectedCategory, "$subject: $message")
                                message = ""
                                subject = ""
                                submittedNotice = true
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = AkwaIbomGreen),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(imageVector = Icons.Default.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Send Message", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // FAQs
        item {
            Text(
                text = "Frequently Asked Questions",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E2923)
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        items(faqs) { faq ->
            FaqCard(faq = faq)
        }
    }
}

@Composable
fun FaqCard(faq: FaqItem) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = faq.question,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = AkwaIbomGreen
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = faq.answer,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF334155), lineHeight = 19.sp)
                    )
                }
            }
        }
    }
}
