package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.model.Announcement
import com.example.data.model.CommunityEvent
import com.example.data.model.CommunityPost
import com.example.ui.theme.AkwaIbomGold
import com.example.ui.theme.AkwaIbomGreen
import com.example.ui.theme.AkwaIbomSun
import com.example.ui.viewmodel.AppSection

@Composable
fun HomeScreen(
    announcements: List<Announcement>,
    featuredPosts: List<CommunityPost>,
    upcomingEvents: List<CommunityEvent>,
    isGreetingActive: Boolean,
    onTriggerGreeting: () -> Unit,
    onNavigate: (AppSection) -> Unit,
    onOpenPostComments: (CommunityPost) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFAFCFA)),
        contentPadding = PaddingValues(bottom = 96.dp)
    ) {
        // Hero Banner with Emblem
        item {
            HeroHeaderCard(
                isGreetingActive = isGreetingActive,
                onTriggerGreeting = onTriggerGreeting,
                onNavigate = onNavigate
            )
        }

        // Quick Navigation Grid
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Explore & Connect",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1E2923)
                    ),
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickNavButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Forum,
                        title = "Community",
                        subtitle = "Discussions & Polls",
                        tint = AkwaIbomGreen,
                        onClick = { onNavigate(AppSection.COMMUNITY) },
                        testTag = "nav_community_btn"
                    )
                    QuickNavButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Restaurant,
                        title = "Culture & Food",
                        subtitle = "Ekpang & Heritage",
                        tint = AkwaIbomGold,
                        onClick = { onNavigate(AppSection.CULTURE) },
                        testTag = "nav_culture_btn"
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    QuickNavButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.School,
                        title = "Opportunities",
                        subtitle = "Jobs & Grants",
                        tint = Color(0xFF00796B),
                        onClick = { onNavigate(AppSection.OPPORTUNITIES) },
                        testTag = "nav_opps_btn"
                    )
                    QuickNavButton(
                        modifier = Modifier.weight(1f),
                        icon = Icons.Default.Storefront,
                        title = "Business Hub",
                        subtitle = "Local Directory",
                        tint = Color(0xFF7B1FA2),
                        onClick = { onNavigate(AppSection.BUSINESS) },
                        testTag = "nav_business_btn"
                    )
                }
            }
        }

        // Important Announcements
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Campaign,
                        contentDescription = null,
                        tint = AkwaIbomGold,
                        modifier = Modifier.size(22.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Announcements & Updates",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E2923)
                        )
                    )
                }
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = AkwaIbomGreen,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.clickable { onNavigate(AppSection.NEWS) }
                )
            }
        }

        items(announcements.take(3)) { announcement ->
            AnnouncementCard(
                announcement = announcement,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Upcoming Events Carousel
        item {
            Column(modifier = Modifier.padding(top = 18.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Event,
                            contentDescription = null,
                            tint = AkwaIbomGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Upcoming Gatherings",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1E2923)
                            )
                        )
                    }
                    Text(
                        text = "Calendar",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = AkwaIbomGreen,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.clickable { onNavigate(AppSection.EVENTS) }
                    )
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(upcomingEvents) { event ->
                        HomeEventCard(event = event, onClick = { onNavigate(AppSection.EVENTS) })
                    }
                }
            }
        }

        // Featured Community Voices
        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 20.dp, bottom = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Voices of Akwa Ibom",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E2923)
                        )
                    )
                    Text(
                        text = "Join Talk",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = AkwaIbomGreen,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.clickable { onNavigate(AppSection.COMMUNITY) }
                    )
                }
            }
        }

        items(featuredPosts.take(2)) { post ->
            HomePostCard(
                post = post,
                onCommentClick = { onOpenPostComments(post) },
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
            )
        }

        // Membership ID teaser
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8F3)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC8E6C9)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .clickable { onNavigate(AppSection.MEMBERSHIP) }
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = CircleShape,
                        color = AkwaIbomGreen,
                        modifier = Modifier.size(44.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Badge,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Your Community Identity Card",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "View your digital verification badge and member profile",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF4A5568))
                        )
                    }
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "View Profile",
                        tint = AkwaIbomGreen
                    )
                }
            }
        }
    }
}

@Composable
private fun HeroHeaderCard(
    isGreetingActive: Boolean,
    onTriggerGreeting: () -> Unit,
    onNavigate: (AppSection) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_light_of_akwa_ibom),
                contentDescription = "The Light of Akwa Ibom",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Greeting Interactive Ribbon
            Surface(
                shape = RoundedCornerShape(14.dp),
                color = if (isGreetingActive) Color(0xFFFFF3E0) else Color(0xFFE8F5E9),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    if (isGreetingActive) AkwaIbomGold else AkwaIbomGreen.copy(alpha = 0.4f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onTriggerGreeting() }
                    .testTag("home_greeting_card")
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = if (isGreetingActive) AkwaIbomGold else AkwaIbomGreen,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (isGreetingActive) "BRIGHTER WE SHINE!" else "THE LIGHT! (Tap to Chant)",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (isGreetingActive) AkwaIbomGold else AkwaIbomGreen
                            )
                        )
                    }
                    Text(
                        text = "CHANT",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = AkwaIbomGold
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickNavButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    title: String,
    subtitle: String,
    tint: Color,
    onClick: () -> Unit,
    testTag: String
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier
            .height(96.dp)
            .testTag(testTag),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = tint.copy(alpha = 0.12f),
                modifier = Modifier.size(34.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = title, tint = tint, modifier = Modifier.size(20.dp))
                }
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold, fontSize = 13.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontSize = 10.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun AnnouncementCard(
    announcement: Announcement,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = if (announcement.isImportant) Color(0xFFFFEBEE) else Color(0xFFE8F5E9)
                ) {
                    Text(
                        text = if (announcement.isImportant) "IMPORTANT" else announcement.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (announcement.isImportant) Color(0xFFC62828) else AkwaIbomGreen
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }

                Text(
                    text = announcement.date,
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = announcement.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = announcement.content,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF334155), lineHeight = 20.sp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Issued by: ${announcement.author}",
                style = MaterialTheme.typography.labelSmall.copy(color = Color(0xFF64748B), fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
            )
        }
    }
}

@Composable
fun HomeEventCard(
    event: CommunityEvent,
    onClick: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color.White),
        modifier = Modifier
            .width(260.dp)
            .height(140.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = AkwaIbomGreen.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = event.category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = AkwaIbomGreen
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                    )
                }
                Text(
                    text = "${event.attendeeCount} going",
                    style = MaterialTheme.typography.labelSmall.copy(color = AkwaIbomGold, fontWeight = FontWeight.Bold)
                )
            }

            Column {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${event.dateString} • ${event.venue}, ${event.lga}",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF64748B)),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
fun HomePostCard(
    post: CommunityPost,
    onCommentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = AkwaIbomGreen.copy(alpha = 0.15f),
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = post.authorName.take(1),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AkwaIbomGreen
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(
                            text = post.authorName,
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "${post.authorLga} LGA • ${post.category}",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF334155)),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${post.likesCount} ❤️ • ${post.commentsCount} comments",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                )

                Text(
                    text = "Reply / Read",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = AkwaIbomGreen,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.clickable { onCommentClick() }
                )
            }
        }
    }
}
