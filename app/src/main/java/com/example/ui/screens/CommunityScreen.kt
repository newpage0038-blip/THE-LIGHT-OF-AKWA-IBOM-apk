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
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.HowToVote
import androidx.compose.material.icons.filled.ModeComment
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CommunityPoll
import com.example.data.model.CommunityPost
import com.example.data.model.PostComment
import com.example.ui.theme.AkwaIbomGold
import com.example.ui.theme.AkwaIbomGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    posts: List<CommunityPost>,
    polls: List<CommunityPoll>,
    selectedPostForComments: CommunityPost?,
    currentComments: List<PostComment>,
    onToggleLike: (CommunityPost) -> Unit,
    onOpenComments: (CommunityPost) -> Unit,
    onCloseComments: () -> Unit,
    onAddComment: (String) -> Unit,
    onCreatePost: (title: String, content: String, category: String) -> Unit,
    onVotePoll: (CommunityPoll, Int) -> Unit,
    onReportPost: (CommunityPost) -> Unit
) {
    var selectedCategoryFilter by remember { mutableStateOf("All") }
    var showCreateDialog by remember { mutableStateOf(false) }

    val categories = listOf("All", "Discussion", "Idea", "Achievement", "Community Project")

    val filteredPosts = remember(posts, selectedCategoryFilter) {
        if (selectedCategoryFilter == "All") posts else posts.filter { it.category == selectedCategoryFilter }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = AkwaIbomGreen,
                contentColor = Color.White,
                modifier = Modifier
                    .padding(bottom = 80.dp)
                    .testTag("create_post_fab")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Share Idea or Achievement")
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFAFCFA)),
            contentPadding = PaddingValues(bottom = 120.dp)
        ) {
            // Community Guidelines Banner
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC5E1A5)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Security,
                            contentDescription = null,
                            tint = AkwaIbomGreen,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Community Guidelines",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = AkwaIbomGreen
                                )
                            )
                            Text(
                                text = "Promote unity, peaceful conversations, mutual respect, and positive representation of Akwa Ibom State.",
                                style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF33691E))
                            )
                        }
                    }
                }
            }

            // Interactive Polls
            if (polls.isNotEmpty()) {
                item {
                    Text(
                        text = "Community Voice & Polls",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E2923)
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }

                items(polls) { poll ->
                    PollCard(
                        poll = poll,
                        onVote = { index -> onVotePoll(poll, index) },
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }

            // Category filter chips
            item {
                Column(modifier = Modifier.padding(top = 14.dp, bottom = 6.dp)) {
                    Text(
                        text = "Discussions & Contributions",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E2923)
                        ),
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { cat ->
                            val isSelected = selectedCategoryFilter == cat
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = if (isSelected) AkwaIbomGreen else Color.White,
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (isSelected) AkwaIbomGreen else Color(0xFFCBD5E1)
                                ),
                                modifier = Modifier.clickable { selectedCategoryFilter = cat }
                            ) {
                                Text(
                                    text = cat,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else Color(0xFF475569)
                                    ),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Posts list
            items(filteredPosts) { post ->
                CommunityPostItem(
                    post = post,
                    onToggleLike = { onToggleLike(post) },
                    onOpenComments = { onOpenComments(post) },
                    onReport = { onReportPost(post) },
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                )
            }
        }
    }

    // Create Post Dialog
    if (showCreateDialog) {
        CreatePostDialog(
            categories = categories.filter { it != "All" },
            onDismiss = { showCreateDialog = false },
            onSubmit = { title, content, cat ->
                onCreatePost(title, content, cat)
                showCreateDialog = false
            }
        )
    }

    // Comments Sheet / Dialog
    if (selectedPostForComments != null) {
        CommentsDialog(
            post = selectedPostForComments,
            comments = currentComments,
            onDismiss = onCloseComments,
            onSendComment = onAddComment
        )
    }
}

