package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthBackground
import org.jetbrains.compose.resources.stringResource
import turnstile.composeapp.generated.resources.Res
import turnstile.composeapp.generated.resources.auth_create_account
import turnstile.composeapp.generated.resources.auth_sign_in
import turnstile.composeapp.generated.resources.auth_tagline

private val ButtonPrimary = Color(0xFF2C2C2E)
private val ButtonSecondary = Color(0xFFD1D1D6)

@Composable
fun WelcomeScreen(
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthBackground(modifier) {
        // Subtitle positioned above center
        Box(
            modifier = Modifier
                .align(BiasAlignment(horizontalBias = 0f, verticalBias = -0.4f))
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.auth_tagline),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
            )
        }

        // Buttons at the bottom, above the navigation bar
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 32.dp, vertical = 52.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(
                onClick = onSignInClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonPrimary),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = stringResource(Res.string.auth_sign_in),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
            }
            Button(
                onClick = onSignUpClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonSecondary,
                    contentColor = Color(0xFF1C1C1E),
                ),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = stringResource(Res.string.auth_create_account),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}
