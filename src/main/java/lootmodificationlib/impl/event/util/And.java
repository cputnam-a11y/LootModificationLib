package lootmodificationlib.impl.event.util;

import lootmodificationlib.api.util.InterestKey;
import net.minecraft.util.Identifier;

import java.util.List;

public record And(List<InterestKey> keys) implements InterestKeyImpl {
    public And(InterestKey... keys) {
        this(List.of(keys));
    }

    @Override
    public boolean declaresInterest(Identifier key) {
        return keys.stream().allMatch(interestKey -> interestKey.declaresInterest(key));
    }
}
