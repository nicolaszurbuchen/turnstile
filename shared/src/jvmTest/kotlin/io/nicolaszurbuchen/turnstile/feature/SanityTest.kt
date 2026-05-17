package io.nicolaszurbuchen.turnstile.feature

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.ext.list.classes
import com.lemonappdev.konsist.api.ext.list.withSourceSet
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test
import kotlin.test.assertTrue

class SanityTest {

    @Test
    fun `konsist can scan the project`() {
        val scope = Konsist.scopeFromProject()
        assertTrue(scope.files.isNotEmpty(), "Konsist found no files")
    }

    @Test
    fun `classes in commonMain should have internal visibility`() {
        Konsist.scopeFromProject()
            .files
            .withSourceSet("commonMain") // Filter to only look at commonMain
            .classes()
            .assertTrue { it.hasInternalModifier }
    }
}
