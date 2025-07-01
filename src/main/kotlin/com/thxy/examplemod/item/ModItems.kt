package com.thxy.examplemod.item

import com.thxy.examplemod.ExampleMod
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



        fun register(eventBus: IEventBus) {
            ITEMS.register(eventBus)
        }
    }
}