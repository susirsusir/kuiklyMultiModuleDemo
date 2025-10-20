package com.susir.account

import com.susir.account.service.AccountServiceImpl
import com.susir.base.service.ModuleServiceManager
import com.susir.base.service.ServiceConstants
import com.tencent.kuikly.core.log.KLog


/**
 * Account模块页面注册器
 * 负责注册Account模块的所有页面和服务
 * @author suzhanfeng
 * @date 2025/9/23
 */
object AccountPageRegistry {

    private const val TAG = "AccountPageRegistry"

    /**
     * 注册所有页面和服务
     */
    fun register() {
        KLog.d(TAG, "开始注册Account模块页面和服务")

        // 注册Account服务
        registerServices()

        KLog.d(TAG, "Account模块页面和服务注册完成")
    }

    /**
     * 注册Account模块的服务
     */
    private fun registerServices() {
        KLog.d(TAG, "注册Account模块服务")
        // 检查服务是否已注册
        if (ModuleServiceManager.isServiceRegistered(ServiceConstants.ACCOUNT_SERVICE)) {
            KLog.d(TAG, "Account服务已注册，跳过重复注册")
            return
        }
        // 注册Account服务
        ModuleServiceManager.registerService(ServiceConstants.ACCOUNT_SERVICE) {
            AccountServiceImpl.INSTANCE
        }
    }
}