package ua.com.dekalo.multimodule.analytics

/**
 * Represents Analytics Engine (Firebase, GoogleAnalytics, Facebook or any other)
 */
interface BIEngine {

    /**
     * Send Event with [eventName] and [params] into the [BIEngine].
     */
    fun track(eventName: String, params: Map<String, Any> = emptyMap())
}
