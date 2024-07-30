package com.legalist.cytpto.sevice

import com.legalist.cytpto.model.CyrptoModelItem
import retrofit2.Response
import retrofit2.http.GET

interface CyrptoApi {
   // https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json
    //baseurl -> https://raw.githubusercontent.com/
    //end pointer -> atilsamancioglu/K21-JSONDataSet/master/crypto.json
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    suspend fun getData ():Response<List<CyrptoModelItem>>


}