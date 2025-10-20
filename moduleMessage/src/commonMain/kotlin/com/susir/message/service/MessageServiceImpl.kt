package com.susir.message.service

import com.tencent.kuikly.core.log.KLog
import com.tencent.kuikly.core.nvi.serialization.json.JSONObject
import hb.xmhaibao.base.service.interfaces.MessageService

/**
 * Mine模块服务实现类
 * @author suzhanfeng
 * @date 2025/7/31
 */
    class MessageServiceImpl : MessageService {
    
    private val TAG = "MessageServiceImpl"

    override fun getUserChatInfo(userId: String): JSONObject {
        KLog.d(TAG, "获取用户聊天信息: userId=$userId")
        val userChatInfo = JSONObject()
        userChatInfo.put("userId", userId)
        userChatInfo.put("nickname", "用户_$userId")

        return userChatInfo
    }

    override fun getModuleName(): String {
        return "Message"
    }

    companion object {
        // 使用 lazy 委托实现线程安全的单例
        val INSTANCE: MessageService by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            MessageServiceImpl()
        }
    }
}