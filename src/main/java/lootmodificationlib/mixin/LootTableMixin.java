package lootmodificationlib.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lootmodificationlib.api.event.ModifyDrops;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.function.Consumer;

@Mixin(LootTable.class)
abstract class LootTableMixin {
    @Unique
    private Identifier id;

    @WrapMethod(
            method = "generateUnprocessedLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V"
    )
    private void modifyLoot(LootContext context, Consumer<ItemStack> lootConsumer, Operation<Void> original) {
        var list = new ObjectArrayList<ItemStack>();
        Consumer<ItemStack> consumer = list::add;
        original.call(context, consumer);
        if (this.id == null) {
            Identifier id = context.getWorld()
                    .getRegistryManager()
                    .getOrThrow(RegistryKeys.LOOT_TABLE)
                    .getId((LootTable) (Object) this);
            if (id == null) {
                throw new IllegalStateException("LootTable appears to not be registered, but has been asked to generate loot");
            }
            this.id = id;
        }

        ModifyDrops.EVENT.invoker().modifyLoot(
                this.id,
                list,
                context
        );
        list.forEach(lootConsumer);
    }
}
