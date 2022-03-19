package com.wojciechkula.goodtime.data.datasource

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.wojciechkula.goodtime.data.entity.ProductEntity
import timber.log.Timber
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class ProductRemoteDataSource @Inject constructor() {

    private val db = Firebase.firestore

    suspend fun fetchProducts(): ArrayList<ProductEntity> = suspendCoroutine { continuation ->
        db.collection("Products").get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val hobbiesList = ArrayList<ProductEntity>()
                for (document in task.result!!) {
                    val product = document.toObject(ProductEntity::class.java)
                    hobbiesList.add(product)
                }
                continuation.resume(hobbiesList)
            } else {
                val exception = task.exception
                Timber.e(task.exception, "Error while fetching products")
                continuation.resumeWithException(exception!!)
            }
        }
    }
}