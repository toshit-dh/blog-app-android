package tech.toshitworks.blogapp.presentation.post

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import tech.toshitworks.blogapp.domain.model.User
import tech.toshitworks.blogapp.presentation.components.CommentsBottomSheet
import tech.toshitworks.blogapp.presentation.components.ImageHolder
import tech.toshitworks.blogapp.utils.Constants
import tech.toshitworks.blogapp.utils.Routes
import tech.toshitworks.blogapp.utils.dateFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostPage(
    viewModel: PostViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val date = dateFormatter(state.postBody.date)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    var isBottomSheetOpen by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    CommentsBottomSheet(
        comment = state.comment,
        sheetState = sheetState,
        isOpen = isBottomSheetOpen,
        comments = state.comments,
        onNameClick = {
            navController.navigate(Routes.ProfileScreen.route)
        },
        onDismissRequest = {
            isBottomSheetOpen = false
        },
        onSend = {
            onEvent(PostEvents.OnCommentSend)
        },
        onCommentChange = {
            onEvent(PostEvents.OnCommentChange(it))
        }
    )

    if (!isLoading) {
        Scaffold(
            topBar = {
                PostTopAppBar(
                    user = state.postBody.user,
                    title = state.postBody.title,
                    onBack = {
                        navController.navigateUp()
                    },
                    onName = {
                        navController.navigate(Routes.ProfileScreen.route)
                    },
                    scrollBehavior = scrollBehavior,
                    isBottomSheetVisible = {
                        isBottomSheetOpen = it
                    },
                    date = date
                )
            },
            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(horizontal = 12.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    ImageHolder(imageUrl = "${Constants.BASEURL}/images/${state.postBody.image}")
                    Text(
                        text = state.postBody.content,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        )
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 40.sp),
                text = "Loading..."
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PostTopAppBar(
    user: User,
    date: String,
    title: String,
    onBack: () -> Unit,
    onName: (Int) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    isBottomSheetVisible: (Boolean) -> Unit
) {
    val collapsedFraction = scrollBehavior.state.collapsedFraction
    val isCollapsed = collapsedFraction > 0.5

    LargeTopAppBar(
        title = {
            Column (
                modifier = Modifier
                    .padding(horizontal = 2.dp)
            ){
                if (!isCollapsed)
                    Text(
                        modifier = Modifier.clickable { onName(user.id) },
                        text = user.name,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    Text(
                        modifier = Modifier
                            .weight(2f),
                        overflow = TextOverflow.Ellipsis,
                        text = title,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.background
                    )
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = date,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBack,

            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        actions = {
            IconButton(
                onClick = {
                    isBottomSheetVisible(true)
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Comment,
                    contentDescription = "Comment",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        colors = TopAppBarDefaults.largeTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        ),
        scrollBehavior = scrollBehavior
    )
}
