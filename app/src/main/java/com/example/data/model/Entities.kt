package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "announcements")
data class Announcement(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val category: String, // "General", "Youth", "Agriculture", "Culture", "Governance"
    val date: String,
    val author: String,
    val isImportant: Boolean = false
)

@Entity(tableName = "community_posts")
data class CommunityPost(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val content: String,
    val authorName: String,
    val authorLga: String,
    val category: String, // "Discussion", "Idea", "Achievement", "Community Project"
    val timestamp: Long = System.currentTimeMillis(),
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val isLikedByMe: Boolean = false,
    val isPinned: Boolean = false,
    val isReported: Boolean = false
)

@Entity(tableName = "post_comments")
data class PostComment(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val postId: Long,
    val authorName: String,
    val authorLga: String,
    val comment: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "community_polls")
data class CommunityPoll(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val question: String,
    val category: String,
    val optionsRaw: String, // pipe-delimited: "Option A|Option B|Option C"
    val votesRaw: String,   // pipe-delimited integers: "12|34|18"
    val userVotedIndex: Int = -1, // -1 means not voted yet
    val totalVotes: Int = 0
)

@Entity(tableName = "culture_items")
data class CultureItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val type: String, // "Delicacy", "Language", "Festival", "Dance", "History"
    val title: String,
    val subtitle: String,
    val description: String,
    val guideOrDetails: String,
    val imageResName: String = "", // e.g. "img_ekpang_nkukwo"
    val audioPhonetic: String = ""
)

@Entity(tableName = "opportunities")
data class Opportunity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val organization: String,
    val category: String, // "Job", "Scholarship", "Training", "Grant", "Audition"
    val location: String,
    val deadline: String,
    val description: String,
    val eligibility: String,
    val howToApply: String,
    val isSaved: Boolean = false
)

@Entity(tableName = "businesses")
data class Business(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String, // "Food & Catering", "Tech & Innovation", "Fashion & Crafts", "Agriculture", "Services"
    val lga: String,
    val phone: String,
    val email: String,
    val address: String,
    val description: String,
    val isVerified: Boolean = true
)

@Entity(tableName = "events")
data class CommunityEvent(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val dateString: String,
    val timeString: String,
    val venue: String,
    val lga: String,
    val category: String, // "Cultural", "Youth", "Business", "Town Hall"
    val description: String,
    val isAttending: Boolean = false,
    val attendeeCount: Int = 0
)

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val fullName: String = "Anietie Udoh",
    val lga: String = "Uyo",
    val phoneNumber: String = "+234 803 123 4567",
    val email: String = "anietie@lightofakwaibom.org",
    val occupation: String = "Agri-Tech Enthusiast & Artisan",
    val talentOrInterest: String = "Community Development & Cultural Arts",
    val bio: String = "Proud Akwa Ibomite passionate about community empowerment, unity, and showcasing our heritage to the world.",
    val memberIdNumber: String = "AKW-2026-0842",
    val isRegistered: Boolean = true,
    val joinDate: String = "October 2026"
)
