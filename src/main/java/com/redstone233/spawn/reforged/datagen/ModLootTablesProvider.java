package com.redstone233.spawn.reforged.datagen;

import com.redstone233.spawn.reforged.block.AppleCropBlock;
import com.redstone233.spawn.reforged.block.ModBlocks;
import com.redstone233.spawn.reforged.items.ModItems;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.condition.BlockStatePropertyLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.LeafEntry;
import net.minecraft.loot.entry.LootPoolEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;

import net.minecraft.predicate.StatePredicate;

public class ModLootTablesProvider extends FabricBlockLootTableProvider {

    public ModLootTablesProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        //throw new UnsupportedOperationException("Unimplemented method 'generate'");
        addDrop(ModBlocks.FIRE_REFORGED_BLOCK);
        addDrop(ModBlocks.REFORGED_BLOCK);
        addDrop(ModBlocks.REFORGED_BLOCK1);
        addDrop(ModBlocks.REFORGED_BUTTON);
        addDrop(ModBlocks.REFORGED_FENCE);
        addDrop(ModBlocks.REFORGED_FENCE_GATE);
        addDrop(ModBlocks.REFORGED_PRESSURE_PLATE);
        addDrop(ModBlocks.REFORGED_STAIR);
        addDrop(ModBlocks.REFORGED_TRAPDOOR);
        addDrop(ModBlocks.CHANCE_BLOCK);

        addDrop(ModBlocks.REFORGED_DOOR,doorDrops(ModBlocks.REFORGED_DOOR));
        addDrop(ModBlocks.REFORGED_SLAB,slabDrops(ModBlocks.REFORGED_SLAB));
        
        addDrop(ModBlocks.REFORGED_ORE,copperOreLikeDrops(ModBlocks.REFORGED_ORE,ModItems.REFORGED_ITEM));

        BlockStatePropertyLootCondition.Builder builder = 
            BlockStatePropertyLootCondition.builder(ModBlocks.REFORGED_APPLE_CROP)
                .properties(StatePredicate.Builder.create()
                .exactMatch(AppleCropBlock.AGE, 5));

        addDrop(ModBlocks.REFORGED_APPLE_CROP,cropDrops(ModBlocks.REFORGED_APPLE_CROP,
            ModItems.REFORGED_APPLE, 
                ModItems.REFORGED_APPLE_SEEDS, builder));
    }
    
    public LootTable.Builder copperOreLikeDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop,
                (LootPoolEntry.Builder)this.applyExplosionDecay(drop,
                        ((LeafEntry.Builder) ItemEntry.builder(item)
                                .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 5.0f))))
                                .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }
}


