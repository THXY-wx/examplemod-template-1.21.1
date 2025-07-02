package com.thxy.examplemod.item.custom

import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.Item
import net.minecraft.world.item.context.UseOnContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks

/**
 * 自定义凿子物品，用于转换特定类型的方块。
 *
 * 该物品允许玩家右键点击特定方块时，将其转换为预设的对应方块类型，
 * 同时消耗物品耐久并播放音效。
 *
 * @property properties 物品的基础属性配置
 */
class ChiselItem(properties: Properties) : Item(properties) {
    companion object {
        /**
         * 定义凿刻(Chisel)操作的方块转换映射关系。
         *
         * 该映射表用于表示在凿刻操作中，原始方块与其对应的砖块变体之间的双向转换关系。
         * 映射表包含两个方向的转换：
         * - 将原始基础方块转换为对应的砖块形态（如石头 -> 石砖）
         * - 将砖块形态还原为基础方块（如石砖 -> 石头）
         *
         * 每个映射对包含以下转换类型：
         * 1. 基础方块（如STONE, DEEPSLATE）与砖块方块（如STONE_BRICKS, DEEPSLATE_BRICKS）
         * 2. 楼梯变体（如STONE_STAIRS <-> STONE_BRICK_STAIRS）
         * 3. 台阶变体（如STONE_SLAB <-> STONE_BRICK_SLAB）
         * 4. 墙壁变体（如TUFF_WALL <-> TUFF_BRICK_WALL）
         *
         * 特殊材料转换包括：
         * - 下界岩(NETHERRACK)与地狱砖(NETHER_BRICKS)
         * - 泥块(PACKED_MUD)与泥砖(MUD_BRICKS)
         * - 凝灰岩(TUFF)及其砖块变体
         * - 海晶石(PRISMARINE)及其砖块变体
         * - 苔石(MOSSY_COBBLESTONE)与苔石砖(MOSSY_STONE_BRICKS)
         */
        val CHISEL_MAP = mapOf<Block, Block>(
            // 基础方块 -> 砖块形态的转换
            Blocks.STONE to Blocks.STONE_BRICKS,
            Blocks.STONE_STAIRS to Blocks.STONE_BRICK_STAIRS,
            Blocks.STONE_SLAB to Blocks.STONE_BRICK_SLAB,
            Blocks.END_STONE to Blocks.END_STONE_BRICKS,
            Blocks.DEEPSLATE to Blocks.DEEPSLATE_BRICKS,
            Blocks.POLISHED_BLACKSTONE to Blocks.POLISHED_BLACKSTONE_BRICKS,
            Blocks.POLISHED_BLACKSTONE_STAIRS to Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS,
            Blocks.POLISHED_BLACKSTONE_SLAB to Blocks.POLISHED_BLACKSTONE_BRICK_SLAB,
            Blocks.POLISHED_BLACKSTONE_WALL to Blocks.POLISHED_BLACKSTONE_BRICK_WALL,
            Blocks.NETHERRACK to Blocks.NETHER_BRICKS,
            Blocks.PACKED_MUD to Blocks.MUD_BRICKS,
            Blocks.TUFF to Blocks.TUFF_BRICKS,
            Blocks.TUFF_SLAB to Blocks.TUFF_BRICK_SLAB,
            Blocks.TUFF_STAIRS to Blocks.TUFF_BRICK_STAIRS,
            Blocks.TUFF_WALL to Blocks.TUFF_BRICK_WALL,
            Blocks.PRISMARINE to Blocks.PRISMARINE_BRICKS,
            Blocks.PRISMARINE_SLAB to Blocks.PRISMARINE_BRICK_SLAB,
            Blocks.PRISMARINE_STAIRS to Blocks.PRISMARINE_BRICK_STAIRS,
            Blocks.MOSSY_COBBLESTONE to Blocks.MOSSY_STONE_BRICKS,
            Blocks.MOSSY_COBBLESTONE_SLAB to Blocks.MOSSY_STONE_BRICK_SLAB,
            Blocks.MOSSY_COBBLESTONE_STAIRS to Blocks.MOSSY_STONE_BRICK_STAIRS,
            Blocks.MOSSY_COBBLESTONE_WALL to Blocks.MOSSY_STONE_BRICK_WALL,

            // 砖块形态 -> 基础方块的转换（反向映射）
            Blocks.STONE_BRICKS to Blocks.STONE,
            Blocks.STONE_BRICK_STAIRS to Blocks.STONE_STAIRS,
            Blocks.STONE_BRICK_SLAB to Blocks.STONE_SLAB,
            Blocks.END_STONE_BRICKS to Blocks.END_STONE,
            Blocks.DEEPSLATE_BRICKS to Blocks.DEEPSLATE,
            Blocks.POLISHED_BLACKSTONE_BRICKS to Blocks.POLISHED_BLACKSTONE,
            Blocks.POLISHED_BLACKSTONE_BRICK_STAIRS to Blocks.POLISHED_BLACKSTONE_STAIRS,
            Blocks.POLISHED_BLACKSTONE_BRICK_SLAB to Blocks.POLISHED_BLACKSTONE_SLAB,
            Blocks.POLISHED_BLACKSTONE_BRICK_WALL to Blocks.POLISHED_BLACKSTONE_WALL,
            Blocks.NETHER_BRICKS to Blocks.NETHERRACK,
            Blocks.MUD_BRICKS to Blocks.PACKED_MUD,
            Blocks.TUFF_BRICKS to Blocks.TUFF,
            Blocks.TUFF_BRICK_SLAB to Blocks.TUFF_SLAB,
            Blocks.TUFF_BRICK_STAIRS to Blocks.TUFF_STAIRS,
            Blocks.TUFF_BRICK_WALL to Blocks.TUFF_WALL,
            Blocks.PRISMARINE_BRICKS to Blocks.PRISMARINE,
            Blocks.PRISMARINE_BRICK_SLAB to Blocks.PRISMARINE_SLAB,
            Blocks.PRISMARINE_BRICK_STAIRS to Blocks.PRISMARINE_STAIRS,
            Blocks.MOSSY_STONE_BRICKS to Blocks.MOSSY_COBBLESTONE,
            Blocks.MOSSY_STONE_BRICK_SLAB to Blocks.MOSSY_COBBLESTONE_SLAB,
            Blocks.MOSSY_STONE_BRICK_STAIRS to Blocks.MOSSY_COBBLESTONE_STAIRS,
            Blocks.MOSSY_STONE_BRICK_WALL to Blocks.MOSSY_COBBLESTONE_WALL
        )
    }

