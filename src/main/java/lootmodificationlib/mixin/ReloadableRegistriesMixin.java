package lootmodificationlib.mixin;

import net.minecraft.loot.LootDataType;
import net.minecraft.registry.MutableRegistry;
import net.minecraft.registry.ReloadableRegistries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ReloadableRegistries.class)
public class ReloadableRegistriesMixin {
    @Inject(
            method = "method_58286",
            at = @At("HEAD")
    )
    private static <G> void onBeforeAddToRegistry(MutableRegistry<G> mutableRegistry, LootDataType<G> rawLootDataType, Identifier id, G value, CallbackInfo ci) {
        rawLootDataType.lootmodificationlib$getIdSetter().accept(value, id);
    }
}
