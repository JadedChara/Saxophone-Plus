package net.chemthunder.saxophone.core.index;

import com.mojang.serialization.Codec;
import net.acoyt.acornlib.api.registrants.ComponentTypeRegistrant;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.component.ContractComponent;
import net.minecraft.component.ComponentType;

/**
 * @author Chemthunder
 */
public interface SaxoDataComponents {
    ComponentTypeRegistrant DATA_COMPONENTS = new ComponentTypeRegistrant(Saxophone.MOD_ID);

    ComponentType<ContractComponent> CONTRACT = DATA_COMPONENTS.register("contract", builder -> builder.codec(ContractComponent.CODEC));
    ComponentType<Integer> CHARGES = DATA_COMPONENTS.register("charges", builder -> builder.codec(Codec.INT));

    static void init() {}
}