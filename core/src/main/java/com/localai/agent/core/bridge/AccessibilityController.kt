package com.localai.agent.core.bridge

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

interface AccessibilityDelegate {
    fun getForegroundPackage(): String?
    fun findNodesWithText(text: String): List<String>
    fun clickByText(text: String): Boolean
    fun scrollForward(): Boolean
    fun scrollBackward(): Boolean
    fun performBack(): Boolean
    fun performHome(): Boolean
}

@Singleton
class AccessibilityController @Inject constructor() {
    private var delegate: AccessibilityDelegate? = null
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()

    private val _foregroundApp = MutableStateFlow<String?>(null)
    val foregroundApp: StateFlow<String?> = _foregroundApp.asStateFlow()

    fun attach(delegate: AccessibilityDelegate) {
        this.delegate = delegate
        _isConnected.value = true
    }

    fun detach() {
        delegate = null
        _isConnected.value = false
        _foregroundApp.value = null
    }

    fun updateForeground(pkg: String?) {
        _foregroundApp.value = pkg
    }

    fun getForegroundPackage(): String? = delegate?.getForegroundPackage()
    fun findNodesWithText(text: String): List<String> = delegate?.findNodesWithText(text).orEmpty()
    fun clickByText(text: String): Boolean = delegate?.clickByText(text) == true
    fun scrollForward(): Boolean = delegate?.scrollForward() == true
    fun scrollBackward(): Boolean = delegate?.scrollBackward() == true
    fun performBack(): Boolean = delegate?.performBack() == true
    fun performHome(): Boolean = delegate?.performHome() == true
}
