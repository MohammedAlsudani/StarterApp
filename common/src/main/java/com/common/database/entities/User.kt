package com.common.database.entities

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity
@Keep
data class User(
    @PrimaryKey var uid: String,
    @ColumnInfo(name = "name") var name: String,

)

data class UserResponse(var userAlreadyExist :Boolean = false, val user: User)