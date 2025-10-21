package dev.q4niel.mixin;

import dev.q4niel.ModConfig;
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

    @Inject(method = "tick()V", at = @At("HEAD"))
    void tick(CallbackInfo ci) {
        if (_self.getHealth() == _self.getMaxHealth() || _hostilesNearby()) {
            _healTicker = 0;
            return;
        }

        if (_healTicker++ < ModConfig.INSTANCE.get().getHealTickerLimit()) return;

        _healTicker = 0;
        _self.heal(1);
    }

    private boolean _hostilesNearby() {
        Vec3d pos = _self.getEntityPos();
        double y = ModConfig.INSTANCE.get().getVerticalBlockDistance();
        double xz = ModConfig.INSTANCE.get().getHorizontalBlockDistance();

        List<HostileEntity> list = _self.getEntityWorld().getEntitiesByClass (
                HostileEntity.class,
                new Box (
                    pos.getX() - xz,
                    pos.getY() - y,
                    pos.getZ() - xz,
                    pos.getX() + xz,
                    pos.getY() + y,
                    pos.getZ() + xz
                ),
                entity -> entity.isAngryAt(_self.getEntityWorld(), _self)
        );

        return !list.isEmpty();
    }
}