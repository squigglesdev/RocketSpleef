package dev.squiggles.rocketspleef.mixin;

import dev.squiggles.rocketspleef.command.RocketBreakableBlocksCommand;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.TntBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FireworkRocketEntity.class)
public abstract class RocketMixin {

    @Inject(method = "onBlockHit", at = @At("HEAD"))
    public void onBlockHit(BlockHitResult blockHitResult, CallbackInfo ci) {
        BlockPos blockPos = new BlockPos(blockHitResult.getBlockPos());
        FireworkRocketEntity instance = (FireworkRocketEntity) (Object) this;
        instance.getWorld().getBlockState(blockPos).onEntityCollision(instance.getWorld(), blockPos, instance);
        World world = instance.getWorld();

        // Check the block directly hit
        BlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        // Check the block above in case the pressure plate is above
        BlockPos abovePos = blockPos.up();
        BlockState aboveBlockState = world.getBlockState(abovePos);
        Block aboveBlock = aboveBlockState.getBlock();


        if (RocketBreakableBlocksCommand.getIgnitesTNT()) {

            // Handle Pressure plate-block-tnt situations
            if (aboveBlock instanceof PressurePlateBlock) {
                BlockPos tntBlockPos = blockPos.down();
                BlockState tntBlockState = world.getBlockState(tntBlockPos);
                Block potentialTntBlock = tntBlockState.getBlock();
                if (potentialTntBlock instanceof TntBlock) {
                    TntBlock.primeTnt(world, tntBlockPos);
                    world.removeBlock(tntBlockPos, false);
                    world.removeBlock(abovePos, false);
                }
            }

            // Handle snowball-tnt situations
            if (block instanceof TntBlock) {
                TntBlock.primeTnt(world, blockPos);
                world.removeBlock(blockPos, false);
            }
        }

        if (RocketBreakableBlocksCommand.getBreakableBlocks().contains(block)) {
            world.breakBlock(blockPos, false);
        }


        if (!world.isClient() && !instance.hasExplosionEffects()) {
            instance.discard();
        }
    }

    @Inject(method = "onEntityHit", at = @At("HEAD"))
    public void onEntityHit(EntityHitResult entityHitResult, CallbackInfo ci) {
        FireworkRocketEntity instance = (FireworkRocketEntity) (Object) this;
        LivingEntity entity = (LivingEntity) entityHitResult.getEntity();

        if (entity instanceof PlayerEntity) {
            entity.takeKnockback(0.5, instance.getX() - entity.getX(), instance.getZ() - entity.getZ());
            entity.damage(instance.getDamageSources().fireworks(instance, (instance).getOwner()), 1.0f);
        }
    }
}
