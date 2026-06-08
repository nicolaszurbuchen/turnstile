package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Notes
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Title
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.infra.design.component.AppBanner
import io.nicolaszurbuchen.turnstile.infra.design.component.AppErrorView
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileTextField
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.infra.ui.InitialLoad

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialEditorScreen(
    state: CredentialEditorState,
    onTitleChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onMemoChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    onRetry: () -> Unit,
    onDismissSaveError: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (state.id.isEmpty()) "New Credential" else "Edit Credential",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = turnstileColors.textPrimary,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancelClick) {
                        Icon(Icons.Default.Close, contentDescription = "Cancel", tint = turnstileColors.textPrimary)
                    }
                },
                actions = {
                    if (state.initialLoad is InitialLoad.Loaded) {
                        IconButton(onClick = onSaveClick, enabled = state.canSave) {
                            Icon(Icons.Default.Done, contentDescription = "Save", tint = if (state.canSave) turnstileColors.accent else turnstileColors.textDisabled)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = turnstileColors.background),
            )
        },
        containerColor = turnstileColors.background,
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        when (val initialLoad = state.initialLoad) {
            is InitialLoad.Loading -> {
                // TODO: Skeleton
            }
            is InitialLoad.Failed -> {
                AppErrorView(
                    message = initialLoad.error.message,
                    onRetry = onRetry,
                    modifier = Modifier.padding(padding),
                )
            }
            is InitialLoad.Loaded -> {
                Column(
                    modifier =
                        Modifier
                            .padding(padding)
                            .fillMaxSize()
                            .padding(horizontal = spacing.md),
                ) {
                    state.saveError?.let { error ->
                        AppBanner(
                            message = error.message,
                            onDismiss = onDismissSaveError,
                            modifier = Modifier.padding(vertical = spacing.sm),
                        )
                    }

                    Spacer(Modifier.height(spacing.md))

                    EditLabel("Title")
                    TurnstileTextField(
                        value = state.title,
                        onValueChange = onTitleChange,
                        hint = "e.g. Google",
                        leadingIcon = Icons.Default.Title,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(spacing.md))

                    EditLabel("Username")
                    TurnstileTextField(
                        value = state.username,
                        onValueChange = onUsernameChange,
                        hint = "Username",
                        leadingIcon = Icons.Default.Person,
                        isError = state.username.isEmpty() && state.usernameError != null,
                        errorMessage = if (state.username.isEmpty()) state.usernameError else null,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(spacing.md))

                    EditLabel("Password")
                    TurnstileTextField(
                        value = state.password,
                        onValueChange = onPasswordChange,
                        hint = "Password",
                        leadingIcon = Icons.Default.Lock,
                        isPassword = true,
                        isError = state.password.isEmpty() && state.passwordError != null,
                        errorMessage = if (state.password.isEmpty()) state.passwordError else null,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(spacing.md))

                    EditLabel("Memo")
                    TurnstileTextField(
                        value = state.memo.orEmpty(),
                        onValueChange = onMemoChange,
                        hint = "Memo",
                        leadingIcon = Icons.Default.Notes,
                        singleLine = false,
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                    )
                }
            }
        }
    }
}

@Composable
private fun EditLabel(text: String) {
    Text(
        text = text,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.turnstileColors.textSecondary,
        modifier = Modifier.padding(bottom = 6.dp),
    )
}
