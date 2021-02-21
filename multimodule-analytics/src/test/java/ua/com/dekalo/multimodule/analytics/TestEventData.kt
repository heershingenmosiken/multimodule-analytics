package ua.com.dekalo.multimodule.analytics

data class TestEventData(
    val eventName: String,
    val params: Map<String, Any> = emptyMap()
)