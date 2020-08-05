package io.github.cottonmc.zeldrops;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.Entity;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.predicate.PlayerPredicate;
import net.minecraft.util.JsonSerializer;

public class PlayerPropertiesLootContition implements LootCondition {
    private final PlayerPredicate predicate;

    public PlayerPropertiesLootContition(PlayerPredicate predicate) {
        this.predicate = predicate;
    }

    @Override
    public boolean test(LootContext context) {
        Entity killer = context.get(LootContextParameters.KILLER_ENTITY);
        return predicate.test(killer);
    }

    @Override
    public LootConditionType getType() {
        return Zeldrops.PLAYER_PROPERTIES;
    }

    public static class Serializer implements JsonSerializer<PlayerPropertiesLootContition> {

        @Override
        public void toJson(JsonObject json, PlayerPropertiesLootContition condition, JsonSerializationContext context) {
            json.add("predicate", condition.predicate.toJson());
        }

        @Override
        public PlayerPropertiesLootContition fromJson(JsonObject json, JsonDeserializationContext context) {
            PlayerPredicate predicate = PlayerPredicate.fromJson(json.get("predicate"));
            return new PlayerPropertiesLootContition(predicate);
        }
    }
}
