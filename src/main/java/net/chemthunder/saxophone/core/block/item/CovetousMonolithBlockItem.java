package net.chemthunder.saxophone.core.block.item;

import com.nitron.nitrogen.util.interfaces.ColorableItem;
import net.chemthunder.saxophone.core.index.SaxoBlocks;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

/**
 * @author Chemthunder
 */
public class CovetousMonolithBlockItem extends BlockItem implements ColorableItem {
    public CovetousMonolithBlockItem(Settings settings) {
        super(SaxoBlocks.COVETOUS_MONOLITH, settings);
    }

    public int startColor(ItemStack itemStack) {
        return 0xFFd70048;
    }
    public int endColor(ItemStack itemStack) {
        return 0xFF8e1a41;
    }

    public int backgroundColor(ItemStack itemStack) {
        return 0xFF1c0810;
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("block.saxophone.covetous_monolith");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }
}
