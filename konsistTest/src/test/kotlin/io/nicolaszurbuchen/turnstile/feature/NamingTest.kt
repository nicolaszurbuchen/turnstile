package io.nicolaszurbuchen.turnstile.feature

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class NamingTest {

    @Test
    fun `classes extending MviViewModel must end with ViewModel`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.hasParentWithName("MviViewModel") }
            .assertTrue { it.name.endsWith("ViewModel") }
    }

    @Test
    fun `objects or classes implementing Reducer must end with Reducer`() {
        val scope = Konsist.scopeFromProject()
        val reducers = scope.classes() + scope.objects()

        reducers
            .filter { it.hasParentWithName("Reducer") }
            .assertTrue { it.name.endsWith("Reducer") }
    }

    @Test
    fun `classes or objects implementing State must end with State`() {
        val scope = Konsist.scopeFromProject()
        val states = scope.classes() + scope.objects()

        states
            .filter { it.hasParentWithName("State") }
            .assertTrue { it.name.endsWith("State") }
    }

    @Test
    fun `sealed interfaces implementing Intent must end with Intent`() {
        Konsist.scopeFromProject()
            .interfaces()
            .filter { it.hasParentWithName("Intent") }
            .assertTrue {
                if (it.hasSealedModifier) it.name.endsWith("Intent") else true
            }
    }

    @Test
    fun `sealed interfaces implementing Action must end with Action`() {
        Konsist.scopeFromProject()
            .interfaces()
            .filter { it.hasParentWithName("Action") }
            .assertTrue {
                if (it.hasSealedModifier) it.name.endsWith("Action") else true
            }
    }

    @Test
    fun `sealed interfaces implementing Command must end with Command`() {
        Konsist.scopeFromProject()
            .interfaces()
            .filter { it.hasParentWithName("Command") }
            .assertTrue {
                if (it.hasSealedModifier) it.name.endsWith("Command") else true
            }
    }

    @Test
    fun `sealed interfaces implementing Event must end with Event`() {
        Konsist.scopeFromProject()
            .interfaces()
            .filter { it.hasParentWithName("Event") }
            .assertTrue {
                if (it.hasSealedModifier) it.name.endsWith("Event") else true
            }
    }

    @Test
    fun `sealed interfaces implementing Trigger must end with Trigger`() {
        Konsist.scopeFromProject()
            .interfaces()
            .filter { it.hasParentWithName("Trigger") }
            .assertTrue {
                if (it.hasSealedModifier) it.name.endsWith("Trigger") else true
            }
    }

    @Test
    fun `marker interfaces must have no methods or properties`() {
        val markers = listOf("State", "Intent", "Action", "Command", "Event", "Trigger")
        Konsist.scopeFromProject()
            .interfaces()
            .filter { markers.contains(it.name) }
            .assertTrue {
                it.numDeclarations(includeNested = false) == 0
            }
    }
}
