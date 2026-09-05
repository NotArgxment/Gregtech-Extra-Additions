package com.extendedfeatures.client.internal.logic.multiblock;

import com.extendedfeatures.client.internal.logic.machine.ExpandedDataAccessHatch;
import com.extendedfeatures.client.internal.logic.machine.WirelessOpticalHatch;
import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.capability.recipe.FluidRecipeCapability;
import com.gregtechceu.gtceu.api.capability.recipe.IO;
import com.gregtechceu.gtceu.api.machine.IMachineBlockEntity;
import com.gregtechceu.gtceu.api.machine.feature.multiblock.IMultiPart;
import com.gregtechceu.gtceu.api.transfer.fluid.FluidHandlerList;
import com.gregtechceu.gtceu.common.machine.multiblock.electric.research.DataBankMachine;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import lombok.Getter;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

import static com.gregtechceu.gtceu.common.data.GTMaterials.PCBCoolant;
import static com.gregtechceu.gtceu.utils.GTTransferUtils.drainFluidAccountNotifiableList;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.EXECUTE;
import static net.minecraftforge.fluids.capability.IFluidHandler.FluidAction.SIMULATE;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MatrixDataRelayMachine extends DataBankMachine {

    public static final int hatchAmpUsage = 1;
    public static final int expandedDataHatchUsage = 8192;

    public static final int coolantAmount = 144;
    private static final int consuptionInterval = 20;

    private IFluidHandler coolantHandler = new FluidHandlerList(new ArrayList<>());
    private int coolantTickCounter = 0;

    @Getter
    private boolean coolantStarved = false;

    public MatrixDataRelayMachine(IMachineBlockEntity holder) {
        super(holder);
    }

    // Checks for an avaiable input hatch and tried to gather PCB Coolant from it
    @Override
    public void onStructureFormed() {
        super.onStructureFormed();

        if (!isFormed())
            return;

        List<IFluidHandler> coolantContainers = new ArrayList<>();

        Long2ObjectMap<IO> ioMap = getMultiblockState().getMatchContext().getOrCreate("ioMap",
                Long2ObjectMaps::emptyMap);

        for (IMultiPart part : getParts()) {
            IO io = ioMap.getOrDefault(part.self().getPos().asLong(), IO.BOTH);

            if (io == IO.NONE || io == IO.OUT)
                continue;

            for (var handlerList : part.getRecipeHandlers()) {

                if (!handlerList.isValid(io)) continue;
                handlerList.getCapability(FluidRecipeCapability.CAP).stream()
                        .filter(IFluidHandler.class::isInstance)
                        .map(IFluidHandler.class::cast)
                        .forEach(coolantContainers::add);
            }
        }
        this.coolantHandler = new FluidHandlerList(coolantContainers);
        this.coolantTickCounter = 0;
        this.coolantStarved = false;
    }

    @Override
    public void onStructureInvalid() {
        super.onStructureInvalid();
        this.coolantHandler = new FluidHandlerList(new ArrayList<>());
        this.coolantTickCounter = 0;
        this.coolantStarved = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!isFormed()) return;

        if (++coolantTickCounter >= consuptionInterval) {
            coolantTickCounter = 0;
            coolantStarved = !consumeCoolant();
        }

        if (coolantStarved) {
            getRecipeLogic().setWaiting(Component.translatable("gtceu.recipe_logic.insufficient_in")
                    .append(": ").append(FluidRecipeCapability.CAP.getName()));
        }
    }

    // Passive PCB Coolant Draining WHILE working
    private boolean consumeCoolant() {
        FluidStack required = PCBCoolant.getFluid(coolantAmount);
        FluidStack simulated = drainFluidAccountNotifiableList(coolantHandler, required, SIMULATE);
        if (simulated.getAmount() < coolantAmount)
            return false;
        drainFluidAccountNotifiableList(coolantHandler, required, EXECUTE);
            return true;
    }

    // Checks for the W.H tier, and applies an energy consuption of 1A of the next tier
    private long hatchAmpConsuption() {
        long fullConsuption = 0;
        for (IMultiPart part : getParts()) {
            if (!(part instanceof WirelessOpticalHatch hatch)) continue;

            int energyTier = hatch.getWirelessTier().gtTier + 1;
            if (energyTier >= GTValues.V.length) continue;

            fullConsuption += (long) hatchAmpUsage * GTValues.V[energyTier];
        }
        return fullConsuption;
    }

    private long expandedDataHatchConsuption() {
        long fullConsuption = 0;
        for (IMultiPart part : getParts()) {
            if (!(part instanceof ExpandedDataAccessHatch)) continue;

            fullConsuption += expandedDataHatchUsage;
        }
        return fullConsuption;
    }

    // Checks for the total energy usage
    @Override
    protected int calculateEnergyUsage() {
        int baseUsage = super.calculateEnergyUsage();

        long surcharge = hatchAmpConsuption() + expandedDataHatchConsuption();
        if (surcharge == 0)
            return baseUsage;

        long total = baseUsage + surcharge;

        return total > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) total;
    }
}