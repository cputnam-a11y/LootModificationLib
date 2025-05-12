package lootmodificationlib.impl.loot.extension;

import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public interface LootDataTypeExtension<T> {
    default BiConsumer<T, Identifier> lootmodificationlib$getIdSetter() {
        throw new AssertionError("Implemented In Mixin");
    }
}
