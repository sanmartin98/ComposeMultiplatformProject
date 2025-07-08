package org.example.project.screens.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import composemutiplatformproject.composeapp.generated.resources.Res
import composemutiplatformproject.composeapp.generated.resources.app_name
import composemutiplatformproject.composeapp.generated.resources.clone
import composemutiplatformproject.composeapp.generated.resources.delete
import composemutiplatformproject.composeapp.generated.resources.item_cloned
import composemutiplatformproject.composeapp.generated.resources.item_deleted
import composemutiplatformproject.composeapp.generated.resources.more_options
import composemutiplatformproject.composeapp.generated.resources.show_grid_view
import composemutiplatformproject.composeapp.generated.resources.show_list_view
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(viewModel: HomeViewModel = viewModel { HomeViewModel() }) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    var isGrid by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    viewModel.state.notification?.let { notification ->
        val stringRes = if (notification.action == Action.CLONE) Res.string.item_cloned else Res.string.item_deleted
        val message = stringResource(stringRes, notification.title)

        LaunchedEffect(message) {
            snackBarHostState.showSnackbar(message)
            viewModel.clearNotification()
        }
    }

    Scaffold(
        topBar = {
            HomeTopAppBar(
                isGrid = isGrid,
                onGridClick = { isGrid = !isGrid },
                scrollBehavior = scrollBehavior
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        if (isGrid) {
            HomeGrid(
                items = viewModel.state.items,
                onActionClick = viewModel::onAction,
                modifier = Modifier.padding(paddingValues)
            )
        } else {
            HomeList(
                items = viewModel.state.items,
                onActionClick = viewModel::onAction,
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar(
    isGrid: Boolean,
    onGridClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = { Text(stringResource(Res.string.app_name)) },
        scrollBehavior = scrollBehavior,
        actions = {
            IconButton(onClick = onGridClick) {
                Icon(
                    imageVector = if (isGrid) Icons.AutoMirrored.Default.ViewList else Icons.Default.GridView,
                    contentDescription = stringResource(if (isGrid) Res.string.show_list_view else Res.string.show_grid_view )
                )
            }
        }
    )
}


@Composable
fun MoreActionIconButton(onActionClick: (Action) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(onClick = { expanded = !expanded }) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = stringResource(Res.string.more_options)
        )
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.clone)) },
                onClick = { expanded = false; onActionClick(Action.CLONE) }
            )
            DropdownMenuItem(
                text = { Text(stringResource(Res.string.delete)) },
                onClick = { expanded = false; onActionClick(Action.DELETE) }
            )
        }
    }
}