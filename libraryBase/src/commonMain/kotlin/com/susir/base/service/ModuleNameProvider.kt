package com.susir.base.service

import kotlin.reflect.KClass

/**
 * 模块名称提供器*
 * @author suzhanfeng
 * @date 2025/8/5
 */
object ModuleNameProvider {

    // 缓存页面类到模块名的映射（基于qualifiedName）
    private val classToModuleCache = mutableMapOf<KClass<*>, String>()
    
    // 包名前缀到模块名的映射表（性能优化）
    private val packageToModuleMap = mapOf(
        "com.susir.mine" to "moduleMine",
        "com.susir.message" to "moduleMessage",
        "com.susir.account" to "moduleAccount",
        "com.susir.base" to "libraryBase",
        "com.susir.multimodule" to "shared"
    )

    
    /**
     * 通过页面类获取模块名（高性能版本，基于包名映射）
     * @param pageClass 页面类
     * @return 模块名
     */
    fun getModuleNameByPageClass(pageClass: KClass<*>): String {
        // 先从缓存中获取
        classToModuleCache[pageClass]?.let { return it }

        // 获取完整的类名（包含包名）
        val qualifiedName = pageClass.qualifiedName
        if (qualifiedName != null) {
            // 基于包名前缀匹配模块
            val moduleName = getModuleNameByPackage(qualifiedName)
            if (moduleName.isNotEmpty()) {
                // 缓存结果
                classToModuleCache[pageClass] = moduleName
                return moduleName
            }
        }

        return "kcross"
    }
    
    /**
     * 基于包名获取模块名（私有方法，性能优化核心）
     * @param qualifiedName 完整类名（包含包名）
     * @return 模块名，如果未找到匹配则返回空字符串
     */
    private fun getModuleNameByPackage(qualifiedName: String): String {
        // 遍历包名映射表，找到最长匹配的前缀
        var bestMatch = ""
        var bestModuleName = ""
        
        for ((packagePrefix, moduleName) in packageToModuleMap) {
            if (qualifiedName.startsWith(packagePrefix) && packagePrefix.length > bestMatch.length) {
                bestMatch = packagePrefix
                bestModuleName = moduleName
            }
        }
        
        return bestModuleName
    }

}