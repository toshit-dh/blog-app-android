package tech.toshitworks.blogapp.presentation.components

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import tech.toshitworks.blogapp.R

@Composable
fun ImageHolder(
    modifier: Modifier = Modifier,
    imageUrl: String?
){
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentDescription = "Article Image",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .aspectRatio(16 / 9f),
        placeholder = painterResource(id = R.drawable.loading),
        error = painterResource(id = R.drawable.error)
    )
}