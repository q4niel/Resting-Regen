package dev.q4niel.resting_regen.mixin;

import dev.q4niel.resting_regen.RestingRegen;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    private ServerPlayerEntity _self = (ServerPlayerEntity)(Object)this;
    private int _healTicker = 0;

    @Inject (
            method = "tick()V",
            at = @At("HEAD")
    )
    void tick(CallbackInfo ci) {
        RestingRegen.INSTANCE.serverExec(() -> {
            if (_self.getHealth() == _self.getMaxHealth() || hostilesNearby()) {
                _healTicker = 0;
                return;
            }

            if (_healTicker++ < RestingRegen.INSTANCE.getHealTickerLimit()) return;

            _healTicker = 0;
            _self.heal(1);
        });
    }

    private boolean hostilesNearby() {
        Vec3d pos = _self.getPos();

        List<HostileEntity> list = _self.getWorld().getEntitiesByClass (
                HostileEntity.class,
                new Box (
                        pos.getX() - RestingRegen.INSTANCE.getHorizontalBlockDistance(),
                        pos.getY() - RestingRegen.INSTANCE.getVerticalBlockDistance(),
                        pos.getZ() - RestingRegen.INSTANCE.getHorizontalBlockDistance(),
                        pos.getX() + RestingRegen.INSTANCE.getHorizontalBlockDistance(),
                        pos.getY() + RestingRegen.INSTANCE.getVerticalBlockDistance(),
                        pos.getZ() + RestingRegen.INSTANCE.getHorizontalBlockDistance()
                ),
                entity -> entity.isAngryAt(_self)
        );

        return !list.isEmpty();
    }
}