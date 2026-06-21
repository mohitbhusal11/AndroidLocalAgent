package com.localai.agent.featureaccessibility.service

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo
import com.localai.agent.core.bridge.AccessibilityController
import com.localai.agent.core.bridge.AccessibilityDelegate
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AgentAccessibilityService : AccessibilityService(), AccessibilityDelegate {

    @Inject lateinit var accessibilityController: AccessibilityController

    private var foregroundPackage: String? = null

    override fun onServiceConnected() {
        super.onServiceConnected()
        accessibilityController.attach(this)
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event ?: return
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            foregroundPackage = event.packageName?.toString()
            accessibilityController.updateForeground(foregroundPackage)
        }
    }

    override fun onInterrupt() {}
    override fun onDestroy() {
        accessibilityController.detach()
        super.onDestroy()
    }

    override fun getForegroundPackage(): String? = foregroundPackage

    override fun findNodesWithText(text: String): List<String> {
        val root = rootInActiveWindow ?: return emptyList()
        val results = mutableListOf<String>()
        findNodes(root, text.lowercase(), results)
        return results.distinct()
    }

    override fun clickByText(text: String): Boolean {
        val root = rootInActiveWindow ?: return false
        val node = findClickableNode(root, text.lowercase()) ?: return false
        return node.performAction(AccessibilityNodeInfo.ACTION_CLICK)
    }

    override fun scrollForward(): Boolean {
        val root = rootInActiveWindow ?: return false
        return findScrollable(root)?.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD) == true
    }

    override fun scrollBackward(): Boolean {
        val root = rootInActiveWindow ?: return false
        return findScrollable(root)?.performAction(AccessibilityNodeInfo.ACTION_SCROLL_BACKWARD) == true
    }

    override fun performBack(): Boolean = performGlobalAction(GLOBAL_ACTION_BACK)
    override fun performHome(): Boolean = performGlobalAction(GLOBAL_ACTION_HOME)

    private fun findNodes(node: AccessibilityNodeInfo, query: String, out: MutableList<String>) {
        node.text?.toString()?.let { if (it.lowercase().contains(query)) out.add(it) }
        node.contentDescription?.toString()?.let { if (it.lowercase().contains(query)) out.add(it) }
        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { findNodes(it, query, out) }
        }
    }

    private fun findClickableNode(node: AccessibilityNodeInfo, query: String): AccessibilityNodeInfo? {
        val selfText = listOfNotNull(node.text?.toString(), node.contentDescription?.toString())
            .any { it.lowercase().contains(query) }
        if (selfText && node.isClickable) return node
        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { child ->
                findClickableNode(child, query)?.let { return it }
            }
        }
        return null
    }

    private fun findScrollable(node: AccessibilityNodeInfo): AccessibilityNodeInfo? {
        if (node.isScrollable) return node
        for (i in 0 until node.childCount) {
            node.getChild(i)?.let { child ->
                findScrollable(child)?.let { return it }
            }
        }
        return null
    }
}
