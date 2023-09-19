package com.prmto.core_data.local.datasource.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.prmto.core_data.common.Constants.BIO_PREFERENCES_KEY
import com.prmto.core_data.common.Constants.NAME_PREFERENCES_KEY
import com.prmto.core_data.common.Constants.PROFILE_PICTURE_URL_PREFERENCES_KEY
import com.prmto.core_data.common.Constants.USERNAME_PREFERENCES_KEY
import com.prmto.core_data.common.Constants.WEB_SITE_PREFERENCES_KEY
import com.prmto.core_data.common.safeCallWithTryCatch
import com.prmto.core_domain.constants.Resource
import com.prmto.core_domain.constants.SimpleResource
import com.prmto.core_domain.constants.UiText
import com.prmto.core_domain.model.UserDetail
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoreUserPreferencesLocalDataSourceImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : CoreUserPreferencesLocalDataSource {
    private object PreferencesKey {
        val USER_NAME = stringPreferencesKey(USERNAME_PREFERENCES_KEY)
        val NAME = stringPreferencesKey(NAME_PREFERENCES_KEY)
        val BIO = stringPreferencesKey(BIO_PREFERENCES_KEY)
        val WEB_SITE = stringPreferencesKey(WEB_SITE_PREFERENCES_KEY)
        val PROFILE_PICTURE_URL =
            stringPreferencesKey(PROFILE_PICTURE_URL_PREFERENCES_KEY)
    }

    override suspend fun saveUserDetail(userDetail: UserDetail): SimpleResource {
        return safeCallWithTryCatch {
            dataStore.edit {
                it[PreferencesKey.USER_NAME] = userDetail.username
                it[PreferencesKey.NAME] = userDetail.name
                it[PreferencesKey.BIO] = userDetail.bio
                it[PreferencesKey.WEB_SITE] = userDetail.webSite
                it[PreferencesKey.PROFILE_PICTURE_URL] = userDetail.profilePictureUrl
            }
            Resource.Success(Unit)
        }
    }

    override suspend fun getUserDetail(): Resource<UserDetail> {
        return dataStore.data.map {
            val username = it[PreferencesKey.USER_NAME]
            val name = it[PreferencesKey.NAME]
            val bio = it[PreferencesKey.BIO]
            val webSite = it[PreferencesKey.WEB_SITE]
            val profilePictureUrl = it[PreferencesKey.PROFILE_PICTURE_URL]
            Resource.Success(
                UserDetail(
                    username = username ?: "",
                    name = name ?: "",
                    bio = bio ?: "",
                    webSite = webSite ?: "",
                    profilePictureUrl = profilePictureUrl ?: ""
                )
            )
        }.catch {
            Resource.Error<UserDetail>(UiText.unknownError())
        }.first()
    }

    override suspend fun getProfilePictureUrl(): String? {
        return dataStore.data.map {
            it[PreferencesKey.PROFILE_PICTURE_URL]
        }.first()
    }

    override suspend fun deleteUserDetail(): SimpleResource {
        return safeCallWithTryCatch {
            dataStore.edit {
                it.remove(PreferencesKey.USER_NAME)
                it.remove(PreferencesKey.NAME)
                it.remove(PreferencesKey.BIO)
                it.remove(PreferencesKey.WEB_SITE)
                it.remove(PreferencesKey.PROFILE_PICTURE_URL)
            }
            Resource.Success(Unit)
        }
    }
}