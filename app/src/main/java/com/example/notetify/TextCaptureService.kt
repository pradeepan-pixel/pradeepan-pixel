package com.example.notetify

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import android.os.Handler
import android.os.Looper


class TextCaptureService : AccessibilityService() {

    override fun onServiceConnected() {
        super.onServiceConnected()
        Toast.makeText(this, "Service Connected ✅", Toast.LENGTH_LONG).show()
        Log.d("TextCaptureService", "Service connected")
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event?.eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED) {
            val selectedText = event.text?.joinToString(" ") ?: ""
            if (selectedText.isNotBlank()) {
                SharedData.selectedText = selectedText
                Toast.makeText(this, "Selected: $selectedText", Toast.LENGTH_SHORT).show()
            }
        }
    }




    override fun onInterrupt() {
        // Required override
    }
}
