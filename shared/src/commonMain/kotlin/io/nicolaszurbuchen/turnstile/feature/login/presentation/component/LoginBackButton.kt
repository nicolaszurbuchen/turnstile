package io.nicolaszurbuchen.turnstile.feature.login.presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.design.theme.turnstileColors

@Composable
fun LoginBackButton(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledIconButton(
        onClick = onNavigateBack,
        colors =
            IconButtonDefaults.filledIconButtonColors(
                containerColor = MaterialTheme.turnstileColors.surface,
                contentColor = MaterialTheme.turnstileColors.textPrimary,
            ),
        modifier = modifier.padding(MaterialTheme.spacing.md),
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
        )
    }
}
