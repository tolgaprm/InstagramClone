package com.prmto.instagramclone.di

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.prmto.instagramclone.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseApp(
        @ApplicationContext context: Context
    ): FirebaseApp {
        val options = FirebaseOptions.Builder()
            .setProjectId(BuildConfig.FIREBASE_PROJECT_ID)
            .setApplicationId(BuildConfig.FIREBASE_APPLICATION_ID)
            .setApiKey(BuildConfig.FIREBASE_API_KEY)
            .build()

        return Firebase.initialize(context, options, BuildConfig.FIREBASE_PROJECT_ID)
    }

    @Provides
    @Singleton
    fun provideFirebaseFirestore(
        firebaseApp: FirebaseApp
    ) = Firebase.firestore(firebaseApp)
}