    /**
     * 当玩家使用该物品右键点击方块时触发。
     *
     * 主要功能：
     * 1. 检查被点击方块是否在转换映射表中
     * 2. 在服务器端执行方块转换
     * 3. 消耗物品耐久并播放音效
     *
     * @param context 物品使用上下文，包含玩家、世界、点击位置等信息
     * @return 始终返回InteractionResult.SUCCESS，表示操作成功
     */
    override fun useOn(context: UseOnContext): InteractionResult {
        val level = context.level

        // 获取被点击方块的类型
        val clickedState = level.getBlockState(context.clickedPos)
        val clickedBlock = clickedState.block

        // 仅在服务端处理有效方块的转换逻辑
        if (clickedBlock in CHISEL_MAP && !level.isClientSide) {
            // 执行方块转换：根据映射表替换方块状态
            level.setBlockAndUpdate(
                context.clickedPos,
                CHISEL_MAP[clickedBlock]?.withPropertiesOf(clickedState)
                    ?: clickedState
            )

            // 消耗物品耐久并处理损坏逻辑
            context.itemInHand.hurtAndBreak(
                1,
                level as ServerLevel,
                context.player
            ) { item ->
                context.player?.onEquippedItemBroken(
                    item,
                    EquipmentSlot.MAINHAND
                )
            }

            // 在方块位置播放凿刻音效
            level.playSound(
                null,
                context.clickedPos,
                SoundEvents.GRINDSTONE_USE,
                SoundSource.BLOCKS,
            )
        }

        return InteractionResult.SUCCESS
    }
}
