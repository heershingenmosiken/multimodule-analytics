package ua.com.dekalo.multimodule.analytics

import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.lang.Exception
import java.lang.RuntimeException

class AnalyticsTest {

    lateinit var testEngines: MutableList<TestEngine>

    @Before
    fun setup() {
        Analytics.tearDown()
        testEngines = mutableListOf()
    }

    @After
    fun tearDown() {
        Analytics.tearDown()
        testEngines.forEach {
            it.ensureNoMoreEvents()
        }
    }

    @Test
    fun testSingleLoggerSingleEngine() {
        Analytics.addLogger(TestLogger::class.java, EngineBasedTestLoggerImpl())

        TestEngine().apply {
            Analytics.addEngine(this)
            testEngines.add(this)
            add(TestLoggerImpl.createSimpleEventData())
            add(TestLoggerImpl.createComplexEventData("event1", 1))
        }


        Analytics.getLogger(TestLogger::class.java)?.simpleEvent()
        Analytics.getLogger(TestLogger::class.java)?.complexEvent("event1", 1)
    }

    @Test
    fun testMultipleLoggersMultipleEngines() {

        listOf(TestEngine(), TestEngine()).forEach {
            Analytics.addEngine(it)
            testEngines.add(it)
            it.add(TestEventData("test1Method"))
            it.add(TestEventData("test2Method"))
        }

        Analytics.addLogger(TestLogger1::class.java, EngineBasedTestLogger1())
        Analytics.addLogger(TestLogger2::class.java, EngineBasedTestLogger2())

        Analytics.require(TestLogger1::class.java).test1Method()
        Analytics.require(TestLogger2::class.java).test2Method()
    }

    @Test
    fun testCouldBeAddedOnlyEngineBasedLogger() {

        try {
            Analytics.addLogger(TestLogger::class.java, TestLoggerImpl())
            Assert.fail("IllegalArgumentException expected")
        } catch (exception: IllegalArgumentException) {
            // expected
        }
    }

    @Test
    fun testCorrectRethrowEngineException() {
        Analytics.addLogger(TestLogger::class.java, EngineBasedTestLoggerImpl())
        Analytics.addEngine(FailingEngine())

        try {
            Analytics.require(TestLogger::class.java).simpleEvent()
            Assert.fail("FailingEngineException expected")
        } catch (exception: EngineFailureException) {
            // expected
            Assert.assertTrue(exception.cause is FailingEngine.FailingEngineException)
        }
    }

    class FailingEngine : BIEngine {

        override fun track(eventName: String, params: Map<String, Any>) {
            throw FailingEngineException()
        }

        class FailingEngineException : RuntimeException()
    }

    interface TestLogger1 : TestLogger {
        fun test1Method()
    }

    interface TestLogger2 : TestLogger {
        fun test2Method()
    }

    class EngineBasedTestLogger1 : EngineBasedTestLoggerImpl(), TestLogger1 {
        override fun test1Method() {
            track("test1Method")
        }
    }

    class EngineBasedTestLogger2 : EngineBasedTestLoggerImpl(), TestLogger2 {
        override fun test2Method() {
            track("test2Method")
        }
    }
}