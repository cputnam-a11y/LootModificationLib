package lootmodificationlib.impl.event.util;

import net.minecraft.util.Identifier;

import java.util.function.Predicate;

public record Dynamic(Predicate<Identifier> predicate) implements InterestKeyImpl {
    @Override
    public boolean declaresInterest(Identifier key) {
        return predicate.test(key);
    }
}
