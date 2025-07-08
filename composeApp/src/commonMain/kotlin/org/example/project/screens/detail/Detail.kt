package org.example.project.screens.detail

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data class Detail(
    val id: Int
)

@Composable
fun Detail(viewModel: DetailViewModel) {
    viewModel.state?.let { item ->
        Text(item.title)
    }
}