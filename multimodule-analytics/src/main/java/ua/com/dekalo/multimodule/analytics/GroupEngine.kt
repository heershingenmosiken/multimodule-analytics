package ua.com.dekalo.multimodule.analytics

import java.util.*

/**
 * Composition of 0..N [BIEngine] as single instance.
 */
class GroupEngine : BIEngine {

    private val engines: MutableList<BIEngine> = ArrayList()

    fun add(engine: BIEngine) {
        engines.add(engine)
    }

    override fun track(eventName: String, params: Map<String, Any>) {
        for (engine in engines) {
            try {
                engine.track(eventName, params)
            } catch (throwable: Throwable) {
                throw EngineFailureException(throwable)
            }
        }
    }

    internal fun tearDown() {
        engines.clear()
    }
}