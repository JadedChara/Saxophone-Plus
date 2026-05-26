package net.chemthunder.saxophone.core.item;

import net.acoyt.acornlib.api.item.CustomHitParticleItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.cca.entity.InsistenceComponent;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Chemthunder
 */
public class WrathOfTwilightItem extends SaxophoneItem implements ModelVaryingItem, CustomHitParticleItem {
    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{
            new SweepParticleEffect(0xd70048, 0x0c0105)
    };

    public WrathOfTwilightItem(Settings settings) {
        super(settings);
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("item.saxophone.wrath_of_twilight");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 7.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -2.6f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Identifier.ofVanilla("base_entity_interaction_range"), 0.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public Identifier getModel(ModelTransformationMode modelTransformationMode, ItemStack itemStack, @Nullable LivingEntity livingEntity) {
        return MiscUtils.isGui(modelTransformationMode) ? Saxophone.id("wrath_of_twilight") : Saxophone.id("wrath_of_twilight_handheld");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Saxophone.id("wrath_of_twilight"),
                Saxophone.id("wrath_of_twilight_handheld")
        );
    }

    public void spawnHitParticles(PlayerEntity playerEntity, Entity entity) {
        ParticleUtils.spawnSweepParticles(EFFECTS[playerEntity.getRandom().nextInt(EFFECTS.length)], playerEntity);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        InsistenceComponent insis = InsistenceComponent.KEY.get(entity);

        if (!user.getItemCooldownManager().isCoolingDown(this)) {
            if (insis.getActiveTicks() == 0) {
                insis.setActiveTicks(240);
                user.getItemCooldownManager().set(this, 400);
            }
        }
        return super.useOnEntity(stack, user, entity, hand);
    }
}