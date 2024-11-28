package q4niel.regen.mixin;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import q4niel.regen.PassiveRegen;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    int preHealCount = 500;
    int duringHealCount = 100;

    boolean shouldHeal = false;
    int counter = 0;

    @Inject (
            method = "tick()V",
            at = @At("HEAD")
    )
    void tick(CallbackInfo ci) {
        PlayerEntity self = (PlayerEntity)(Object)this;
        if (self.getWorld().isClient() || self.getHealth() == self.getMaxHealth()) return;

        PassiveRegen.LOGGER.info(String.valueOf(counter));

        if (!shouldHeal && counter++ >= preHealCount) {
            shouldHeal = true;
            counter = 0;
        }

        if (shouldHeal && counter++ >= duringHealCount) {
            self.heal(1);
            counter = 0;
        }
    }

    @Inject (
            method = "applyDamage(Lnet/minecraft/entity/damage/DamageSource;F)V",
            at = @At("HEAD")
    )
    void applyDamage(DamageSource source, float amount, CallbackInfo ci) {
        if (((PlayerEntity)(Object)this).getWorld().isClient()) return;
        shouldHeal = false;
        counter = 0;
    }
}