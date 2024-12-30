package lootmodificationlib.impl.loot.extension;

import net.minecraft.util.Identifier;

import java.util.function.BiConsumer;

public interface LootDataTypeExtension<T> {
    BiConsumer<T, Identifier> lootmodificationlib$getIdSetter();
    void lootmodificationlib$setIdSetter(BiConsumer<T, Identifier> idSetter);
}
