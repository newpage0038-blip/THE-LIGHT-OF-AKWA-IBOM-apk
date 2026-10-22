package com.example.ui.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Balance
import androidx.compose.material.icons.filled.Diversity1
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Handshake
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.AkwaIbomGold
import com.example.ui.theme.AkwaIbomGreen

@Composable
fun AboutScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA)),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 96.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // App Identity Header
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_light_of_akwa_ibom),
                        contentDescription = "Emblem",
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Fit
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "THE LIGHT OF AKWA IBOM",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            color = AkwaIbomGreen
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "SLOGAN: BRIGHTER WE SHINE!",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = AkwaIbomGold
                        ),
                        modifier = Modifier.padding(top = 4.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Vision Card
        item {
            AboutSectionCard(
                title = "Our Vision",
                icon = Icons.Default.WbSunny,
                tint = AkwaIbomGold,
                content = "To build a united, prosperous, and culturally vibrant Akwa Ibom State where every son and daughter has the opportunity, support, and platform to excel and shine brightly at home and across the globe."
            )
        }

        // Mission Card
        item {
            AboutSectionCard(
                title = "Our Mission",
                icon = Icons.Default.AutoAwesome,
                tint = AkwaIbomGreen,
                content = "To bring the people of Akwa Ibom together through unity, cultural celebration, community engagement, education, empowerment, and economic opportunities. We celebrate our identity, support emerging talents, and foster mutual cooperation across all 31 Local Government Areas."
            )
        }

        // Collective Leadership Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC8E6C9)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Groups, contentDescription = null, tint = AkwaIbomGreen)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Collective Leadership Philosophy",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AkwaIbomGreen
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This platform is not built around any single individual. It belongs to all Akwa Ibom people! It is founded on collective participation, shared ideas, servant leadership, transparency, and equal voice for youth, women, elders, and artisans alike.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF1B5E20), lineHeight = 21.sp)
                    )
                }
            }
        }

        // Community Greeting Meaning
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF8E1)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFFE082)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "The Community Greeting",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AkwaIbomGold
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "Leader: \"THE LIGHT!\"",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AkwaIbomGreen
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Members: \"BRIGHTER WE SHINE!\"",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AkwaIbomGold
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "This call and response represents unshakable unity, boundless hope, and our collective commitment to lifting each other up so our state continues to illuminate Nigeria and the world.",
                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF5D4037), lineHeight = 19.sp)
                    )
                }
            }
        }

        // 7 Core Values
        item {
            Text(
                text = "Our Core Community Values",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E2923)
                ),
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        item { ValueItem(number = "1", title = "Unity and Togetherness", desc = "Standing as one family across all communities and dialects.", icon = Icons.Default.Diversity1) }
        item { ValueItem(number = "2", title = "Respect for Every Member", desc = "Treating every son, daughter, and elder with dignity.", icon = Icons.Default.Favorite) }
        item { ValueItem(number = "3", title = "Peaceful Conversations", desc = "Constructive dialogue, tolerance, and calm conflict resolution.", icon = Icons.Default.Forum) }
        item { ValueItem(number = "4", title = "Pride in Akwa Ibom Identity", desc = "Honouring our languages, foods, crafts, and rich cultural heritage.", icon = Icons.Default.Psychology) }
        item { ValueItem(number = "5", title = "Equal Opportunities to Participate", desc = "Open access to youth, women, and elders to voice ideas and thrive.", icon = Icons.Default.Balance) }
        item { ValueItem(number = "6", title = "Transparency & Accountability", desc = "Clear stewardship, open communication, and honest conduct.", icon = Icons.Default.VerifiedUser) }
        item { ValueItem(number = "7", title = "Cooperation & Mutual Support", desc = "Patronizing local businesses and mentoring the next generation.", icon = Icons.Default.Handshake) }
    }
}

@Composable
fun AboutSectionCard(
    title: String,
    icon: ImageVector,
    tint: Color,
    content: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = tint.copy(alpha = 0.12f),
                    modifier = Modifier.size(36.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(imageVector = icon, contentDescription = null, tint = tint, modifier = Modifier.size(20.dp))
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF334155), lineHeight = 22.sp)
            )
        }
    }
}

@Composable
fun ValueItem(
    number: String,
    title: String,
    desc: String,
    icon: ImageVector
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = AkwaIbomGreen,
                modifier = Modifier.size(32.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = number,
                        style = MaterialTheme.typography.titleSmall.copy(color = Color.White, fontWeight = FontWeight.Bold)
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B))
                )
            }
        }
    }
}
