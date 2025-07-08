package org.example.project.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import composemutiplatformproject.composeapp.generated.resources.Res
import composemutiplatformproject.composeapp.generated.resources.hide_password
import composemutiplatformproject.composeapp.generated.resources.login
import composemutiplatformproject.composeapp.generated.resources.password
import composemutiplatformproject.composeapp.generated.resources.show_password
import composemutiplatformproject.composeapp.generated.resources.success
import composemutiplatformproject.composeapp.generated.resources.user
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.stringResource

@Serializable
object Login

@Composable
fun Login(
    onLoggedIn: () -> Unit,
    viewModel: LoginViewModel = viewModel { LoginViewModel() }
) {
    LaunchedEffect(viewModel.state.loggedIn) {
        if (viewModel.state.loggedIn) onLoggedIn()
    }

    val state = viewModel.state
    val message = when {
        state.error != null -> stringResource(state.error)
        else -> null
    }

    var user by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val loginEnabled = user.isNotEmpty() && password.isNotEmpty()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
    ) {
        OutlinedTextField(
            value = user,
            onValueChange = { user = it },
            label = { Text(stringResource(Res.string.user)) },
            isError = state.error != null,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        var isPassVisible by remember { mutableStateOf(false) }
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(Res.string.password)) },
            isError = state.error != null,
            visualTransformation = if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {isPassVisible = !isPassVisible}) {
                    Icon(
                        imageVector = if (isPassVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = if (isPassVisible) stringResource(Res.string.hide_password) else stringResource(Res.string.show_password)
                    )
                }
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions { if (loginEnabled) viewModel.login(user, password) }
        )

        /*PasswordTextField(
            value = password,
            onValueChange = { password = it },
            onDone = { if (loginEnabled) viewModel.login(user, password) },
            isError = state.error != null
        )*/

        Button(
            onClick = { viewModel.login(user, password)  },
            enabled = loginEnabled
        ) {
            Text(stringResource(Res.string.login))
        }
        if (message != null) {
            Text(message)
        }
    }
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false
) {
    var isPassVisible by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = { onValueChange },
        label = { Text(stringResource(Res.string.password)) },
        isError = isError,
        visualTransformation = if (isPassVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {isPassVisible = !isPassVisible}) {
                Icon(
                    imageVector = if (isPassVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (isPassVisible) stringResource(Res.string.hide_password) else stringResource(Res.string.show_password)
                )
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions { onDone() }
    )
}