package com.extendedfeatures.init.contents.misc;

import com.gregtechceu.gtceu.api.GTCEuAPI;
import com.gregtechceu.gtceu.api.data.chemical.ChemicalHelper;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.MultiblockShapeInfo;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.gregtechceu.gtceu.api.GTValues.*;
import static com.gregtechceu.gtceu.common.data.GCYMBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMachines.*;
import static com.gregtechceu.gtceu.common.data.GTMaterials.*;
import static com.gregtechceu.gtceu.common.data.machines.GCYMMachines.*;

@MethodsReturnNonnullByDefault
public class ExtendedShapeInfo {

    public static List<MultiblockShapeInfo> RobustAlloyMaterializer(MultiblockMachineDefinition definition) {
        List<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        var builder = MultiblockShapeInfo.builder()
                .aisle("   1@2   ", "   XXX   ", "   XXX   ", "   EEE   ", "   XXX   ", "   XXX   ", "   456   ")
                .aisle(" BBBCBBB ", " XXTTTXX ", " XXTTTXX ", " EETFTEE ", " XXTTTXX ", " XXTTTXX ", " BBBCBBB ")
                .aisle(" BBBCBBB ", " XETTTEX ", " XETTTEX ", " EETFTEE ", " XETTTEX ", " XETTTEX ", " BEECEEB ")
                .aisle("CBBCCCBBC", "XTTTTTTTX", "XTTTTTTTX", "ETTTFTTTE", "XTTTTTTTX", "XTTTTTTTX", "CBECCCEBC")
                .aisle("CCCCECCCC", "XTTTFTTTX", "XTTTFTTTX", "EFFFFFFFE", "XTTTFTTTX", "XTTTFTTTX", "CCCCLCCCC")
                .aisle("CBBCCCBBC", "XTTTTTTTX", "XTTTTTTTX", "ETTTFTTTE", "XTTTTTTTX", "XTTTTTTTX", "CBECCCEBC")
                .aisle(" BBBCBBB ", " XETTTEX ", " XETTTEX ", " EETFTEE ", " XETTTEX ", " XETTTEX ", " BEECEEB ")
                .aisle(" BBBCBBB ", " XXTTTXX ", " XXTTTXX ", " EETFTEE ", " XXTTTXX ", " XXTTTXX ", " BBBCBBB ")
                .aisle("   3CC   ", "   XXX   ", "   XXX   ", "   EEE   ", "   XXX   ", "   XXX   ", "   CCC   ")
                .where('@', definition, Direction.NORTH)
                .where('1', PARALLEL_HATCH[IV], Direction.NORTH)
                .where('2', MAINTENANCE_HATCH, Direction.NORTH)
                .where('3', ENERGY_INPUT_HATCH[LV], Direction.SOUTH)
                .where('4', ITEM_IMPORT_BUS[LV], Direction.NORTH)
                .where('5', FLUID_IMPORT_HATCH[LV], Direction.NORTH)
                .where('6', FLUID_EXPORT_HATCH[LV], Direction.NORTH)
                .where('L', MUFFLER_HATCH[LV], Direction.UP)
                .where('T', Blocks.AIR.defaultBlockState())
                .where('C', CASING_TUNGSTENSTEEL_ROBUST.getDefaultState())
                .where('B', CASING_HIGH_TEMPERATURE_SMELTING.getDefaultState())
                .where('F', CASING_TUNGSTENSTEEL_PIPE.getDefaultState())
                .where('E', HEAT_VENT.getDefaultState());

        GTCEuAPI.HEATING_COILS
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey().getTier()))
                .forEach(coil -> shapeInfo.add(builder
                        .shallowCopy()
                        .where('X', coil.getValue().get())
                        .build()
                ));
        return shapeInfo;
    }

    public static List<MultiblockShapeInfo> LargeCrackingMachine(MultiblockMachineDefinition definition) {
        List<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        var builder = MultiblockShapeInfo.builder()
                .aisle("FIIFIIF", "FIIFIIF", "F12@34F", "FIIFIIF", "FIIFIIF")
                .aisle("FIIFIIF", "FKKFKKF", "FKKDKKF", "FKKFKKF", "FIIFIIF")
                .aisle("FIIFIIF", "FKKDKKF", "D##D##D", "FKKDKKF", "FIIFIIF")
                .aisle("5IIFIIF", "FKKFKKF", "FKKDKKF", "FKKFKKF", "FIIFIIF")
                .aisle("5IIFIIF", "FIIFIIF", "FFFYFFF", "FIIFIIF", "FIIFIIF")
                .where('@', definition, Direction.NORTH)
                .where('1', FLUID_IMPORT_HATCH[LV], Direction.NORTH)
                .where('2', PARALLEL_HATCH[IV], Direction.NORTH)
                .where('3', MAINTENANCE_HATCH, Direction.NORTH)
                .where('4', FLUID_EXPORT_HATCH[LV], Direction.NORTH)
                .where('5', ENERGY_INPUT_HATCH[LV], Direction.WEST)
                .where('Y', MUFFLER_HATCH[LV], Direction.SOUTH)
                .where('#', Blocks.AIR.defaultBlockState())
                .where('I', CASING_LAMINATED_GLASS.getDefaultState())
                .where('F', CASING_TUNGSTENSTEEL_ROBUST.getDefaultState())
                .where('D', CASING_TUNGSTENSTEEL_PIPE.getDefaultState());

        GTCEuAPI.HEATING_COILS
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey().getTier()))
                .forEach(coil -> shapeInfo.add(builder
                        .shallowCopy()
                        .where('K', coil.getValue().get())
                        .build()
                ));
        return shapeInfo;
    }

    public static List<MultiblockShapeInfo> LargePyrolysisOven(MultiblockMachineDefinition definition) {
        List<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        var builder = MultiblockShapeInfo.builder()
                .aisle("  1@2  ", "  HHH  ", "  HHH  ", "  HHH  ", "  4E5  ")
                .aisle(" FGGGF ", " GKKKG ", " GKKKG ", " GKKKG ", " FGGGF ")
                .aisle("EGEEEGE", "HK###KH", "HK###KH", "HK###KH", "EGEEEGE")
                .aisle("EGEEEGE", "HK#O#KH", "HK#O#KH", "HK#O#KH", "EGEYEGE")
                .aisle("EGEEEGE", "HK###KH", "HK###KH", "HK###KH", "EGEEEGE")
                .aisle(" FGGGF ", " GKKKG ", " GKKKG ", " GKKKG ", " FGGGF ")
                .aisle("  33E  ", "  HHH  ", "  HHH  ", "  HHH  ", "  6E7  ")
                .where('@', definition, Direction.NORTH)
                .where('1', PARALLEL_HATCH[IV], Direction.NORTH)
                .where('2', MAINTENANCE_HATCH, Direction.NORTH)
                .where('3', ENERGY_INPUT_HATCH[LV], Direction.SOUTH)
                .where('4', ITEM_IMPORT_BUS[LV], Direction.NORTH)
                .where('5', FLUID_IMPORT_HATCH[LV], Direction.NORTH)
                .where('6', ITEM_EXPORT_BUS[LV], Direction.SOUTH)
                .where('7', FLUID_EXPORT_HATCH[LV], Direction.SOUTH)
                .where('Y', MUFFLER_HATCH[LV], Direction.UP)
                .where('#', Blocks.AIR.defaultBlockState())
                .where('E', CASING_TUNGSTENSTEEL_ROBUST.getDefaultState())
                .where('H', CASING_LAMINATED_GLASS.getDefaultState())
                .where('F', HEAT_VENT.getDefaultState())
                .where('G', CASING_HIGH_TEMPERATURE_SMELTING.getDefaultState())
                .where('O', CASING_TUNGSTENSTEEL_PIPE.getDefaultState());

        GTCEuAPI.HEATING_COILS
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey().getTier()))
                .forEach(coil -> shapeInfo.add(builder
                        .shallowCopy()
                        .where('K', coil.getValue().get())
                        .build()
                ));
        return shapeInfo;
    }

    public static List<MultiblockShapeInfo> SynthesisVessel(MultiblockMachineDefinition definition) {
        List<MultiblockShapeInfo> shapeInfo = new ArrayList<>();
        var builder = MultiblockShapeInfo.builder()
                .aisle("#F3D4F#", "#FD@DF#", "#F516F#")
                .aisle("FNDDDNF", "FCK#KCF", "FNDDDNF")
                .aisle("DDDDDDD", "DKK#KKD", "DDDDDDD")
                .aisle("DDDDDDD", "D##K##D", "DDDDDDD")
                .aisle("DDDDDDD", "DKK#KKD", "DDDDDDD")
                .aisle("FNDDDNF", "FCK#KCF", "FNDDDNF")
                .aisle("#F22DF#", "#FDDDF#", "#FDDDF#")
                .where('@', definition, Direction.NORTH)
                .where('1', MAINTENANCE_HATCH, Direction.NORTH)
                .where('2', ENERGY_INPUT_HATCH[LV], Direction.SOUTH)
                .where('3', ITEM_IMPORT_BUS[LV], Direction.NORTH)
                .where('4', FLUID_IMPORT_HATCH[LV], Direction.NORTH)
                .where('5', ITEM_EXPORT_BUS[LV], Direction.NORTH)
                .where('6', FLUID_EXPORT_HATCH[LV], Direction.NORTH)
                .where('#', Blocks.AIR.defaultBlockState())
                .where('N', HEAT_VENT.getDefaultState())
                .where('K', CASING_POLYTETRAFLUOROETHYLENE_PIPE.getDefaultState())
                .where('D', CASING_PTFE_INERT.getDefaultState())
                .where('F', ChemicalHelper.getBlock(TagPrefix.frameGt, Polytetrafluoroethylene));

        GTCEuAPI.HEATING_COILS
                .entrySet()
                .stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey().getTier()))
                .forEach(coil -> shapeInfo.add(builder
                        .shallowCopy()
                        .where('C', coil.getValue().get())
                        .build()
                ));
        return shapeInfo;
    }

}