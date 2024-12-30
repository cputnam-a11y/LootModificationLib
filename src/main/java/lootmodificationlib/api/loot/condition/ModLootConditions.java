package lootmodificationlib.api.loot.condition;

import lootmodificationlib.impl.loot.condition.ModLootConditionsImpl;
import net.minecraft.loot.condition.LootCondition;

public class ModLootConditions {
    public static LootCondition alwaysTrue() {
        return ModLootConditionsImpl.TRUE;
    }

    public static LootCondition alwaysFalse() {
        return ModLootConditionsImpl.FALSE;
    }
}
