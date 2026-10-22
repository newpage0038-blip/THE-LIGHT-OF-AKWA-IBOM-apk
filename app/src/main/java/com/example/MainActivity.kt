package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Badge
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.AboutScreen
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.BusinessScreen
import com.example.ui.screens.CommunityScreen
import com.example.ui.screens.ContactScreen
import com.example.ui.screens.CultureScreen
import com.example.ui.screens.EventsScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MembershipScreen
import com.example.ui.screens.NewsScreen
import com.example.ui.screens.OpportunitiesScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.AkwaIbomGold
import com.example.ui.theme.AkwaIbomGreen
import com.example.ui.theme.AkwaIbomSun
import com.example.ui.theme.LightOfAkwaIbomTheme
import com.example.ui.viewmodel.AppSection
import com.example.ui.viewmodel.AppViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LightOfAkwaIbomTheme {
                MainApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(appViewModel: AppViewModel = viewModel()) {
    val showWelcome by appViewModel.showWelcomeScreen.collectAsStateWithLifecycle()
    val currentSection by appViewModel.currentSection.collectAsStateWithLifecycle()
    val isGreetingActive by appViewModel.isGreetingActive.collectAsStateWithLifecycle()
    val isAdminAuthenticated by appViewModel.isAdminAuthenticated.collectAsStateWithLifecycle()

    val announcements by appViewModel.announcements.collectAsStateWithLifecycle()
    val activePosts by appViewModel.activePosts.collectAsStateWithLifecycle()
    val adminPosts by appViewModel.adminPosts.collectAsStateWithLifecycle()
    val polls by appViewModel.polls.collectAsStateWithLifecycle()
    val cultureItems by appViewModel.cultureItems.collectAsStateWithLifecycle()
    val opportunities by appViewModel.opportunities.collectAsStateWithLifecycle()
    val businesses by appViewModel.businesses.collectAsStateWithLifecycle()
    val events by appViewModel.events.collectAsStateWithLifecycle()
    val userProfile by appViewModel.userProfile.collectAsStateWithLifecycle()
    val selectedPostForComments by appViewModel.selectedPostForComments.collectAsStateWithLifecycle()
    val currentComments by appViewModel.currentComments.collectAsStateWithLifecycle()
    val selectedCultureItem by appViewModel.selectedCultureItem.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showMoreSheet by remember { mutableStateOf(false) }

    // Listen to UI messages
    LaunchedEffect(Unit) {
        appViewModel.uiMessage.collectLatest { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    // Hardware back button navigation handling
    BackHandler(enabled = !showWelcome) {
        val handled = appViewModel.handleBackNavigation()
        if (!handled && currentSection == AppSection.HOME) {
            // Let system handle default back
        }
    }

    if (showWelcome) {
        WelcomeScreen(
            onGetStarted = { appViewModel.dismissWelcomeScreen() },
            isGreetingActive = isGreetingActive,
            onTriggerGreeting = { appViewModel.triggerCommunityGreeting() }
        )
    } else {
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable { appViewModel.navigateTo(AppSection.HOME) }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.img_light_of_akwa_ibom),
                                contentDescription = "Logo",
                                modifier = Modifier
                                    .size(34.dp)
                                    .clip(RoundedCornerShape(6.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Column {
                                Text(
                                    text = "The Light of Akwa Ibom",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = AkwaIbomGreen
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Text(
                                    text = "BRIGHTER WE SHINE!",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = AkwaIbomGold,
                                        fontSize = 9.sp
                                    )
                                )
                            }
                        }
                    },
                    actions = {
                        // Quick Chant trigger button
                        IconButton(
                            onClick = { appViewModel.triggerCommunityGreeting() },
                            modifier = Modifier.testTag("chant_top_bar_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "Chant Slogan",
                                tint = AkwaIbomGold
                            )
                        }

                        // Admin lock / console button
                        IconButton(
                            onClick = { appViewModel.navigateTo(AppSection.ADMIN) },
                            modifier = Modifier.testTag("admin_top_bar_btn")
                        ) {
                            Icon(
                                imageVector = Icons.Default.AdminPanelSettings,
                                contentDescription = "Admin Console",
                                tint = if (isAdminAuthenticated) AkwaIbomGreen else Color.Gray
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.White
                    )
                )
            },
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    contentColor = AkwaIbomGreen,
                    modifier = Modifier.navigationBarsPadding(),
                    tonalElevation = 6.dp
                ) {
                    NavigationBarItem(
                        selected = currentSection == AppSection.HOME,
                        onClick = { appViewModel.navigateTo(AppSection.HOME) },
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AkwaIbomGreen,
                            selectedTextColor = AkwaIbomGreen,
                            indicatorColor = Color(0xFFE8F5E9)
                        ),
                        modifier = Modifier.testTag("bottom_nav_home")
                    )

                    NavigationBarItem(
                        selected = currentSection == AppSection.COMMUNITY,
                        onClick = { appViewModel.navigateTo(AppSection.COMMUNITY) },
                        icon = { Icon(Icons.Default.Forum, contentDescription = "Community") },
                        label = { Text("Community") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AkwaIbomGreen,
                            selectedTextColor = AkwaIbomGreen,
                            indicatorColor = Color(0xFFE8F5E9)
                        ),
                        modifier = Modifier.testTag("bottom_nav_community")
                    )

                    NavigationBarItem(
                        selected = currentSection == AppSection.CULTURE,
                        onClick = { appViewModel.navigateTo(AppSection.CULTURE) },
                        icon = { Icon(Icons.Default.Palette, contentDescription = "Culture") },
                        label = { Text("Culture") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AkwaIbomGreen,
                            selectedTextColor = AkwaIbomGreen,
                            indicatorColor = Color(0xFFE8F5E9)
                        ),
                        modifier = Modifier.testTag("bottom_nav_culture")
                    )

                    NavigationBarItem(
                        selected = currentSection == AppSection.OPPORTUNITIES,
                        onClick = { appViewModel.navigateTo(AppSection.OPPORTUNITIES) },
                        icon = { Icon(Icons.Default.School, contentDescription = "Opportunities") },
                        label = { Text("Talents") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AkwaIbomGreen,
                            selectedTextColor = AkwaIbomGreen,
                            indicatorColor = Color(0xFFE8F5E9)
                        ),
                        modifier = Modifier.testTag("bottom_nav_talents")
                    )

                    val isMoreSectionActive = currentSection in listOf(
                        AppSection.NEWS,
                        AppSection.BUSINESS,
                        AppSection.EVENTS,
                        AppSection.MEMBERSHIP,
                        AppSection.ABOUT,
                        AppSection.CONTACT,
                        AppSection.ADMIN
                    )

                    NavigationBarItem(
                        selected = isMoreSectionActive,
                        onClick = { showMoreSheet = true },
                        icon = { Icon(Icons.Default.Menu, contentDescription = "More") },
                        label = { Text("More") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = AkwaIbomGold,
                            selectedTextColor = AkwaIbomGold,
                            indicatorColor = Color(0xFFFFF3E0)
                        ),
                        modifier = Modifier.testTag("bottom_nav_more")
                    )
                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentSection,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "SectionTransition"
                ) { section ->
                    when (section) {
                        AppSection.HOME -> HomeScreen(
                            announcements = announcements,
                            featuredPosts = activePosts,
                            upcomingEvents = events,
                            isGreetingActive = isGreetingActive,
                            onTriggerGreeting = { appViewModel.triggerCommunityGreeting() },
                            onNavigate = { appViewModel.navigateTo(it) },
                            onOpenPostComments = { appViewModel.openComments(it) }
                        )

                        AppSection.COMMUNITY -> CommunityScreen(
                            posts = activePosts,
                            polls = polls,
                            selectedPostForComments = selectedPostForComments,
                            currentComments = currentComments,
                            onToggleLike = { appViewModel.toggleLike(it) },
                            onOpenComments = { appViewModel.openComments(it) },
                            onCloseComments = { appViewModel.closeComments() },
                            onAddComment = { appViewModel.addComment(it) },
                            onCreatePost = { title, content, cat -> appViewModel.createPost(title, content, cat) },
                            onVotePoll = { poll, index -> appViewModel.votePoll(poll, index) },
                            onReportPost = { appViewModel.reportPost(it) }
                        )

                        AppSection.CULTURE -> CultureScreen(
                            items = cultureItems,
                            selectedItem = selectedCultureItem,
                            onSelectItem = { appViewModel.selectCultureItem(it) },
                            onCloseDetails = { appViewModel.closeCultureDetails() }
                        )

                        AppSection.OPPORTUNITIES -> OpportunitiesScreen(
                            opportunities = opportunities,
                            onToggleSave = { appViewModel.toggleSaveOpportunity(it) }
                        )

                        AppSection.BUSINESS -> BusinessScreen(
                            businesses = businesses,
                            onRegisterBusiness = { name, cat, lga, phone, email, address, desc ->
                                appViewModel.registerBusiness(name, cat, lga, phone, email, address, desc)
                            }
                        )

                        AppSection.EVENTS -> EventsScreen(
                            events = events,
                            onToggleAttendance = { appViewModel.toggleEventAttendance(it) }
                        )

                        AppSection.NEWS -> NewsScreen(
                            announcements = announcements
                        )

                        AppSection.MEMBERSHIP -> MembershipScreen(
                            profile = userProfile,
                            onSaveProfile = { name, lga, phone, email, occ, talent, bio ->
                                appViewModel.saveUserProfile(name, lga, phone, email, occ, talent, bio)
                            }
                        )

                        AppSection.ABOUT -> AboutScreen()

                        AppSection.CONTACT -> ContactScreen(
                            onSendMessage = { cat, msg ->
                                scope.launch {
                                    snackbarHostState.showSnackbar("Thank you! Your $cat has been securely submitted.")
                                }
                            }
                        )

                        AppSection.ADMIN -> AdminScreen(
                            isAuthenticated = isAdminAuthenticated,
                            announcements = announcements,
                            allPosts = adminPosts,
                            onAuthenticate = { appViewModel.authenticateAdmin(it) },
                            onLogout = { appViewModel.logoutAdmin() },
                            onAddAnnouncement = { title, content, cat, urgent ->
                                appViewModel.addAnnouncement(title, content, cat, urgent)
                            },
                            onDeleteAnnouncement = { appViewModel.deleteAnnouncement(it) },
                            onApprovePost = { appViewModel.approveReportedPost(it) },
                            onDeletePost = { appViewModel.deletePost(it) },
                            onAddEvent = { title, date, time, venue, lga, cat, desc ->
                                appViewModel.addEventAdmin(title, date, time, venue, lga, cat, desc)
                            },
                            onAddOpportunity = { title, org, cat, loc, deadline, desc, elig, apply ->
                                appViewModel.addOpportunityAdmin(title, org, cat, loc, deadline, desc, elig, apply)
                            }
                        )
                    }
                }
            }
        }

        // More Sections Bottom Sheet
        if (showMoreSheet) {
            ModalBottomSheet(
                onDismissRequest = { showMoreSheet = false },
                containerColor = Color.White
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp)
                ) {
                    Text(
                        text = "More Sections & Tools",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = AkwaIbomGreen
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MoreSheetButton(
                            modifier = Modifier.weight(1f),
                            title = "Business Hub",
                            icon = Icons.Default.Storefront,
                            tint = Color(0xFF7B1FA2),
                            onClick = {
                                showMoreSheet = false
                                appViewModel.navigateTo(AppSection.BUSINESS)
                            }
                        )

                        MoreSheetButton(
                            modifier = Modifier.weight(1f),
                            title = "Events Calendar",
                            icon = Icons.Default.Event,
                            tint = AkwaIbomGreen,
                            onClick = {
                                showMoreSheet = false
                                appViewModel.navigateTo(AppSection.EVENTS)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MoreSheetButton(
                            modifier = Modifier.weight(1f),
                            title = "News & Updates",
                            icon = Icons.Default.Newspaper,
                            tint = AkwaIbomGold,
                            onClick = {
                                showMoreSheet = false
                                appViewModel.navigateTo(AppSection.NEWS)
                            }
                        )

                        MoreSheetButton(
                            modifier = Modifier.weight(1f),
                            title = "Membership Card",
                            icon = Icons.Default.Badge,
                            tint = Color(0xFF00796B),
                            onClick = {
                                showMoreSheet = false
                                appViewModel.navigateTo(AppSection.MEMBERSHIP)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MoreSheetButton(
                            modifier = Modifier.weight(1f),
                            title = "About Us",
                            icon = Icons.Default.Info,
                            tint = Color(0xFF1565C0),
                            onClick = {
                                showMoreSheet = false
                                appViewModel.navigateTo(AppSection.ABOUT)
                            }
                        )

                        MoreSheetButton(
                            modifier = Modifier.weight(1f),
                            title = "Contact & Help",
                            icon = Icons.Default.ContactSupport,
                            tint = Color(0xFFD84315),
                            onClick = {
                                showMoreSheet = false
                                appViewModel.navigateTo(AppSection.CONTACT)
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        MoreSheetButton(
                            modifier = Modifier.weight(1f),
                            title = "Admin Dashboard",
                            icon = Icons.Default.AdminPanelSettings,
                            tint = Color(0xFF2E7D32),
                            onClick = {
                                showMoreSheet = false
                                appViewModel.navigateTo(AppSection.ADMIN)
                            }
                        )

                        MoreSheetButton(
                            modifier = Modifier.weight(1f),
                            title = "Welcome Screen",
                            icon = Icons.Default.WbSunny,
                            tint = AkwaIbomSun,
                            onClick = {
                                showMoreSheet = false
                                appViewModel.openWelcomeScreen()
                            }
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
        }
    }
}

@Composable
fun MoreSheetButton(
    modifier: Modifier = Modifier,
    title: String,
    icon: ImageVector,
    tint: Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0)),
        modifier = modifier.height(64.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                shape = CircleShape,
                color = tint.copy(alpha = 0.12f),
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(imageVector = icon, contentDescription = title, tint = tint, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
