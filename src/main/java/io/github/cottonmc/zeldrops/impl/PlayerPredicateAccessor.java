package io.github.cottonmc.zeldrops.impl;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import net.minecraft.predicate.NumberRange;
import net.minecraft.stat.Stat;
import net.minecraft.util.Identifier;
import net.minecraft.world.GameMode;

import java.util.Map;

public interface PlayerPredicateAccessor {
    void setExperienceLevel(NumberRange.IntRange range);

    void setGameMode(GameMode mode);

    void setStats(Map<Stat<?>, NumberRange.IntRange> stats);

    void setRecipes(Object2BooleanMap<Identifier> recipes);

    //TODO: figure out a way to do advancement predicates
//    void setAdvancements(Map<Identifier, PlayerPredicate.AdvancementPredicate> advancements)

    void setHealth(NumberRange.FloatRange range);
}
