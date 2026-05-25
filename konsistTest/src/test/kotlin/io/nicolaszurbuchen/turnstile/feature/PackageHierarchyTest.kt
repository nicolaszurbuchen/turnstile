package io.nicolaszurbuchen.turnstile.feature

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class PackageHierarchyTest {

    @Test
    fun `Direct children of feature or common must be in allowed list`() {
        // Adding to this list requires a conscious architectural decision
        val allowed = listOf("presentation", "domain", "data", "di")

        Konsist.scopeFromProject()
            .packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test
    fun `Direct children of presentation must be in allowed list`() {
        // Adding to this list requires a conscious architectural decision
        val allowed = listOf("screen", "component", "navigation", "model", "flow")

        Konsist.scopeFromProject()
            .packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.presentation\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test
    fun `Direct children of domain must be in allowed list`() {
        // Adding to this list requires a conscious architectural decision
        val allowed = listOf("model", "repository", "usecase")

        Konsist.scopeFromProject()
            .packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.domain\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test
    fun `Direct children of data must be in allowed list`() {
        // Adding to this list requires a conscious architectural decision
        val allowed = listOf("repository", "datasource")

        Konsist.scopeFromProject()
            .packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.data\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test
    fun `Direct children of data datasource must be in allowed list`() {
        // Adding to this list requires a conscious architectural decision
        val allowed = listOf("remote", "local", "cache")

        Konsist.scopeFromProject()
            .packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.data\\.datasource\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }

    @Test
    fun `Direct children of data datasource remote must be in allowed list`() {
        // Adding to this list requires a conscious architectural decision
        val allowed = listOf("api", "dto", "mapper")

        Konsist.scopeFromProject()
            .packages
            .filter { it.name.matches(Regex(".*\\.(feature|common)\\.[^.]+\\.data\\.datasource\\.remote\\.[^.]+$")) }
            .assertTrue { pkg ->
                val segments = pkg.name.split(Regex("\\.(feature|common)\\.")).last().split(".")
                allowed.contains(segments.last())
            }
    }
}
