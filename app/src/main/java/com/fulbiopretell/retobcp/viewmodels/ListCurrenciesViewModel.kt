package com.fulbiopretell.retobcp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.fulbiopretell.retobcp.models.ApiResponse
import com.fulbiopretell.retobcp.models.GetTypeExchange
import com.fulbiopretell.retobcp.repositories.ListCurrenciesRepository
import com.fulbiopretell.retobcp.source.remote.HelperWs
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

class ListCurrenciesViewModel : ViewModel() {

    private val repository: ListCurrenciesRepository = ListCurrenciesRepository(HelperWs.getServiceData())

    fun getTypeExchange(typeExchange: GetTypeExchange) = liveData(Dispatchers.IO) {
        emit(ViewState.Loading)
        try {
            val typeExchangeResponse = repository.callGetOneTypeExchenge(typeExchange) as ListCurrenciesRepository.State.Success
            emit(ViewState.FetchSuccess(typeExchangeResponse.response))
        } catch (e: Exception) {
            emit(ViewState.FetchFailure(e.message ?: "error"))
            Timber.e(e)
        }
    }

    sealed class ViewState() {
        object Loading : ViewState()
        data class FetchSuccess(val response: ApiResponse?) : ViewState()
        data class FetchFailure(val error: String) : ViewState()
    }
}