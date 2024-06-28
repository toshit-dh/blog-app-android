package tech.toshitworks.blogapp.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import tech.toshitworks.blogapp.domain.model.PostBody

@Composable
fun PostCard(
    postBody: PostBody,
    onCommentClick: (Int) -> Unit,
    fullPost: (Int) -> Unit
) {

}