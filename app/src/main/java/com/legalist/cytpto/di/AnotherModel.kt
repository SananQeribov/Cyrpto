package com.legalist.cytpto.di

import com.legalist.cytpto.view.CyrptoFragment
import org.koin.core.qualifier.named
import org.koin.dsl.module

val module = module {
    scope<CyrptoFragment> {
        scoped(qualifier = named("Hello")) {
            "Hello Friends"

        }
scoped (qualifier = named("Hi")){
    "Hi Friends"

}

    }

}