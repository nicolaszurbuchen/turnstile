package io.nicolaszurbuchen.turnstile.infra.network

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.auth
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.dsl.module

val firebaseModule =
    module {
        single<FirebaseAuth> {
            Firebase.auth.also {
                println("FirebaseAuth current user: ${it.currentUser?.uid}")
            }
        }
        single<FirebaseFirestore> { Firebase.firestore }
    }
