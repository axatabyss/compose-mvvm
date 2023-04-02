package com.axat.composemvvm.repository

import com.axat.composemvvm.model.Post
import com.axat.composemvvm.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PostRepository @Inject constructor(private val apiService: ApiService) {

    fun getPost(): Flow<List<Post>> = flow {
        emit(apiService.getPosts())
    }.flowOn(Dispatchers.IO)

}