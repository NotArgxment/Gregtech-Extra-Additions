package com.extendedfeatures.mixins;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.CreativeModeTabRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

// Mixin to move populated tabs into the gap left by an empty creative tab
@Mixin(value = CreativeModeTabRegistry.class,remap = false)
public class CreativeTabVisibilityMixin {

    @Inject(
            method = "getSortedCreativeModeTabs",
            at = @At("RETURN"), cancellable = true
    )
    private static void extendedfeatures$hideEmptyTabs(CallbackInfoReturnable<List<CreativeModeTab>> cir) {

        List<CreativeModeTab> tabs = new ArrayList<>(cir.getReturnValue());

        tabs.removeIf(tab -> tab.getType() == CreativeModeTab.Type.CATEGORY && tab.getDisplayItems().isEmpty());
        cir.setReturnValue(tabs);
    }

}
