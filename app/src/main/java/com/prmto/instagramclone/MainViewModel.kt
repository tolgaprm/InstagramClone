package com.prmto.instagramclone

import androidx.lifecycle.ViewModel
import com.prmto.core_domain.repository.auth.FirebaseAuthCoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val firebaseAuthCoreRepository: FirebaseAuthCoreRepository
) : ViewModel() {
    fun isUserLoggedIn(): Boolean {
        return firebaseAuthCoreRepository.currentUser() != null
    }
}