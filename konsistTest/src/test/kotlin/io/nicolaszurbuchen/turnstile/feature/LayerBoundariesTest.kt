package io.nicolaszurbuchen.turnstile.feature

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.verify.assertFalse
import kotlin.test.Test

class LayerBoundariesTest {

    @Test
    fun `domain layer should not depend on presentation or data`() {
        Konsist.scopeFromProject()
            .files
            .filter { it.hasPackage("..domain..") }
            .assertFalse {
                it.imports.any { import ->
                    import.name.contains(".presentation") || import.name.contains(".data")
                }
            }
    }

    @Test
    fun `presentation layer should not depend on data`() {
        Konsist.scopeFromProject()
            .files
            .filter { it.hasPackage("..presentation..") }
            .assertFalse {
                it.imports.any { import -> import.name.contains(".data") }
            }
    }

    @Test
    fun `data layer should not depend on presentation`() {
        Konsist.scopeFromProject()
            .files
            .filter { it.hasPackage("..data..") }
            .assertFalse {
                it.imports.any { import -> import.name.contains(".presentation") }
            }
    }

    @Test
    fun `feature layers should not depend on other features internal implementation`() {
        Konsist.scopeFromProject()
            .files
            .filter { it.hasPackage("..feature..") }
            .assertFalse { file ->
                val packageName = file.packagee?.name ?: ""
                val currentFeature = packageName.substringAfter("feature.").substringBefore(".")
                
                file.imports.any { import ->
                    val importName = import.name
                    if (importName.contains("feature.")) {
                        val importedFeature = importName.substringAfter("feature.").substringBefore(".")
                        val isInternal = importName.contains(".presentation") || importName.contains(".data")
                        
                        importedFeature != currentFeature && isInternal
                    } else {
                        false
                    }
                }
            }
    }

    @Test
    fun `core should not depend on features except for root di and navigation`() {
        Konsist.scopeFromProject()
            .files
            .filter { it.hasPackage("..core..") }
            .filterNot { it.name.contains("AppModule") || it.name.contains("NavGraph") }
            .assertFalse {
                it.imports.any { import -> import.name.contains(".feature.") }
            }
    }
}
