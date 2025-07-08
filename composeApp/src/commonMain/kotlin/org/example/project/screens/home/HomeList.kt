package org.example.project.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.example.project.data.Item

@Composable
fun HomeList(
    items: List<Item>,
    onItemClick: (Item) -> Unit,
    onActionClick: (Action, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize()
    ) {
        itemsIndexed(items, key = {_, item -> item.id}) { index, item ->
            ListItem(
                headlineContent = { Text(item.title) },
                leadingContent = {
                    AsyncImage(
                        model = item.thumb,
                        contentDescription = item.title,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                    )
                },
                supportingContent = { Text(item.subtitle) },
                trailingContent = {
                    MoreActionIconButton(
                        onActionClick = { onActionClick(it, index) }
                    )
                },
                modifier = Modifier
                    .animateItem()
                    .clickable { onItemClick(item) }
            )
        }
    }
}