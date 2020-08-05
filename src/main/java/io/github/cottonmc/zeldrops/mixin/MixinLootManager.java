package io.github.cottonmc.zeldrops.mixin;

import com.google.gson.JsonObject;
import io.github.cottonmc.zeldrops.Zeldrops;
import net.minecraft.loot.LootManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LootManager.class)
public class MixinLootManager {
	@Inject(method = "apply", at = @At("HEAD"))
	private void prepIds(Map<Identifier, JsonObject> resources, ResourceManager manager, Profiler profiler, CallbackInfo info) {
		Zeldrops.prepareTableIds();
	}
}
