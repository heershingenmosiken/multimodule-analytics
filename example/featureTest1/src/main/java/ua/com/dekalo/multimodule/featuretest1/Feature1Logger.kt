package ua.com.dekalo.multimodule.featuretest1

import ua.com.dekalo.multimodule.analytics.BILogger
import ua.com.dekalo.multimodule.analytics.Analytics

interface Feature1Logger : BILogger {

    fun trackFeature1Shown()

    companion object {
        @JvmStatic
        fun require(): Feature1Logger = Analytics.getLogger(Feature1Logger::class.java)!!
    }
}


fun withTest2Logger(init: Feature1Logger.() -> Unit) {
    Analytics.getLogger(Feature1Logger::class.java)?.let { init.invoke(it) }
}