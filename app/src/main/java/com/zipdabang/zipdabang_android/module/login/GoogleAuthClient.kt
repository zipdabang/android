package com.zipdabang.zipdabang_android.module.login

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.service.autofill.UserData
import android.util.Log
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.zipdabang.zipdabang_android.BuildConfig
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class GoogleAuthClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {

    companion object {
        const val TAG = "GoogleAuthClient"
    }

    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        val result = try {
            // beginSignIn : returns a task
            Log.d(TAG, "Google Sign In entering")
            oneTapClient.beginSignIn(
                // BeginSignInRequest 클래스가 매개변수
                buildSignInRequest()
            ).await()


        } catch(e: Exception) {
            Log.d(TAG, "Google Sign In exception ${e.message}")
            if (e is CancellationException) throw e
            null
        }

        // intentSender
        // application will get intent 'back' with the information about user sign in

        Log.d(TAG, "Google Sign In ended ${result}")
        return result?.pendingIntent?.intentSender
    }

    // the function to deserialize the intent from signIn(), for getting real information
    suspend fun signInWithIntent(intent: Intent): SocialLoginResult? {
        // can be directly derived from intent
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        Log.d(TAG, "token ${googleIdToken}")
        // googleIdToken을 이용하여, 실제로 로그인
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {

            val user = auth
                .signInWithCredential(googleCredentials)
                .await()
                .user // user info

            SocialLoginResult(
                data = UserLoginInfo(
                    profile = user?.photoUrl.toString(),
                    email = user?.email
                )
            )


        } catch (e: Exception) {
            if (e is CancellationException) throw e
            null
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        Log.d(TAG, "buildSignInRequest started")
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true) // google authenticating by google token id is supported
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.GOOGLE_WEB_CLIENT_ID)
                    .build()
            )
            .setAutoSelectEnabled(true) // 디바이스에 구글 계정이 오직 하나라면 자동으로 그 계정 선택
            .build()
    }

    suspend fun signOut() {
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        } catch(e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
        }
    }

    fun getSignedInUser(): UserLoginInfo? = auth.currentUser?.run {
        UserLoginInfo(
            email = email,
            profile = photoUrl.toString()
        )
    }
}



/*
* the class for signing in, signing out, getting user info

* constructors
    * context
    * oneTapClient: SignInClient
        => the client that comes from firebase sdk
        => for showing the dialog to select ids
* member variables
    * auth : as a global variable of this class
        * for getting functions related to authentication(signin, signout) from Firebase
* functions
    * signIn (as a suspend function -> 로그인에는 특정 시간이 걸리며, 그 시간 동안에는 잠시 다른 작업은 멈추어야 함)
        * returns an IntentSender; An Object to send out an Intent(delegation to firebase)

* By Default, All Firebase Functions return a "task"(asynchronous)
    * about task in android...
        *
 */