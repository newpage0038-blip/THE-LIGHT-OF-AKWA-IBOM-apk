package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.Announcement
import com.example.data.model.Business
import com.example.data.model.CommunityEvent
import com.example.data.model.CommunityPoll
import com.example.data.model.CommunityPost
import com.example.data.model.CultureItem
import com.example.data.model.Opportunity
import com.example.data.model.PostComment
import com.example.data.model.UserProfile
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {

    // Announcements
    @Query("SELECT * FROM announcements ORDER BY isImportant DESC, id DESC")
    fun getAllAnnouncements(): Flow<List<Announcement>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: Announcement): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncements(announcements: List<Announcement>)

    @Query("DELETE FROM announcements WHERE id = :id")
    suspend fun deleteAnnouncement(id: Long)

    // Community Posts
    @Query("SELECT * FROM community_posts WHERE isReported = 0 ORDER BY isPinned DESC, timestamp DESC")
    fun getActivePosts(): Flow<List<CommunityPost>>

    @Query("SELECT * FROM community_posts ORDER BY isPinned DESC, timestamp DESC")
    fun getAllPostsAdmin(): Flow<List<CommunityPost>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: CommunityPost): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<CommunityPost>)

    @Query("UPDATE community_posts SET likesCount = :newCount, isLikedByMe = :isLiked WHERE id = :postId")
    suspend fun updatePostLike(postId: Long, newCount: Int, isLiked: Boolean)

    @Query("UPDATE community_posts SET isReported = 1 WHERE id = :postId")
    suspend fun reportPost(postId: Long)

    @Query("UPDATE community_posts SET isReported = 0 WHERE id = :postId")
    suspend fun approveReportedPost(postId: Long)

    @Query("DELETE FROM community_posts WHERE id = :postId")
    suspend fun deletePost(postId: Long)

    @Query("UPDATE community_posts SET commentsCount = commentsCount + 1 WHERE id = :postId")
    suspend fun incrementCommentsCount(postId: Long)

    // Post Comments
    @Query("SELECT * FROM post_comments WHERE postId = :postId ORDER BY timestamp ASC")
    fun getCommentsForPost(postId: Long): Flow<List<PostComment>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: PostComment): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComments(comments: List<PostComment>)

    // Polls
    @Query("SELECT * FROM community_polls ORDER BY id DESC")
    fun getAllPolls(): Flow<List<CommunityPoll>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoll(poll: CommunityPoll): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPolls(polls: List<CommunityPoll>)

    @Query("UPDATE community_polls SET votesRaw = :newVotesRaw, userVotedIndex = :userVotedIndex, totalVotes = :totalVotes WHERE id = :pollId")
    suspend fun updatePollVote(pollId: Long, newVotesRaw: String, userVotedIndex: Int, totalVotes: Int)

    // Culture
    @Query("SELECT * FROM culture_items ORDER BY id ASC")
    fun getAllCultureItems(): Flow<List<CultureItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCultureItems(items: List<CultureItem>)

    // Opportunities
    @Query("SELECT * FROM opportunities ORDER BY id DESC")
    fun getAllOpportunities(): Flow<List<Opportunity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunity(opportunity: Opportunity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOpportunities(opportunities: List<Opportunity>)

    @Query("UPDATE opportunities SET isSaved = :isSaved WHERE id = :id")
    suspend fun setOpportunitySaved(id: Long, isSaved: Boolean)

    @Query("DELETE FROM opportunities WHERE id = :id")
    suspend fun deleteOpportunity(id: Long)

    // Businesses
    @Query("SELECT * FROM businesses ORDER BY isVerified DESC, name ASC")
    fun getAllBusinesses(): Flow<List<Business>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusiness(business: Business): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBusinesses(businesses: List<Business>)

    @Query("DELETE FROM businesses WHERE id = :id")
    suspend fun deleteBusiness(id: Long)

    // Events
    @Query("SELECT * FROM events ORDER BY id ASC")
    fun getAllEvents(): Flow<List<CommunityEvent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: CommunityEvent): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<CommunityEvent>)

    @Query("UPDATE events SET isAttending = :isAttending, attendeeCount = :attendeeCount WHERE id = :id")
    suspend fun updateEventAttendance(id: Long, isAttending: Boolean, attendeeCount: Int)

    @Query("DELETE FROM events WHERE id = :id")
    suspend fun deleteEvent(id: Long)

    // User Profile
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProfile(profile: UserProfile)

    // Counts for initialization check
    @Query("SELECT COUNT(*) FROM announcements")
    suspend fun getAnnouncementCount(): Int
}
