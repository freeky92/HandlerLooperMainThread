package com.asurspace.handlerloopermainthread.contract

import com.asurspace.handlerloopermainthread.contract.model.CustomAction

interface ICustomToolbarActionProvider {

    fun provideAction(): CustomAction

}