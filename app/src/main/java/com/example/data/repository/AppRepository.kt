package com.example.data.repository

import com.example.data.local.AppDao
import com.example.data.model.Announcement
import com.example.data.model.Business
import com.example.data.model.CommunityEvent
import com.example.data.model.CommunityPoll
import com.example.data.model.CommunityPost
import com.example.data.model.CultureItem
import com.example.data.model.Opportunity
import com.example.data.model.PostComment
import com.example.data.model.UserProfile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AppRepository(private val dao: AppDao) {

    val announcements: Flow<List<Announcement>> = dao.getAllAnnouncements()
    val activePosts: Flow<List<CommunityPost>> = dao.getActivePosts()
    val allPostsAdmin: Flow<List<CommunityPost>> = dao.getAllPostsAdmin()
    val polls: Flow<List<CommunityPoll>> = dao.getAllPolls()
    val cultureItems: Flow<List<CultureItem>> = dao.getAllCultureItems()
    val opportunities: Flow<List<Opportunity>> = dao.getAllOpportunities()
    val businesses: Flow<List<Business>> = dao.getAllBusinesses()
    val events: Flow<List<CommunityEvent>> = dao.getAllEvents()
    val userProfile: Flow<UserProfile?> = dao.getUserProfile()

    fun getCommentsForPost(postId: Long): Flow<List<PostComment>> = dao.getCommentsForPost(postId)

    suspend fun checkAndSeedDatabase() = withContext(Dispatchers.IO) {
        if (dao.getAnnouncementCount() == 0) {
            seedDefaultContent()
        }
    }

    private suspend fun seedDefaultContent() {
        // 1. Announcements
        dao.insertAnnouncements(
            listOf(
                Announcement(
                    title = "Welcome to The Light of Akwa Ibom",
                    content = "We are a community united by hope, driven by purpose, and committed to making our state and our people shine brighter! Join conversations, discover opportunities, and celebrate our culture.",
                    category = "General",
                    date = "Today",
                    author = "Platform Central Executive",
                    isImportant = true
                ),
                Announcement(
                    title = "Akwa Ibom Youth Skill-Up Programme (AYSP 2026)",
                    content = "Applications are open for 3,000 free certified training slots in Solar Installation, Modern Aquaculture, UI/UX Design, and Sustainable Agriculture across all 31 LGAs.",
                    category = "Youth",
                    date = "Yesterday",
                    author = "Youth Empowerment Directorate",
                    isImportant = true
                ),
                Announcement(
                    title = "Orange September Cultural Showcase & Grand Festival",
                    content = "Mark your calendar! The grand exhibition of Akwa Ibom culinary art, masquerade displays, Ekombi dance, and literature takes place at Ibom Hall Grounds, Uyo.",
                    category = "Culture",
                    date = "Oct 20, 2026",
                    author = "Culture & Tourism Committee",
                    isImportant = false
                ),
                Announcement(
                    title = "Clean & Green Community Sanitation Drive",
                    content = "Join your neighborhood ward this Saturday morning for our monthly communal cleanliness drive. Let us preserve our clean, beautiful state.",
                    category = "General",
                    date = "Oct 18, 2026",
                    author = "Community Welfare Group",
                    isImportant = false
                )
            )
        )

        // 2. Initial Community Posts
        val post1Id = dao.insertPost(
            CommunityPost(
                title = "Revitalizing Traditional Palm Produce Cooperatives",
                content = "Our blessed state has fertile soil and the finest palm belt in West Africa. If our young graduates partner with local farmers using modern processing equipment, we can build multi-million naira agro-export hubs right here in Ikot Abasi, Abak, and Oruk Anam. What are your thoughts on forming youth agro-clusters?",
                authorName = "Elder Bassey Inyang",
                authorLga = "Ikot Abasi",
                category = "Discussion",
                timestamp = System.currentTimeMillis() - 7200000,
                likesCount = 28,
                commentsCount = 2,
                isPinned = true
            )
        )
        val post2Id = dao.insertPost(
            CommunityPost(
                title = "Akwa Ibom Tech Students Win National Robotics Trophy!",
                content = "Proud moment for all of us! A team of 4 brilliant young minds from Uyo designed an automated solar irrigation system and won first prize at the National Innovation Fair. Brighter we shine!",
                authorName = "Emem Essien",
                authorLga = "Uyo",
                category = "Achievement",
                timestamp = System.currentTimeMillis() - 18000000,
                likesCount = 64,
                commentsCount = 1,
                isPinned = false
            )
        )
        dao.insertPost(
            CommunityPost(
                title = "Free Weekend Ibibio & Annang Language Classes for Kids",
                content = "To preserve our mother tongue and cultural idioms, our community center in Ikot Ekpene is hosting free Saturday story-telling and language sessions for children aged 6-15. Parents, let us bring our children!",
                authorName = "Idongesit Akpan",
                authorLga = "Ikot Ekpene",
                category = "Community Project",
                timestamp = System.currentTimeMillis() - 43200000,
                likesCount = 41,
                commentsCount = 0,
                isPinned = false
            )
        )

        // 3. Comments for seeded posts
        dao.insertComments(
            listOf(
                PostComment(
                    postId = post1Id,
                    authorName = "Nseobong Paul",
                    authorLga = "Abak",
                    comment = "Brilliant proposal Elder! I am an agricultural engineer ready to volunteer technical training for our youths in the cluster.",
                    timestamp = System.currentTimeMillis() - 5000000
                ),
                PostComment(
                    postId = post1Id,
                    authorName = "Anietie Udoh",
                    authorLga = "Uyo",
                    comment = "Full support! Cooperative funding will make it easy to purchase mini processing mills without crippling interest loans.",
                    timestamp = System.currentTimeMillis() - 3600000
                ),
                PostComment(
                    postId = post2Id,
                    authorName = "Ekaette Okon",
                    authorLga = "Eket",
                    comment = "Pure pride! Our children have world-class potential. Keep shining bright!",
                    timestamp = System.currentTimeMillis() - 14000000
                )
            )
        )

        // 4. Poll
        dao.insertPoll(
            CommunityPoll(
                question = "Which empowerment initiative should The Light of Akwa Ibom prioritize in your local government area?",
                category = "Community Development",
                optionsRaw = "Youth Tech & Agro Hubs|Women Business Micro-Grants|Language & Cultural Centers|Free Community Health Clinics",
                votesRaw = "142|118|65|94",
                userVotedIndex = -1,
                totalVotes = 419
            )
        )

        // 5. Culture Items
        dao.insertCultureItems(
            listOf(
                CultureItem(
                    type = "Delicacy",
                    title = "Ekpang Nkukwo",
                    subtitle = "The Revered Royal Delicacy of Akwa Ibom",
                    description = "Ekpang Nkukwo is an indigenous, nutrient-packed dish made with freshly grated water yam and cocoyam, individually wrapped in tender cocoyam or pumpkin leaves and slow-cooked in a rich seafood broth.",
                    guideOrDetails = "Authentic Preparation:\n• Peel and finely grate water yam and cocoyam in equal proportions with a pinch of native salt.\n• Carefully wash tender young cocoyam leaves (or pumpkin leaves) and cut into compact palm-sized wraps.\n• Roll small dollops of the paste tightly into each leaf and layer in a pot seasoned with pure virgin red palm oil.\n• Add dried bonga fish, boiled periwinkles (mfi), grounded crayfish, scotch bonnets, and fragrant scent leaves (ntoing).\n• Simmer over controlled heat until the wraps steam firm, releasing a mouthwatering aroma.",
                    imageResName = "img_ekpang_nkukwo",
                    audioPhonetic = "Ek-pang N-kuk-wo"
                ),
                CultureItem(
                    type = "Delicacy",
                    title = "Afang Soup",
                    subtitle = "The World-Acclaimed King of Soups",
                    description = "A culinary masterpiece renowned globally, Afang soup combines freshly sliced Afang leaves (Gnetum africanum) pounded smoothly with succulent waterleaf, assorted meats, cow skin (kanda), smoked fish, stockfish, and sea periwinkles.",
                    guideOrDetails = "Key Steps:\n• Wash and slice Afang leaves very thinly and pound in a wooden mortar or blend dry.\n• Wash and slice fresh waterleaves.\n• Cook seasoned beef, kpomo, and stockfish with crayfish and scotch bonnets until tender.\n• Add waterleaf and let it soften without added water, followed by palm oil and periwinkles.\n• Stir in the pounded Afang leaves and simmer for 3-5 minutes so the leaves retain their deep green vitality and nutrients.",
                    imageResName = "img_ekpang_nkukwo",
                    audioPhonetic = "A-fang Soup"
                ),
                CultureItem(
                    type = "Language",
                    title = "Essential Ibibio & Annang Expressions",
                    subtitle = "Heritage Vocabulary & Greetings",
                    description = "The indigenous languages of Akwa Ibom are rich in warmth, respect, and poetic expressions. Learn the common greetings used every day across the state.",
                    guideOrDetails = "• Mme kom o! = Warm greetings to you! (Formal & universal)\n• Abadie? = How are you doing? / How is everything?\n• Idem mfo song? = How is your health and well-being?\n• Idem mi asong, Sosongo! = I am fine and strong, thank you!\n• Eyen Eka = My sibling / dear kin / friend\n• Emem odu ye afo = Peace be with you\n• Akwa Ibom Brighter We Shine! = The Light of our people shines forever!",
                    imageResName = "img_light_of_akwa_ibom",
                    audioPhonetic = "Mme kom o! • Abadie? • Sosongo!"
                ),
                CultureItem(
                    type = "Festival",
                    title = "Orange September & Ekpe Cultural Festival",
                    subtitle = "Celebrating History, Colors and Unity",
                    description = "Orange September celebrates the creation of Akwa Ibom State with carnival floats, artistic displays, traditional wrestling, and community feasts. The ancient Ekpe masquerade society showcases sacred choreography and rich symbolism.",
                    guideOrDetails = "Highlights:\n• Grand State Carnival Parade along Udo Udoma Avenue, Uyo.\n• Traditional craft exhibitions of raffia, woodcarving, and clay pottery.\n• High-energy musical performances featuring traditional xylophone (Ikon), horns, and drums.\n• Awarding outstanding community innovators and cultural custodians.",
                    imageResName = "img_light_of_akwa_ibom",
                    audioPhonetic = "Orange September • Ekpe"
                ),
                CultureItem(
                    type = "Dance",
                    title = "Ekombi Dance",
                    subtitle = "The Graceful Rhythm of the Ocean Waves",
                    description = "Ekombi is a captivating ceremonial dance performed by maiden dancers adorned in colourful beaded skirts (onyonyo and mini-wrappers), ankle bells, and ornate headpieces.",
                    guideOrDetails = "Movement & Rhythm:\n• Dancers move their hips in gentle, fluid ripples that simulate oceanic tides.\n• Demonstrates poise, beauty, discipline, and communal harmony.\n• Traditionally performed during weddings, coronation of paramount rulers, and harvest celebrations.",
                    imageResName = "img_light_of_akwa_ibom",
                    audioPhonetic = "E-kom-bi Dance"
                )
            )
        )

        // 6. Opportunities
        dao.insertOpportunities(
            listOf(
                Opportunity(
                    title = "Ibom Digital & Solar Tech Fellowship 2026",
                    organization = "Akwa Ibom Innovation & Skills Agency",
                    category = "Training",
                    location = "Uyo / Hybrid",
                    deadline = "November 15, 2026",
                    description = "A 6-month fully-funded training programme in Solar PV engineering, Mobile App development, and IoT smart agriculture with guaranteed internship placement.",
                    eligibility = "Open to all Akwa Ibom indigenes aged 18-35. Basic secondary school certificate or tertiary education.",
                    howToApply = "Offline Application: Visit the Ministry of Science and Technology desk at Idongesit Nkanga Secretariat, Uyo, or contact local LGA coordinators."
                ),
                Opportunity(
                    title = "Statewide Undergraduate STEM & Heritage Scholarship",
                    organization = "Light of Akwa Ibom Foundation",
                    category = "Scholarship",
                    location = "State-wide (All 31 LGAs)",
                    deadline = "December 5, 2026",
                    description = "Annual education grant of N250,000 per academic session awarded to 500 promising undergraduate students enrolled in recognized universities.",
                    eligibility = "Undergraduate students from 200 level and above with minimum CGPA of 3.5 / 5.0.",
                    howToApply = "Submit letter of identification from your village head/ward counselor, school ID, and academic transcript."
                ),
                Opportunity(
                    title = "Women in Cooperative Agri-Business Grant",
                    organization = "Akwa Ibom Agricultural Development Bank",
                    category = "Grant",
                    location = "Ikot Ekpene, Eket & Oron Zones",
                    deadline = "November 30, 2026",
                    description = "Non-repayable equipment and capital grant of N500,000 for registered women farming cooperatives specializing in cassava, palm oil, or vegetables.",
                    eligibility = "Registered women cooperative of at least 5 members operating for 1+ year.",
                    howToApply = "Submit cooperative registration papers and farm location coordinates at any LGA Agricultural office."
                ),
                Opportunity(
                    title = "Creative Arts & Screenplay Auditions 2026",
                    organization = "Ibom Film & Creative Arts Guild",
                    category = "Audition",
                    location = "Ibom Hall, Uyo",
                    deadline = "November 10, 2026",
                    description = "Casting auditions for upcoming historical docu-drama celebrating the heroic founding leaders and rich cultural heritage of Akwa Ibom.",
                    eligibility = "Actors, voice artists, singers, and traditional instrumentalists aged 16 and above.",
                    howToApply = "Prepare a 2-minute monologue in English or Ibibio. Auditions held daily 9am - 3pm at Ibom Hall."
                )
            )
        )

        // 7. Businesses
        dao.insertBusinesses(
            listOf(
                Business(
                    name = "Ikot Ekpene Raffia & Craft Hub",
                    category = "Fashion & Crafts",
                    lga = "Ikot Ekpene",
                    phone = "+234 802 345 6789",
                    email = "raffiahub@gmail.com",
                    address = "Crafts Village, Old Aba Road, Ikot Ekpene",
                    description = "Authentic hand-woven raffia bags, shoes, carpets, and ceremonial headpieces crafted by master artisans with decades of heritage."
                ),
                Business(
                    name = "Ibom Golden Palm Agro-Allied Ltd",
                    category = "Agriculture",
                    lga = "Ikot Abasi",
                    phone = "+234 803 987 6543",
                    email = "goldenpalmagro@gmail.com",
                    address = "Plot 14 Industrial Layout, Ikot Abasi",
                    description = "Pure virgin red palm oil, high-grade palm kernel oil, and certified palm seedlings for sustainable commercial farming."
                ),
                Business(
                    name = "Mama Edidiong Authentic Delicacies",
                    category = "Food & Catering",
                    lga = "Uyo",
                    phone = "+234 814 555 1212",
                    email = "mamaedidiongkitchen@gmail.com",
                    address = "42 Brooks Street, Uyo",
                    description = "Freshly prepared Ekpang Nkukwo, Afang Soup, Edikang Ikong, and smoked fish prepared in traditional earthenware."
                ),
                Business(
                    name = "Dakkada Digital Solutions",
                    category = "Tech & Innovation",
                    lga = "Uyo",
                    phone = "+234 805 777 8899",
                    email = "contact@dakkadadigital.com",
                    address = "E-Library Complex, IBB Avenue, Uyo",
                    description = "Software development, mobile app design, cloud solutions, and digital skills workshops for SMEs."
                ),
                Business(
                    name = "Oron Coastal Fresh Seafood Depot",
                    category = "Food & Catering",
                    lga = "Oron",
                    phone = "+234 806 333 4455",
                    email = "oronseafood@gmail.com",
                    address = "Oron Beach Road Terminal, Oron",
                    description = "Wholesale and retail supplies of dried crayfish, sea prawns, fresh periwinkles, and dried bonga fish directly from coastal fishermen."
                )
            )
        )

        // 8. Events
        dao.insertEvents(
            listOf(
                CommunityEvent(
                    title = "The Light of Akwa Ibom Annual Assembly",
                    dateString = "Saturday, November 28, 2026",
                    timeString = "10:00 AM",
                    venue = "E-Library Auditorium, IBB Avenue",
                    lga = "Uyo",
                    category = "Town Hall",
                    description = "A gathering of community members, youth leaders, professionals, and diaspora representatives to review our annual development roadmap and celebrate community impact.",
                    attendeeCount = 185
                ),
                CommunityEvent(
                    title = "Akwa Ibom Young Entrepreneurs Exhibition",
                    dateString = "Saturday, December 12, 2026",
                    timeString = "09:00 AM",
                    venue = "Ibom Tropicana Mall Pavilion",
                    lga = "Uyo",
                    category = "Business",
                    description = "Showcase of 100+ local startups, artisans, food processors, and creative designers. Free entry for all members.",
                    attendeeCount = 310
                ),
                CommunityEvent(
                    title = "Ekpe Cultural Festival & Masquerade Pageant",
                    dateString = "Thursday, December 24, 2026",
                    timeString = "01:00 PM",
                    venue = "Township Stadium, Ikot Ekpene",
                    lga = "Ikot Ekpene",
                    category = "Cultural",
                    description = "Spectacular festival celebrating masquerade traditions, indigenous music, and ancestral folklore with elders and youth.",
                    attendeeCount = 520
                )
            )
        )

        // 9. Default User Profile
        dao.saveUserProfile(
            UserProfile(
                fullName = "Anietie Udoh",
                lga = "Uyo",
                phoneNumber = "+234 803 123 4567",
                email = "anietie@lightofakwaibom.org",
                occupation = "Agri-Tech Enthusiast & Artisan",
                talentOrInterest = "Community Development & Cultural Arts",
                bio = "Proud son of Akwa Ibom dedicated to unity, community empowerment, and making our state shine brighter!",
                memberIdNumber = "AKW-2026-0842",
                isRegistered = true,
                joinDate = "October 2026"
            )
        )
    }

    // Community Actions
    suspend fun toggleLike(post: CommunityPost) = withContext(Dispatchers.IO) {
        val newLiked = !post.isLikedByMe
        val newCount = if (newLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
        dao.updatePostLike(post.id, newCount, newLiked)
    }

    suspend fun addPost(title: String, content: String, authorName: String, authorLga: String, category: String) = withContext(Dispatchers.IO) {
        dao.insertPost(
            CommunityPost(
                title = title,
                content = content,
                authorName = authorName,
                authorLga = authorLga,
                category = category,
                timestamp = System.currentTimeMillis()
            )
        )
    }

    suspend fun addComment(postId: Long, authorName: String, authorLga: String, comment: String) = withContext(Dispatchers.IO) {
        dao.insertComment(
            PostComment(
                postId = postId,
                authorName = authorName,
                authorLga = authorLga,
                comment = comment,
                timestamp = System.currentTimeMillis()
            )
        )
        dao.incrementCommentsCount(postId)
    }

    suspend fun votePoll(poll: CommunityPoll, optionIndex: Int) = withContext(Dispatchers.IO) {
        if (poll.userVotedIndex != -1) return@withContext
        val votes = poll.votesRaw.split("|").map { it.toIntOrNull() ?: 0 }.toMutableList()
        if (optionIndex in votes.indices) {
            votes[optionIndex] = votes[optionIndex] + 1
            val newVotesRaw = votes.joinToString("|")
            dao.updatePollVote(poll.id, newVotesRaw, optionIndex, poll.totalVotes + 1)
        }
    }

    suspend fun toggleSaveOpportunity(opportunity: Opportunity) = withContext(Dispatchers.IO) {
        dao.setOpportunitySaved(opportunity.id, !opportunity.isSaved)
    }

    suspend fun registerBusiness(business: Business) = withContext(Dispatchers.IO) {
        dao.insertBusiness(business)
    }

    suspend fun toggleEventAttendance(event: CommunityEvent) = withContext(Dispatchers.IO) {
        val newAttending = !event.isAttending
        val newCount = if (newAttending) event.attendeeCount + 1 else (event.attendeeCount - 1).coerceAtLeast(0)
        dao.updateEventAttendance(event.id, newAttending, newCount)
    }

    suspend fun saveProfile(profile: UserProfile) = withContext(Dispatchers.IO) {
        dao.saveUserProfile(profile)
    }

    // Admin Operations
    suspend fun addAnnouncement(announcement: Announcement) = withContext(Dispatchers.IO) {
        dao.insertAnnouncement(announcement)
    }

    suspend fun deleteAnnouncement(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteAnnouncement(id)
    }

    suspend fun reportPost(postId: Long) = withContext(Dispatchers.IO) {
        dao.reportPost(postId)
    }

    suspend fun approveReportedPost(postId: Long) = withContext(Dispatchers.IO) {
        dao.approveReportedPost(postId)
    }

    suspend fun deletePost(postId: Long) = withContext(Dispatchers.IO) {
        dao.deletePost(postId)
    }

    suspend fun addEvent(event: CommunityEvent) = withContext(Dispatchers.IO) {
        dao.insertEvent(event)
    }

    suspend fun deleteEvent(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteEvent(id)
    }

    suspend fun addOpportunity(opportunity: Opportunity) = withContext(Dispatchers.IO) {
        dao.insertOpportunity(opportunity)
    }

    suspend fun deleteOpportunity(id: Long) = withContext(Dispatchers.IO) {
        dao.deleteOpportunity(id)
    }
}
