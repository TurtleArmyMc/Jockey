package com.turtlearmymc.jockey.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EggEntity.class)
public class EggEntityMixin {
	@Inject(method = "onCollision", locals = LocalCapture.CAPTURE_FAILHARD,
			at = @At(target = "net/minecraft/world/World.spawnEntity(Lnet/minecraft/entity/Entity;)Z",
					shift = At.Shift.AFTER, value = "INVOKE"))
	void onCollisionMixin(HitResult hitResult, CallbackInfo ci, int i, int j, ChickenEntity chickenEntity) {
		if (j == 0) { // Only ride the first chicken spawned
			if (hitResult instanceof EntityHitResult) {
				EntityHitResult entityHitResult = (EntityHitResult) hitResult;
				Entity entity = entityHitResult.getEntity();
				if (entity instanceof MobEntity || entity instanceof PlayerEntity) {
					entity.startRiding(chickenEntity);
				}
			}
		}
	}
}
