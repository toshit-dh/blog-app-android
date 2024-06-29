package tech.toshitworks.blogapp.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import tech.toshitworks.blogapp.domain.model.CommentBody

import tech.toshitworks.blogapp.utils.dateFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsBottomSheet(
    comment: String,
    onSend: () -> Unit,
    onCommentChange: (String) -> Unit,
    sheetState: SheetState,
    isOpen: Boolean,
    comments: List<CommentBody>,
    bottomSheetTitle: String = "Comments",
    onNameClick: (Int) -> Unit,
    onDismissRequest: () -> Unit
) {
    if (isOpen)
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = onDismissRequest,
            dragHandle = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetDefaults.DragHandle()
                    Text(
                        text = bottomSheetTitle
                    )
                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )
                    HorizontalDivider()
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(comments) { comment ->
                        val date = dateFormatter(comment.date)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onNameClick(comment.user.id)
                                }
                                .padding(8.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = comment.user.name
                                    )
                                    Text(
                                        text = date
                                    )
                                }
                                HorizontalDivider()
                                Text(text = comment.content)
                            }
                        }
                    }
                    if (comments.isEmpty())
                        item {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "No Comments"
                            )
                        }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(text = "Comment")
                    },
                    placeholder = {
                        Text(text = "Add a comment")
                    },
                    maxLines = 2,
                    value = comment,
                    onValueChange = {
                        onCommentChange(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Send
                    ),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            onSend()
                        }
                    )
                )
            }
        }
}
