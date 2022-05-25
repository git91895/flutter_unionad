package com.gstory.flutter_unionad

import android.content.Context
import android.util.Log
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.PluginRegistry

/**
 * @Description:
 * @Author: gstory0404@gmail
 * @CreateDate: 2020/8/19 18:52
 */
class FlutterUnionadEventPlugin : FlutterPlugin, EventChannel.StreamHandler {

    companion object {
        private var eventChannel: EventChannel? = null

        private var eventSink: EventChannel.EventSink? = null

        private var context: Context? = null

        fun sendContent(content: MutableMap<String, Any?>) {
            eventSink?.success(content)
        }

        fun sendError(errorCode: String, errorMessage: String, content: MutableMap<String, Any?>) {
            eventSink?.error(errorCode, errorMessage, content)
        }

        fun registerWith(registrar: PluginRegistry.Registrar) {
            val instance = FlutterUnionadEventPlugin()
            instance.onAttachedEngine(registrar.messenger(), registrar.activeContext())
        }
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        eventSink = events
    }

    override fun onCancel(arguments: Any?) {
        eventSink = null
    }

    private fun onAttachedEngine(
        messenger: io.flutter.plugin.common.BinaryMessenger,
        activeContext: Context
    ) {
        eventChannel = EventChannel(messenger, FlutterunionadViewConfig.adevent)
        eventChannel!!.setStreamHandler(this)
        context = activeContext
    }

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        onAttachedEngine(binding.binaryMessenger, binding.applicationContext)
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        eventChannel = null
        eventChannel!!.setStreamHandler(null)
    }
}
