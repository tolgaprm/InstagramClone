package com.prmto.profile_domain.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ValidateWebSiteUrlUseCaseTest {

    @Test
    fun webSiteUrlValidate() {
        val validateUrl = "https://github.com/tolgaprm/InstagramClone/issues/30"
        val validateWebSiteUrlUseCase = ValidateWebSiteUrlUseCase()
        val result = validateWebSiteUrlUseCase(validateUrl)
        assertThat(result).isTrue()
    }

    @Test
    fun webSiteUrlInvalid() {
        val invalidUrl = "http://example invalid url.com."
        val validateWebSiteUrlUseCase = ValidateWebSiteUrlUseCase()
        val result = validateWebSiteUrlUseCase(invalidUrl)
        assertThat(result).isFalse()
    }
}