package net.chemthunder.saxophone.core.item;

import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.cca.entity.InsistenceComponent;
import net.chemthunder.saxophone.core.index.SaxoSoundEvents;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

/**
 * @author Chemthunder
 */
public class VirtusDeiItem extends SaxophoneItem {
    public VirtusDeiItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("item.saxophone.virtus_dei");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            List<Entity> targets = ModUtils.getEntitiesAroundEntity(user, 10);

            user.playSound(SaxoSoundEvents.BELL_TOLL, 1, 1);

            for (Entity entity : targets) {
                if (entity instanceof LivingEntity living) {
                    if (living != user) {
                        InsistenceComponent component = InsistenceComponent.KEY.get(living);

                        component.setActiveTicks(260);
                    }
                }
            }

            if (!user.isCreative()) {
                user.getItemCooldownManager().set(this, 400);
            }
        }
        return super.use(world, user, hand);
    }
}