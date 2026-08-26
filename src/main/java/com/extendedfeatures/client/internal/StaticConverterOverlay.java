package com.extendedfeatures.client.internal;

import com.gregtechceu.gtceu.api.registry.registrate.MachineBuilder;
import com.gregtechceu.gtceu.client.model.machine.overlays.EnergyIOOverlay;
import com.gregtechceu.gtceu.common.data.models.GTMachineModels;

import static com.gregtechceu.gtceu.api.machine.property.GTMachineModelProperties.IS_FE_TO_EU;
import static com.gregtechceu.gtceu.client.model.machine.overlays.EnergyIOOverlay.IN_OVERLAYS_FOR_AMP;
import static com.gregtechceu.gtceu.client.model.machine.overlays.EnergyIOOverlay.OUT_OVERLAYS_FOR_AMP;
import static com.gregtechceu.gtceu.common.data.models.GTMachineModels.TRANSFORMER_LIKE;

public class StaticConverterOverlay {

    public static MachineBuilder.ModelInitializer converterOverlay() {
        return (ctx, prov, builder) -> {
            final EnergyIOOverlay energyIn = IN_OVERLAYS_FOR_AMP.get(64);
            final EnergyIOOverlay energyOut = OUT_OVERLAYS_FOR_AMP.get(64);

            var euToFeModel = prov.models().nested()
                    .parent(prov.models().getExistingFile(TRANSFORMER_LIKE))
                    .texture("overlay_in_io", energyIn.getIoPart())
                    .texture("overlay_in_tinted", energyIn.getTintedPart())
                    .texture("overlay_in_io_emissive", energyIn.getIoPartEmissive())
                    .texture("overlay_out_io_emissive", GTMachineModels.CONVERTER_FE_OUT_EMISSIVE)
                    .texture("overlay_out_io", GTMachineModels.CONVERTER_FE_OUT);
            GTMachineModels.tieredHullTextures(euToFeModel, builder.getOwner().getTier());

            var feToEuModel = prov.models().nested()
                    .parent(prov.models().getExistingFile(TRANSFORMER_LIKE))
                    .texture("overlay_in_io", energyOut.getIoPart())
                    .texture("overlay_in_tinted", energyOut.getTintedPart())
                    .texture("overlay_in_io_emissive", energyOut.getIoPartEmissive())
                    .texture("overlay_out_io_emissive", GTMachineModels.CONVERTER_FE_IN_EMISSIVE)
                    .texture("overlay_out_io", GTMachineModels.CONVERTER_FE_IN);
            GTMachineModels.tieredHullTextures(feToEuModel, builder.getOwner().getTier());

            builder.partialState().with(IS_FE_TO_EU, false).setModel(euToFeModel)
                    .partialState().with(IS_FE_TO_EU, true).setModel(feToEuModel);
        };
    }

}
