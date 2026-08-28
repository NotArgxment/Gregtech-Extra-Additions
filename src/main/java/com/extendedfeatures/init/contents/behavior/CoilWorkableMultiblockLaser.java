package com.extendedfeatures.init.contents.behavior;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.level.block.Block;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class CoilWorkableMultiblockLaser extends CoilWorkableElectricMultiblockMachine {

    public CoilWorkableMultiblockLaser(IMachineBlockEntity holder) {
        super(holder);
    }

    /*
     * xor logic:
     * allows exclusively 1 laser hatch OR exclusively 1-2 energy hatches,
     * so there is no way to put both on the same structure,
     * for now only its being used only on the Robust Alloy Materializer
     */

    @Override
    public void onStructureFormed() {
        super.onStructureFormed();

        int laserCount = 0;
        int energyCount = 0;

        for (IMultiPart part : getParts()) {
            Block block = part.self().getBlockState().getBlock();
            if (PartAbility.INPUT_LASER.isApplicable(block)) laserCount++;
            if (PartAbility.INPUT_ENERGY.isApplicable(block)) energyCount++;
        }

        boolean validLaser = (laserCount == 1) && (energyCount == 0);
        boolean validEnergy = (energyCount >= 1) && (energyCount <= 2) && (laserCount == 0);

        if (!validLaser && !validEnergy) {
            onStructureInvalid();
        }
    }
}
