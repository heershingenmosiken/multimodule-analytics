package ua.com.dekalo.multimodule.analytics

open class EngineBasedTestLoggerImpl : EngineBasedLogger(), TestLogger {

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
}