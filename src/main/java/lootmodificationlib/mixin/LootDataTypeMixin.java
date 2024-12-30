package lootmodificationlib.mixin;

import com.mojang.serialization.Codec;
import lootmodificationlib.impl.loot.extension.LootDataTypeExtension;
import lootmodificationlib.impl.loot.extension.LootTableExtension;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(LootDataType.class)
public abstract class LootDataTypeMixin<T> implements LootDataTypeExtension<T> {
    @Shadow
    @Final
    public static LootDataType<LootTable> LOOT_TABLES;
    @Unique
    private BiConsumer<T, Identifier> idSetter;

    @Unique
    @Override
    public BiConsumer<T, Identifier> lootmodificationlib$getIdSetter() {
        return this.idSetter;
    }

    @Unique
    @Override
    public void lootmodificationlib$setIdSetter(BiConsumer<T, Identifier> idSetter) {
        this.idSetter = idSetter;
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void lootmodificationlib$init(RegistryKey<T> registryKey, Codec<T> codec, LootDataType.Validator<T> validator, CallbackInfo ci) {
        this.idSetter = (a, b) -> {
        };
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static <T extends LootTable & LootTableExtension> void lootmodificationlib$clinit(CallbackInfo ci) {
        var type = (LootDataType<T> & LootDataTypeExtension<T>) (Object) LOOT_TABLES;
        type.lootmodificationlib$setIdSetter(LootTableExtension::lootmodificationlib$setId);
    }

}
