package org.example.project

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.example.project.screens.detail.Detail
import org.example.project.screens.detail.DetailViewModel
import org.example.project.screens.home.Home
import org.example.project.screens.login.Login
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    KmpTheme {
        val navController = rememberNavController()
        NavHost(navController, startDestination = Login) {
            composable<Login> {
                Login(onLoggedIn = {navController.navigate(Home)})
            }
            composable<Home> {
                Home(onItemClick = { navController.navigate(Detail(it.id)) })
            }
            composable<Detail> { backStackEntry ->
                val detail = backStackEntry.toRoute<Detail>()
                Detail(
                    viewModel = viewModel { DetailViewModel(detail.id) },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}