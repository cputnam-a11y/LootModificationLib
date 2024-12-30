package lootmodificationlib.impl.event.util;

import lootmodificationlib.api.util.InterestKey;
import net.minecraft.util.Identifier;

import java.util.List;

public record Or(List<InterestKey> keys) implements InterestKeyImpl {
    public Or(InterestKey... keys) {
        this(List.of(keys));
    }

    @Override
    public boolean declaresInterest(Identifier key) {
        return keys.stream().anyMatch(interestKey -> interestKey.declaresInterest(key));
    }
}
