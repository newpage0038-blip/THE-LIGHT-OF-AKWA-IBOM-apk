package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.model.Announcement
import com.example.data.model.Business
import com.example.data.model.CommunityEvent
import com.example.data.model.CommunityPoll
import com.example.data.model.CommunityPost
import com.example.data.model.CultureItem
import com.example.data.model.Opportunity
import com.example.data.model.PostComment
import com.example.data.model.UserProfile
import com.example.data.repository.AppRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class AppSection(val title: String) {
    HOME("Home"),
    ABOUT("About Us"),
    COMMUNITY("Community"),
    NEWS("News & Updates"),
    CULTURE("Culture & Arts"),
    OPPORTUNITIES("Opportunities"),
    BUSINESS("Business Hub"),
    EVENTS("Events"),
    MEMBERSHIP("Membership"),
    CONTACT("Contact & Support"),
    ADMIN("Admin Dashboard")
}

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AppRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AppRepository(database.appDao())
        viewModelScope.launch {
            repository.checkAndSeedDatabase()
        }
    }

    // Navigation and screen state
    private val _showWelcomeScreen = MutableStateFlow(true)
    val showWelcomeScreen: StateFlow<Boolean> = _showWelcomeScreen.asStateFlow()

    private val _currentSection = MutableStateFlow(AppSection.HOME)
    val currentSection: StateFlow<AppSection> = _currentSection.asStateFlow()

    private val _previousSection = MutableStateFlow(AppSection.HOME)

    // Call and response slogan state
    private val _isGreetingActive = MutableStateFlow(false)
    val isGreetingActive: StateFlow<Boolean> = _isGreetingActive.asStateFlow()

    // Admin authentication
    private val _isAdminAuthenticated = MutableStateFlow(false)
    val isAdminAuthenticated: StateFlow<Boolean> = _isAdminAuthenticated.asStateFlow()

    // Notification toast / message
    private val _uiMessage = MutableSharedFlow<String>()
    val uiMessage: SharedFlow<String> = _uiMessage.asSharedFlow()

    // Data streams
    val announcements: StateFlow<List<Announcement>> = repository.announcements
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val activePosts: StateFlow<List<CommunityPost>> = repository.activePosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val adminPosts: StateFlow<List<CommunityPost>> = repository.allPostsAdmin
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val polls: StateFlow<List<CommunityPoll>> = repository.polls
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cultureItems: StateFlow<List<CultureItem>> = repository.cultureItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val opportunities: StateFlow<List<Opportunity>> = repository.opportunities
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val businesses: StateFlow<List<Business>> = repository.businesses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val events: StateFlow<List<CommunityEvent>> = repository.events
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProfile: StateFlow<UserProfile?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Active discussion comments modal/view
    private val _selectedPostForComments = MutableStateFlow<CommunityPost?>(null)
    val selectedPostForComments: StateFlow<CommunityPost?> = _selectedPostForComments.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentComments: StateFlow<List<PostComment>> = _selectedPostForComments.flatMapLatest { post ->
        if (post != null) repository.getCommentsForPost(post.id) else flowOf(emptyList())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Selected culture item details modal
    private val _selectedCultureItem = MutableStateFlow<CultureItem?>(null)
    val selectedCultureItem: StateFlow<CultureItem?> = _selectedCultureItem.asStateFlow()

    // Navigation triggers
    fun dismissWelcomeScreen() {
        _showWelcomeScreen.value = false
    }

    fun openWelcomeScreen() {
        _showWelcomeScreen.value = true
    }

    fun navigateTo(section: AppSection) {
        _previousSection.value = _currentSection.value
        _currentSection.value = section
    }

    fun handleBackNavigation(): Boolean {
        if (_selectedPostForComments.value != null) {
            _selectedPostForComments.value = null
            return true
        }
        if (_selectedCultureItem.value != null) {
            _selectedCultureItem.value = null
            return true
        }
        if (_currentSection.value != AppSection.HOME) {
            _currentSection.value = AppSection.HOME
            return true
        }
        return false
    }

    // Interactive Call & Response ("THE LIGHT!" -> "BRIGHTER WE SHINE!")
    fun triggerCommunityGreeting() {
        viewModelScope.launch {
            _isGreetingActive.value = true
            _uiMessage.emit("Leader: THE LIGHT! 🌟 Members: BRIGHTER WE SHINE!")
            kotlinx.coroutines.delay(2600)
            _isGreetingActive.value = false
        }
    }

    // Community interactions
    fun toggleLike(post: CommunityPost) {
        viewModelScope.launch {
            repository.toggleLike(post)
        }
    }

    fun openComments(post: CommunityPost) {
        _selectedPostForComments.value = post
    }

    fun closeComments() {
        _selectedPostForComments.value = null
    }

    fun addComment(text: String) {
        val post = _selectedPostForComments.value ?: return
        if (text.isBlank()) return
        viewModelScope.launch {
            val author = userProfile.value?.fullName ?: "Community Member"
            val lga = userProfile.value?.lga ?: "Akwa Ibom"
            repository.addComment(post.id, author, lga, text.trim())
            _uiMessage.emit("Comment posted successfully!")
        }
    }

    fun createPost(title: String, content: String, category: String) {
        if (title.isBlank() || content.isBlank()) return
        viewModelScope.launch {
            val author = userProfile.value?.fullName ?: "Community Member"
            val lga = userProfile.value?.lga ?: "Akwa Ibom"
            repository.addPost(title.trim(), content.trim(), author, lga, category)
            _uiMessage.emit("Your contribution was shared to the community!")
        }
    }

    fun votePoll(poll: CommunityPoll, optionIndex: Int) {
        viewModelScope.launch {
            repository.votePoll(poll, optionIndex)
            _uiMessage.emit("Vote recorded! Thank you for participating.")
        }
    }

    fun reportPost(post: CommunityPost) {
        viewModelScope.launch {
            repository.reportPost(post.id)
            _uiMessage.emit("Post submitted to administrators for review.")
        }
    }

    // Opportunities & Business
    fun toggleSaveOpportunity(opp: Opportunity) {
        viewModelScope.launch {
            repository.toggleSaveOpportunity(opp)
            _uiMessage.emit(if (!opp.isSaved) "Opportunity saved to bookmarks" else "Opportunity removed from bookmarks")
        }
    }

    fun registerBusiness(name: String, category: String, lga: String, phone: String, email: String, address: String, description: String) {
        if (name.isBlank() || phone.isBlank()) return
        viewModelScope.launch {
            val business = Business(
                name = name.trim(),
                category = category,
                lga = lga.trim(),
                phone = phone.trim(),
                email = email.trim(),
                address = address.trim(),
                description = description.trim(),
                isVerified = true
            )
            repository.registerBusiness(business)
            _uiMessage.emit("Business registered successfully in directory!")
        }
    }

    // Events
    fun toggleEventAttendance(event: CommunityEvent) {
        viewModelScope.launch {
            repository.toggleEventAttendance(event)
            _uiMessage.emit(if (!event.isAttending) "Marked as Attending! See you there." else "Removed from your schedule.")
        }
    }

    // Culture
    fun selectCultureItem(item: CultureItem) {
        _selectedCultureItem.value = item
    }

    fun closeCultureDetails() {
        _selectedCultureItem.value = null
    }

    // Membership Profile
    fun saveUserProfile(name: String, lga: String, phone: String, email: String, occupation: String, talent: String, bio: String) {
        viewModelScope.launch {
            val current = userProfile.value
            val updated = (current ?: UserProfile()).copy(
                fullName = name.trim().ifEmpty { "Anietie Udoh" },
                lga = lga.trim().ifEmpty { "Uyo" },
                phoneNumber = phone.trim().ifEmpty { "+234 803 123 4567" },
                email = email.trim().ifEmpty { "member@lightofakwaibom.org" },
                occupation = occupation.trim().ifEmpty { "Entrepreneur" },
                talentOrInterest = talent.trim().ifEmpty { "Community Leadership" },
                bio = bio.trim().ifEmpty { "Proud Akwa Ibomite. Brighter We Shine!" },
                isRegistered = true
            )
            repository.saveProfile(updated)
            _uiMessage.emit("Membership profile updated successfully!")
        }
    }

    // Admin dashboard
    fun authenticateAdmin(pin: String): Boolean {
        return if (pin == "2026" || pin == "1234") {
            _isAdminAuthenticated.value = true
            true
        } else {
            false
        }
    }

    fun logoutAdmin() {
        _isAdminAuthenticated.value = false
        _currentSection.value = AppSection.HOME
    }

    fun addAnnouncement(title: String, content: String, category: String, isImportant: Boolean) {
        if (title.isBlank() || content.isBlank()) return
        viewModelScope.launch {
            val announcement = Announcement(
                title = title.trim(),
                content = content.trim(),
                category = category,
                date = "Today",
                author = "Platform Admin",
                isImportant = isImportant
            )
            repository.addAnnouncement(announcement)
            _uiMessage.emit("Announcement published!")
        }
    }

    fun deleteAnnouncement(id: Long) {
        viewModelScope.launch {
            repository.deleteAnnouncement(id)
            _uiMessage.emit("Announcement removed.")
        }
    }

    fun approveReportedPost(postId: Long) {
        viewModelScope.launch {
            repository.approveReportedPost(postId)
            _uiMessage.emit("Post approved and restored.")
        }
    }

    fun deletePost(postId: Long) {
        viewModelScope.launch {
            repository.deletePost(postId)
            _uiMessage.emit("Post removed from community.")
        }
    }

    fun addOpportunityAdmin(title: String, org: String, category: String, location: String, deadline: String, desc: String, eligibility: String, apply: String) {
        if (title.isBlank() || org.isBlank()) return
        viewModelScope.launch {
            val opp = Opportunity(
                title = title.trim(),
                organization = org.trim(),
                category = category,
                location = location.trim(),
                deadline = deadline.trim(),
                description = desc.trim(),
                eligibility = eligibility.trim(),
                howToApply = apply.trim()
            )
            repository.addOpportunity(opp)
            _uiMessage.emit("New opportunity published!")
        }
    }

    fun addEventAdmin(title: String, date: String, time: String, venue: String, lga: String, category: String, desc: String) {
        if (title.isBlank() || venue.isBlank()) return
        viewModelScope.launch {
            val event = CommunityEvent(
                title = title.trim(),
                dateString = date.trim(),
                timeString = time.trim(),
                venue = venue.trim(),
                lga = lga.trim(),
                category = category,
                description = desc.trim(),
                attendeeCount = 12
            )
            repository.addEvent(event)
            _uiMessage.emit("Event added to community calendar!")
        }
    }
}
