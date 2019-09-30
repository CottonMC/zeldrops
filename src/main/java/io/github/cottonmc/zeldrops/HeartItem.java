package io.github.cottonmc.zeldrops;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.world.World;

public class HeartItem extends Item {
	public HeartItem(Settings settings) {
		super(settings);
	}

	@Override
	public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
		if (entity instanceof PlayerEntity) {
			PlayerEntity player = (PlayerEntity) entity;
			if (player.getHealth() < player.getMaximumHealth() && !player.getItemCooldownManager().isCoolingDown(this)) {
				player.heal(2f);
				player.getItemCooldownManager().set(this, 20);
				player.playSound(Zeldrops.HEAL, SoundCategory.PLAYERS, 1f, 1f);
				stack.decrement(1);
			}
		}
	}
}
