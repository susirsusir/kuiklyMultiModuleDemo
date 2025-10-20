package hb.xmhaibao.base.service.interfaces

import com.tencent.kuikly.core.nvi.serialization.json.JSONObject

/**
 * 我的模块服务接口
 *
 * @author suzhanfeng
 * @date 2025/7/31
 */
interface MineService {

    /**
     * 获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    fun getUserInfo(userId: String): JSONObject


    /**
     * 获取模块名称 这里主要用于跨模块调用验证
     * @return 模块名称
     */
    fun getModuleName():String
}