package ua.com.dekalo.multimodule.analytics

import org.junit.After
import org.junit.Before
import org.junit.Test

class BiLoggerTest {

    lateinit var testLoggerImpl: TestLoggerImpl

    @Before
    fun setup() {
        testLoggerImpl = TestLoggerImpl()
    }

    @After
    fun tearDown() {
        testLoggerImpl.ensureNoMoreEvents()
    }

    fun getTestLogger(): TestLogger = testLoggerImpl

    @Test
    fun testSimpleEvent() {
        testLoggerImpl.add(TestLoggerImpl.createSimpleEventData())
        getTestLogger().simpleEvent()
    }

    @Test
    fun testComplexEvent() {
        testLoggerImpl.add(TestLoggerImpl.createComplexEventData("1", 1))
        getTestLogger().complexEvent("1", 1)
    }

    @Test
    fun testCustomSimpleEvent() {
        testLoggerImpl.add(TestEventData("customEvent1"))

        getTestLogger().track("customEvent1")
    }

    @Test
    fun testCustomComplexEvent() {
        testLoggerImpl.add(TestEventData("customEvent2", mapOf(Pair("cost", 1.0))))

        getTestLogger().track("customEvent2", mapOf(Pair("cost", 1.0)))
    }

    @Test
    fun testSimpleAndComplexEvent() {

        testLoggerImpl.add(TestLoggerImpl.createSimpleEventData())
        testLoggerImpl.add(TestLoggerImpl.createComplexEventData("1", 1))

        getTestLogger().apply {
            simpleEvent()
            complexEvent("1", 1)
        }
    }
}