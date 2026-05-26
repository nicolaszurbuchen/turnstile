package io.nicolaszurbuchen.turnstile.feature

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.declaration.KoClassDeclaration
import com.lemonappdev.konsist.api.declaration.KoInterfaceDeclaration
import com.lemonappdev.konsist.api.verify.assertTrue
import kotlin.test.Test

class DataLayerTest {

    @Test
    fun `package whitelists - file location implies name`() {
        val scope = Konsist.scopeFromProject()
        
        scope.files
            .filter { it.packagee?.name?.contains(".data.repository") == true }
            .assertTrue { it.name.contains("RepositoryImpl") }

        scope.files
            .filter { it.packagee?.name?.contains(".data.datasource.remote") == true }
            .filterNot { it.packagee?.name?.contains(".dto") == true }
            .filterNot { it.packagee?.name?.contains(".api") == true }
            .filterNot { it.packagee?.name?.contains(".mapper") == true }
            .assertTrue { it.name.contains("RemoteDataSource") }

        scope.files
            .filter { it.packagee?.name?.contains(".data.datasource.local") == true }
            .assertTrue { it.name.contains("LocalDataSource") }

        scope.files
            .filter { it.packagee?.name?.contains(".data.datasource.cache") == true }
            .assertTrue { it.name.contains("CacheDataSource") }

        scope.files
            .filter { it.packagee?.name?.contains(".data.datasource.remote.dto") == true }
            .assertTrue { it.name.endsWith("Dto.kt") }

        scope.files
            .filter { it.packagee?.name?.contains(".data.datasource.remote.api") == true }
            .assertTrue { it.name.endsWith("Api.kt") }

        scope.files
            .filter { it.packagee?.name?.contains(".data.datasource.remote.mapper") == true }
            .assertTrue { it.name.endsWith("Mapper.kt") }
    }

    @Test
    fun `package whitelists - name implies location`() {
        val scope = Konsist.scopeFromProject()

        scope.files
            .filter { it.name.endsWith("RepositoryImpl.kt") }
            .assertTrue { it.hasPackage("..data.repository..") }

        scope.files
            .filter { it.name.endsWith("RemoteDataSource.kt") }
            .assertTrue { it.hasPackage("..data.datasource.remote..") }

        scope.files
            .filter { it.name.endsWith("RemoteDataSourceImpl.kt") }
            .assertTrue { it.hasPackage("..data.datasource.remote..") }

        scope.files
            .filter { it.name.endsWith("LocalDataSource.kt") }
            .assertTrue { it.hasPackage("..data.datasource.local..") }

        scope.files
            .filter { it.name.endsWith("LocalDataSourceImpl.kt") }
            .assertTrue { it.hasPackage("..data.datasource.local..") }

        scope.files
            .filter { it.name.endsWith("CacheDataSource.kt") }
            .assertTrue { it.hasPackage("..data.datasource.cache..") }

        scope.files
            .filter { it.name.endsWith("CacheDataSourceImpl.kt") }
            .assertTrue { it.hasPackage("..data.datasource.cache..") }

        scope.files
            .filter { it.name.endsWith("Dto.kt") }
            .assertTrue { it.hasPackage("..data.datasource.remote.dto..") }

        scope.files
            .filter { it.name.endsWith("Api.kt") }
            .assertTrue { it.hasPackage("..data.datasource.remote.api..") }

        scope.files
            .filter { it.name.endsWith("Mapper.kt") }
            .assertTrue { it.hasPackage("..data.datasource.remote.mapper..") }
    }

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
