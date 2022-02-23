package com.asurspace.handlerloopermainthread.contract

import androidx.fragment.app.Fragment

fun Fragment.navigate(): Navigator{
    return requireActivity() as Navigator
}

interface Navigator {

    fun showFirstHandlerVariantFragment()

    fun showSecondHandlerVariantFragment()

}