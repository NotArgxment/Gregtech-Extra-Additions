package com.extendedfeatures.client.internal.logic.machine;

import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.common.machine.multiblock.part.DataAccessHatchMachine;

public class ExpandedDataAccessHatch extends DataAccessHatchMachine {

    public ExpandedDataAccessHatch(IMachineBlockEntity holder, int tier, boolean isCreative) {
        super(holder, tier, isCreative);
    }

    @Override
    protected int getInventorySize() {
        return 1;
    }

}
