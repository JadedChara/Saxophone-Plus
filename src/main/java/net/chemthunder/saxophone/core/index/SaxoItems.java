package net.chemthunder.saxophone.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.chemthunder.saxophone.core.Saxophone;
import net.chemthunder.saxophone.core.block.item.CovetousMonolithBlockItem;
import net.chemthunder.saxophone.core.item.*;
import net.minecraft.item.Item;

/**
 * @author Chemthunder
 */
public interface SaxoItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Saxophone.MOD_ID);

    Item LIBERATION = ITEMS.register("liberation", LiberationItem::new, new Item.Settings().maxCount(1).attributeModifiers(LiberationItem.createAttributeModifiers()));
    Item CONTRACT = ITEMS.register("contract", ContractItem::new, new Item.Settings().maxCount(1));
    Item DEIFIC_WARRANT = ITEMS.register("deific_warrant", DeificWarrantItem::new, new Item.Settings().maxCount(1));
    Item FORSAKEN_CHARTER = ITEMS.register("forsaken_charter", ForsakenCharterItem::new, new Item.Settings().maxCount(1));
    Item VIRTUS_DEI = ITEMS.register("virtus_dei", VirtusDeiItem::new, new Item.Settings().maxCount(1));
    Item REVENANT_EFFIGY = ITEMS.register("revenant_effigy", RevenantEffigyItem::new, new Item.Settings().maxCount(1));
    Item WRATH_OF_TWILIGHT = ITEMS.register("wrath_of_twilight", WrathOfTwilightItem::new, new Item.Settings().maxCount(1).attributeModifiers(WrathOfTwilightItem.createAttributeModifiers()));
    Item MARTYRDOM = ITEMS.register("martyrdom", MartyrdomItem::new, new Item.Settings().maxCount(1).attributeModifiers(MartyrdomItem.createAttributeModifiers()));
    Item AUTHORITYS_OBITUARY = ITEMS.register("authoritys_obituary", AuthoritysObituaryItem::new, new Item.Settings().maxCount(1));

    Item COVETOUS_MONOLITH_ITEM = ITEMS.register("covetous_monolith", CovetousMonolithBlockItem::new, new Item.Settings().maxCount(1));

    static void init() {}
}
