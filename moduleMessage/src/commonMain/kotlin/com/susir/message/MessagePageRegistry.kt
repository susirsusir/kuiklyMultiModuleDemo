package com.susir.message

import com.susir.base.service.ModuleServiceManager
import com.susir.base.service.ServiceConstants
import com.tencent.kuikly.core.log.KLog
import com.susir.message.service.MessageServiceImpl

/**
 * 私信模块页面注册器
 * 负责注册私信模块的所有页面和服务
 * @author suzhanfeng
 * @date 2025/7/31
 */
object MessagePageRegistry {
    
    private const val TAG = "MessagePageRegistry"
    
    /**
     * 注册所有页面和服务
     */
    fun register() {
        KLog.d(TAG, "开始注册私信模块页面和服务")
        
        // 注册QuCall服务
        registerServices()

        KLog.d(TAG, "私信模块页面和服务注册完成")
    }
    
    /**
     * 注册我的模块的服务
     */
    private fun registerServices() {
        KLog.d(TAG, "注册私信模块服务")
        // 检查服务是否已注册
        if (ModuleServiceManager.isServiceRegistered(ServiceConstants.MESSAGE_SERVICE)) {
            KLog.d(TAG, "私信服务已注册，跳过重复注册")
            return
        }
        // 注册私信服务
        ModuleServiceManager.registerService(ServiceConstants.MESSAGE_SERVICE) {
            MessageServiceImpl.INSTANCE
        }
    }
}