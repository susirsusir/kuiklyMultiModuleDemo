package com.susir.base.service.interfaces

import com.tencent.kuikly.core.pager.IPager

/**
 * Account模块服务接口
 * 提供给其他模块调用的服务
 * @author suzhanfeng
 * @date 2025/9/23
 */
interface AccountService {

    /**
     * 获取模块名称 这里主要用于跨模块调用验证
     * @return 模块名称
     */
    fun getModuleName(): String
}