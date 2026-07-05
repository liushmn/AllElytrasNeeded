package de.crafty.aen.init;

import de.crafty.aen.AllElytrasNeeded;
import de.crafty.aen.items.AENSmithingTemplateItem;
import de.crafty.aen.items.TieredElytraItem;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SmithingTemplateItem;

public class ItemRegistry {


    //New elytras
    public static final Item IRON_ELYTRA = registerItem("iron_elytra", new TieredElytraItem(TieredElytraItem.Tier.IRON, (new Item.Properties()).durability(320)));
    public static final Item GOLDEN_ELYTRA = registerItem("golden_elytra", new TieredElytraItem(TieredElytraItem.Tier.GOLD, (new Item.Properties()).durability(384)));
    public static final Item DIAMOND_ELYTRA = registerItem("diamond_elytra", new TieredElytraItem(TieredElytraItem.Tier.DIAMOND, (new Item.Properties()).durability(448)));
    public static final Item NETHERITE_ELYTRA = registerItem("netherite_elytra", new TieredElytraItem(TieredElytraItem.Tier.NETHERITE, (new Item.Properties()).durability(640).fireResistant()));

    //Templates
    public static final Item POTION_TEMPLATE = registerItem("elytra_effect_upgrade_smithing_template", AENSmithingTemplateItem.createPotionEffectTemplate());
    public static final Item CHESTPLATE_TEMPLATE = registerItem("chestplate_upgrade_smithing_template", AENSmithingTemplateItem.createChestplateUpgradeTemplate());

    private static Item registerItem(String id, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(AllElytrasNeeded.MOD_ID, id), item);
    }

    public static void perform() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.addAfter(Items.ELYTRA, IRON_ELYTRA, GOLDEN_ELYTRA, DIAMOND_ELYTRA, NETHERITE_ELYTRA);
            entries.addAfter(Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, POTION_TEMPLATE, CHESTPLATE_TEMPLATE);
        });
    }

}
