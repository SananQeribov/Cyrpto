package com.legalist.cytpto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.legalist.cytpto.model.CyrptoModelItem
import com.legalist.cytpto.sevice.CyrptoApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CyrptoViewModel : ViewModel() {
    private var job: Job? = null
    val cyrptoList = MutableLiveData<List<CyrptoModelItem>?>()
    val cyrptoLoading = MutableLiveData<Boolean>()
    val cyrptoError = MutableLiveData<Boolean>()
    private lateinit var viewModel: CyrptoViewModel

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Error:{${throwable.localizedMessage}}")
        cyrptoError.value = true

    }

    fun fromGetDataApi() {
        cyrptoLoading.value = true
        val Baseurl = "https://raw.githubusercontent.com/"


        val retrofit = Retrofit.Builder()
            .baseUrl(Baseurl)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CyrptoApi::class.java)
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {

            val response = retrofit.getData()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cyrptoLoading.value = false
                    cyrptoError.value = false
                    response.body().let { it ->
                        cyrptoList.value = it

                    }


                }

            }
        }

    }
}
