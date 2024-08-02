package com.legalist.cytpto.repository

import com.legalist.cytpto.model.CyrptoModelItem
import com.legalist.cytpto.sevice.CyrptoApi
import com.legalist.cytpto.util.Resource

class CyrptoIMPL(private val cyrptoApi: CyrptoApi) : CyrptoDowland {
    override suspend fun dowland(): Resource<List<CyrptoModelItem>> {
        return try {
            val retrofit = cyrptoApi.getData()
            if (retrofit.isSuccessful) {
                retrofit.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)

            } else {
                Resource.error("Error", null)

            }
        }catch (e:java.lang.Exception){
            Resource.error("NO DATA",null)
        }
    }

}