package tech.toshitworks.blogapp.presentation.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tech.toshitworks.blogapp.domain.model.CategoryBody

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Categories(
    pagerState: PagerState,
    categories: List<CategoryBody>,
    onCategorySelected: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        edgePadding = 0.dp,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        val category: MutableList<CategoryBody> = categories.toMutableStateList()
        category.add(0,CategoryBody(0,"","General"))
        if (categories.isNotEmpty())
            category.forEachIndexed { index, c ->
                Tab(
                    content = {
                        Text(
                            modifier = Modifier
                                .padding(vertical = 8.dp, horizontal = 2.dp),
                            text = c.title,
                            fontSize = 20.sp,
                            color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.background else Color.Unspecified
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        onCategorySelected(index)
                    }
                )
            }
    }
}
