package com.unitsendtest.presentation.generic

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataScope
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun CoroutineScope.cancelChildren() = coroutineContext.cancelChildren()

fun CoroutineScope.launchOnIO(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit,
) = launch(context = context + Dispatchers.IO, block = block)

fun CoroutineScope.launchOnMain(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend CoroutineScope.() -> Unit,
) = launch(context = context + Dispatchers.Main, block = block)

fun <T> liveDataOnIO(
    context: CoroutineContext = EmptyCoroutineContext,
    block: suspend LiveDataScope<T>.() -> Unit,
): LiveData<T> {
    return liveData(context = context + Dispatchers.IO, block = block)
}