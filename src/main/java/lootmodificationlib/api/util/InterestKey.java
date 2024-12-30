package lootmodificationlib.api.util;

import lootmodificationlib.impl.event.util.InterestKeyImpl;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

@ApiStatus.NonExtendable
public interface InterestKey {
    boolean declaresInterest(Identifier key);

    static InterestKey all() {
        return InterestKeyImpl.all();
    }

    static InterestKey create(Identifier key) {
        return InterestKeyImpl.create(key);
    }

    static InterestKey create(Identifier... keys) {
        return InterestKeyImpl.create(keys);
    }

    static InterestKey create(Predicate<Identifier> predicate) {
        return InterestKeyImpl.create(predicate);
    }

    static InterestKey allOf(InterestKey... keys) {
        return InterestKeyImpl.allOf(keys);
    }

    static InterestKey anyOf(InterestKey... keys) {
        return InterestKeyImpl.anyOf(keys);
    }

    InterestKey and(InterestKey other);

    InterestKey or(InterestKey other);
}
