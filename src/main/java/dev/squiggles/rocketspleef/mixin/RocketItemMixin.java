package dev.squiggles.rocketspleef.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FireworkRocketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FireworkRocketItem.class)
public abstract class RocketItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (user.isFallFlying()) {
            if (!world.isClient) {
                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemStack, user);
                world.spawnEntity(fireworkRocketEntity);
                if (!user.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                user.incrementStat(Stats.USED.getOrCreateStat((FireworkRocketItem) (Object) this));
            }
            cir.setReturnValue(TypedActionResult.success(user.getStackInHand(hand), world.isClient()));
        } else {
            if (!world.isClient) {
                double x = user.getX();
                double y = user.getEyeY() - 0.15000000596046448;
                double z = user.getZ();

                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemStack, user, x, y, z, true);
                Vec3d lookDirection = user.getRotationVector().normalize();
                fireworkRocketEntity.setVelocity(lookDirection);
                world.spawnEntity(fireworkRocketEntity);
                user.incrementStat(Stats.USED.getOrCreateStat((FireworkRocketItem) (Object) this));
            }
            cir.setReturnValue(TypedActionResult.success(user.getStackInHand(hand), world.isClient()));
        }
    }

    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    public void useOnBlock(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        ItemStack itemStack = context.getStack();
        PlayerEntity user = context.getPlayer();
        World world = context.getWorld();

        assert user != null;
        if (user.isFallFlying()) {
            if (!world.isClient) {
                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemStack, user);
                world.spawnEntity(fireworkRocketEntity);
                if (!user.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                user.incrementStat(Stats.USED.getOrCreateStat((FireworkRocketItem) (Object) this));
            }
            cir.setReturnValue(ActionResult.success(true));
        } else {
            if (!world.isClient) {
                double x = user.getX();
                double y = user.getEyeY() - 0.15000000596046448;
                double z = user.getZ();

                FireworkRocketEntity fireworkRocketEntity = new FireworkRocketEntity(world, itemStack, user, x, y, z, true);
                Vec3d lookDirection = user.getRotationVector().normalize();
                fireworkRocketEntity.setVelocity(lookDirection);
                world.spawnEntity(fireworkRocketEntity);
                user.incrementStat(Stats.USED.getOrCreateStat((FireworkRocketItem) (Object) this));
            }
            cir.setReturnValue(ActionResult.success(true));
        }
    }
}