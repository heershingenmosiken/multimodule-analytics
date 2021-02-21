package ua.com.dekalo.multimodule.featuretest2

import ua.com.dekalo.multimodule.analytics.BILogger
import ua.com.dekalo.multimodule.analytics.Analytics

interface Feature2Logger : BILogger {

    fun trackFeature2Shown()

    companion object {
        @JvmStatic
        fun get(): Feature2Logger? = Analytics.getLogger(Feature2Logger::class.java)
    }
}


fun withTest2Logger(init: Feature2Logger.() -> Unit) {
    Analytics.getLogger(Feature2Logger::class.java)?.let { init.invoke(it) }
}