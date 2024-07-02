package tech.toshitworks.blogapp.presentation.add_post

import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.FormatAlignLeft
import androidx.compose.material.icons.automirrored.filled.FormatAlignRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddLink
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatAlignCenter
import androidx.compose.material.icons.filled.FormatBold
import androidx.compose.material.icons.filled.FormatColorText
import androidx.compose.material.icons.filled.FormatItalic
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.FormatUnderlined
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import tech.toshitworks.blogapp.presentation.components.AddCategoryDialog
import tech.toshitworks.blogapp.presentation.components.ImageHolder
import tech.toshitworks.blogapp.utils.Routes
import tech.toshitworks.blogapp.utils.SnackBarEvent

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddPostPage(
    viewModel: AddPostViewModel,
    navController: NavHostController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var isDialogOpen by rememberSaveable {
        mutableStateOf(false)
    }
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val focusRequester = remember {
        FocusRequester()
    }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val rteState = rememberRichTextState()
    val titleSize = MaterialTheme.typography.displaySmall.fontSize
    val subtitleSize = MaterialTheme.typography.titleLarge.fontSize
    val snackBarEvent = viewModel.snackBarEventFlow
    val snackBarHostState = remember {
        SnackbarHostState()
    }
    val singleImagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        onEvent(AddPostEvents.OnAddChangePhoto(it))
    }
    LaunchedEffect(key1 = true) {
        snackBarEvent.collect{
            when(it){
                is SnackBarEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = it.message,
                        duration = it.duration
                    )
                }

                is SnackBarEvent.ShowPostSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = POST_ADDED
                    )
                    navController.navigate("${Routes.PostScreen.route}/${it.id}")
                }
            }
        }
    }
    AddCategoryDialog(
        isOpen = isDialogOpen,
        onDismissRequest = {
            isDialogOpen = false
        },
        onConfirmButtonClick = {
            isDialogOpen = false
        },
        title = state.commentTitle,
        description = state.commentBody,
        onTitleNameChange = {
            println(it)
            println(state.commentTitle)
            println("koo")
            onEvent(AddPostEvents.OnCommentTitleChange(it))
        },
        onDescriptionChange = {
            onEvent(AddPostEvents.OnCommentBodyChange(it))
        }
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Crossfade(
            targetState = state.isSearchBarVisible,
            label = "Switch"
        ) {
            if (it) {
                Scaffold {
                    Column {
                        SearchBar(
                            modifier = Modifier
                                .focusRequester(focusRequester),
                            query = state.searchQuery,
                            placeholder = {
                                Text(text = "Search categories ...")
                            },
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        onEvent(AddPostEvents.OnCloseIconClick)
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close Bar"
                                    )
                                }
                            },
                            onQueryChange = { s ->
                                onEvent(AddPostEvents.OnSearchQueryChange(s))
                            },
                            onSearch = {
                                keyboardController?.hide()
                                focusManager.clearFocus()
                            },
                            active = state.isSearchBarVisible,
                            onActiveChange = { b ->
                                if (!b) {
                                    onEvent(AddPostEvents.OnCloseIconClick)
                                }
                            }
                        ) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(6.dp),
                            ) {
                                items(state.categories) { category ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                onEvent(AddPostEvents.OnCloseIconClick)
                                                onEvent(AddPostEvents.OnCategoryAdd(category))
                                            },
                                        elevation = CardDefaults.cardElevation(4.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.surface
                                        ),
                                        border = BorderStroke(
                                            1.dp,
                                            MaterialTheme.colorScheme.primary
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                        ) {
                                            Column(
                                                modifier = Modifier
                                                    .weight(2f)
                                                    .padding(16.dp),
                                                verticalArrangement = Arrangement.spacedBy(8.dp),
                                                horizontalAlignment = Alignment.Start
                                            ) {
                                                Text(
                                                    text = "Title: ${category.title}",
                                                    style = MaterialTheme.typography.titleMedium,
                                                    fontWeight = FontWeight.Bold
                                                )
                                                Text(
                                                    text = "Description: ${category.description}",
                                                    style = MaterialTheme.typography.bodyMedium
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackBarHostState)
                    },
                    topBar = {
                        AddPostTopBar(
                            onSearch = {
                                onEvent(AddPostEvents.OnSearchIconClick)
                            },
                            title = state.title,
                            onChangeClick = {s->
                                onEvent(AddPostEvents.OnTitleChange(s))
                            }
                            , onBack = {
                                navController.navigateUp()
                            }
                        )
                    },
                    floatingActionButtonPosition = FabPosition.Start,
                    floatingActionButton = {
                        if (!state.showEditorControls)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(5.dp))
                                    .background(MaterialTheme.colorScheme.primary)

                            ) {
                                IconButton(onClick = {
                                    onEvent(AddPostEvents.OnChangeEditorControlVisibility)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowUpward,
                                        contentDescription = ""
                                    )
                                }
                            }
                    }
                ) { pv ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(pv)
                            .imePadding()
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(7.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = if (state.selectedCategory == null) "Select a category or add your own" else state.selectedCategory?.title
                                        ?: "",
                                    color = if (state.selectedCategory == null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onPrimaryContainer,
                                    fontSize = 20.sp
                                )
                                Box {
                                    Row {
                                        if(state.selectedCategory == null)
                                            IconButton(
                                                onClick = {
                                                    isDialogOpen = true
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Add,
                                                    contentDescription = "",
                                                )
                                            }
                                        if (state.selectedCategory != null)
                                            IconButton(
                                                onClick = {
                                                    onEvent(AddPostEvents.OnRemoveCategory)
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.Close,
                                                    contentDescription = "",
                                                )
                                            }
                                    }
                                }
                            }
                        }
                        if (state.image == null) {
                            Card(
                                modifier = Modifier
                                    .padding(7.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(4.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Want to add image for post? Add here",
                                        color = MaterialTheme.colorScheme.error,
                                        fontSize = 20.sp
                                    )
                                    IconButton(onClick = {
                                        singleImagePicker.launch(
                                            PickVisualMediaRequest(mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Image,
                                            contentDescription = "",
                                        )
                                    }
                                }
                            }
                        } else {
                            Card(
                                modifier = Modifier
                                    .padding(7.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .padding(7.dp)
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Image",
                                            fontSize = 30.sp
                                        )
                                        Box {
                                            Row {

                                                IconButton(
                                                    onClick = {
                                                        onEvent(AddPostEvents.OnChangeImageVisibility)
                                                    }
                                                ) {
                                                    Icon(
                                                        imageVector = if (state.showImage) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                                                        contentDescription = ""
                                                    )
                                                }
                                                IconButton(onClick = {
                                                    onEvent(AddPostEvents.OnRemoveImage)
                                                }) {
                                                    Icon(
                                                        imageVector = Icons.Default.Close,
                                                        contentDescription = ""
                                                    )
                                                }
                                            }
                                        }
                                    }
                                    if (state.showImage)
                                        ImageHolder(imageUrl = null, imageUri = state.image)
                                }
                            }
                        }
                        Row {
                            Card(
                                modifier = Modifier
                                    .padding(3.dp)
                            ) {
                                if (state.showEditorControls)
                                    EditorControls(
                                        modifier = Modifier.weight(2f),
                                        state = rteState,
                                        onHideClick = {
                                            onEvent(AddPostEvents.OnChangeEditorControlVisibility)
                                        },
                                        isImage = state.image != null,
                                        imageVisible = state.showImage,
                                        error = state.error.isNotEmpty(),
                                        onBoldClick = {
                                            rteState.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                                        },
                                        onItalicClick = {
                                            rteState.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                                        },
                                        onUnderlineClick = {
                                            rteState.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                                        },
                                        onTitleClick = {
                                            rteState.toggleSpanStyle(SpanStyle(fontSize = titleSize))
                                        },
                                        onSubtitleClick = {
                                            rteState.toggleSpanStyle(SpanStyle(fontSize = subtitleSize))
                                        },
                                        onTextColorClick = {
                                            rteState.toggleSpanStyle(SpanStyle(color = Color.Red))
                                        },
                                        onStartAlignClick = {
                                            rteState.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Start))
                                        },
                                        onEndAlignClick = {
                                            rteState.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.End))
                                        },
                                        onCenterAlignClick = {
                                            rteState.toggleParagraphStyle(ParagraphStyle(textAlign = TextAlign.Center))
                                        },
                                        onExportClick = {
                                            onEvent(AddPostEvents.OnAddPost(rteState.toHtml(),context))
                                        }
                                    )

                            }
                            RichTextEditor(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .fillMaxHeight()
                                    .padding(7.dp)
                                    .weight(8f),
                                state = rteState,
                            )
                        }
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddPostTopBar(
    onBack: () -> Unit,
    onSearch: () -> Unit,
    title: String,
    onChangeClick: (String) -> Unit
) {
    TopAppBar(
        title = {

                TextField(
                    value = title,
                    onValueChange = {
                        onChangeClick(it)
                    },
                    label = {
                        Text(text = "Title")
                    },
                    placeholder = {
                        Text(text = "Add Title For Post")
                    },
                    shape = RoundedCornerShape(13.dp),
                    maxLines = 1
                )

        },
        actions = {
            IconButton(
                onClick = onSearch
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        navigationIcon = {
            IconButton(
                onClick = onBack,

                ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onPrimary
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

@Composable
fun EditorControls(
    modifier: Modifier = Modifier,
    state: RichTextState,
    error: Boolean,
    isImage: Boolean,
    imageVisible: Boolean,
    onHideClick: () -> Unit,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onTitleClick: () -> Unit,
    onSubtitleClick: () -> Unit,
    onTextColorClick: () -> Unit,
    onStartAlignClick: () -> Unit,
    onEndAlignClick: () -> Unit,
    onCenterAlignClick: () -> Unit,
    onExportClick: () -> Unit,
) {
    var boldSelected by rememberSaveable {
        mutableStateOf(false)
    }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var subtitleSelected by rememberSaveable { mutableStateOf(false) }
    var textColorSelected by rememberSaveable { mutableStateOf(false) }
    var linkSelected by rememberSaveable { mutableStateOf(false) }
    var alignmentSelected by rememberSaveable { mutableIntStateOf(0) }

    var showLinkDialog by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            onHideClick()
        }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDownward,
            contentDescription = ""
        )
    }
    Column(
        modifier = modifier
            .padding(all = 10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = if (!imageVisible || !isImage) Arrangement.SpaceBetween else Arrangement.spacedBy(
            3.dp
        )
    ) {
        ControlWrapper(
            selected = boldSelected,
            onChangeClick = {
                boldSelected = it
            },
            onClick = onBoldClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatBold,
                contentDescription = "Bold Control",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        ControlWrapper(
            selected = italicSelected,
            onChangeClick = {
                italicSelected = it
            },
            onClick = onItalicClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatItalic,
                contentDescription = "Italic Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = underlineSelected,
            onChangeClick = {
                underlineSelected = it
            },
            onClick = onUnderlineClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatUnderlined,
                contentDescription = "Underline Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = titleSelected,
            onChangeClick = {
                titleSelected = it
            },
            onClick = onTitleClick
        ) {
            Icon(
                imageVector = Icons.Default.Title,
                contentDescription = "Title Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = subtitleSelected,
            onChangeClick = {
                subtitleSelected = it
            },
            onClick = onSubtitleClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatSize,
                contentDescription = "Subtitle Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = textColorSelected,
            onChangeClick = {
                textColorSelected = it
            },
            onClick = onTextColorClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatColorText,
                contentDescription = "Text Color Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = linkSelected,
            onChangeClick = {
                linkSelected = it
            },
            onClick = { showLinkDialog = true }
        ) {
            Icon(
                imageVector = Icons.Default.AddLink,
                contentDescription = "Link Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 0,
            onChangeClick = {
                alignmentSelected = 0
            },
            onClick = onStartAlignClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.FormatAlignLeft,
                contentDescription = "Start Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 1,
            onChangeClick = {
                alignmentSelected = 1
            },
            onClick = onCenterAlignClick
        ) {
            Icon(
                imageVector = Icons.Default.FormatAlignCenter,
                contentDescription = "Center Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = alignmentSelected == 2,
            onChangeClick = {
                alignmentSelected = 2
            },
            onClick = onEndAlignClick
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.FormatAlignRight,
                contentDescription = "End Align Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
        ControlWrapper(
            selected = true,
            selectedColor = MaterialTheme.colorScheme.tertiary,
            onChangeClick = { },
            error = error,
            onClick = onExportClick
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = "Export Control",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}


@Composable
private fun ControlWrapper(
    selected: Boolean,
    error: Boolean = false,
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    unselectedColor: Color = MaterialTheme.colorScheme.inversePrimary,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .background(
                if (!error) if (selected) selectedColor else unselectedColor
                else MaterialTheme.colorScheme.errorContainer
            )
            .border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
