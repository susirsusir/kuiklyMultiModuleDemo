package com.susir.base.ext

import com.susir.base.service.ModuleNameProvider
import com.tencent.kuikly.core.base.attr.IImageAttr
import com.tencent.kuikly.core.views.ImageAttr
import com.tencent.kuikly.core.views.internal.GroupAttr

/**
 * 拓展获取模块图片方法
 *
 * @author suzhanfeng
 * @date 2025/8/5
 */


/**
 * ImageAttr 扩展方法：设置模块通用资源
 * @param path 资源相对路径
 * @param moduleName 模块名称，如果不传递则自动获取当前模块名称,页面名称需要严格按照模块前缀命名，比如moduleHiChat模块下page名称为hiChatXxxPage
 * @return ImageAttr 对象，支持链式调用
 */
fun ImageAttr.srcCommon(
    path: String,
    moduleName: String? = null,
    isDotNineImage: Boolean = false
): IImageAttr {
    val realModuleName = if (moduleName.isNullOrEmpty().not())
        moduleName
    else
        ModuleNameProvider.getModuleNameByPageClass(getPager()::class)
    val imageUri = "assets://${realModuleName}/common/".plus(path)
    return this.src(imageUri, isDotNineImage)
}

/**
 * ImageAttr 扩展方法：设置模块页面特定资源
 * @param path 资源相对路径
 * @param moduleName 模块名称，如果不传递则自动获取当前模块名称,页面名称需要严格按照模块前缀命名，比如moduleHiChat模块下page名称为hiChatXxxPage
 * @param pageName 页面名称，如果不传递则自动获取当前页面名称
 * @return ImageAttr 对象，支持链式调用
 */
fun ImageAttr.srcPage(
    path: String,
    moduleName: String? = null,
    pageName: String? = null,
    isDotNineImage: Boolean = false
): IImageAttr {
    val realModuleName = if (moduleName.isNullOrEmpty().not())
        moduleName
    else
        ModuleNameProvider.getModuleNameByPageClass(getPager()::class)
    val realPageName = if (pageName.isNullOrEmpty().not())
        pageName
    else
        getPager().pageName
    val imageUri = "assets://${realModuleName}/${realPageName?.capitalizeFirstLetter()}/".plus(path)
    return this.src(imageUri, isDotNineImage)
}

/**
 * GroupAttr 扩展方法：设置模块页面特定资源 对应[GroupAttr.backgroundImage]方法
 * @param path 资源相对路径
 * @param moduleName 模块名称，如果不传递则自动获取当前模块名称,页面名称需要严格按照模块前缀命名，比如moduleHiChat模块下page名称为hiChatXxxPage
 * @param pageName 页面名称，如果不传递则自动获取当前页面名称
 * @return ImageAttr 对象，支持链式调用
 */
fun GroupAttr.backgroundPageImage(path: String, moduleName: String? = null, pageName: String? = null, imageAttr: (ImageAttr.() -> Unit)? = null) {
    val realModuleName = if (moduleName.isNullOrEmpty().not())
        moduleName
    else
        ModuleNameProvider.getModuleNameByPageClass(getPager()::class)
    val realPageName = if (pageName.isNullOrEmpty().not())
        pageName
    else
        getPager().pageName
    val imageUri = "assets://${realModuleName}/${realPageName?.capitalizeFirstLetter()}/".plus(path)
    this.backgroundImage(imageUri, imageAttr)
}

/**
 * GroupAttr 扩展方法：设置模块通用资源 对应[GroupAttr.backgroundImage]方法
 * @param path 资源相对路径
 * @param moduleName 模块名称，如果不传递则自动获取当前模块名称,页面名称需要严格按照模块前缀命名，比如moduleHiChat模块下page名称为hiChatXxxPage
 * @return ImageAttr 对象，支持链式调用
 */
fun GroupAttr.backgroundCommonImage(path: String, moduleName: String? = null, imageAttr: (ImageAttr.() -> Unit)? = null) {
    val realModuleName = if (moduleName.isNullOrEmpty().not())
        moduleName
    else
        ModuleNameProvider.getModuleNameByPageClass(getPager()::class)
    val imageUri = "assets://${realModuleName}/common/".plus(path)
    this.backgroundImage(imageUri, imageAttr)
}

/**
 * 将字符串的首字母转换为大写
 * @return 首字母大写后的字符串，如果原字符串为空则返回空字符串
 */
fun String.capitalizeFirstLetter(): String {
    return if (this.isEmpty()) {
        ""
    } else {
        this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }
}


/**
 * ImageAttr 扩展方法：头像方法 -头像默认图
 * @param path 头像网络地址
 */
fun ImageAttr.srcAvatar(
    path: String
): IImageAttr {
    this.placeholderSrc("assets://libraryBase/common/base_default_user_avatar.png")
    return this.src(path)
}
