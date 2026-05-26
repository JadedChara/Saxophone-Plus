package net.chemthunder.saxophone.core.item;

import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

/**
 * @author Chemthunder
 */
public class DeificWarrantItem extends SaxophoneItem {
    public DeificWarrantItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("item.saxophone.deific_warrant");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }
}