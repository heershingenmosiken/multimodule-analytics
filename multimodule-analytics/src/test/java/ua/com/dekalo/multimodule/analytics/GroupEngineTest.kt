package ua.com.dekalo.multimodule.analytics

import org.junit.Before
import org.junit.Test

class GroupEngineTest {

    private lateinit var groupEngine: GroupEngine

    @Before
    fun setup() {
        groupEngine = GroupEngine()
    }

    @Test
    fun testSingleEventSingleEngine() {

        val testEngine = TestEngine1(
            TestEventData(
                "test1",
                mapOf(Pair("test_key_1", "test+val_1"), Pair("test_key_2", 1))
            )
        )

        groupEngine.add(testEngine)

        groupEngine.track(
            eventName = "test1", mapOf(
                Pair("test_key_1", "test+val_1"),
                Pair("test_key_2", 1)
            )
        )

        testEngine.ensureNoMoreEvents()
    }

    @Test
    fun testMultipleEventsMultipleEngines() {

        val testEvents = listOf(
            TestEventData(
                "test1",
                mapOf(Pair("test_key_1", "test+val_1"), Pair("test_key_2", 1))
            ),
            TestEventData(
                "test2",
                mapOf(Pair("test_key_3", "test+val_4"), Pair("test_key_5", 2))
            )
        )

        val testEngines = mutableListOf<TestEngine>()

        TestEngine1(testEvents[0], testEvents[1]).apply {
            testEngines.add(this)
            groupEngine.add(this)
        }

        (groupEngine as BIEngine).track(
            eventName = "test1", mapOf(
                Pair("test_key_1", "test+val_1"),
                Pair("test_key_2", 1)
            )
        )

        TestEngine2(testEvents[1]).apply {
            testEngines.add(this)
            groupEngine.add(this)
        }


        (groupEngine as BIEngine).track(
            eventName = "test2", mapOf(
                Pair("test_key_3", "test+val_4"),
                Pair("test_key_5", 2)
            )
        )

        testEngines.forEach { it.ensureNoMoreEvents() }
    }
}

class TestEngine1(
    vararg expectedEventsData: TestEventData
) : TestEngine(expectedEventsData.toList()), BIEngine

class TestEngine2(
    vararg expectedEventsData: TestEventData
) : TestEngine(expectedEventsData.toList()), BIEngine
