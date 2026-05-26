package net.chemthunder.saxophone.core.item;

import com.nitron.nitrogen.util.interfaces.ScreenShaker;
import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.entity.ForsakenCharterEntity;
import net.chemthunder.saxophone.core.index.SaxoEntities;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;

/**
 * @author Chemthunder
 */
public class ForsakenCharterItem extends SaxophoneItem {
    public ForsakenCharterItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("item.saxophone.forsaken_charter");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();

        if (player != null) {
            if (player.isSneaking()) {
                ForsakenCharterEntity entity = new ForsakenCharterEntity(SaxoEntities.FORSAKEN_CHARTER, context.getWorld());

                entity.setPosition(context.getBlockPos().getX() + 0.5f, context.getBlockPos().getY() + 2.0f, context.getBlockPos().getZ() + 0.5f);
                context.getWorld().spawnEntity(entity);

                player.getStackInHand(player.getActiveHand()).decrement(1);

                if (player instanceof ScreenShaker shaker) {
                    shaker.addScreenShake(2.0f, 20);
                }
            }
        }
        return super.useOnBlock(context);
    }
}