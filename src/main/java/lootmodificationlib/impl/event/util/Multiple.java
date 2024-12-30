package lootmodificationlib.impl.event.util;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;

@ApiStatus.Internal
record Multiple(List<Identifier> keys) implements InterestKeyImpl {
    public Multiple(Identifier... keys) {
        this(List.of(keys));
    }

    public boolean declaresInterest(Identifier key) {
        return keys.contains(key);
    }
}
