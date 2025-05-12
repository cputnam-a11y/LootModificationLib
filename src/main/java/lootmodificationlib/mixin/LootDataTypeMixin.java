package lootmodificationlib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.serialization.Codec;
import lootmodificationlib.impl.loot.extension.LootDataTypeExtension;
import lootmodificationlib.impl.loot.extension.LootTableExtension;
import net.minecraft.loot.LootDataType;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiConsumer;

@Mixin(LootDataType.class)
public abstract class LootDataTypeMixin<T> implements LootDataTypeExtension<T> {
    // @Formatter:OFF
    @Unique
    private static final ThreadLocal<BiConsumer<Object, Identifier>> NEXT_UP = ThreadLocal.withInitial(
            () -> (a, b) -> {}
    );
    // @Formatter:ON
    @Unique
    private BiConsumer<T, Identifier> idSetter;

    @Override
    public BiConsumer<T, Identifier> lootmodificationlib$getIdSetter() {
        return this.idSetter;
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "<init>", at = @At("TAIL"))
    private void lootmodificationlib$init(RegistryKey<T> registryKey, Codec<T> codec, LootDataType.Validator<T> validator, CallbackInfo ci) {
        this.idSetter = (BiConsumer<T, Identifier>) NEXT_UP.get();
        NEXT_UP.remove();
    }

    @WrapOperation(
            method = "<clinit>",
            at = @At(
                    value = "NEW",
                    target = "(Lnet/minecraft/registry/RegistryKey;Lcom/mojang/serialization/Codec;Lnet/minecraft/loot/LootDataType$Validator;)Lnet/minecraft/loot/LootDataType;",
                    ordinal = 2
            )
    )
    private static LootDataType<LootTable> v(RegistryKey<LootTable> registryKey, Codec<LootTable> codec, LootDataType.Validator<LootTable> validator, Operation<LootDataType<LootTable>> original) {
        NEXT_UP.set(reinterpret(LootTableExtension::lootmodificationlib$setId));
        return original.call(registryKey, codec, validator);
    }

    @Unique
    @SuppressWarnings("unchecked")
    private static BiConsumer<Object, Identifier> reinterpret(BiConsumer<LootTableExtension, Identifier> consumer) {
        return (BiConsumer<Object, Identifier>) (Object) consumer;
    }
}
