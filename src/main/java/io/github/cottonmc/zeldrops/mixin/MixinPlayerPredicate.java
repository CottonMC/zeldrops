package io.github.cottonmc.zeldrops.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import io.github.cottonmc.zeldrops.impl.PlayerPredicateAccessor;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.PlayerPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;
import java.util.Map;

@Mixin(PlayerPredicate.class)
public class MixinPlayerPredicate implements PlayerPredicateAccessor {

    @Shadow @Final @Mutable private NumberRange.IntRange experienceLevel;

    @Shadow @Final @Mutable private GameMode gamemode;

    @Shadow @Final @Mutable private Map<Stat<?>, NumberRange.IntRange> stats;

    @Shadow @Final @Mutable private Object2BooleanMap<Identifier> recipes;

    //TODO: advancement support

    private NumberRange.FloatRange health = NumberRange.FloatRange.ANY;

    @Override
    public void setExperienceLevel(NumberRange.IntRange range) {
        this.experienceLevel = range;
    }

    @Override
    public void setGameMode(GameMode mode) {
        this.gamemode = mode;
    }

    @Override
    public void setStats(Map<Stat<?>, NumberRange.IntRange> stats) {
        this.stats = stats;
    }

    @Override
    public void setRecipes(Object2BooleanMap<Identifier> recipes) {
        this.recipes = recipes;
    }

    @Override
    public void setHealth(NumberRange.FloatRange range) {
        this.health = range;
    }

    @Inject(method = "fromJson", at = @At("RETURN"))
    private static void deserializeHealth(@Nullable JsonElement json, CallbackInfoReturnable<PlayerPredicate> info) {
        PlayerPredicate pred = info.getReturnValue();
        if (!pred.equals(PlayerPredicate.ANY)) {
            ((PlayerPredicateAccessor)pred).setHealth(NumberRange.FloatRange.fromJson(JsonHelper.asObject(json, "player").get("health")));
        }
    }

    @Inject(method = "toJson", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void serializeHealth(CallbackInfoReturnable<JsonElement> info, JsonObject ret) {
        ret.add("health", health.toJson());
    }

    @Inject(method = "test", at = @At("TAIL"), cancellable = true)
    private void testPlayerHealth(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (!this.health.test(((ServerPlayerEntity)entity).getHealth())) info.setReturnValue(false);
    }
}
