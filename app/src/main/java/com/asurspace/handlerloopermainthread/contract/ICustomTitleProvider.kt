package com.asurspace.handlerloopermainthread.contract

import androidx.annotation.StringRes

interface ICustomTitleProvider {

    @StringRes
    fun provideTitle(): Int

}