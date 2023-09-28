package com.prmto.core_presentation.dummy_data_generator

import com.prmto.core_presentation.util.PasswordTextFieldState
import com.prmto.core_presentation.util.TextFieldState

fun textFieldState() = TextFieldState(
    "test@gmail.com",
    error = null
)

fun passwordTextFieldState() = PasswordTextFieldState(
    "123456",
    error = null,
    isPasswordVisible = false
)