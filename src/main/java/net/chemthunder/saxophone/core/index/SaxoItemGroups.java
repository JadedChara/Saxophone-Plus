package net.chemthunder.saxophone.core.index;

import net.acoyt.acornlib.api.registrants.ItemGroupRegistrant;
import net.chemthunder.saxophone.core.Saxophone;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

/**
 * @author Chemthunder
 */
public interface SaxoItemGroups {
    ItemGroupRegistrant ITEM_GROUPS = new ItemGroupRegistrant(Saxophone.MOD_ID);

    MutableText text = Text.translatable("itemGroup.saxophone");
    ItemGroup MAIN = FabricItemGroup.builder()
            .icon(() -> new ItemStack(SaxoItems.LIBERATION))
            .displayName(text.withColor(0xFFd70048))
            .build();

    static void init() {
        ITEM_GROUPS.register("saxophone", MAIN, SaxoItemGroups::buildItemGroup);
    }

    private static void buildItemGroup(FabricItemGroupEntries entries) {
        for (Item item : SaxoItems.ITEMS.toRegister) {
            entries.add(item);
        }
    }
}