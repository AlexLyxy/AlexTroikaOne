package com.alexlyxy.alextroikaone.api

import com.alexlyxy.alextroikaone.pojo.CoinInfoListOfData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top/totalvolfull")
    //@GET("current.json")

    fun getDogBreedList(
        @Query(QUERY_PARAM_API_KEY) apiKey: String =
            "3b3ba35438659a4323ad8042ca70492a2a9cbc6390e7a07d908aec1989d96b81",
        //@Query(QUERY_PARAM_API_KEY) apiKey: String =
        //    "8ea82f6e78674e4996e181556230908",
        @Query(QUERY_PARAM_LIMIT) limit: Int = 1,
        @Query(QUERY_PARAM_TO_SYMBOL) tSym: String = CURRENCY

    ): Single<CoinInfoListOfData>

    companion object {
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_TO_SYMBOL = "tsym"
        private const val CURRENCY ="USD"


    }
}