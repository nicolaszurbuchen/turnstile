package io.nicolaszurbuchen.turnstile.feature

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.parameters
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class MviConventionsTest {

    @Test
    fun `State must be a data class or data object`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.hasParentWithName("State") }
            .assertTrue { it.hasDataModifier }

        Konsist.scopeFromProject()
            .objects()
            .filter { it.hasParentWithName("State") }
            .assertTrue { it.hasDataModifier }
    }

    @Test
    fun `Trigger, Intent, Action, Event, Command must be sealed interface`() {
        val markers = listOf("Trigger", "Intent", "Action", "Event", "Command")
        Konsist.scopeFromProject()
            .interfaces()
            .filter { it.hasParentWithName(markers) }
            .assertTrue { it.hasSealedModifier }
    }

    @Test
    fun `MVI children with properties must be data class`() {
        val markers = listOf("Intent", "Action", "Event", "Command")
        Konsist.scopeFromProject()
            .classes()
            .filter { it.hasParentWithName(markers) }
            .assertTrue {
                if (it.hasProperties()) it.hasDataModifier else true
            }
    }

    @Test
    fun `MVI children with no properties must be data object`() {
        val markers = listOf("Intent", "Action", "Event", "Command")
        Konsist.scopeFromProject()
            .objects()
            .filter { it.hasParentWithName(markers) }
            .assertTrue { it.hasDataModifier }
    }

    @Test
    fun `MVI markers must be in a Contract file`() {
        val markers = listOf("State", "Trigger", "Intent", "Action", "Event", "Command")
        
        (Konsist.scopeFromProject().classes() + 
         Konsist.scopeFromProject().objects() + 
         Konsist.scopeFromProject().interfaces())
            .filter { decl -> decl.hasParentWithName(markers) }
            .assertTrue { it.containingFile.name.contains("Contract") }
    }

    @Test
    fun `packages with MviViewModel must have all MVI components`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.hasParentWithName("MviViewModel") }
            .assertTrue { viewModel ->
                val packageFiles = Konsist.scopeFromPackage(viewModel.packagee?.name ?: "").files
                
                packageFiles.any { it.name.endsWith("ViewModel.kt") } &&
                packageFiles.any { it.name.endsWith("Route.kt") } &&
                packageFiles.any { it.name.endsWith("Screen.kt") } &&
                packageFiles.any { it.name.endsWith("Reducer.kt") } &&
                packageFiles.any { it.name.endsWith("Contract.kt") }
            }
    }

    @Test
    fun `static views must not use MVI`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.hasParentWithName("MviViewModel") }
            .assertTrue { viewModel ->
                val packagee = viewModel.packagee?.name ?: ""
                val scope = Konsist.scopeFromPackage(packagee)
                
                val hasStateWithData = scope.classes().any { it.hasParentWithName("State") && !it.hasDataModifier } || 
                                     scope.classes().any { it.hasParentWithName("State") && it.hasProperties() }
                
                val hasCommands = scope.interfaces().any { it.hasParentWithName("Command") && it.numDeclarations() > 0 }
                val hasActions = scope.interfaces().any { it.hasParentWithName("Action") && it.numDeclarations() > 0 }
                
                // If it's a data object state with no commands or actions, it's likely static
                hasStateWithData || hasCommands || hasActions
            }
    }

    @Test
    fun `loadable initial state must have Skeleton file`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.hasParentWithName("MviViewModel") }
            .assertTrue { viewModel ->
                val isLoadable = viewModel.hasParent { it.name == "MviViewModel" && it.text.contains("Loadable") }
                
                if (isLoadable) {
                    val packageFiles = Konsist.scopeFromPackage(viewModel.packagee?.name ?: "").files
                    packageFiles.any { it.name.endsWith("Skeleton.kt") }
                } else {
                    true
                }
            }
    }

    @Test
    fun `MVI components must be in presentation package`() {
        val markers = listOf("State", "Trigger", "Intent", "Action", "Event", "Command", "MviViewModel", "Reducer")
        
        (Konsist.scopeFromProject().classes() + 
         Konsist.scopeFromProject().objects() + 
         Konsist.scopeFromProject().interfaces())
            .filter { decl -> decl.hasParentWithName(markers) }
            .assertTrue { it.resideInPackage("..presentation..") }
    }

    @Test
    fun `Screen composables must not have ViewModel as parameter`() {
        Konsist.scopeFromProject()
            .functions()
            .filter { it.hasNameEndingWith("Screen") && it.hasAnnotation { anim -> anim.name == "Composable" } }
            .parameters
            .assertTrue { !it.type.name.endsWith("ViewModel") }
    }

    @Test
    fun `ViewModel must have a corresponding Route`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.name.endsWith("ViewModel") && it.hasParentWithName("MviViewModel") }
            .assertTrue { viewModel ->
                val routeName = viewModel.name.replace("ViewModel", "Route")
                val packageFiles = Konsist.scopeFromPackage(viewModel.packagee?.name ?: "").files
                packageFiles.any { it.name.contains(routeName) }
            }
    }

    @Test
    fun `Reducers must be objects`() {
        Konsist.scopeFromProject()
            .classes()
            .assertTrue { !it.hasParentWithName("Reducer") }
    }
}
