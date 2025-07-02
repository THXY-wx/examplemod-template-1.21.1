package com.thxy.examplemod.block

import com.thxy.examplemod.ExampleMod
import com.thxy.examplemod.item.ModItems
import net.minecraft.util.valueproviders.UniformInt
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.DropExperienceBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.neoforged.bus.api.IEventBus
import net.neoforged.neoforge.registries.DeferredBlock
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

class ModBlocks {
    companion object {
        val BLOCKS: DeferredRegister.Blocks =
            DeferredRegister.createBlocks(ExampleMod.MOD_ID)

        val BISMUTH_BLOCK =
            registerBlock("bismuth_block") {
                Block(
                    BlockBehaviour.Properties.of()
                        .strength(4f)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.AMETHYST)
                )
            }
        val BISMUTH_ORE =
            registerBlock("bismuth_ore") {
                DropExperienceBlock(
                    UniformInt.of(2, 4),
                    BlockBehaviour.Properties.of()
                        .strength(3f)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.STONE)
                )
            }
        val BISMUTH_DEEPSLATE_ORE =
            registerBlock("bismuth_deepslate_ore") {
                DropExperienceBlock(
                    UniformInt.of(3, 6),
                    BlockBehaviour.Properties.of()
                        .strength(4f)
                        .requiresCorrectToolForDrops()
                        .sound(SoundType.DEEPSLATE)
                )
            }


        private fun <T : Block> registerBlock(
            name: String,
            block: Supplier<T>
        ): DeferredBlock<T> {
            val toReturn = BLOCKS.register(name, block)
            registerBlockItem(name, toReturn)
            return toReturn
        }

        private fun <T : Block> registerBlockItem(name: String, block: DeferredBlock<T>) {
            ModItems.ITEMS.register(name) { _ ->
                BlockItem(block.get(), Item.Properties())
            }
        }


        fun register(eventBus: IEventBus) {
            BLOCKS.register(eventBus)
        }
    }
}