package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Surface
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
import io.nicolaszurbuchen.turnstile.infra.design.component.AppBanner
import io.nicolaszurbuchen.turnstile.infra.design.component.AppEmptyView
import io.nicolaszurbuchen.turnstile.infra.design.component.AppErrorView
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.infra.ui.InitialLoad

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
            Surface(
                shadowElevation = 4.dp,
                color = MaterialTheme.turnstileColors.surfaceRaised,
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "My Vault",
                            fontWeight = FontWeight.Bold,
                        )
                    },
                    actions = {
                        IconButton(onClick = onSignOutClick) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Logout,
                                contentDescription = "Sign Out",
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
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateClick,
                containerColor = MaterialTheme.turnstileColors.accent,
                contentColor = MaterialTheme.turnstileColors.onAccent,
                shape = CircleShape,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Credential")
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
                    AppErrorView(
                        message = initialLoad.error.message,
                        onRetry = onRetryInitialLoad,
                    )
                }

                is InitialLoad.Loaded -> {
                    Column(modifier = Modifier.fillMaxSize()) {
                        state.streamError?.let { error ->
                            AppBanner(
                                message = error.message,
                                onDismiss = onDismissStreamError,
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.sm),
                            )
                        }

                        if (state.isEmpty) {
                            AppEmptyView(
                                title = "Your vault is empty",
                                subtitle = "Tap + to add your first credential",
                            )
                        } else {
                            LazyColumn(
                                modifier =
                                    Modifier
                                        .fillMaxSize()
                                        .navigationBarsPadding(),
                            ) {
                                items(items = state.entries, key = { it.id }) { entry ->
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
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing
    var showMenu by remember { mutableStateOf(false) }

    Card(
        onClick = onClick,
        modifier =
            modifier
                .fillMaxWidth()
                .padding(horizontal = spacing.md, vertical = 4.dp),
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = turnstileColors.surface),
        border = BorderStroke(1.dp, turnstileColors.borderSubtle),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Row(
            modifier = Modifier.padding(spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier =
                    Modifier
                        .size(44.dp)
                        .background(turnstileColors.accentSubtle, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = entry.title.firstOrNull()?.uppercase() ?: "?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = turnstileColors.onAccentSubtle,
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = entry.title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = turnstileColors.textPrimary,
                )
                Spacer(Modifier.height(spacing.xs))
                Text(
                    text = entry.username,
                    fontSize = 13.sp,
                    color = turnstileColors.textSecondary,
                )
            }

            Spacer(Modifier.weight(1f))

            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        tint = turnstileColors.textSecondary,
                    )
                }

                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("Copy username") },
                        onClick = {
                            onCopyUsername(entry.username)
                            showMenu = false
                        },
                    )
                    DropdownMenuItem(
                        text = { Text("Copy password") },
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
