package io.github.cottonmc.zeldrops;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.resource.ResourceType;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.loot.ConstantLootTableRange;
import net.minecraft.world.loot.UniformLootTableRange;
import net.minecraft.world.loot.condition.LootConditions;
import net.minecraft.world.loot.entry.ItemEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class Zeldrops implements ModInitializer {
	public static final String MODID = "zeldrops";

	public static final Item HEART = new HeartItem(new Item.Settings().maxCount(16));

	public static final Tag<EntityType<?>> HEART_DROPPING = TagRegistry.entityType(new Identifier(MODID, "heart_dropping"));

	public static final Identifier HEAL_ID = new Identifier(MODID, "heal");
	public static final SoundEvent HEAL = new SoundEvent(HEAL_ID);

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(MODID, "heart"), HEART);
		Registry.register(Registry.SOUND_EVENT, HEAL_ID, HEAL);
		LootConditions.register(new PlayerPropertiesLootContition.Factory());

		LootTableLoadingCallback.EVENT.register(((resourceManager, lootManager, id, supplier, setter) -> {
			//TODO: figure out some way to do this in `n` instead of `O(n)` and without stale data
			List<Identifier> tableIds = new ArrayList<>();
			for (EntityType type : HEART_DROPPING.values()) {
				tableIds.add(type.getLootTableId());
			}
			if (tableIds.contains(id)) {
				FabricLootPoolBuilder builder = FabricLootPoolBuilder.builder()
						.withCondition(new PlayerPropertiesLootContition(
								new PlayerPredicateBuilder()
										.withHealth(RangeHelper.floatAtMost(18F))
										.build()))
						.withRolls(UniformLootTableRange.between(0, 2))
						.withEntry(ItemEntry.builder(HEART));

				supplier.withPool(builder);
			}
		}));
	}
}
