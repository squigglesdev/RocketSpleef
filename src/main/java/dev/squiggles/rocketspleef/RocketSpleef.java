package dev.squiggles.rocketspleef;

import dev.squiggles.rocketspleef.command.RocketBreakableBlocksCommand;
import dev.squiggles.rocketspleef.storage.BlockListStorage;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

public class RocketSpleef implements ModInitializer {
    @Override
    public void onInitialize() {
        RocketBreakableBlocksCommand.getBreakableBlocks().addAll(BlockListStorage.loadBlockList());

        CommandRegistrationCallback.EVENT.register(RocketBreakableBlocksCommand::register);
    }
}
