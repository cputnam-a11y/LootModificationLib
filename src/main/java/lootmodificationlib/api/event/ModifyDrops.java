package lootmodificationlib.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.AllOfLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.util.TriConsumer;

import java.util.List;

@FunctionalInterface
public interface ModifyDrops {
    // no conditions = all conditions match
    LootCondition TRUE = AllOfLootCondition.builder().build();

    Event<ModifyDrops> EVENT = EventFactory.createArrayBacked(ModifyDrops.class, (listeners) -> (id, drops, context) -> {

        for (var listener : listeners) {

            if (!listener.applicationCondition(id).test(context)) {
                continue;
            }

            listener.modifyLoot(id, drops, context);
        }

    });

    default LootCondition applicationCondition(Identifier id) {
        return TRUE;
    }

    void modifyLoot(Identifier id, List<ItemStack> drops, LootContext context);

    static ModifyDrops withCondition(LootCondition condition, TriConsumer<Identifier, List<ItemStack>, LootContext> modification) {
        return new ModifyDrops() {
            @Override
            public void modifyLoot(Identifier id, List<ItemStack> drops, LootContext context) {
                modification.accept(id, drops, context);
            }

            @Override
            public LootCondition applicationCondition(Identifier id) {
                return condition;
            }
        };
    }
}
