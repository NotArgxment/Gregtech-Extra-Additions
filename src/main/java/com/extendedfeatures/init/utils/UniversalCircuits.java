package com.extendedfeatures.init.utils;

import com.extendedfeatures.CreativeTabs;
import com.extendedfeatures.client.integrations.Configuration.EFConfig;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.data.recipe.CustomTags;

import com.tterrag.registrate.util.entry.ItemEntry;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.ArrayList;
import java.util.List;

import static com.extendedfeatures.ExtendedFeaturesCore.ExtendedFeaturesRegister;

@SuppressWarnings("unchecked")
public class UniversalCircuits {

    static {
        ExtendedFeaturesRegister.creativeModeTab(() -> CreativeTabs.CIRCUITS_TAB);
    }

    // Credits to witherschat for allowing me to use the universal circuits textures
    public static final ItemEntry<Item>[] UNIVERSAL_CIRCUITS = new ItemEntry[GTValues.TIER_COUNT];

    private static final Object[][] CIRCUIT_DATA = {
            { GTValues.ULV, "ulv_universal_circuit", "§8ULV §rUniversal Circuit", CustomTags.ULV_CIRCUITS },
            { GTValues.LV, "lv_universal_circuit", "§7LV §rUniversal Circuit", CustomTags.LV_CIRCUITS },
            { GTValues.MV, "mv_universal_circuit", "§bMV §rUniversal Circuit", CustomTags.MV_CIRCUITS },
            { GTValues.HV, "hv_universal_circuit", "§6HV §rUniversal Circuit", CustomTags.HV_CIRCUITS },
            { GTValues.EV, "ev_universal_circuit", "§5EV §rUniversal Circuit", CustomTags.EV_CIRCUITS },
            { GTValues.IV, "iv_universal_circuit", "§9IV §rUniversal Circuit", CustomTags.IV_CIRCUITS },
            { GTValues.LuV, "luv_universal_circuit", "§dLuV §rUniversal Circuit", CustomTags.LuV_CIRCUITS },
            { GTValues.ZPM, "zpm_universal_circuit", "§cZPM §rUniversal Circuit", CustomTags.ZPM_CIRCUITS },
            { GTValues.UV, "uv_universal_circuit", "§3UV §rUniversal Circuit", CustomTags.UV_CIRCUITS },
            { GTValues.UHV, "uhv_universal_circuit", "§4UHV §rUniversal Circuit", CustomTags.UHV_CIRCUITS },
            { GTValues.UEV, "uev_universal_circuit", "§2UEV §rUniversal Circuit", CustomTags.UEV_CIRCUITS },
            { GTValues.UIV, "uiv_universal_circuit", "§aUIV §rUniversal Circuit", CustomTags.UIV_CIRCUITS },
            { GTValues.UXV, "uxv_universal_circuit", "§eUXV §rUniversal Circuit", CustomTags.UXV_CIRCUITS },
            { GTValues.OpV, "opv_universal_circuit", "§9OpV §rUniversal Circuit", CustomTags.OpV_CIRCUITS },
    };

    static {
        if (EFConfig.INSTANCE.UniversalCircuits || GTCEu.isDataGen()) {

            for (Object[] data : CIRCUIT_DATA) {
                int tier = (int) data[0];

                String registryName = (String) data[1];
                String displayName = (String) data[2];

                TagKey<Item> tag = (TagKey<Item>) data[3];

                UNIVERSAL_CIRCUITS[tier] = ExtendedFeaturesRegister
                        .item(registryName, Item::new)
                        .lang(displayName)
                        .model((ctx, prov) -> prov.generated(ctx, prov.modLoc("item/universal/" + registryName)))
                        .tag(tag)
                        .register();
            }
        }
    }

    // "isHighTier" filter
    public static List<ItemEntry<Item>> getHighTierCircuits() {
        int[] highTiers = { GTValues.UHV, GTValues.UEV, GTValues.UIV, GTValues.UXV, GTValues.OpV };
        List<ItemEntry<Item>> result = new ArrayList<>();

        for (int tier : highTiers) {
            ItemEntry<Item> entry = UNIVERSAL_CIRCUITS[tier];

            if (entry != null) {
                result.add(entry);
            }
        }
        return result;
    }

    public static void register(IEventBus eventBus) {}
}
