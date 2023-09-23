package com.common.interactor

import androidx.lifecycle.MutableLiveData
import com.common.database.entities.UserResponse
import com.common.repository.CommonRepository
import com.common.utils.CallResult
import com.common.utils.SharedPreferenceStorage
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class CommonInteractor @Inject constructor(private val commonRepository: CommonRepository, val sharedPreferenceStorage: SharedPreferenceStorage) {

    suspend fun getCurrentUser(_onError: MutableLiveData<Exception>): FirebaseUser? {
        return when (val response = commonRepository.getCurrentUser()) {
            is CallResult.Success -> {
                response.data
            }
            is CallResult.Error -> {
                _onError.postValue(response.exception)
                null
            }
            else -> {
                null
            }
        }
    }

    suspend fun createUpdateUser(user: FirebaseUser, _onError: MutableLiveData<Exception>): UserResponse? {
        return when (val response = commonRepository.createUpdateUser(user)) {
            is CallResult.Success -> {
                sharedPreferenceStorage.saveUserData(response.data?.user)
                response.data
            }
            is CallResult.Error -> {
                _onError.postValue(response.exception)
                null
            }
            else -> {
                null
            }
        }
    }

    suspend fun signInAnonymously(_onError: MutableLiveData<Exception>): FirebaseUser? {
        return when (val response = commonRepository.signInAnonymously()) {
            is CallResult.Success -> {
                response.data
            }
            is CallResult.Error -> {
                _onError.postValue(response.exception)
                null
            }
            else -> {
                null
            }
        }
    }

    fun isUserInfoExist(): Boolean {
       return sharedPreferenceStorage.getUserInfo() != null
    }

    suspend fun verifyingInvitationCode(invitationCode: String, _onError: MutableLiveData<Exception>): Boolean {
        return when (val response = commonRepository.verifyingInvitationCode(invitationCode)) {
            is CallResult.Success -> {
                response.data
            }
            is CallResult.Error -> {
                _onError.postValue(response.exception)
                false
            }
        }
    }
}