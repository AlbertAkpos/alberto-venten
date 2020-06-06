package me.alberto.albertoventen.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.coroutines.CoroutineContext


interface ManagedCoroutineScope : CoroutineScope {
    abstract fun launch(block: suspend CoroutineScope.() -> Unit): Job
}

class TestScope(override val coroutineContext: CoroutineContext) : ManagedCoroutineScope {

    @ExperimentalCoroutinesApi
    val scope = TestCoroutineScope(coroutineContext)

    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
        return scope.launch {
            block.invoke(this)
        }
    }


}