@Composable
fun PollCard(
    poll: CommunityPoll,
    onVote: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val options = remember(poll.optionsRaw) { poll.optionsRaw.split("|") }
    val votes = remember(poll.votesRaw) { poll.votesRaw.split("|").map { it.toIntOrNull() ?: 0 } }
    val hasVoted = poll.userVotedIndex != -1
    val total = if (poll.totalVotes > 0) poll.totalVotes else 1

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.HowToVote,
                        contentDescription = null,
                        tint = AkwaIbomGold,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = poll.category.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = AkwaIbomGold
                        )
                    )
                }
                Text(
                    text = "${poll.totalVotes} total votes",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = poll.question,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(12.dp))

            options.forEachIndexed { index, option ->
                val voteCount = votes.getOrElse(index) { 0 }
                val percent = (voteCount.toFloat() / total.toFloat()).coerceIn(0f, 1f)
                val isMyVote = poll.userVotedIndex == index

                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = if (isMyVote) Color(0xFFE8F5E9) else Color(0xFFF8FAFC),
                    border = androidx.compose.foundation.BorderStroke(
                        1.dp,
                        if (isMyVote) AkwaIbomGreen else Color(0xFFE2E8F0)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable(enabled = !hasVoted) { onVote(index) }
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isMyVote) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isMyVote) AkwaIbomGreen else Color(0xFF1E293B)
                                )
                            )
                            if (hasVoted) {
                                Text(
                                    text = "${(percent * 100).toInt()}% ($voteCount)",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isMyVote) AkwaIbomGreen else Color.Gray
                                    )
                                )
                            }
                        }
                        if (hasVoted) {
                            Spacer(modifier = Modifier.height(6.dp))
                            LinearProgressIndicator(
                                progress = { percent },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp)),
                                color = if (isMyVote) AkwaIbomGreen else AkwaIbomGold,
                                trackColor = Color(0xFFE2E8F0)
                            )
                        }
                    }
                }
            }

            if (!hasVoted) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tap any option above to cast your vote",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = Color.Gray,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                )
            }
        }
    }
}

@Composable
fun CommunityPostItem(
    post: CommunityPost,
    onToggleLike: () -> Unit,
    onOpenComments: () -> Unit,
    onReport: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Author row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = CircleShape,
                        color = AkwaIbomGreen,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text = post.authorName.take(1),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = post.authorName,
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            if (post.isPinned) {
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.PushPin,
                                    contentDescription = "Pinned",
                                    tint = AkwaIbomGold,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Text(
                            text = "${post.authorLga} LGA • ${post.category}",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                        )
                    }
                }

                IconButton(
                    onClick = onReport,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Flag,
                        contentDescription = "Report post",
                        tint = Color.LightGray,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = post.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF334155), lineHeight = 22.sp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Actions row: Like, Comment
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onToggleLike() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = if (post.isLikedByMe) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Like",
                            tint = if (post.isLikedByMe) Color(0xFFE53935) else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${post.likesCount}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (post.isLikedByMe) Color(0xFFE53935) else Color.Gray
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .clickable { onOpenComments() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ModeComment,
                            contentDescription = "Comments",
                            tint = AkwaIbomGreen,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "${post.commentsCount} replies",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AkwaIbomGreen
                            )
                        )
                    }
                }

                Text(
                    text = "Reply",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = AkwaIbomGreen
                    ),
                    modifier = Modifier.clickable { onOpenComments() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostDialog(
    categories: List<String>,
    onDismiss: () -> Unit,
    onSubmit: (String, String, String) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }
    var selectedCat by remember { mutableStateOf(categories.firstOrNull() ?: "Discussion") }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Share with Akwa Ibom Community",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title / Topic") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = selectedCat,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    selectedCat = cat
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                OutlinedTextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Your Thoughts / Idea / Achievement") },
                    minLines = 4,
                    maxLines = 8,
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = "Remember our values: Peaceful, respectful, and constructive engagement for the progress of our state.",
                    style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (title.isNotBlank() && content.isNotBlank()) {
                        onSubmit(title, content, selectedCat)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AkwaIbomGreen)
            ) {
                Text("Publish")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun CommentsDialog(
    post: CommunityPost,
    comments: List<PostComment>,
    onDismiss: () -> Unit,
    onSendComment: (String) -> Unit
) {
    var replyText by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Discussion & Replies",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(380.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFFF8FAFC),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = post.title,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            text = "By ${post.authorName} (${post.authorLga} LGA)",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                if (comments.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No comments yet. Be the first to share your thoughts!",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(comments) { comment ->
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFF1F5F9),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = "${comment.authorName} • ${comment.authorLga} LGA",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = AkwaIbomGreen
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = comment.comment,
                                        style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFF1E293B))
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = replyText,
                        onValueChange = { replyText = it },
                        placeholder = { Text("Write a respectful reply...") },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    IconButton(
                        onClick = {
                            if (replyText.isNotBlank()) {
                                onSendComment(replyText)
                                replyText = ""
                            }
                        },
                        modifier = Modifier
                            .size(44.dp)
                            .background(AkwaIbomGreen, CircleShape)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {}
    )
}
