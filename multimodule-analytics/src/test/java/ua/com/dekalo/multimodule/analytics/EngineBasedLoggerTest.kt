package ua.com.dekalo.multimodule.analytics

import org.junit.Before
import org.junit.Test

class EngineBasedLoggerTest {

    lateinit var engineBasedLogger: EngineBasedLogger

    @Before
    fun setup() {
        engineBasedLogger = EngineBasedLogger()
    }

    @Test
    fun testSimpleEventHandling() {
        val testEngine = TestEngine(listOf(TestEventData("simple_event")))
        engineBasedLogger.setupEngine(testEngine)

        (engineBasedLogger as BILogger).track("simple_event")

        testEngine.ensureNoMoreEvents()
    }

    @Test
    fun testComplexEventHandling() {

        val testEngine = TestEngine(
            listOf(
                TestEventData(
                    eventName = "simple_event",
                    mapOf(
                        Pair("first_key", "label"),
                        Pair("second_key", 1)
                    )
                )
            )
        )

        engineBasedLogger.setupEngine(testEngine)


        (engineBasedLogger as BILogger).track("simple_event", mapOf(
            Pair("first_key", "label"),
            Pair("second_key", 1)
        ))

        testEngine.ensureNoMoreEvents()
    }
}



