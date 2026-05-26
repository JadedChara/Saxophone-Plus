package net.chemthunder.resewn.core.index;

import net.acoyt.acornlib.api.registrants.ItemRegistrant;
import net.chemthunder.resewn.core.Resewn;

/**
 * @author Chemthunder
 */
public interface ResewnItems {
    ItemRegistrant ITEMS = new ItemRegistrant(Resewn.MOD_ID);


    static void init() {}
}
