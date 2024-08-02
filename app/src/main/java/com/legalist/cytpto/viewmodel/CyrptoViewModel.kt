package com.legalist.cytpto.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.legalist.cytpto.model.CyrptoModelItem
import com.legalist.cytpto.repository.CyrptoDowland
import com.legalist.cytpto.util.Resource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CyrptoViewModel(private val repository: CyrptoDowland) : ViewModel() {
    private var job: Job? = null
    val cyrptoList = MutableLiveData<Resource<List<CyrptoModelItem>>>()
    val cyrptoLoading = MutableLiveData<Resource<Boolean>>()
    val cyrptoError = MutableLiveData<Resource<Boolean>>()
    private lateinit var viewModel: CyrptoViewModel

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

        cyrptoError.value = Resource.error(throwable.localizedMessage ?: "Error", data = true)

    }

    fun fromGetDataApi() {
        cyrptoLoading.value = Resource.loading(data = true)
        /*
        val Baseurl = "https://raw.githubusercontent.com/"


        val retrofit = Retrofit.Builder()
            .baseUrl(Baseurl)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(CyrptoApi::class.java)*/
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val resource = repository.dowland()

            withContext(Dispatchers.Main) {
                resource.data?.let {
                    cyrptoList.value = resource
                    cyrptoLoading.value = Resource.loading(data = false)
                    cyrptoError.value = Resource.error("", data = false)

                }


            }

        }
    }

}

