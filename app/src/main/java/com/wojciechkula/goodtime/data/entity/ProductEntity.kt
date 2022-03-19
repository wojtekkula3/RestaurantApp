package com.wojciechkula.goodtime.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.DocumentId

@Entity(tableName = "Products")
data class ProductEntity(
    @DocumentId
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val name: String = "",
    val number: Int = 0,
    val type: String = "",
    val ingredients: String = "",
    val price: Double = 0.0,
    val picture: String = ""
)