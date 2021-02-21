package ua.com.dekalo.multimodule.analytics

import org.junit.Assert

open class TestEngine constructor(
    expectedEventsData: List<TestEventData> = emptyList()
) : BIEngine {

    private val expectedEventsData = expectedEventsData.toMutableList()

    fun add(testEventData: TestEventData) {
        expectedEventsData.add(testEventData)
    }

    override fun track(eventName: String, params: Map<String, Any>) {
        val expectedEvent = expectedEventsData.removeFirstOrNull()

        if (expectedEvent == null) Assert.fail("No more expected events")
        else {
            Assert.assertEquals(expectedEvent.eventName, eventName)
            Assert.assertArrayEquals(
                expectedEvent.params.keys.toTypedArray().sortedArray(),
                params.keys.toTypedArray().sortedArray()
            )

            expectedEvent.params.keys.forEach {
                Assert.assertEquals(expectedEvent.params[it], params[it])
            }
        }
    }

    fun ensureNoMoreEvents() {
        Assert.assertTrue(expectedEventsData.isEmpty())
    }
}