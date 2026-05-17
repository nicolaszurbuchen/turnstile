package io.nicolaszurbuchen.turnstile.feature.auth.presentation.screen.signup

import io.nicolaszurbuchen.turnstile.core.mvi.MviViewModel

class SignUpViewModel :
    MviViewModel<SignUpState, SignUpTrigger, SignUpIntent, SignUpAction, SignUpCommand, SignUpEvent>(
        initialState = SignUpState(),
        reducer = SignUpReducer,
    ) {
    override suspend fun executeCommand(command: SignUpCommand) {
        when (command) {
            is SignUpCommand.CallRegister -> {
                // TODO: wire to AuthRepository
                dispatchAction(SignUpAction.RegisterSucceeded("stub_token"))
            }
        }
    }
}
