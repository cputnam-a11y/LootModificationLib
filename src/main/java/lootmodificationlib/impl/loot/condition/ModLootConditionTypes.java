package lootmodificationlib.impl.loot.condition;

import net.minecraft.loot.condition.LootConditionType;

public class ModLootConditionTypes {
    public static final LootConditionType BOOLEAN = new LootConditionType(
            BooleanCondition.CODEC
    );
}
