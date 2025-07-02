package com.thxy.examplemod.item

import com.thxy.examplemod.ExampleMod
import com.thxy.examplemod.item.custom.ChiselItem
import net.minecraft.world.item.Item
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredItem
import net.neoforged.neoforge.registries.DeferredRegister

class ModItems {
    companion object {
        val ITEMS: DeferredRegister.Items =
            DeferredRegister.createItems(ExampleMod.MOD_ID)

        val BISMUTH: DeferredItem<Item> =
            ITEMS.register("bismuth") { _ ->
                Item(Item.Properties())
            }
        val RAW_BISMUTH: DeferredItem<Item> =
            ITEMS.register("raw_bismuth") { _ ->
                Item(Item.Properties())
            }
        val CHISEL: DeferredItem<Item> =
            ITEMS.register("chisel") { _ ->
                ChiselItem(
                    Item.Properties()
                        .durability(32)
                )
            }


        fun register(eventBus: IEventBus) {
            ITEMS.register(eventBus)
        }
    }
}