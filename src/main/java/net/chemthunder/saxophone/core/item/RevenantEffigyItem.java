package net.chemthunder.saxophone.core.item;

import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.cca.entity.RevenantDeathAnimationComponent;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

/**
 * @author Chemthunder
 */
public class RevenantEffigyItem extends SaxophoneItem {
    public RevenantEffigyItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("item.saxophone.revenant_effigy");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.isCreative()) {
            RevenantDeathAnimationComponent component = RevenantDeathAnimationComponent.KEY.get(user);

            component.beginAnimation();
        }
        return super.use(world, user, hand);
    }
}