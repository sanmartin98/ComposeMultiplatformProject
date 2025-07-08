package org.example.project.screens.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import composemutiplatformproject.composeapp.generated.resources.Res
import composemutiplatformproject.composeapp.generated.resources.back
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

@Serializable
data class Detail(
    val id: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Detail(viewModel: DetailViewModel, onBack: () -> Unit) {
    viewModel.state?.let { item ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(item.title) },
                    navigationIcon = {
                        IconButton(onClick = { onBack() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(Res.string.back)
                            )
                        }
                    }
                )
            }
        ) {
            AsyncImage(
                model = item.thumb,
                contentDescription = item.title,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}