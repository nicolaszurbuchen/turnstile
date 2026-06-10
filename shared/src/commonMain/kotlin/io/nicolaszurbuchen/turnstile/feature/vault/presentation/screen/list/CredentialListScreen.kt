package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileBanner
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileEmptyView
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileErrorView
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.infra.ui.InitialLoad
import org.jetbrains.compose.resources.stringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.vault_list_copy_password
import turnstile.shared.generated.resources.vault_list_copy_username
import turnstile.shared.generated.resources.vault_list_empty_subtitle
import turnstile.shared.generated.resources.vault_list_empty_title
import turnstile.shared.generated.resources.vault_list_title

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CredentialListScreen(
    state: CredentialListState,
    onEntryClick: (String) -> Unit,
    onCopyUsername: (String) -> Unit,
    onCopyPassword: (String) -> Unit,
    onCreateClick: () -> Unit,
    onSignOutClick: () -> Unit,
    onRetryInitialLoad: () -> Unit,
    onDismissStreamError: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.vault_list_title),
                        fontWeight = FontWeight.Bold,
                    )
                },
                actions = {
                    IconButton(onClick = onSignOutClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = null,
                        )
                    }
                },
                colors =
                    TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.turnstileColors.surfaceRaised,
                        titleContentColor = MaterialTheme.turnstileColors.textPrimary,
                        actionIconContentColor = MaterialTheme.turnstileColors.textPrimary,
                    ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                containerColor = MaterialTheme.turnstileColors.accent,
                contentColor = MaterialTheme.turnstileColors.onAccent,
                shape = CircleShape,
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                )
            }
        },
        containerColor = MaterialTheme.turnstileColors.background,
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        Column(
            modifier =
                Modifier
                    .padding(padding)
                    .fillMaxSize(),
        ) {
            when (val initialLoad = state.initialLoad) {
                is InitialLoad.Loading -> {
                    CredentialListSkeleton()
                }

                is InitialLoad.Failed -> {
                    TurnstileErrorView(
                        message = initialLoad.error.message,
                        onRetry = onRetryInitialLoad,
                    )
                }

                is InitialLoad.Loaded -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        state.streamError?.let { error ->
                            TurnstileBanner(
                                message = error.message,
                                onDismiss = onDismissStreamError,
                                modifier =
                                    Modifier
                                        .padding(
                                            horizontal = MaterialTheme.spacing.md,
                                            vertical = MaterialTheme.spacing.sm,
                                        ),
                            )
                        }

                        if (state.isEmpty) {
                            TurnstileEmptyView(
                                title = stringResource(Res.string.vault_list_empty_title),
                                subtitle = stringResource(Res.string.vault_list_empty_subtitle),
                            )
                        } else {
                            LazyColumn(
                                contentPadding = PaddingValues(MaterialTheme.spacing.md),
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .navigationBarsPadding(),
                            ) {
                                items(
                                    items = state.entries,
                                    key = { it.id },
                                ) { entry ->
                                    CredentialListItem(
                                        entry = entry,
                                        onClick = { onEntryClick(entry.id) },
                                        onCopyUsername = onCopyUsername,
                                        onCopyPassword = onCopyPassword,
                                    )
                                }
                                item { Spacer(Modifier.height(MaterialTheme.spacing.md)) }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CredentialListItem(
    entry: CredentialUi,
    onClick: () -> Unit,
    onCopyUsername: (String) -> Unit,
    onCopyPassword: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showMenu by remember { mutableStateOf(false) }

    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.turnstileColors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        modifier =
            modifier
                .fillMaxWidth()
                .padding(
                    vertical = 4.dp,
                ),
    ) {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier =
                    Modifier
                        .size(44.dp)
                        .background(MaterialTheme.turnstileColors.accentSubtle, CircleShape),
            ) {
                Text(
                    text = entry.title.firstOrNull()?.uppercase() ?: "?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.turnstileColors.onAccentSubtle,
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = entry.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.turnstileColors.textPrimary,
                )
                Spacer(Modifier.height(MaterialTheme.spacing.xs))
                Text(
                    text = entry.username,
                    fontSize = 13.sp,
                    color = MaterialTheme.turnstileColors.textSecondary,
                )
            }

            Spacer(Modifier.weight(1f))

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = MaterialTheme.turnstileColors.textSecondary,
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(
                        text = { Text(stringResource(Res.string.vault_list_copy_username)) },
                        onClick = {
                            onCopyUsername(entry.username)
                            showMenu = false
                        },
                    )
                    DropdownMenuItem(
                        text = { Text(stringResource(Res.string.vault_list_copy_password)) },
                        onClick = {
                            onCopyPassword(entry.password)
                            showMenu = false
                        },
                    )
                }
            }
        }
    }
}
