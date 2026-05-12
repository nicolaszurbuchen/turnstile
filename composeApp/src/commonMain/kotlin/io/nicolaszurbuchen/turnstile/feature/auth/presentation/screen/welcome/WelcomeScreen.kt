package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthBackground

private val ButtonDark = Color(0xFF2C2C2E)

@Composable
fun WelcomeScreen(
    onSignInClicked: () -> Unit,
    onSignUpClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthBackground(modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 52.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = "TURNSTILE",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 4.sp,
                color = Color.White,
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Your gateway to everything",
                fontSize = 16.sp,
                color = Color.White.copy(alpha = 0.75f),
            )
            Spacer(Modifier.height(48.dp))
            Button(
                onClick = onSignInClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ButtonDark),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(
                    text = "Sign In",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                )
            }
            Spacer(Modifier.height(16.dp))
            TextButton(
                onClick = onSignUpClicked,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Create an account",
                    fontSize = 15.sp,
                    color = Color.White,
                    textDecoration = TextDecoration.Underline,
                )
            }
        }
    }
}
