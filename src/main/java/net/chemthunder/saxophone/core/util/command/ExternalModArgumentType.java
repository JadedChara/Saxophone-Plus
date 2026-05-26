package net.chemthunder.saxophone.core.util.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.chemthunder.oracle.impl.index.OracleItems;
import net.chemthunder.reverence.impl.index.ReverenceItems;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.registry.Registries;

import java.util.concurrent.CompletableFuture;

public class ExternalModArgumentType extends ItemStackArgumentType {
    public ExternalModArgumentType(CommandRegistryAccess commandRegistryAccess) {
        super(commandRegistryAccess);
    }

    public static ExternalModArgumentType itemStack(CommandRegistryAccess commandRegistryAccess) {
        return new ExternalModArgumentType(commandRegistryAccess);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        builder.suggest(Registries.ITEM.getId(OracleItems.SALVATION).toString());
        builder.suggest(Registries.ITEM.getId(ReverenceItems.EMPTINESS).toString());
        return builder.buildFuture();
    }
}
