package io.github.cottonmc.zeldrops;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.PlayerPredicate;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.world.loot.condition.LootCondition;
import net.minecraft.world.loot.context.LootContext;
import net.minecraft.world.loot.context.LootContextParameters;

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

    public static class Factory extends LootCondition.Factory<PlayerPropertiesLootContition> {
        protected Factory() {
            super(new Identifier("player_properties"), PlayerPropertiesLootContition.class);
        }

        public void toJson(JsonObject json, PlayerPropertiesLootContition condition, JsonSerializationContext context) {
            json.add("predicate", condition.predicate.toJson());
        }

        public PlayerPropertiesLootContition fromJson(JsonObject json, JsonDeserializationContext context) {
            PlayerPredicate predicate = PlayerPredicate.fromJson(json.get("predicate"));
            return new PlayerPropertiesLootContition(predicate);
        }
    }
}
