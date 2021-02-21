package ua.com.dekalo.multimodule.analytics

import org.junit.Assert

interface TestLogger : BILogger {
    fun simpleEvent()
    fun complexEvent(value1: String, value2: Int)
}

open class TestLoggerImpl(vararg expectedEvents: TestEventData) : TestLogger {

    private val expectedEvents = expectedEvents.toMutableList()

    fun add(testEvent: TestEventData) {
        expectedEvents.add(testEvent)
    }

    override fun simpleEvent() {
        track("simpleEvent")
    }

    override fun complexEvent(value1: String, value2: Int) {
        track(
            "complexEvent", mapOf(
                Pair("label1", value1),
                Pair("label2", value2)
            )
        )
    }

    override fun track(eventName: String, params: Map<String, Any>) {
        val nextExpected = expectedEvents.removeFirstOrNull()

        if (nextExpected == null) {
            Assert.fail("No nextExpected test event, but should be.")
        } else {
            Assert.assertEquals(nextExpected, TestEventData(eventName, params))
        }
    }

    fun ensureNoMoreEvents() {
        Assert.assertTrue(expectedEvents.isEmpty())
    }

    companion object {
        fun createSimpleEventData(): TestEventData = TestEventData("simpleEvent")
        fun createComplexEventData(value1: String, value2: Int): TestEventData {
            return TestEventData(
                "complexEvent", mapOf(
                    Pair("label1", value1),
                    Pair("label2", value2)
                )
            )
        }
    }
}