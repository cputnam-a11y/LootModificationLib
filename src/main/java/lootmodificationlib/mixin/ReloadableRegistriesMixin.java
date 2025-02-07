package lootmodificationlib.mixin;

import lootmodificationlib.impl.loot.extension.LootDataTypeExtension;
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
    // safe because LootDataType implements LootDataTypeExtensionImpl via mixin,
    // javac just doesn't like it because LootDataType is a record and thus final
    // the unchecked is safe because the consumer that takes G is set on the LootDataType, which is also of type G
    @SuppressWarnings({"DataFlowIssue", "unchecked"})
    @Inject(method = "method_58286", at = @At("HEAD"))
    private static <G> void onBeforeAddToRegistry(MutableRegistry<G> mutableRegistry, LootDataType<G> rawLootDataType, Identifier id, Object /*G*/ value, CallbackInfo ci) {
        var lootDataType = (LootDataType<G> & LootDataTypeExtension<G>) (Object) rawLootDataType;
        lootDataType.lootmodificationlib$getIdSetter().accept((G) value, id);
    }
}
