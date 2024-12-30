package lootmodificationlib.impl.event.util;


import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
record Single(Identifier key) implements InterestKeyImpl {
    @Override
    public boolean declaresInterest(Identifier key) {
        return key.equals(this.key);
    }
}
