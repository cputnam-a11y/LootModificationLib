package lootmodificationlib.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lootmodificationlib.api.event.ModifyDrops;
import lootmodificationlib.impl.loot.extension.LootTableExtension;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(LootTable.class)
public abstract class LootTableMixinModifyDrops implements LootTableExtension {
    @ModifyArg(
            method = "generateUnprocessedLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/loot/function/LootFunction;apply(Ljava/util/function/BiFunction;Ljava/util/function/Consumer;Lnet/minecraft/loot/context/LootContext;)Ljava/util/function/Consumer;"
            )
    )
    private Consumer<ItemStack> lootmodificationlib$CaptureStackOutput(
            Consumer<ItemStack> original,
            @Share(
                    namespace = "lootmodificationlib",
                    value = "originalConsumer"
            ) LocalRef<Consumer<ItemStack>> originalConsumer,
            @Share(
                    namespace = "lootmodificationlib",
                    value = "droppedStacks"
            ) LocalRef<ObjectArrayList<ItemStack>> droppedStacks
    ) {
        originalConsumer.set(original);
        droppedStacks.set(new ObjectArrayList<>());
        return droppedStacks.get()::add;
    }

    @Inject(
            method = "generateUnprocessedLoot(Lnet/minecraft/loot/context/LootContext;Ljava/util/function/Consumer;)V",
            at = @At(
                    value = "INVOKE",
                    target =  "Lnet/minecraft/loot/context/LootContext;markInactive(Lnet/minecraft/loot/context/LootContext$Entry;)V"
            )
    )
    private void lootmodificationlib$onLootGenerated(
            LootContext lootContext, Consumer<ItemStack> consumer, CallbackInfo ci,
            @Share(
                    namespace = "lootmodificationlib",
                    value = "originalConsumer"
            ) LocalRef<Consumer<ItemStack>> originalConsumer,
            @Share(
                    namespace = "lootmodificationlib",
                    value = "droppedStacks"
            ) LocalRef<ObjectArrayList<ItemStack>> droppedStacks
    ) {

        ModifyDrops.EVENT.invoker().modifyLoot(
                this.lootmodificationlib$getId(),
                droppedStacks.get(),
                lootContext
        );
        droppedStacks.get().forEach(originalConsumer.get());
    }
}
