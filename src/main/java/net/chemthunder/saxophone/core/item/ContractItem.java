package net.chemthunder.saxophone.core.item;

import com.everest.hibiscus.api.modules.rendering.text.HibiscusPresetEffects;
import com.everest.hibiscus.api.modules.rendering.text.registry.TextEffectManager;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.component.ContractComponent;
import net.chemthunder.saxophone.core.index.SaxoDataComponents;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Chemthunder
 */
public class ContractItem extends SaxophoneItem implements ModelVaryingItem {
    public ContractItem(Settings settings) {
        super(settings.component(SaxoDataComponents.CONTRACT, new ContractComponent("", false, false)));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        var component = stack.get(SaxoDataComponents.CONTRACT);
        boolean canApplyAvarice = ModUtils.isAvarice(user) || user.getOffHandStack().isIn(ItemTags.SWORDS);

        if (user.isSneaking()) {
            if (component != null) {
                if (!component.isSigned()) {
                    stack.set(SaxoDataComponents.CONTRACT, new ContractComponent(user.getNameForScoreboard(), true, canApplyAvarice));
                    Saxophone.ALL_CONTRACTED_PLAYERS.add(user.getUuid());

                    if (world.isClient) {
                        user.swingHand(hand);
                        user.playSoundToPlayer(SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.PLAYERS, 1, 1);
                    }
                }
            }
        }
        return super.use(world, user, hand);
    }

    public Identifier getModel(ModelTransformationMode modelTransformationMode, ItemStack itemStack, @Nullable LivingEntity livingEntity) {
        return itemStack.getOrDefault(SaxoDataComponents.CONTRACT, new ContractComponent("", false, false)).isSigned() ? Saxophone.id("contract_signed") : Saxophone.id("contract");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Saxophone.id("contract"),
                Saxophone.id("contract_signed")
        );
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        var comp = stack.get(SaxoDataComponents.CONTRACT);

        if (comp != null) {
            if (comp.isSigned() && !comp.isAvarice()) {
                tooltip.add(Text.literal("Behold, a bastard named ").formatted(Formatting.DARK_GRAY).append(Text.literal(comp.signerName()).withColor(0xff0055)).append(Text.literal(".").formatted(Formatting.DARK_GRAY)));
                tooltip.add(Text.literal("Let their actions serve as a warning.").formatted(Formatting.DARK_GRAY));
            }

            if (comp.isAvarice()) {
                tooltip.add(Text.literal("My love, oh my love. I'm sorry for having you watch what I've become.").formatted(Formatting.DARK_GRAY));
                tooltip.add(Text.literal("I only wish I could take it all back from the start;").formatted(Formatting.DARK_GRAY));
                tooltip.add(Text.literal("To scrape away the parasites in my heart.").formatted(Formatting.DARK_GRAY));
            }
        }

        super.appendTooltip(stack, context, tooltip, type);
    }

    public Text getName(ItemStack stack) {
        var comp = stack.get(SaxoDataComponents.CONTRACT);
        MutableText text;

        if (comp != null) {
            if (!comp.isAvarice()) {
                text = Text.translatable(comp.isSigned() ? "item.saxophone.contract_signed" : "item.saxophone.contract");
                return text.setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
            } else {
                if (!comp.isSigned()) {
                    text = Text.translatable("item.saxophone.contract");
                } else {
                    text = Text.literal("A Lover's Farewell");
                }
                return text.setStyle(TextEffectManager.withEffect(text.getStyle(), HibiscusPresetEffects.LERP_WAVE_EFFECT, TextEffectManager.getEffect(HibiscusPresetEffects.LERP_WAVE_EFFECT))).withColor(0xd70048);
            }
        }
        return super.getName(stack);
    }
}