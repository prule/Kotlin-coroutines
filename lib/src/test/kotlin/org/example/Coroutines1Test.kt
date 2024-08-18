/*
 * This source file was generated by the Gradle 'init' task
 */
package org.example

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertContentEquals

class Coroutines1Test {
    // tag::tag1[]
    @Test
    fun `should wait for join`() {
        // can only call launch() within a Coroutine scope, hence runBlocking
        runBlocking {
            val result = mutableListOf<String>()

            val job = launch { // launch a new coroutine and keep a reference to its Job
                delay(1000L)
                result.add("World!")
            }
            result.add("Hello")
            job.join() // wait until child coroutine completes
            result.add("Done")

            assertContentEquals(listOf("Hello", "World!", "Done"), result)
        }
    }
    // end::tag1[]

    @Test
    fun `should not wait for coroutine to complete`() {
        runBlocking {
            val result = mutableListOf<String>()

            launch { // launch a new coroutine and keep a reference to its Job
                delay(1000L)
                result.add("World!")
            }
            result.add("Hello")
            // let's not join here and see what happens
            result.add("Done")

            // order of elements in the result is different this time because we didn't join
            // with no join, it won't wait for the child coroutine to complete
            assertContentEquals(listOf("Hello", "Done"), result)
        }
    }

    @Test
    fun `should work with Threads`() {
        // write the same program using threads
        // (remove runBlocking, replace launch with thread, and replace delay with Thread.sleep)
        val result = mutableListOf<String>()

        val job = Thread { // launch a new thread and keep a reference
            Thread.sleep(1000L)
            result.add("World!")
        }
        job.start()

        result.add("Hello")
        job.join() // wait until thread completes
        result.add("Done")

        assertContentEquals(listOf("Hello", "World!", "Done"), result)
    }

}
