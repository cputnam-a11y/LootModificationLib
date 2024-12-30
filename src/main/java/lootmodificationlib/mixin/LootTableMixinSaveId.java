package lootmodificationlib.mixin;

import lootmodificationlib.impl.loot.extension.LootTableExtension;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(LootTable.class)
public abstract class LootTableMixinSaveId implements LootTableExtension {
    @Unique
    private Identifier id;

    @Unique
    @Override
    public void lootmodificationlib$setId(Identifier id) {
        this.id = id;
    }

    @Unique
    @Override
    public Identifier lootmodificationlib$getId() {
        if (this.id == null)
            throw new IllegalStateException("Id is not set");
        return this.id;
    }
}
