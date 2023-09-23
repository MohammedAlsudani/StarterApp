package com.common.repository

import com.common.database.entities.User
import com.common.database.entities.UserResponse
import com.common.utils.CallResult
import com.common.utils.DateTimeUtils
import com.common.utils.SharedPreferenceStorage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(val sharedPreferenceStorage: SharedPreferenceStorage) : CommonRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userCollection: CollectionReference = db.collection("users")

    override suspend fun getCurrentUser(): CallResult<FirebaseUser?> {
        return try {
            CallResult.Success(auth.currentUser)
        } catch (e: Exception) {
            CallResult.Error(e)
        }
    }

    override suspend fun createUpdateUser(firebaseUser: FirebaseUser): CallResult<UserResponse?> {
        return try {
            val userRef = userCollection.document(firebaseUser.uid)
            val userDoc = userRef.get().await()
            if (userDoc.exists()) {
                // User document already exists, update the fields that are not null
                val user = userDoc.toObject(User::class.java)!!
                sharedPreferenceStorage.userId = firebaseUser.uid
                CallResult.Success(UserResponse(true, user))
            } else {
                // User document does not exist, create a new one with all fields
                val user = User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: "",
                    createdAt = DateTimeUtils.getCurrentTimeStampDate(),
                    lastActive = DateTimeUtils.getCurrentTimeStampDate(),
                    tokens = 0
                )
                sharedPreferenceStorage.userId = firebaseUser.uid
                userCollection.document(firebaseUser.uid).set(user).await()
                CallResult.Success(UserResponse(false, user))
            }
        } catch (e: Exception) {
            CallResult.Error(e)
        }
    }

    override suspend fun signInAnonymously(): CallResult<FirebaseUser?> {
        return try {
            val authResult = auth.signInAnonymously().await()
            CallResult.Success(authResult.user)
        } catch (e: Exception) {
            CallResult.Error(e)
        }
    }

    override suspend fun verifyingInvitationCode(invitationCode: String): CallResult<Boolean> {
        return try {
            val userRef = userCollection.document(invitationCode)
            val userDoc = userRef.get().await()
            if (userDoc.exists()) {
                userCollection.document(invitationCode).update("numberOfReferrals", FieldValue.increment(1)).await()
                CallResult.Success(true)
            }else{
                CallResult.Success(false)
            }

        } catch (e: Exception) {
            CallResult.Error(e)
        }
    }
}