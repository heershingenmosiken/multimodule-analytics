package analytics

import android.util.Log
import ua.com.dekalo.multimodule.analytics.BIEngine

class FirebaseAnalyticsEngine : BIEngine {

    override fun track(eventName: String, params: Map<String, Any>) {
        Log.d(
            "FirebaseAnalyticsEngine",
            "track(event = $eventName, params = ${
                params.entries.joinToString(
                    prefix = "(",
                    postfix = ")"
                ) { "${it.key}, ${it.value}" }
            })"
        )
    }
}