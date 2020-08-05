package io.github.cottonmc.zeldrops.mixin;

import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.condition.LootConditionTypes;
import net.minecraft.util.JsonSerializer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LootConditionTypes.class)
public interface LootConditionTypesAccessor {
	@Invoker
	static LootConditionType invokeRegister(String id, JsonSerializer<? extends LootCondition> serializer) {
		throw new UnsupportedOperationException("Couldn't apply mixin!");
	}
}
