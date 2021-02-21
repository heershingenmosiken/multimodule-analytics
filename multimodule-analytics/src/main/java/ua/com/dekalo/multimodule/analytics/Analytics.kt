package ua.com.dekalo.multimodule.analytics

import java.util.*

/**
 * Root class of multi-module analytics.
 */
object Analytics {

    private val loggers: MutableMap<Class<out BILogger>, GroupLogger<out BILogger>> =
        HashMap<Class<out BILogger>, GroupLogger<out BILogger>>()
    private val groupEngine = GroupEngine()

    @JvmStatic
    fun <T : BILogger> getLogger(loggerClass: Class<T>): T? {
        if (loggers[loggerClass] == null) loggers[loggerClass] = GroupLogger(loggerClass)
        return loggers[loggerClass]?.let { loggerClass.cast(it.logger) }
    }

    @JvmStatic
    fun <T : BILogger> require(loggerClass: Class<T>): T {
        return getLogger(loggerClass)!!
    }

    fun addEngine(engine: BIEngine) {
        groupEngine.add(engine)
    }

    fun <B : BILogger> addLogger(keyClass: Class<B>, logger: B) {
        val groupLogger = loggers.getOrPut(keyClass) { GroupLogger(keyClass) } as GroupLogger<B>

        if (logger is EngineBasedLogger) {
            logger.setupEngine(groupEngine)
            groupLogger.add(logger)
        } else {
            throw IllegalArgumentException("$logger should be subclass of ${EngineBasedLogger::class}")
        }
    }

    internal fun tearDown() {
        loggers.clear()
        groupEngine.tearDown()
    }
}