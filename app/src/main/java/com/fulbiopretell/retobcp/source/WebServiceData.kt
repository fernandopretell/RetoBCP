package com.fulbiopretell.retobcp.source.remote

import com.fulbiopretell.retobcp.constants.Urls
import com.fulbiopretell.retobcp.models.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by fernandopretell.
 */
interface WebServiceData {

    @POST(Urls.FETCH_ONE)
    fun getTypeExchange(
        @Query("api_key") query: String?,
        @Query("from") from: String?,
        @Query("to") to: String?, @HeaderMap headers: Map<String, String>): Deferred<Response<ApiResponse?>?>
}