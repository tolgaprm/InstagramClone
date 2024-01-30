package com.prmto.core_domain.usecase

import com.google.common.truth.Truth.assertThat
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_testing.fake_repository.user.FakeFirebaseUserCoreRepository
import com.prmto.core_testing.userData
import com.prmto.core_testing.userDetail
import com.prmto.core_testing.util.TestConstants
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetUserBySearchingUsernameUseCaseTest {

    private lateinit var firebaseUserCoreRepository: FakeFirebaseUserCoreRepository
    private lateinit var getUserBySearchingUsernameUseCase: GetUserBySearchingUsernameUseCase

    @Before
    fun setUp() {
        firebaseUserCoreRepository = FakeFirebaseUserCoreRepository()
        firebaseUserCoreRepository.userListInFirebase = mutableListOf(
            userData(
                userUid = "test_user_uid",
                userDetail = userDetail().copy(username = "prmto")
            )
        )
        getUserBySearchingUsernameUseCase =
            GetUserBySearchingUsernameUseCase(firebaseUserCoreRepository)
    }

    @Test
    fun `getUserBySearchingUsername() should return a user if there is a user with the same username`() =
        runTest {
            val expectedUserData = userData().copy(
                userUid = "test_user_uid",
                userDetail = userDetail().copy(username = "prmto")
            )

            val result = getUserBySearchingUsernameUseCase("prmto")

            assertThat(result).isInstanceOf(Resource.Success::class.java)
            assertThat((result as Resource.Success).data).isEqualTo(expectedUserData)
        }

    @Test
    fun `getUserBySearchingUsername() should return an error if there is no user with the same username`() =
        runTest {
            val result = getUserBySearchingUsernameUseCase("no_exist_username")

            assertThat(result).isInstanceOf(Resource.Error::class.java)
            assertThat((result as Resource.Error).uiText).isEqualTo(
                UiText.DynamicString(TestConstants.USERNAME_DOES_NOT_EXIST_ERROR)
            )
        }
}