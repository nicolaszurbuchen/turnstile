package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Key
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
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileTextField
import io.nicolaszurbuchen.turnstile.infra.design.component.AppBanner
import io.nicolaszurbuchen.turnstile.infra.design.component.AppErrorView
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
                        IconButton(onClick = onSaveClick, enabled = !state.isSaving) {
                            Icon(Icons.Default.Done, contentDescription = "Save", tint = turnstileColors.accent)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = turnstileColors.background),
            )
        },
        containerColor = turnstileColors.background,
        modifier = modifier.fillMaxSize().statusBarsPadding(),
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

                    TurnstileTextField(
                        value = state.title,
                        onValueChange = onTitleChange,
                        hint = "Title",
                        leadingIcon = Icons.Default.Title,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(spacing.md))

                    TurnstileTextField(
                        value = state.username,
                        onValueChange = onUsernameChange,
                        hint = "Username",
                        leadingIcon = Icons.Default.Person,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(spacing.md))

                    TurnstileTextField(
                        value = state.password,
                        onValueChange = onPasswordChange,
                        hint = "Password",
                        leadingIcon = Icons.Default.Key,
                        isPassword = true,
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(Modifier.height(spacing.md))

                    TurnstileTextField(
                        value = state.memo.orEmpty(),
                        onValueChange = onMemoChange,
                        hint = "Memo",
                        leadingIcon = Icons.Default.Notes,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
