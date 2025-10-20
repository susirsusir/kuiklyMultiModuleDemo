package com.susir.mine.service

import com.tencent.kuikly.core.log.KLog
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import hb.xmhaibao.base.service.interfaces.MineService

/**
 * Mine模块服务实现类
 * @author suzhanfeng
 * @date 2025/7/31
 */
    class MineServiceImpl : MineService {
    
    private val TAG = "MineServiceImpl"

    override fun getUserInfo(userId: String): JSONObject {
        KLog.d(TAG, "获取用户信息: userId=$userId")
        val userInfo = JSONObject()
        userInfo.put("userId", userId)
        userInfo.put("nickname", "用户_$userId")

        return userInfo
    }

    override fun getModuleName(): String {
        return "Mine"
    }

    companion object {
        // 使用 lazy 委托实现线程安全的单例
        val INSTANCE: MineService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            MineServiceImpl()
        }
    }
}