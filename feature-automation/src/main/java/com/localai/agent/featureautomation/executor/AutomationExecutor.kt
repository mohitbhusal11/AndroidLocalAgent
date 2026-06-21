package com.localai.agent.featureautomation.executor

import com.localai.agent.coreagent.agent.PlannerAgent
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AutomationExecutor @Inject constructor(
    private val plannerAgent: PlannerAgent
) {
    suspend fun execute(command: String) = plannerAgent.planAndExecute(command)
}
