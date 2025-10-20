package com.susir.mine

import com.susir.base.service.ModuleServiceManager
import com.susir.base.service.ServiceConstants
import com.susir.mine.service.MineServiceImpl
import com.tencent.kuikly.core.log.KLog

/**
 * 我的模块页面注册器
 * 负责注册我的模块的所有页面和服务
 * @author suzhanfeng
 * @date 2025/7/31
 */
object MinePageRegistry {
    
    private const val TAG = "MinePageRegistry"

    /**
     * 注册所有页面和服务
     */
    fun register() {
        KLog.d(TAG, "开始注册我的模块页面和服务")

        // 注册QuCall服务
        registerServices()

        KLog.d(TAG, "我的模块页面和服务注册完成")
    }
    
    /**
     * 注册我的模块的服务
     */
    private fun registerServices() {
        KLog.d(TAG, "注册我的模块服务")
        // 检查服务是否已注册
        if (ModuleServiceManager.isServiceRegistered(ServiceConstants.MINE_SERVICE)) {
            KLog.d(TAG, "我的服务已注册，跳过重复注册")
            return
        }
        // 注册我的服务
        ModuleServiceManager.registerService(ServiceConstants.MINE_SERVICE) {
            MineServiceImpl.INSTANCE
        }
    }
}