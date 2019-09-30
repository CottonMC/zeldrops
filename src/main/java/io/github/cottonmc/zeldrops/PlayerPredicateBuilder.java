package io.github.cottonmc.zeldrops;

import io.github.cottonmc.zeldrops.impl.PlayerPredicateAccessor;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.predicate.NumberRange;
import net.minecraft.predicate.PlayerPredicate;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;

import java.util.Map;

public class PlayerPredicateBuilder extends PlayerPredicate.Builder {
    private NumberRange.IntRange experienceLevel = null;
    private GameMode gameMode = null;
    private Map<Stat<?>, NumberRange.IntRange> stats = null;
    private Object2BooleanMap<Identifier> recipes = null;
    //TODO: advancement support
    private NumberRange.FloatRange health = null;

    public PlayerPredicateBuilder withExperienceLevel(NumberRange.IntRange level) {
        this.experienceLevel = level;
        return this;
    }

    public PlayerPredicateBuilder withGameMode(GameMode mode) {
        this.gameMode = mode;
        return this;
    }

    public PlayerPredicateBuilder withStats(Map<Stat<?>, NumberRange.IntRange> stats) {
        this.stats = stats;
        return this;
    }

    public PlayerPredicateBuilder withRecipes(Object2BooleanMap<Identifier> recipes) {
        this.recipes = recipes;
        return this;
    }

    public PlayerPredicateBuilder withHealth(NumberRange.FloatRange health) {
        this.health = health;
        return this;
    }

    public PlayerPredicate build() {
        PlayerPredicate ret = super.build();
        PlayerPredicateAccessor accessor = (PlayerPredicateAccessor)ret;
        if (experienceLevel != null) accessor.setExperienceLevel(experienceLevel);
        if (gameMode != null) accessor.setGameMode(gameMode);
        if (stats != null) accessor.setStats(stats);
        if (recipes != null) accessor.setRecipes(recipes);
        if (health != null) accessor.setHealth(health);
        else accessor.setHealth(NumberRange.FloatRange.ANY);
        return ret;
    }
}
