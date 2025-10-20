package com.susir.base.page

import com.susir.base.ext.srcPage
import com.susir.base.service.getMessageService
import com.tencent.kuikly.core.base.ViewBuilder
import com.tencent.kuikly.core.pager.Pager
import com.tencent.kuikly.core.reactive.handler.observable
import com.tencent.kuikly.core.views.Image
import com.tencent.kuikly.core.views.Text
import com.tencent.kuikly.core.views.View
import com.susir.base.service.getMineService
import com.tencent.kuikly.core.annotations.Page
import com.tencent.kuikly.core.manager.PagerManager
import com.tencent.kuikly.core.module.RouterModule

/**
 *  libraryBase demo page
 *
 * @author suzhanfeng
 * @date 2025/10/20
 */
@Page(name = "BaseDemoPage", moduleId = "libraryBase")
class BaseDemoPage : Pager() {
    private var title by observable("Base")

    override fun body(): ViewBuilder {
        val ctx = this
        return {
            View {
                attr {
                    height(56f)
                    marginTop(pagerData.statusBarHeight)
                    flexDirectionRow()
                    alignItemsCenter()
                }
                Image {
                    attr {
                        size(32f, 32f)
                        srcPage("base_btn_back_ic.png")
                    }
                    event {
                        click {
                            ctx.getModule<RouterModule>(RouterModule.MODULE_NAME)?.closePage()
                        }
                    }
                }
                Text {
                    attr {
                        flex(1f)
                        text(ctx.title)
                        fontSize(16f)
                        fontWeightBold()
                        textAlignCenter()
                    }
                }
                event {
                    click {
                        ctx.title = getMessageService()?.getModuleName() ?: "Message"
                    }
                }
            }
        }
    }
}