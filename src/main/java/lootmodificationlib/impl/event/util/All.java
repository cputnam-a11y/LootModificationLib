package lootmodificationlib.impl.event.util;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
enum All implements InterestKeyImpl {
    INSTANCE;

    @Override
    public boolean declaresInterest(Identifier key) {
        return true;
    }
}
