package de.crafty.aen.items;

import de.crafty.aen.AllElytrasNeeded;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;

import java.util.List;

public class AENSmithingTemplateItem extends SmithingTemplateItem {

    //------------------- Vanilla Empty Slots -------------------
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");

    private static final ResourceLocation EMPTY_SLOT_ELYTRA = new ResourceLocation(AllElytrasNeeded.MOD_ID, "item/empty_armor_slot_elytra");
    private static final ResourceLocation EMPTY_SLOT_ELYTRA_CHESTPLATE = new ResourceLocation(AllElytrasNeeded.MOD_ID, "item/empty_armor_slot_elytra_chestplate");
    private static final ResourceLocation EMPTY_SLOT_POTION = new ResourceLocation(AllElytrasNeeded.MOD_ID, "item/empty_armor_slot_potion");
    private static final ResourceLocation EMPTY_SLOT_SPLASH_POTION = new ResourceLocation(AllElytrasNeeded.MOD_ID, "item/empty_armor_slot_splash_potion");
    private static final ResourceLocation EMPTY_SLOT_LINGERING_POTION = new ResourceLocation(AllElytrasNeeded.MOD_ID, "item/empty_armor_slot_lingering_potion");


    //------------------- Potion Effect Upgrade -------------------
    private static final Component ELYTRA_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(AllElytrasNeeded.MOD_ID, "smithing_template.elytra_effect_upgrade.applies_to")))
            .withStyle(ChatFormatting.BLUE);

    private static final Component ELYTRA_EFFECT_UPGRADE_INGREDIENTS = Component.translatable(
            Util.makeDescriptionId("item", new ResourceLocation(AllElytrasNeeded.MOD_ID, "smithing_template.elytra_effect_upgrade.ingredients"))
    ).withStyle(ChatFormatting.BLUE);

    private static final Component ELYTRA_EFFECT_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation(AllElytrasNeeded.MOD_ID, "elytra_effect_upgrade")))
            .withStyle(ChatFormatting.GRAY);

    private static final Component ELYTRA_EFFECT_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(
            Util.makeDescriptionId("item", new ResourceLocation(AllElytrasNeeded.MOD_ID, "smithing_template.elytra_effect_upgrade.base_slot_description"))
    );

    private static final Component ELYTRA_EFFECT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(
            Util.makeDescriptionId("item", new ResourceLocation(AllElytrasNeeded.MOD_ID, "smithing_template.elytra_effect_upgrade.additions_slot_description"))
    );

    //-------------- Chestplate Upgrade -----------------
    private static final Component CHESTPLATE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item", new ResourceLocation(AllElytrasNeeded.MOD_ID, "smithing_template.chestplate_upgrade.applies_to")))
            .withStyle(ChatFormatting.BLUE);

    private static final Component CHESTPLATE_UPGRADE_INGREDIENTS = Component.translatable(
            Util.makeDescriptionId("item", new ResourceLocation(AllElytrasNeeded.MOD_ID, "smithing_template.chestplate_upgrade.ingredients"))
    ).withStyle(ChatFormatting.BLUE);

    private static final Component CHESTPLATE_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade", new ResourceLocation(AllElytrasNeeded.MOD_ID, "chestplate_upgrade")))
            .withStyle(ChatFormatting.GRAY);

    private static final Component CHESTPLATE_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(
            Util.makeDescriptionId("item", new ResourceLocation(AllElytrasNeeded.MOD_ID, "smithing_template.chestplate_upgrade.base_slot_description"))
    );

    private static final Component CHESTPLATE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(
            Util.makeDescriptionId("item", new ResourceLocation(AllElytrasNeeded.MOD_ID, "smithing_template.chestplate_upgrade.additions_slot_description"))
    );

    public AENSmithingTemplateItem(Component component, Component component2, Component component3, Component component4, Component component5, List<ResourceLocation> list, List<ResourceLocation> list2) {
        super(component, component2, component3, component4, component5, list, list2);
    }


    public static AENSmithingTemplateItem createPotionEffectTemplate() {
        return new AENSmithingTemplateItem(
                ELYTRA_APPLIES_TO,
                ELYTRA_EFFECT_UPGRADE_INGREDIENTS,
                ELYTRA_EFFECT_UPGRADE,
                ELYTRA_EFFECT_UPGRADE_BASE_SLOT_DESCRIPTION,
                ELYTRA_EFFECT_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
                List.of(EMPTY_SLOT_ELYTRA, EMPTY_SLOT_ELYTRA_CHESTPLATE),
                List.of(EMPTY_SLOT_POTION, EMPTY_SLOT_SPLASH_POTION, EMPTY_SLOT_LINGERING_POTION)
        );
    }

    public static AENSmithingTemplateItem createChestplateUpgradeTemplate() {
        return new AENSmithingTemplateItem(
                CHESTPLATE_APPLIES_TO,
                CHESTPLATE_UPGRADE_INGREDIENTS,
                CHESTPLATE_UPGRADE,
                CHESTPLATE_UPGRADE_BASE_SLOT_DESCRIPTION,
                CHESTPLATE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
                List.of(EMPTY_SLOT_CHESTPLATE),
                List.of(EMPTY_SLOT_ELYTRA)
        );
    }
}
