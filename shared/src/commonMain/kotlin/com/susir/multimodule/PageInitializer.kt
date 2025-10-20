package com.susir.multimodule

import com.susir.mine.MinePageRegistry


/**
 * 页面初始化器
 * 用于初始化所有模块的页面注册
 */
object PageInitializer {
    
    private var isInitialized = false
    
    /**
     * 初始化所有页面
     */
    fun initializePages() {
        if (isInitialized) return
        // 注册moduleMine模块的页面
        MinePageRegistry.register()
        
        isInitialized = true
    }
}