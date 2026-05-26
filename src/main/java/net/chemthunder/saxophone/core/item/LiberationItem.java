package net.chemthunder.saxophone.core.item;

import net.acoyt.acornlib.api.item.CustomHitParticleItem;
import net.acoyt.acornlib.api.item.CustomHitSoundItem;
import net.acoyt.acornlib.api.item.CustomKillSourceItem;
import net.acoyt.acornlib.api.item.ModelVaryingItem;
import net.acoyt.acornlib.api.util.MiscUtils;
import net.acoyt.acornlib.api.util.ParticleUtils;
import net.acoyt.acornlib.impl.client.particle.SweepParticleEffect;
import net.chemthunder.saxophone.api.extendable.SaxophoneItem;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.index.SaxoSoundEvents;
import net.chemthunder.saxophone.core.index.data.SaxoDamageSources;
import net.chemthunder.saxophone.core.util.ModUtils;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * @author Chemthunder
 */
public class LiberationItem extends SaxophoneItem implements CustomHitParticleItem, CustomKillSourceItem, CustomHitSoundItem, ModelVaryingItem {
    public static final SweepParticleEffect[] EFFECTS = new SweepParticleEffect[]{
        new SweepParticleEffect(0xd70048, 0x0c0105)
    };

    public LiberationItem(Settings settings) {
        super(settings);
    }

    public void spawnHitParticles(PlayerEntity playerEntity, Entity entity) {
        ParticleUtils.spawnSweepParticles(EFFECTS[playerEntity.getRandom().nextInt(EFFECTS.length)], playerEntity);
    }

    public Identifier getModel(ModelTransformationMode modelTransformationMode, ItemStack itemStack, @Nullable LivingEntity livingEntity) {
        return MiscUtils.isGui(modelTransformationMode) ? Saxophone.id("liberation") : Saxophone.id("liberation_handheld");
    }

    public List<Identifier> getModelsToLoad() {
        return Arrays.asList(
                Saxophone.id("liberation"),
                Saxophone.id("liberation_handheld")
        );
    }

    public static AttributeModifiersComponent createAttributeModifiers() {
        return AttributeModifiersComponent.builder()
                .add(
                        EntityAttributes.GENERIC_ATTACK_DAMAGE,
                        new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, 9.0f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.GENERIC_ATTACK_SPEED,
                        new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, -3.0f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .add(
                        EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE,
                        new EntityAttributeModifier(Identifier.ofVanilla("base_entity_interaction_range"), 0.5f, EntityAttributeModifier.Operation.ADD_VALUE),
                        AttributeModifierSlot.MAINHAND
                )
                .build();
    }

    public Text getName(ItemStack stack) {
        MutableText text = Text.translatable("item.saxophone.liberation");
        return super.getName(stack).copy().setStyle(ModUtils.nameEffect(text)).withColor(0xd70048);
    }

    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    public DamageSource getKillSource(LivingEntity livingEntity) {
        return livingEntity.getDamageSources().create(SaxoDamageSources.LIBERATE);
    }

    public void playHitSound(PlayerEntity playerEntity, Entity entity) {
        playerEntity.playSound(SaxoSoundEvents.LIBERATION_SWING, 1.0f, (float) (1.0f + playerEntity.getRandom().nextGaussian() / 10.0f));
    }
}