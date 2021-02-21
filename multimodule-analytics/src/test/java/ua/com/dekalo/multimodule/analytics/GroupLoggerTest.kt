package ua.com.dekalo.multimodule.analytics

import org.junit.Before
import org.junit.Test

class GroupLoggerTest {

    private lateinit var groupLogger: GroupLogger<TestLogger>

    @Before
    fun setup() {
        groupLogger = GroupLogger(TestLogger::class.java)
    }

    @Test
    fun testWithSingleLogger() {
        val testLogger = TestLoggerImpl(TestLoggerImpl.createComplexEventData("value1", 1))
        groupLogger.add(testLogger)

        groupLogger.logger.complexEvent("value1", 1)

        testLogger.ensureNoMoreEvents()
    }

    @Test
    fun testWithMultipleLoggers() {
        val loggers = listOf(
            TestLoggerImpl(TestLoggerImpl.createComplexEventData("bi", 1)),
            TestLoggerImpl(TestLoggerImpl.createComplexEventData("bi", 1))
        )

        loggers.forEach { groupLogger.add(it) }

        groupLogger.logger.complexEvent("bi", 1)

        loggers.forEach { it.ensureNoMoreEvents() }
    }

    @Test
    fun testWithMultipleLoggersMultipleEvents() {
        val loggers = listOf<TestLoggerImpl>(
            TestLoggerImpl(
                TestLoggerImpl.createComplexEventData("bi", 1),
                TestLoggerImpl.createComplexEventData("bi2", 2)

            ),
            TestLoggerImpl(
                TestLoggerImpl.createComplexEventData("bi", 1),
                TestLoggerImpl.createComplexEventData("bi2", 2)
            )
        )

        loggers.forEach { groupLogger.add(it) }

        groupLogger.logger.complexEvent("bi", 1)
        groupLogger.logger.complexEvent("bi2", 2)

        loggers.forEach { it.ensureNoMoreEvents() }
    }
}