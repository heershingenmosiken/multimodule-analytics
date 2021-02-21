package ua.com.dekalo.multimodule.app

import analytics.*
import android.app.Application
import ua.com.dekalo.multimodule.analytics.Analytics
import ua.com.dekalo.multimodule.featuretest1.Feature1Logger
import ua.com.dekalo.multimodule.featuretest2.Feature2Logger
import ua.com.dekalo.multimodule.featuretest3purejava.Feature3Logger

class ExampleApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupAnalytics()

        Analytics.getLogger(AppLogger::class.java)?.trackAppStart()
    }

    private fun setupAnalytics() {
        Analytics.addEngine(FirebaseAnalyticsEngine())

        ApplicationLogger().let { applicationLogger ->
            Analytics.addLogger(Feature1Logger::class.java, applicationLogger)
            Analytics.addLogger(Feature2Logger::class.java, applicationLogger)
            Analytics.addLogger(Feature3Logger::class.java, applicationLogger)
            Analytics.addLogger(AppLogger::class.java, applicationLogger)
        }


    }
}