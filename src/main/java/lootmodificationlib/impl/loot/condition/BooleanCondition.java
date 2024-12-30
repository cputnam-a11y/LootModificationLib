package lootmodificationlib.impl.loot.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.LootConditionType;
import net.minecraft.loot.context.LootContext;

public enum BooleanCondition implements LootCondition {
    FALSE,
    TRUE;
    public static final MapCodec<BooleanCondition> CODEC =
            RecordCodecBuilder.mapCodec(
                    instance -> instance.group(
                            Codec.BOOL
                                    .fieldOf("value")
                                    .forGetter(BooleanCondition::value)
                    ).apply(
                            instance,
                            BooleanCondition::fromBoolean
                    )
            );

    public boolean value() {
        return this == TRUE;
    }

    public static BooleanCondition fromBoolean(boolean value) {
        return value
               ? TRUE
               : FALSE;
    }

    @Override
    public LootConditionType getType() {
        return ModLootConditionTypes.BOOLEAN;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return this == TRUE;
    }
}
