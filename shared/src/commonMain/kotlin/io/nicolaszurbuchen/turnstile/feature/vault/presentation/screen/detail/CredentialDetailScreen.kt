package io.nicolaszurbuchen.turnstile.feature.vault.presentation.screen.detail

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
import androidx.compose.material.icons.filled.ArrowBack
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
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors

@Composable
fun CredentialDetailScreen(
    state: CredentialDetailState,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing
    val credential = state.credential

    Scaffold(
        topBar = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = spacing.sm, vertical = spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = turnstileColors.textPrimary)
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onEditClick) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit", tint = turnstileColors.textPrimary)
                }
                IconButton(onClick = onDeleteClick) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete", tint = turnstileColors.danger)
                }
            }
        },
        containerColor = turnstileColors.background,
        modifier = modifier.fillMaxSize(),
    ) { padding ->
        if (credential != null) {
            Column(
                modifier =
                    Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .padding(horizontal = spacing.md),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Card(
                        shape = CircleShape,
                        colors = CardDefaults.cardColors(containerColor = turnstileColors.surfaceRaised),
                        modifier = Modifier.size(64.dp),
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center,
                        ) {
                            Text(
                                text = credential.title.firstOrNull()?.uppercase() ?: "?",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = turnstileColors.textSecondary,
                            )
                        }
                    }
                    Spacer(Modifier.width(spacing.md))
                    Text(
                        text = credential.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = turnstileColors.textPrimary,
                    )
                }

                Spacer(Modifier.height(spacing.xl))

                DetailField(label = "Username", value = credential.username)
                Spacer(Modifier.height(spacing.lg))
                DetailField(label = "Password", value = credential.password, isPassword = true)
                
                if (credential.memo.isNotEmpty()) {
                    Spacer(Modifier.height(spacing.lg))
                    DetailField(label = "Memo", value = credential.memo)
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
) {
    val turnstileColors = MaterialTheme.turnstileColors
    val spacing = MaterialTheme.spacing

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = turnstileColors.textTertiary,
            fontWeight = FontWeight.Medium,
        )
        Spacer(Modifier.height(spacing.xs))
        Text(
            text = if (isPassword) "••••••••" else value,
            fontSize = 16.sp,
            color = turnstileColors.textPrimary,
            fontWeight = FontWeight.SemiBold,
        )
    }
}
