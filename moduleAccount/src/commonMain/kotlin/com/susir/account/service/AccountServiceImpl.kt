package com.susir.account.service

import com.susir.base.service.interfaces.AccountService
import com.tencent.kuikly.core.module.RouterModule
import com.tencent.kuikly.core.pager.IPager

/**
 * Account模块服务实现类
 * 提供给其他模块调用的服务
 * @author suzhanfeng
 * @date 2025/9/23
 */
class AccountServiceImpl : AccountService {

    private val TAG = "AccountServiceImpl"

    override fun getModuleName(): String {
        return "用户系统"
    }

    companion object {
        // 使用 lazy 委托实现线程安全的单例
        val INSTANCE: AccountService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            AccountServiceImpl()
        }
    }
}