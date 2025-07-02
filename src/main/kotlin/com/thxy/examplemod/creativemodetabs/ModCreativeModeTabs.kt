package com.thxy.examplemod.creativemodetabs

import com.thxy.examplemod.ExampleMod
import com.thxy.examplemod.block.ModBlocks
import com.thxy.examplemod.item.ModItems
import net.minecraft.core.registries.Registries
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

class ModCreativeModeTabs {
    companion object {
        val CREATIVE_MODE_TAB: DeferredRegister<CreativeModeTab> =
            DeferredRegister.create(
                Registries.CREATIVE_MODE_TAB,
                ExampleMod.MOD_ID
            )

        val EXAMPLE_ITEMS_TAB: Supplier<CreativeModeTab> =
            CREATIVE_MODE_TAB.register("example_items_tab") { _ ->
                CreativeModeTab.builder()
                    .icon {
                        ItemStack(ModItems.BISMUTH.get())
                    }
                    .title(
                        Component.translatable(
                            "creativetab.examplemod.example_items"
                        )
                    )
                    .displayItems { itemDisplayParams, output ->
                        output.accept(ModItems.BISMUTH)
                        output.accept(ModItems.RAW_BISMUTH)
                        output.accept(ModItems.CHISEL)
                    }
                    .build()
            }

        val EXAMPLE_BLOCKS_TAB: Supplier<CreativeModeTab> =
            CREATIVE_MODE_TAB.register("example_blocks_tab") { _ ->
                CreativeModeTab.builder()
                    .icon {
                        ItemStack(ModBlocks.BISMUTH_BLOCK.get())
                    }
                    .withTabsBefore(
                        ResourceLocation.fromNamespaceAndPath(
                            ExampleMod.MOD_ID,
                            "example_items_tab"
                        )
                    )
                    .title(
                        Component.translatable(
                            "creativetab.examplemod.example_blocks"
                        )
                    )
                    .displayItems { itemDisplayParams, output ->
                        output.accept(ModBlocks.BISMUTH_BLOCK)
                        output.accept(ModBlocks.BISMUTH_ORE)
                        output.accept(ModBlocks.BISMUTH_DEEPSLATE_ORE)
                    }
                    .build()
            }


        fun register(eventBus: IEventBus) {
            CREATIVE_MODE_TAB.register(eventBus)
        }
    }
}