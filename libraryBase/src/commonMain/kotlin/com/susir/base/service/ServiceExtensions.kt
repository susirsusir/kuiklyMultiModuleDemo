package com.susir.base.service

import com.tencent.kuikly.core.log.KLog
import hb.xmhaibao.base.service.interfaces.MessageService
import hb.xmhaibao.base.service.interfaces.MineService

/**
 * 跨模块服务访问扩展函数
 * 提供便捷的服务获取方法
 * @author suzhanfeng
 * @date 2025/7/29
 */

/**
 * 获取Message模块服务
 * 返回MessageService接口类型
 */
fun getMessageService(): MessageService? {
    return ModuleServiceManager.getService<MessageService>(ServiceConstants.MESSAGE_SERVICE)
}

/**
 * 获取Message模块服务（泛型版本）
 * 使用内联函数提高性能，适用于同模块内调用
 */
inline fun <reified T : Any> getMessageServiceTyped(): T? {
    return ModuleServiceManager.getService<T>(ServiceConstants.MESSAGE_SERVICE)
}

/**
 * 获取Mine模块服务
 * 返回MineService接口类型
 */
fun getMineService(): MineService? {
    return ModuleServiceManager.getService<MineService>(ServiceConstants.MINE_SERVICE)
}

/**
 * 获取Mine模块服务（泛型版本）
 * 使用内联函数提高性能，适用于同模块内调用
 */
inline fun <reified T : Any> getMineServiceTyped(): T? {
    return ModuleServiceManager.getService<T>(ServiceConstants.MINE_SERVICE)
}

/**
 * 安全获取服务，带默认值
 * @param serviceName 服务名称
 * @param defaultValue 默认值
 * @return 服务实例或默认值
 */
inline fun <reified T : Any> getServiceSafely(serviceName: String, defaultValue: T): T {
    return ModuleServiceManager.getService<T>(serviceName) ?: defaultValue
}

/**
 * 安全获取服务，带错误处理
 * @param serviceName 服务名称
 * @param onError 错误处理回调
 * @return 服务实例或null
 */
inline fun <reified T : Any> getServiceWithErrorHandling(
    serviceName: String,
    onError: (String) -> Unit = { error -> KLog.e("ServiceExtensions", "获取服务失败: $error") }
): T? {
    return try {
        val service = ModuleServiceManager.getService<T>(serviceName)
        if (service == null) {
            onError("服务未注册或创建失败: $serviceName")
        }
        service
    } catch (e: Exception) {
        onError("获取服务异常: ${e.message}")
        null
    }
}

/**
 * 检查服务是否可用
 * @param serviceName 服务名称
 * @return 是否可用
 */
fun isServiceAvailable(serviceName: String): Boolean {
    return ModuleServiceManager.isServiceRegistered(serviceName)
}

/**
 * 批量检查服务是否可用
 * @param serviceNames 服务名称列表
 * @return 所有服务是否都可用
 */
fun areServicesAvailable(vararg serviceNames: String): Boolean {
    return serviceNames.all { isServiceAvailable(it) }
}

/**
 * 获取所有可用的服务信息
 * @return 服务信息映射
 */
fun getAvailableServicesInfo(): Map<String, Boolean> {
    val registeredServices = ModuleServiceManager.getRegisteredServices()
    val cachedServices = ModuleServiceManager.getCachedServices()
    
    return registeredServices.associateWith { serviceName ->
        cachedServices.contains(serviceName)
    }
}