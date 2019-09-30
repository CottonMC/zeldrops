package io.github.cottonmc.zeldrops.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface CustomPickup {

    /**
     * @param stack The stack to be picked up.
     * @param player The player picking up the stack.
     * @return Whether the stack can be partially or entirely picked up at this time.
     */
    boolean canPickUp(ItemStack stack, PlayerEntity player);

    /**
     * @param stack The stack to be picked up.
     * @param player The player picking up the stack.
     * @return Any left-over items, or an empty stack if there are none.
     */
    ItemStack doPickUp(ItemStack stack, PlayerEntity player);
}
