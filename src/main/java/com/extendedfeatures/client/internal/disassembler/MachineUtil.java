package com.extendedfeatures.client.internal.disassembler;

import com.gregtechceu.gtceu.api.item.MetaMachineItem;
import com.gregtechceu.gtceu.api.machine.MachineDefinition;

import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class MachineUtil {

    public static Optional<Integer> getMachineTier(ItemStack stack) {
        return getMachineDefinition(stack).map(MachineDefinition::getTier);
    }

    public static Optional<MachineDefinition> getMachineDefinition(ItemStack stack) {
        if (stack.getItem() instanceof MetaMachineItem machineItem) {
            return Optional.of(machineItem.getDefinition());
        }
        return Optional.empty();
    }
}
