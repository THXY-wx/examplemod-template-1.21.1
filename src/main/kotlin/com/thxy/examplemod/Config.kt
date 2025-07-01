package com.thxy.examplemod

import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.ResourceLocation
import net.neoforged.neoforge.common.ModConfigSpec


//一个示例 config 类。这不是必需的，但最好有一个来保持您的配置井井有条。
// 演示如何使用 Neo 的配置 API
object Config {
    private val BUILDER = ModConfigSpec.Builder()

    val SPEC: ModConfigSpec = BUILDER.build()

    private fun validateItemName(obj: Any?): Boolean {
        return obj is String && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(obj))
    }
}
