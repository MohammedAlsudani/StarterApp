package com.common.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.common.interactor.CommonInteractor
import com.common.modules.DispatcherModule
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

class CommonViewModel @Inject constructor(val commonInteractor: CommonInteractor, @DispatcherModule.IODispatcher private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onError = MutableLiveData<Exception>()
    val onError: LiveData<Exception> = _onError

    private val _onVerifyingInvitationCode = MutableLiveData<Boolean>()
    val onVerifyingInvitationCode: LiveData<Boolean> = _onVerifyingInvitationCode

}