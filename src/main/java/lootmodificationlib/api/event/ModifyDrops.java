package lootmodificationlib.api.event;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lootmodificationlib.api.loot.condition.ModLootConditions;
import lootmodificationlib.api.util.InterestKey;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;

@FunctionalInterface
public interface ModifyDrops {
    Event<ModifyDrops> EVENT = EventFactory.createArrayBacked(ModifyDrops.class, (listeners) -> (id, drops, context) -> {
        for (var listener : listeners) {
            if (!listener.getInterestKey().declaresInterest(id) ||
                    !listener.getApplicationCondition(id).test(context))
                continue;
            listener.modifyLoot(id, drops, context);
        }
    });

    default InterestKey getInterestKey() {
        return InterestKey.all();
    }

    default LootCondition getApplicationCondition(Identifier id) {
        return ModLootConditions.alwaysTrue();
    }

    void modifyLoot(Identifier id, ObjectArrayList<ItemStack> drops, LootContext context);
}
