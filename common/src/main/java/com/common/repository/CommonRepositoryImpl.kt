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
}