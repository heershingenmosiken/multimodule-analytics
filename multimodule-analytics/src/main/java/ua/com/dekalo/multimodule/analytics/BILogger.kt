package ua.com.dekalo.multimodule.analytics

/**
 * Base interface for all module level loggers.
 */
interface BILogger {

    /**
     * [track] event with [eventName].
     */
    fun track(eventName: String) {
        track(eventName, emptyMap())
    }

    /**
     * [track] event with [eventName] and [params].
     */
    fun track(eventName: String, params: Map<String, Any> = emptyMap())
}
