package com.common.interactor

import androidx.lifecycle.MutableLiveData
import com.common.database.entities.UserResponse
import com.common.repository.CommonRepository
import com.common.utils.CallResult
import com.common.utils.SharedPreferenceStorage
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class CommonInteractor @Inject constructor(private val commonRepository: CommonRepository, val sharedPreferenceStorage: SharedPreferenceStorage) {

}