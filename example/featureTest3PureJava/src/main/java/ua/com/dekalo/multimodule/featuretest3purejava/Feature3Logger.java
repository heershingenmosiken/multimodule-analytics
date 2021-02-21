package ua.com.dekalo.multimodule.featuretest3purejava;

import ua.com.dekalo.multimodule.analytics.BILogger;

/**
 * Represents events that should be tracked within featureTest3 scope.
 */
public interface Feature3Logger extends BILogger {

    void trackFeature3Shown();
}
