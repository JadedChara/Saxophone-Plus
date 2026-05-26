package net.chemthunder.saxophone.core.util.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.chemthunder.saxophone.core.index.SaxoItems;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.registry.Registries;

import java.util.concurrent.CompletableFuture;

/**
 * @author AcoYT
 * Ported from Minds Eye
 */
public class ItemArgumentType extends ItemStackArgumentType {
    public ItemArgumentType(CommandRegistryAccess commandRegistryAccess) {
        super(commandRegistryAccess);
    }

    public static ItemStackArgumentType itemStack(CommandRegistryAccess commandRegistryAccess) {
        return new ItemArgumentType(commandRegistryAccess);
    }

    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        Registries.ITEM.forEach(item -> {
            if (SaxoItems.ITEMS.toRegister.contains(item)) {
                builder.suggest(Registries.ITEM.getId(item).toString());
            }
        });
        return builder.buildFuture();
    }
}