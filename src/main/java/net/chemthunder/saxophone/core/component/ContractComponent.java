package net.chemthunder.saxophone.core.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

/**
 * @author Chemthunder
 */
public record ContractComponent(String signerName, boolean isSigned, boolean isAvarice) {
    public static final Codec<ContractComponent> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.optionalFieldOf("signerName", "").forGetter(ContractComponent::signerName),
                    Codec.BOOL.optionalFieldOf("isSigned", false).forGetter(ContractComponent::isSigned),
                    Codec.BOOL.optionalFieldOf("isAvarice", false).forGetter(ContractComponent::isAvarice)
            ).apply(instance, ContractComponent::new)
    );
}