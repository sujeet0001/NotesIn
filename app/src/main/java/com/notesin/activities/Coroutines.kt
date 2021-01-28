package com.notesin.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.notesin.R
import kotlinx.coroutines.*
import kotlin.coroutines.cancellation.CancellationException
import kotlin.system.measureTimeMillis

class Coroutines : AppCompatActivity() {

    private val hello = "Hello"
    private val world = "World"
    private val oneSec = 1000L
    private val twoSecs = 2000L
    private val threeSecs = 3000L
    private val fourSecs = 4000L
    private val fiveSecs = 5000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coroutines)

        buildingAScope()
    }



    private fun showLog(msg: String){
        Log.d("mydata", msg)
    }

    private fun myFirstCoroutine(){
        GlobalScope.launch { // runs in background
            delay(oneSec)
            showLog(hello)
        }
        Thread.sleep(twoSecs)
        showLog(world)
    }

    private fun runBlockingEx(){
        //Similar to Thread.sleep() but we can use coroutine stuffs like delay, launch etc inside as it a part of coroutine
        runBlocking {
            delay(threeSecs)
            showLog(hello)
        }
        showLog(world)
    }

    private suspend fun waitingForJob(){

        // suspend functions allows to use coroutine stuffs like delay, coroutineScope etc

        val job = GlobalScope.launch {
            delay(oneSec)
            showLog(world)
        }
        showLog(hello)
        job.join() //make everything wait until coroutine completes
        showLog("hi")
        // o/p - Hello World hi
    }

    private fun structuredConcurrency(){
        // To avoid using GlobalScope.launch as it consumes some memory resources while it runs
        runBlocking {
            launch {
                delay(twoSecs)
                showLog(world)
            }
            showLog(hello)
        }
    }

    private fun scopeBuilder(){

        // Here showLog("4th") wont get executed unless coroutineScope gets completed
        // but, as first launch is called earlier than that, the code inside it will get executed

        // launch lets that code below that to run simultaneously

        runBlocking {
            launch {
                delay(200L)
                showLog("2nd")
            }

            coroutineScope { // Creates a coroutine scope
                launch {
                    delay(500L)
                    showLog("3rd")
                }

                delay(100L)
                showLog("1st")
            }

            showLog("4th")
        }

        // o/p - 1st 2nd 3rd 4th
    }

    private fun cancelCoroutineExecution(){

        /*runBlocking {
            val job = launch {
                showLog("1st")
                delay(twoSecs)
                showLog("2nd")
            }
            delay(oneSec)
            job.cancel()
        }*/


        val job = GlobalScope.launch {
            showLog("1st")
            delay(twoSecs)
            showLog("2nd")
        }
        Thread.sleep(oneSec)
        job.cancel()


        // o/p - 1st
    }

    private fun cancelAndJoinEx(){

        // check tryCatchFinallyEx() method to understand it clearly

        runBlocking {
            val job = launch (Dispatchers.Default) {
                repeat(5){
                    showLog(it.toString())
                    delay(oneSec)
                }
            }
            delay(threeSecs)
            job.cancelAndJoin()
            showLog("Completed")
        }
    }

    private fun tryCatchFinallyEx(){
        runBlocking {
            val job = launch {
                try {
                    repeat(10){
                        showLog(it.toString())
                        delay(oneSec)
                    }
                } catch (e: CancellationException){
                    showLog("Caught Exception: Job cancelled")
                } finally {
                    showLog("In finally")
                    withContext(NonCancellable){ // delay isnt allowed in finally but here it is non cancellable,
                        // so after a delay of 1 sec "Non cancellable" is printed (Try removing withContext(NonCancellable) and run)
                        delay(oneSec)
                        showLog("Non cancellable")
                    }
                }
            }
            delay(twoSecs)
            job.cancelAndJoin()
            showLog("Done")
        }

        /*
        o/p - For more understanding of cancel and cancelAndJoin
        for job.cancel() -> 0-1-Done-Caught Exception: Job cancelled-In finally-Non cancellable

        for job.cancelAndJoin() -> 0-1-Caught Exception: Job cancelled-In finally-Non cancellable-Done
        */

    }

    private fun timeOut(){

       runBlocking {
            try {
                withTimeout(threeSecs) { //throws TimeoutCancellationException after 3 secs
                    repeat(10) {
                        showLog(it.toString())
                        delay(oneSec)
                    }
                }
            } catch (e: TimeoutCancellationException){
                showLog("TimeoutCancellationException")
            }
        }

        // You can also use withTimeoutOrNull to not use try-catch
        runBlocking {
            val result = withTimeoutOrNull(threeSecs){
                repeat(10) {
                    showLog(it.toString())
                    delay(oneSec)
                }
                "Completed"
            }
            showLog(result.toString())  // returns null on TimeoutCancellationException or return "Completed"
        }

        // If you are using it inside launch then you wont get exception,
        // because timeout exception is considered to be a part of coroutine
        runBlocking {
            launch {
                withTimeout(threeSecs){
                    repeat(10) {
                        showLog(it.toString())
                        delay(oneSec)
                    }
                }
            }
        }
    }

    private fun runningAsyncTasks(){

        /*
        Conceptually, async is just like launch. It starts a separate coroutine which is a light-weight thread
        that works concurrently with all the other coroutines. The difference is that launch returns a Job and
        does not carry any resulting value, while async returns a Deferred — a light-weight non-blocking
        future that represents a promise to provide a result later. You can use .await() on a deferred
        value to get its eventual result, but Deferred is also a Job, so you can cancel it if needed.
        */

        runBlocking {
            val time = measureTimeMillis {
                val resOne = async {
                    delay(oneSec)
                    1
                }
                val resTwo = async {
                    delay(oneSec)
                    2
                }
                showLog((resOne.await() + resTwo.await()).toString())
            }

            showLog(time.toString())
        }

        // o/p - 3 1021 (Executing simultaneously)


        // async can be started on demand by using (start = CoroutineStart.LAZY) and starting it as job.start()
        runBlocking {
            val time = measureTimeMillis {
                val resOne = async  (start = CoroutineStart.LAZY) {
                    delay(oneSec)
                    1
                }
                val resTwo = async (start = CoroutineStart.LAZY) {
                    delay(oneSec)
                    2
                }
                resOne.start()
                resTwo.start()
                showLog((resOne.await() + resTwo.await()).toString())
            }

            showLog(time.toString())
        }

    }

    private fun buildingAScope(){
        /*val mainScope = MainScope()

        mainScope.launch {

        }*/

        val coroutineScope = CoroutineScope(Dispatchers.Main)

        coroutineScope.launch {
            delay(threeSecs)
            Toast.makeText(applicationContext, "Hoo", Toast.LENGTH_LONG).show()
        }
        Toast.makeText(applicationContext, "Done", Toast.LENGTH_LONG).show()

        /*mainScope.cancel()
        coroutineScope.cancel()*/

        // o/p - Done - Hoo

    }

    /*fun Int.addNumbers(a: Int) = this + a
    1.addNumbers(2)*/


}