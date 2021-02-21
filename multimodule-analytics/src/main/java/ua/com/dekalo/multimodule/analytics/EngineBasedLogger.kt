package ua.com.dekalo.multimodule.analytics

/**
 * [BILogger] implementation that sends all events into supplied [BIEngine].
 */
open class EngineBasedLogger : BILogger {

    private lateinit var engine: BIEngine

    internal fun setupEngine(engine: BIEngine) {
        this.engine = engine
    }

    override fun track(eventName: String) {
        engine.track(eventName)
    }

    override fun track(eventName: String, params: Map<String, Any>) {
        engine.track(eventName, params)
    }
}