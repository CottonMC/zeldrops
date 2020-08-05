package io.github.cottonmc.zeldrops;

import io.github.cottonmc.zeldrops.mixin.LootConditionTypesAccessor;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.UniformLootTableRange;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.Tag;
import net.minecraft.tag.TagContainers;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class Zeldrops implements ModInitializer {
	public static final String MODID = "zeldrops";

	public static final Item HEART = new HeartItem(new Item.Settings().maxCount(4));

	public static final Tag.Identified<EntityType<?>> HEART_DROPPING = (Tag.Identified<EntityType<?>>) TagRegistry.entityType(new Identifier(MODID, "heart_dropping"));

	public static final Identifier HEAL_ID = new Identifier(MODID, "heal");
	public static final SoundEvent HEAL = new SoundEvent(HEAL_ID);

	private static final List<Identifier> tableIds = new ArrayList<>();

	public static final LootConditionType PLAYER_PROPERTIES = LootConditionTypesAccessor.invokeRegister("player_properties", new PlayerPropertiesLootContition.Serializer());

	@Override
	public void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier(MODID, "heart"), HEART);
		Registry.register(Registry.SOUND_EVENT, HEAL_ID, HEAL);
		LootTableLoadingCallback.EVENT.register(((resourceManager, lootManager, id, supplier, setter) -> {
			if (tableIds.contains(id)) {
				FabricLootPoolBuilder builder = FabricLootPoolBuilder.builder()
						.withCondition(new PlayerPropertiesLootContition(
								new PlayerPredicateBuilder()
										.setOnlyWhenDamaged()
										.build()))
						.rolls(UniformLootTableRange.between(0, 2))
						.withEntry(ItemEntry.builder(HEART).build());

				supplier.withPool(builder.build());
			}
		}));
	}

	public static void prepareTableIds() {
		tableIds.clear();
		for (EntityType<?> type : TagContainers.instance().entityTypes().get(HEART_DROPPING.getId()).values()) {
			tableIds.add(type.getLootTableId());
		}
	}
}
