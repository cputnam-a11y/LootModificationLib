package lootmodificationlib.impl.event.util;

import lootmodificationlib.api.util.InterestKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Predicate;

public sealed interface InterestKeyImpl extends InterestKey permits All, And, Dynamic, Multiple, Or, Single {
    static @NotNull InterestKey all() {
        return All.INSTANCE;
    }

    static @NotNull InterestKey create(@NotNull Identifier key) {
        return new Single(key);
    }

    static @NotNull InterestKey create(@NotNull Identifier... keys) {
        return new Multiple(keys);
    }

    static @NotNull InterestKey create(@NotNull Predicate<Identifier> predicate) {
        return new Dynamic(predicate);
    }

    static @NotNull InterestKey allOf(@NotNull InterestKey... keys) {
        return new And(keys);
    }

    static @NotNull InterestKey anyOf(@NotNull InterestKey... keys) {
        return new Or(keys);
    }

    @Override
    default @NotNull InterestKey and(@NotNull InterestKey other) {
        Objects.requireNonNull(other);
        if (this == All.INSTANCE) {
            return other;
        } else if (other == All.INSTANCE) {
            return this;
        }
        return new And(this, other);
    }

    @Override
    default @NotNull InterestKey or(@NotNull InterestKey other) {
        Objects.requireNonNull(other);
        if (this == All.INSTANCE || other == All.INSTANCE) {
            return All.INSTANCE;
        }
        return new Or(this, other);
    }
}
