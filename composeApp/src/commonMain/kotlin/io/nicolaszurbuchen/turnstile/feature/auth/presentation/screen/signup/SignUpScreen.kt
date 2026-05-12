package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.nicolaszurbuchen.turnstile.feature.auth.presentation.component.AuthBackground

private val ButtonDark = Color(0xFF2C2C2E)
private val CardBackground = Color(0xFFF5F5F5)
private val FieldBackground = Color(0xFFFFFFFF)
private val LabelGrey = Color(0xFF8E8E93)

@Composable
fun SignUpScreen(
    state: SignUpState,
    onFullNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onSubmitted: () -> Unit,
    onSignInClicked: () -> Unit,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    AuthBackground(modifier) {
        TextButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(8.dp),
        ) {
            Text(
                text = "← Back",
                color = Color.White,
                fontSize = 15.sp,
            )
        }

        Surface(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
            color = CardBackground,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 36.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp),
            ) {
                Text(
                    text = "Register",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1C1C1E),
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Create your new account",
                    fontSize = 14.sp,
                    color = LabelGrey,
                )
                Spacer(Modifier.height(28.dp))

                OutlinedTextField(
                    value = state.fullName,
                    onValueChange = onFullNameChanged,
                    label = { Text("Full Name") },
                    isError = state.fullNameError != null,
                    supportingText = state.fullNameError?.let { { Text(it) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = FieldBackground,
                        focusedContainerColor = FieldBackground,
                    ),
                    shape = RoundedCornerShape(12.dp),
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.email,
                    onValueChange = onEmailChanged,
                    label = { Text("Email") },
                    isError = state.emailError != null,
                    supportingText = state.emailError?.let { { Text(it) } },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = FieldBackground,
                        focusedContainerColor = FieldBackground,
                    ),
                    shape = RoundedCornerShape(12.dp),
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = state.password,
                    onValueChange = onPasswordChanged,
                    label = { Text("Password") },
                    isError = state.passwordError != null,
                    supportingText = state.passwordError?.let { { Text(it) } },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = FieldBackground,
                        focusedContainerColor = FieldBackground,
                    ),
                    shape = RoundedCornerShape(12.dp),
                )

                state.submitError?.let { error ->
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 13.sp,
                    )
                }

                Spacer(Modifier.height(24.dp))

                Button(
                    onClick = onSubmitted,
                    enabled = state.canSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ButtonDark),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    if (state.loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.height(20.dp),
                            color = Color.White,
                        )
                    } else {
                        Text(
                            text = "Sign Up",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                        )
                    }
                }

                Spacer(Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Already have an account?",
                        fontSize = 14.sp,
                        color = LabelGrey,
                    )
                    TextButton(onClick = onSignInClicked) {
                        Text(
                            text = "Sign In",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ButtonDark,
                        )
                    }
                }
            }
        }
    }
}
