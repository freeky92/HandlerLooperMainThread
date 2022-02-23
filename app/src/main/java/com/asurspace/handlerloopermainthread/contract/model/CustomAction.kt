package com.asurspace.handlerloopermainthread.contract.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class CustomAction(
    @DrawableRes
    val icon: Int,
    @StringRes
    val description: Int,
    val runnable: Runnable
)