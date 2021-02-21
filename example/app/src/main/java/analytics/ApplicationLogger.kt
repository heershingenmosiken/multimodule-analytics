package analytics

import ua.com.dekalo.multimodule.analytics.EngineBasedLogger
import ua.com.dekalo.multimodule.featuretest1.Feature1Logger
import ua.com.dekalo.multimodule.featuretest2.Feature2Logger
import ua.com.dekalo.multimodule.featuretest3purejava.Feature3Logger

class ApplicationLogger : EngineBasedLogger(),
    AppLogger, Feature1Logger, Feature2Logger, Feature3Logger {

    override fun trackFeature1Shown() {
        track("OnFeature1Shown")
    }

    override fun trackFeature2Shown() {
        track("OnFeature2Shown")
    }

    override fun trackFeature3Shown() {
        track("OnFeature3Shown")
    }

    override fun trackAppStart() {
        track("OnAppStart")
    }
}