package com.prmto.auth_domain.register.repository

import com.prmto.auth_domain.register.model.UserData

interface RegisterRepository {
    fun createUserWithEmailAndPassword(
        userData: UserData,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}