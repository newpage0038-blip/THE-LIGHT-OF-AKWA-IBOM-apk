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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.data.model.Opportunity
import com.example.ui.theme.AkwaIbomGold
import com.example.ui.theme.AkwaIbomGreen

@Composable
fun OpportunitiesScreen(
    opportunities: List<Opportunity>,
    onToggleSave: (Opportunity) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var showOnlySaved by remember { mutableStateOf(false) }

    val categories = listOf("All", "Training", "Scholarship", "Grant", "Audition", "Job")

    val filtered = remember(opportunities, searchQuery, selectedCategory, showOnlySaved) {
        opportunities.filter { opp ->
            val matchesSearch = opp.title.contains(searchQuery, ignoreCase = true) ||
                    opp.organization.contains(searchQuery, ignoreCase = true) ||
                    opp.location.contains(searchQuery, ignoreCase = true)
            val matchesCategory = selectedCategory == "All" || opp.category == selectedCategory
            val matchesSaved = !showOnlySaved || opp.isSaved
            matchesSearch && matchesCategory && matchesSaved
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA)),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Search & Filter header
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Talents & Opportunities",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = AkwaIbomGreen
                    )
                )
                Text(
                    text = "Discover jobs, scholarships, training programmes & grants for Akwa Ibom people",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                    modifier = Modifier.padding(top = 2.dp, bottom = 12.dp)
                )

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("Search opportunities, skills, locations...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = Color.Gray)
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        // Category filter chips + Saved toggle
        item {
            Column(modifier = Modifier.padding(bottom = 10.dp)) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (showOnlySaved) AkwaIbomGold else Color.White,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (showOnlySaved) AkwaIbomGold else Color(0xFFCBD5E1)
                            ),
                            modifier = Modifier.clickable { showOnlySaved = !showOnlySaved }
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = if (showOnlySaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                                    contentDescription = null,
                                    tint = if (showOnlySaved) Color.White else Color(0xFF475569),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Saved",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (showOnlySaved) Color.White else Color(0xFF475569)
                                    )
                                )
                            }
                        }
                    }

                    items(categories) { cat ->
                        val isSelected = selectedCategory == cat && !showOnlySaved
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = if (isSelected) AkwaIbomGreen else Color.White,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) AkwaIbomGreen else Color(0xFFCBD5E1)
                            ),
                            modifier = Modifier.clickable {
                                selectedCategory = cat
                                showOnlySaved = false
                            }
                        ) {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else Color(0xFF475569)
                                ),
                                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        if (filtered.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No opportunities found matching your criteria.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
                    )
                }
            }
        } else {
            items(filtered) { opp ->
                OpportunityCard(
                    opportunity = opp,
                    onToggleSave = { onToggleSave(opp) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun OpportunityCard(
    opportunity: Opportunity,
    onToggleSave: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = when (opportunity.category) {
                        "Training" -> Color(0xFFE8F5E9)
                        "Scholarship" -> Color(0xFFFFF3E0)
                        "Grant" -> Color(0xFFE0F2F1)
                        else -> Color(0xFFF3E5F5)
                    }
                ) {
                    Text(
                        text = opportunity.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = when (opportunity.category) {
                                "Training" -> AkwaIbomGreen
                                "Scholarship" -> AkwaIbomGold
                                "Grant" -> Color(0xFF00796B)
                                else -> Color(0xFF7B1FA2)
                            }
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                IconButton(
                    onClick = onToggleSave,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = if (opportunity.isSaved) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                        contentDescription = "Save Opportunity",
                        tint = if (opportunity.isSaved) AkwaIbomGold else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = opportunity.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Text(
                text = opportunity.organization,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = AkwaIbomGreen
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = opportunity.location,
                        style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF475569))
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Deadline: ${opportunity.deadline}",
                        style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF475569))
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = opportunity.description,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF334155), lineHeight = 20.sp)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(10.dp),
                color = Color(0xFFF8FAFC),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Eligibility & Requirements:",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = AkwaIbomGreen)
                    )
                    Text(
                        text = opportunity.eligibility,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF1E293B))
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "How to Apply Offline:",
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = AkwaIbomGold)
                    )
                    Text(
                        text = opportunity.howToApply,
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF1E293B))
                    )
                }
            }
        }
    }
}
