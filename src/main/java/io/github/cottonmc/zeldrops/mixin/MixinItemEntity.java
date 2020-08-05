package io.github.cottonmc.zeldrops.mixin;

import io.github.cottonmc.zeldrops.impl.CustomPickup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ItemEntity.class)
public abstract class MixinItemEntity extends Entity {

    @Shadow private int pickupDelay;

    @Shadow private UUID owner;

    @Shadow private int age;

    @Shadow public abstract ItemStack getStack();

    public MixinItemEntity(EntityType<?> entityType_1, World world_1) {
        super(entityType_1, world_1);
    }

    @Inject(method = "onPlayerCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerInventory;insertStack(Lnet/minecraft/item/ItemStack;)Z"), cancellable = true)
    private void doCustomPickup(PlayerEntity player, CallbackInfo ci) {
        ItemStack stack = this.getStack();
        Item item = stack.getItem();
        int count = stack.getCount();

        if (item instanceof CustomPickup) {
            if (this.pickupDelay == 0 && (this.owner == null || 6000 - this.age <= 200 || this.owner.equals(player.getUuid())) && ((CustomPickup)item).canPickUp(stack, player)) {
                ItemStack retStack = ((CustomPickup)item).doPickUp(stack, player);
                if (!retStack.isEmpty()) {
                    ItemEntity newItemEntity = new ItemEntity(world, getPos().x, getPos().y, getPos().z, retStack);
                    world.spawnEntity(newItemEntity);
                }
                player.sendPickup(this, count);
                player.increaseStat(Stats.PICKED_UP.getOrCreateStat(item), count - retStack.getCount());
                this.remove();
                ci.cancel();
            }
        }

    }
}
