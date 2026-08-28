package com.extendedfeatures.client.internal.logic.machine;

import com.gregtechceu.gtceu.api.capability.*;
import com.gregtechceu.gtceu.api.machine.*;
import com.gregtechceu.gtceu.api.machine.feature.*;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.*;
import com.gregtechceu.gtceu.api.machine.multiblock.*;
import com.gregtechceu.gtceu.common.machine.multiblock.part.*;

import net.minecraft.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

import java.util.Collections;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfigurableCleanroomHatch extends MaintenanceHatchPartMachine {

    private final ICleanroomProvider DUMMY_CLEANROOM;

    public ConfigurableCleanroomHatch(IMachineBlockEntity holder, CleanroomType cleanroomType) {
        super(holder, true);
        DUMMY_CLEANROOM = DummyCleanroom.createForTypes(Collections.singletonList(cleanroomType));
    }

    @Override
    public void addedToController(IMultiController controller) {
        super.addedToController(controller);
        if (controller instanceof ICleanroomReceiver type) {
            type.setCleanroom(DUMMY_CLEANROOM);
        }
    }

    @Override
    public void removedFromController(IMultiController controller) {
        super.removedFromController(controller);
        if (controller instanceof ICleanroomReceiver receiver && receiver.getCleanroom() == DUMMY_CLEANROOM) {
            receiver.setCleanroom(null);
        }
    }

}