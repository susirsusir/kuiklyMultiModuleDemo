package com.susir.base.service

import com.tencent.kuikly.core.log.KLog

/**
 * 跨模块服务管理器
 * 负责管理各个模块的服务实例，提供统一的服务获取接口
 * 使用服务注册机制，避免直接依赖具体实现类
 * @author suzhanfeng
 * @date 2025/7/29
 */
object ModuleServiceManager {
    
    private val TAG = "ModuleServiceManager"
    
    // 服务实例缓存
    private val serviceCache = mutableMapOf<String, Any>()
    
    // 服务提供者注册表
    private val serviceProviders = mutableMapOf<String, () -> Any>()
    
    /**
     * 注册服务提供者
     * @param serviceName 服务名称
     * @param serviceProvider 服务提供者函数
     */
    fun <T : Any> registerService(serviceName: String, serviceProvider: () -> T) {
        KLog.d(TAG, "注册服务提供者: $serviceName")
        serviceProviders[serviceName] = serviceProvider
    }
    
    /**
     * 获取服务实例
     * @param serviceName 服务名称
     * @return 服务实例
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getService(serviceName: String): T? {
        return try {
            serviceCache.getOrPut(serviceName) {
                val provider = serviceProviders[serviceName]
                    ?: throw IllegalArgumentException("服务未注册: $serviceName")
                KLog.d(TAG, "创建服务实例: $serviceName")
                provider()
            } as T
        } catch (e: Exception) {
            KLog.e(TAG, "获取服务失败: $serviceName - ${e.message}")
            null
        }
    }
    
    /**
     * 检查服务是否已注册
     */
    fun isServiceRegistered(serviceName: String): Boolean {
        return serviceProviders.containsKey(serviceName)
    }
    
    /**
     * 清除服务缓存
     */
    fun clearServiceCache() {
        KLog.d(TAG, "清除服务缓存")
        serviceCache.clear()
    }
    
    /**
     * 清除所有注册的服务
     */
    fun clearAllServices() {
        KLog.d(TAG, "清除所有服务")
        serviceCache.clear()
        serviceProviders.clear()
    }
    
    /**
     * 获取所有已注册的服务名称
     */
    fun getRegisteredServices(): Set<String> {
        return serviceProviders.keys.toSet()
    }
    
    /**
     * 获取所有已缓存的服务名称
     */
    fun getCachedServices(): Set<String> {
        return serviceCache.keys.toSet()
    }
}