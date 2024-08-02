package com.legalist.cytpto.repository

import com.legalist.cytpto.model.CyrptoModelItem
import com.legalist.cytpto.util.Resource

interface CyrptoDowland {
    suspend fun  dowland ():Resource<List<CyrptoModelItem>>
}