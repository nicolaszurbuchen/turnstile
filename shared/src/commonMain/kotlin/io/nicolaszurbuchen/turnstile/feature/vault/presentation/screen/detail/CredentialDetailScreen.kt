package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileErrorView
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors
import io.nicolaszurbuchen.turnstile.infra.ui.InitialLoad
import org.jetbrains.compose.resources.stringResource
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.common_password
import turnstile.shared.generated.resources.common_username
import turnstile.shared.generated.resources.vault_detail_none
import turnstile.shared.generated.resources.vault_memo

@Composable
fun CredentialDetailScreen(
    state: CredentialDetailState,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onCopyUsername: (String) -> Unit,
    onCopyPassword: (String) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(
                            horizontal = MaterialTheme.spacing.sm,
                            vertical = MaterialTheme.spacing.sm,
                        ),
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.turnstileColors.textPrimary,
                    )
                }
                Spacer(Modifier.weight(1f))

                if (state.initialLoad is InitialLoad.Loaded) {
                    IconButton(onClick = onEditClick) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            tint = MaterialTheme.turnstileColors.textPrimary,
                        )
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.turnstileColors.textPrimary,
                        )
                    }
                }
            }
        },
        containerColor = MaterialTheme.turnstileColors.background,
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        when (val initialLoad = state.initialLoad) {
            is InitialLoad.Loading -> {
                // TODO: Skeleton
            }

            is InitialLoad.Failed -> {
                TurnstileErrorView(
                    message = initialLoad.error.message,
                    onRetry = onRetry,
                    modifier = Modifier.padding(padding),
                )
            }

            is InitialLoad.Loaded -> {
                state.credential?.let { credential ->
                    Column(
                        modifier =
                            Modifier
                                .padding(padding)
                                .fillMaxSize()
                                .padding(horizontal = MaterialTheme.spacing.md),
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Card(
                                shape = CircleShape,
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.turnstileColors.surfaceRaised),
                                modifier = Modifier.size(64.dp),
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    Text(
                                        text = credential.title.firstOrNull()?.uppercase() ?: "?",
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.turnstileColors.textSecondary,
                                    )
                                }
                            }
                            Spacer(Modifier.width(MaterialTheme.spacing.md))
                            Text(
                                text = credential.title,
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.turnstileColors.textPrimary,
                            )
                        }

                        Spacer(Modifier.height(MaterialTheme.spacing.xl))

                        DetailField(
                            label = stringResource(Res.string.common_username),
                            value = credential.username,
                            onCopy = { onCopyUsername(credential.username) },
                        )
                        Spacer(Modifier.height(MaterialTheme.spacing.lg))

                        DetailField(
                            label = stringResource(Res.string.common_password),
                            value = credential.password,
                            isPassword = true,
                            onCopy = { onCopyPassword(credential.password) },
                        )
                        Spacer(Modifier.height(MaterialTheme.spacing.lg))

                        DetailField(
                            label = stringResource(Res.string.vault_memo),
                            value = credential.memo ?: "",
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailField(
    label: String,
    value: String,
    isPassword: Boolean = false,
    onCopy: (() -> Unit)? = null,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = MaterialTheme.turnstileColors.textTertiary,
            fontWeight = FontWeight.Medium,
        )
        Spacer(Modifier.height(MaterialTheme.spacing.xs))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = if (isPassword) "••••••••" else value.ifBlank { stringResource(Res.string.vault_detail_none) },
                fontSize = 16.sp,
                color = if (value.isBlank()) MaterialTheme.turnstileColors.textDisabled else MaterialTheme.turnstileColors.textPrimary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.weight(1f),
            )
            if (onCopy != null && value.isNotBlank()) {
                IconButton(
                    onClick = onCopy,
                    modifier = Modifier.size(24.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ContentCopy,
                        contentDescription = null,
                        tint = MaterialTheme.turnstileColors.accent,
                        modifier = Modifier.size(16.dp),
                    )
                }
            }
        }
    }
}
