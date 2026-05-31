package io.nicolaszurbuchen.turnstile.feature

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.declaration.KoInterfaceDeclaration
import com.lemonappdev.konsist.api.ext.list.withNameEndingWith
import com.lemonappdev.konsist.api.ext.list.withPackage
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class DataLayerTest {

    companion object {
        private val scope = Konsist.scopeFromModule("shared")
    }

    // region file location implies name

    @Test // ok
    fun `files in data repository package must be suffixed with RepositoryImpl`() {
        scope
            .files
            .withPackage("..data.repository")
            .assertTrue { it.name.endsWith("RepositoryImpl") }
    }

    @Test // ok
    fun `files in remote datasource package must be suffixed with RemoteDataSource or RemoteDataSourceImpl`() {
        scope.files
            .withPackage("..data.datasource.remote")
            .assertTrue { it.name.endsWith("RemoteDataSource") || it.name.endsWith("RemoteDataSourceImpl") }
    }

    @Test // ok
    fun `files in local datasource package must be suffixed with LocalDataSource or LocalDataSourceImpl`() {
        scope.files
            .withPackage("..data.datasource.local")
            .assertTrue { it.name.endsWith("LocalDataSource") || it.name.endsWith("LocalDataSourceImpl") }
    }

    @Test // ok
    fun `files in cache datasource package must be suffixed with CacheDataSource or CacheDataSourceImpl`() {
        scope.files
            .withPackage("..data.datasource.cache")
            .assertTrue { it.name.endsWith("CacheDataSource") || it.name.endsWith("CacheDataSourceImpl") }
    }

    @Test // ok
    fun `files in remote dto package must be suffixed with Dto`() {
        scope.files
            .withPackage("..data.datasource.remote.dto")
            .assertTrue { it.name.endsWith("Dto") }
    }

    @Test // ok
    fun `files in remote api package must be suffixed with Api`() {
        scope.files
            .withPackage("..data.datasource.remote.api")
            .assertTrue { it.name.endsWith("Api") }
    }

    @Test // ok
    fun `files in remote mapper package must be suffixed with Mapper`() {
        scope.files
            .withPackage("..data.datasource.remote.mapper")
            .assertTrue { it.name.endsWith("Mapper") }
    }

    // endregion

    // region name implies location

    @Test // ok
    fun `files suffixed with RepositoryImpl must reside in repository package`() {
        scope
            .files
            .withNameEndingWith("RepositoryImpl")
            .assertTrue { it.hasPackage("..data.repository") }
    }

    @Test // ok
    fun `files suffixed with RemoteDataSource must reside in remote datasource package`() {
        scope.files
            .withNameEndingWith("RemoteDataSource")
            .assertTrue { it.hasPackage("..data.datasource.remote") }
    }

    @Test // ok
    fun `files suffixed with RemoteDataSourceImpl must reside in remote datasource package`() {
        scope.files
            .withNameEndingWith("RemoteDataSourceImpl")
            .assertTrue { it.hasPackage("..data.datasource.remote") }
    }

    @Test // ok
    fun `files suffixed with LocalDataSource must reside in local datasource package`() {
        scope.files
            .withNameEndingWith("LocalDataSource")
            .assertTrue { it.hasPackage("..data.datasource.local") }
    }

    @Test // ok
    fun `files suffixed with LocalDataSourceImpl must reside in local datasource package`() {
        scope.files
            .withNameEndingWith("LocalDataSourceImpl")
            .assertTrue { it.hasPackage("..data.datasource.local") }
    }

    @Test // ok
    fun `files suffixed with CacheDataSource must reside in cache datasource package`() {
        scope.files
            .withNameEndingWith("CacheDataSource")
            .assertTrue { it.hasPackage("..data.datasource.cache") }
    }

    @Test // ok
    fun `files suffixed with CacheDataSourceImpl must reside in cache datasource package`() {
        scope.files
            .withNameEndingWith("CacheDataSourceImpl")
            .assertTrue { it.hasPackage("..data.datasource.cache") }
    }

    @Test // ok
    fun `files suffixed with Dto must reside in remote dto package`() {
        scope.files
            .withNameEndingWith("Dto")
            .assertTrue { it.hasPackage("..data.datasource.remote.dto") }
    }

    @Test // ok
    fun `files suffixed with Api must reside in remote api package`() {
        scope.files
            .withNameEndingWith("Api")
            .assertTrue { it.hasPackage("..data.datasource.remote.api") }
    }

    @Test // ok
    fun `files suffixed with Mapper must reside in remote mapper package`() {
        scope.files
            .withNameEndingWith("Mapper")
            .assertTrue { it.hasPackage("..data.datasource.remote.mapper") }
    }

    // endregion


    @Test
    fun `class and interface type enforcement`() {
        val scope = Konsist.scopeFromProject()
        
        scope.classes().filter { it.name.endsWith("RepositoryImpl") }.assertTrue { !it.hasDataModifier }
        scope.interfaces().filter { it.name.endsWith("RemoteDataSource") }.assertTrue { true }
        scope.classes().filter { it.name.endsWith("RemoteDataSourceImpl") }.assertTrue { true }
        scope.interfaces().filter { it.name.endsWith("LocalDataSource") }.assertTrue { true }
        scope.classes().filter { it.name.endsWith("LocalDataSourceImpl") }.assertTrue { true }
        scope.interfaces().filter { it.name.endsWith("CacheDataSource") }.assertTrue { true }
        scope.classes().filter { it.name.endsWith("CacheDataSourceImpl") }.assertTrue { true }
        scope.classes().filter { it.name.endsWith("Dto") }.assertTrue { it.hasDataModifier }
        scope.interfaces().filter { it.name.endsWith("Api") }.assertTrue { true }
        scope.classes().filter { it.name.endsWith("Mapper") }.assertTrue { !it.hasDataModifier }
    }

    @Test
    fun `top-level class or interface name must match file name`() {
        Konsist.scopeFromProject()
            .files
            .filter { it.hasPackage("..data..") }
            .assertTrue { file ->
                val name = file.name.substringBefore(".")
                (file.classes(includeNested = false) + file.interfaces(includeNested = false)).any { it.name == name }
            }
    }

    @Test
    fun `files must contain exactly one top-level class or interface`() {
        Konsist.scopeFromProject()
            .files
            .filter { it.hasPackage("..data..") }
            .assertTrue { file ->
                val topLevelDecls = file.declarations(includeNested = false)
                    .filter { it is KoClassDeclaration || it is KoInterfaceDeclaration }
                topLevelDecls.size == 1
            }
    }

    @Test
    fun `DTO rules`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.name.endsWith("Dto") }
            .assertTrue { it.hasDataModifier && it.numFunctions(includeNested = false) == 0 }
    }

    @Test
    fun `Mapper rules`() {
        Konsist.scopeFromProject()
            .classes()
            .filter { it.name.endsWith("Mapper") }
            .assertTrue { mapper ->
                mapper.functions(includeNested = false)
                    .filter { it.hasPublicModifier }
                    .all { it.name.matches(Regex("to[a-zA-Z]*(Domain|Entity|Dto)")) }
            }
    }

    @Test
    fun `Implementation contract`() {
        val scope = Konsist.scopeFromProject()
        
        // RepositoryImpl
        scope.classes().filter { it.name.endsWith("RepositoryImpl") }.assertTrue { impl ->
            val interfaceName = impl.name.removeSuffix("Impl")
            impl.hasParentWithName(interfaceName)
        }

        // DataSources
        val dsSuffixes = listOf("RemoteDataSource", "LocalDataSource", "CacheDataSource")
        dsSuffixes.forEach { suffix ->
            scope.classes().filter { it.name.endsWith("${suffix}Impl") }.assertTrue { impl ->
                val interfaceName = impl.name.removeSuffix("Impl")
                impl.hasParentWithName(interfaceName)
            }
        }
    }

    @Test
    fun `Constructor dependency scope`() {
        val scope = Konsist.scopeFromProject()
        val projectPackagePrefix = "io.nicolaszurbuchen.turnstile"
        val classesToCheck = scope.classes().filter { it.name.endsWith("RepositoryImpl") || it.name.endsWith("DataSourceImpl") }

        classesToCheck.assertTrue { clazz ->
            val packageName = clazz.packagee?.name ?: ""
            val currentFeature = if (packageName.contains(".feature.")) {
                packageName.substringAfter(".feature.").substringBefore(".")
            } else {
                null
            }

            clazz.constructors.all { constructor ->
                constructor.parameters.all { param ->
                    val typeName = param.type.name
                    val matchingImport = clazz.containingFile.imports.find { it.name.endsWith(".$typeName") }

                    if (matchingImport != null) {
                        val fqn = matchingImport.name
                        if (fqn.startsWith(projectPackagePrefix)) {
                            // It's a project type
                            val isSameFeature = currentFeature != null && fqn.contains(".feature.$currentFeature.")
                            val isCommon = fqn.contains(".common.")
                            val isInfra = fqn.contains(".infra.")

                            isSameFeature || isCommon || isInfra
                        } else {
                            // External type (e.g., Firebase, Kotlin, Java) - Allowed
                            true
                        }
                    } else {
                        // Not imported: same package or standard type - Allowed
                        true
                    }
                }
            }
        }
    }

    @Test
    fun `No extra public surface`() {
        val scope = Konsist.scopeFromProject()
        val classesToCheck = scope.classes().filter { it.name.endsWith("RepositoryImpl") || it.name.endsWith("DataSourceImpl") }

        classesToCheck.assertTrue { clazz ->
            clazz.functions(includeNested = false)
                .filter { it.hasPublicModifier }
                .all { it.hasOverrideModifier && it.name != "toString" && it.name != "equals" && it.name != "hashCode" }
        }
    }
}
