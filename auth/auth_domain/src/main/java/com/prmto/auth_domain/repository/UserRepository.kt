package com.prmto.auth_domain.repository

import com.prmto.auth_domain.register.model.UserData

interface UserRepository {

    fun saveUser(
        userData: UserData,
        userUid: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    )
}