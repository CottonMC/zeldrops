package io.github.cottonmc.zeldrops;

import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.predicate.NumberRange;

public class RangeHelper {
    public static NumberRange.IntRange intOf(int min, int max) {
        JsonObject json = new JsonObject();
        json.add("min", new JsonPrimitive(min));
        json.add("max", new JsonPrimitive(max));
        return NumberRange.IntRange.fromJson(json);
    }

    public static NumberRange.IntRange intAtMost(int max) {
        JsonObject json = new JsonObject();
        json.add("max", new JsonPrimitive(max));
        return NumberRange.IntRange.fromJson(json);
    }

    public static NumberRange.FloatRange floatOf(float min, float max) {
        JsonObject json = new JsonObject();
        json.add("min", new JsonPrimitive(min));
        json.add("max", new JsonPrimitive(max));
        return NumberRange.FloatRange.fromJson(json);
    }

    public static NumberRange.FloatRange floatAtMost(float max) {
        JsonObject json = new JsonObject();
        json.add("max", new JsonPrimitive(max));
        return NumberRange.FloatRange.fromJson(json);
    }
}
