package hb.xmhaibao.base.service.interfaces

import com.tencent.kuikly.core.nvi.serialization.json.JSONObject

/**
 * 消息模块服务接口
 * 提供给其他模块调用的服务
 * @author suzhanfeng
 * @date 2025/7/31
 */
interface MessageService {
    /**
     * 获取用户聊天信息
     * @param userId 用户ID
     * @return 用户聊天信息
     */
    fun getUserChatInfo(userId: String): JSONObject


    /**
     * 获取模块名称 这里主要用于跨模块调用验证
     * @return 模块名称
     */
    fun getModuleName():String
}