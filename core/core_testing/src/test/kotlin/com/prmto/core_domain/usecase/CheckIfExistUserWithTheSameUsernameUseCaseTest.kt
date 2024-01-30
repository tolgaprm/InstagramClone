package com.prmto.core_domain.usecase

import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.repository.user.FirebaseUserCoreRepository
import com.prmto.core_testing.fake_repository.user.FakeFirebaseUserCoreRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CheckIfExistUserWithTheSameUsernameUseCaseTest {

    private lateinit var firebaseUserCoreRepository: FirebaseUserCoreRepository
    private lateinit var checkIfExistUserWithTheSameUsernameUseCase: CheckIfExistUserWithTheSameUsernameUseCase


    @Before
    fun setUp() {
        firebaseUserCoreRepository = FakeFirebaseUserCoreRepository()
        checkIfExistUserWithTheSameUsernameUseCase =
            CheckIfExistUserWithTheSameUsernameUseCase(firebaseUserCoreRepository)
    }

    @Test
    fun `invoke() should return true if there is a user with the same username`() = runBlocking {

        val result = checkIfExistUserWithTheSameUsernameUseCase("username")

        assertThat(result).isInstanceOf(Resource.Success::class.java)
        assertThat((result as Resource.Success).data).isTrue()
    }

    @Test
    fun `invoke() should return false if there is not a user with the same username`() =
        runBlocking {
            val result = checkIfExistUserWithTheSameUsernameUseCase("username2")

            assertThat(result).isInstanceOf(Resource.Success::class.java)
            assertThat((result as Resource.Success).data).isFalse()
        }
}