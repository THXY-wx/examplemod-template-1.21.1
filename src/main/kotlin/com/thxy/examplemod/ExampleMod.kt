package com.thxy.examplemod

import com.mojang.logging.LogUtils
import com.thxy.examplemod.block.ModBlocks
import com.thxy.examplemod.item.ModItems
import net.minecraft.world.item.CreativeModeTabs
import net.neoforged.api.distmarker.Dist
import net.neoforged.bus.api.IEventBus
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.neoforge.common.NeoForge
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import net.neoforged.neoforge.event.server.ServerStartingEvent
import org.slf4j.Logger

// 这里的值应该与 META-INF/neoforge.mods.toml 文件中的条目匹配
@Mod(ExampleMod.Companion.MOD_ID)
class ExampleMod(modEventBus: IEventBus, modContainer: ModContainer) {
    // mod 类的构造函数是加载 mod 时运行的第一个代码。
    // FML 将识别一些参数类型，如 IEventBus 或 ModContainer 并自动传入它们。
    init {
        // 注册 commonSetup 方法以进行 modloading
        modEventBus.addListener<FMLCommonSetupEvent?> { event: FMLCommonSetupEvent? -> this.commonSetup(event) }

        // 注册我们感兴趣的服务器和其他游戏活动。
        // 请注意，当且仅当我们希望 *this* 类 （ExampleMod） 直接响应事件时，这才是必需的。
        // 如果此类中没有@SubscribeEvent注释的函数，请不要添加此行，例如下面的 onServerStarting（）。
        NeoForge.EVENT_BUS.register(this)

        ModItems.register(modEventBus)
        ModBlocks.register(modEventBus)

        // 将项目注册到广告素材标签页
        modEventBus.addListener<BuildCreativeModeTabContentsEvent?> { event: BuildCreativeModeTabContentsEvent? ->
            this.addCreative(
                event!!
            )
        }

        // 注册我们 Mod 的 ModConfigSpec，以便 FML 可以为我们创建并加载配置文件
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC)
    }

    private fun commonSetup(event: FMLCommonSetupEvent?) {
    }

    // 将示例块项添加到 Building Block 选项卡
    private fun addCreative(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey == CreativeModeTabs.INGREDIENTS){
            event.accept(ModItems.BISMUTH)
            event.accept(ModItems.RAW_BISMUTH)
        }
        if (event.tabKey == CreativeModeTabs.BUILDING_BLOCKS){
            event.accept(ModBlocks.BISMUTH_BLOCK)
        }
        if (event.tabKey == CreativeModeTabs.NATURAL_BLOCKS){
            event.accept(ModBlocks.BISMUTH_ORE)
        }
    }

    // 您可以使用 SubscribeEvent 并让 Event Bus discover 方法来调用
    @SubscribeEvent
    fun onServerStarting(event: ServerStartingEvent?) {
    }

    // 您可以使用 EventBusSubscriber 自动注册带有 @SubscribeEvent 注释的类中的所有静态方法
    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = [Dist.CLIENT])
    object ClientModEvents {
        @SubscribeEvent
        fun onClientSetup(event: FMLClientSetupEvent?) {

        }
    }

    companion object {
        // 在公共位置定义 mod id，以便所有内容都引用
        const val MOD_ID: String = "examplemod"

        // 直接引用 slf4j 记录器
        private val LOGGER: Logger = LogUtils.getLogger()
    }
}