package ua.com.dekalo.multimodule.analytics

import android.util.Log
import java.lang.reflect.InvocationHandler
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * Composition of multiple Loggers as single
 */
internal class GroupLogger<T : BILogger>(loggerClass: Class<T>) {

    private val loggers: MutableList<T> = mutableListOf()
    private val invocationHandler: InvocationHandler = BILoggerInvocationHandler()
    val logger: T

    fun add(logger: T) {
        loggers.add(logger)
    }

    private inner class BILoggerInvocationHandler : InvocationHandler {

        override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
            try {
                for (internalLogger in loggers) {
                    if (args == null) {
                        method.invoke(internalLogger)
                    } else {
                        method.invoke(internalLogger, *args)
                    }
                }
            } catch (exception: InvocationTargetException) {
                if (exception.targetException is EngineFailureException) {
                    throw exception.targetException
                } else {
                    throw exception
                }
            }

            return Unit
        }
    }

    init {
        logger = loggerClass.cast(
            Proxy.newProxyInstance(
                loggerClass.classLoader, arrayOf(loggerClass),
                invocationHandler
            )
        )!!
    }
}
