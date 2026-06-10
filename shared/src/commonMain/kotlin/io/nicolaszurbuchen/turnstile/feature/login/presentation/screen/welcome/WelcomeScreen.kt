package io.nicolaszurbuchen.turnstile.feature.login.presentation.screen.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstilePrimaryButton
import io.nicolaszurbuchen.turnstile.infra.design.component.TurnstileSecondaryButton
import io.nicolaszurbuchen.turnstile.infra.design.theme.spacing
import io.nicolaszurbuchen.turnstile.infra.ui.UiText
import turnstile.shared.generated.resources.Res
import turnstile.shared.generated.resources.login_sign_in
import turnstile.shared.generated.resources.login_sign_up

@Composable
fun WelcomeScreen(
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier =
            modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(
                    horizontal = MaterialTheme.spacing.xl,
                    vertical = MaterialTheme.spacing.xxl,
                ),
    ) {
        Spacer(
            modifier =
                Modifier
                    .weight(1f),
        )
        TurnstilePrimaryButton(
            text = UiText.Resource(Res.string.login_sign_in),
            onClick = onSignInClick,
        )
        TurnstileSecondaryButton(
            text = UiText.Resource(Res.string.login_sign_up),
            onClick = onSignUpClick,
        )
    }
}
