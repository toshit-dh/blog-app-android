package tech.toshitworks.blogapp.presentation.home

import android.annotation.SuppressLint
import android.graphics.drawable.shapes.Shape
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import tech.toshitworks.blogapp.presentation.components.Categories
import tech.toshitworks.blogapp.presentation.components.PostCard
import tech.toshitworks.blogapp.utils.Routes

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomePage(
    viewModel: HomeViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )
    var showBottomSheet by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = {
            state.categories.size
        }
    )
    val focusRequester = remember {
        FocusRequester()
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    if (showBottomSheet)
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {

        }
    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.currentPage }.collect {
            onEvent(HomeEvents.OnCategoryChanged(it))
        }
    }
    LaunchedEffect(key1 = Unit) {
        if (state.searchQuery.isNotEmpty())
            onEvent(HomeEvents.OnSearchQueryChanged(state.searchQuery))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Crossfade(
            targetState = state.isSearchBarVisible,
            label = "Switch"
        ) { ts ->
            if (ts) {
                Scaffold {
                    Column {
                        SearchBar(
                            modifier = Modifier
                                .focusRequester(focusRequester),
                            query = state.searchQuery,
                            placeholder = {
                                Text(text = "Search posts ...")
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        onEvent(HomeEvents.OnCloseIconClicked)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close Bar"
                                    )
                                }
                            },
                            onQueryChange = { s ->
                                onEvent(HomeEvents.OnSearchQueryChanged(s))
                            },
                            onSearch = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            },
                            active = state.isSearchBarVisible,
                            onActiveChange = {b->
                                if(!b){
                                    onEvent(HomeEvents.OnCloseIconClicked)
                                }
                            }
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                items(state.posts) {
//                                PostCard(
//                                    article = a,
//                                    onClick = {
//                                        showBottomSheet = true
//                                        onEvent(NewsScreenEvents.OnNewsCardClicked(it))
//                                    }
//                                )
                                }
                            }
                        }
                    }
                }
            } else {
                Scaffold(
                    modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        HomeScreenTopBar(
                            scrollBehavior = scrollBehavior,
                            onProfile = {
                                navController.navigate(Routes.ProfileScreen.route)
                            },
                            onSearch = {
                                coroutineScope.launch {
                                    delay(500)
                                    focusRequester.requestFocus()
                                }
                                onEvent(HomeEvents.OnSearchIconClicked)
                            }
                        )
                    },
                    floatingActionButtonPosition = FabPosition.Center, floatingActionButton = {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary)

                        ) {
                            IconButton(
                                modifier = Modifier
                                    .size(56.dp) // Size of the FAB
                                    .background(MaterialTheme.colorScheme.primary, shape = CircleShape),
                                onClick = {}
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add Post",
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    }
                ) { pv ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pv)
                    ) {
//                        Categories(
//                            pagerState = pagerState,
//                            categories = state.categories,
//                            onCategorySelected = {
//                                coroutineScope.launch {
//                                    pagerState.animateScrollToPage(it)
//                                }
//                            }
//                        )
                        HorizontalPager(
                            state = pagerState,
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                items(state.posts) { a ->
                                    PostCard(
//                                        article = a,
//                                        onClick = {
//                                            showBottomSheet = true
//                                            onEvent(NewsScreenEvents.OnNewsCardClicked(it))
//                                        }
                                    )
                                }
                            }
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                //if (state.error != null)
//                                    RetryContent(
//                                        error = states.error,
//                                        onRetry = {
//                                            onEvent(NewsScreenEvents.OnCategoryChanged(states.category))
//                                        }
//                                    )
                            }
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenTopBar(
    onProfile: () -> Unit,
    onSearch: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                text = "Posts",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        actions = {
            IconButton(
                onClick = onSearch
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
            IconButton(
                onClick = onProfile
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Search"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    )
}