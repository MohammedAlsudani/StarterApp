package com.common.repository

import com.common.database.entities.UserResponse
import com.common.utils.CallResult
import com.google.firebase.auth.FirebaseUser


/**
 * CommonRepository is the repository interface for the Main Interactor
 * Main Interactor interacts with Main Repository for db queries or getting data from the api .
 */
interface CommonRepository {

    suspend fun getCurrentUser(): CallResult<FirebaseUser?>

    suspend fun createUpdateUser(user: FirebaseUser): CallResult<UserResponse?>

    suspend fun signInAnonymously(): CallResult<FirebaseUser?>

    suspend fun verifyingInvitationCode(invitationCode: String): CallResult<Boolean>
}