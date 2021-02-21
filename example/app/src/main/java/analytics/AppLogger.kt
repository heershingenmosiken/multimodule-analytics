package analytics

import ua.com.dekalo.multimodule.analytics.BILogger

interface AppLogger : BILogger {
    fun trackAppStart()
}