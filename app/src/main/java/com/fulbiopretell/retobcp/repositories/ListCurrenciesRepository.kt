package com.fulbiopretell.retobcp.repositories

import com.fulbiopretell.base.net.BaseRepository
import com.fulbiopretell.retobcp.constants.Constants
import com.fulbiopretell.retobcp.models.ApiResponse
import com.fulbiopretell.retobcp.models.GetTypeExchange
import com.fulbiopretell.retobcp.source.remote.WebServiceData
import com.google.gson.Gson
import retrofit2.Response
import timber.log.Timber

class ListCurrenciesRepository(private val api: WebServiceData) : BaseRepository() {

    suspend fun callGetOneTypeExchenge(typeExchange: GetTypeExchange): State {

        val headers = HashMap<String, String>()
        headers["Content-Type"] = "application/json"

        Timber.e("Get Type Exchange -> " + Gson().toJson(typeExchange))
        val typeExchangeResponse = safeApiCall(
            call = { (api.getTypeExchange(Constants.API_KEY_FAST_FOREST, typeExchange.from, typeExchange.to, headers).await() as Response<*>) },
            errorMessage = "Error Occurred during getting safe Api result"
        )
        return State.Success(typeExchangeResponse as ApiResponse?)
    }

    sealed class State {
        data class Success(val response: ApiResponse?) : State()
        data class Failure(val errorMessage: String) : State()
    }
}