package com.extendedfeatures.init.contents.electric;

import com.extendedfeatures.CreativeTabs;
import com.extendedfeatures.ExtendedFeaturesCore;
import com.extendedfeatures.client.EFDisplayHelper;
import com.extendedfeatures.client.EFRecipeTypes;
import com.extendedfeatures.client.EFTooltipHelper;
import com.extendedfeatures.client.internal.ConfigClass;
import com.extendedfeatures.client.internal.logic.machine.ExpandedAssemblyLineMachine;
import com.extendedfeatures.client.internal.logic.multiblock.DisassemblerMachine;
import com.extendedfeatures.client.internal.logic.multiblock.MatrixDataRelayMachine;
import com.extendedfeatures.init.contents.behavior.CoilWorkableMultiblockLaser;
import com.extendedfeatures.init.contents.misc.EFShapeInfosHelper;
import com.extendedfeatures.init.contents.misc.ExtendedAbilities;

import com.gregtechceu.gtceu.GTCEu;
import com.gregtechceu.gtceu.api.data.RotationState;
import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.machine.multiblock.CoilWorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.machine.multiblock.PartAbility;
import com.gregtechceu.gtceu.api.machine.multiblock.WorkableElectricMultiblockMachine;
import com.gregtechceu.gtceu.api.pattern.FactoryBlockPattern;
import com.gregtechceu.gtceu.api.pattern.Predicates;
import com.gregtechceu.gtceu.api.pattern.util.RelativeDirection;
import com.gregtechceu.gtceu.common.data.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;

import static com.extendedfeatures.ExtendedFeaturesCore.ExtendedFeaturesRegister;
import static com.extendedfeatures.client.EFRecipeTypes.*;
import static com.extendedfeatures.init.contents.modifiers.CustomRecipeModifiers.MACHINE_PARALLEL;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.*;
import static com.gregtechceu.gtceu.api.pattern.Predicates.*;
import static com.gregtechceu.gtceu.common.data.GTBlocks.*;
import static com.gregtechceu.gtceu.common.data.GTMaterialItems.MATERIAL_ITEMS;
import static com.gregtechceu.gtceu.common.data.GTMaterials.Ash;
import static com.gregtechceu.gtceu.common.data.GTRecipeModifiers.*;

public class Multiblocks {

    public static MultiblockMachineDefinition ROBUST_ALLOY_MATERIALIZER = null;
    public static MultiblockMachineDefinition LARGE_CRACKING_MACHINE = null;
    public static MultiblockMachineDefinition SYNTHESIS_VESSEL = null;
    public static MultiblockMachineDefinition LARGE_PYROLYSE_OVEN = null;
    public static MultiblockMachineDefinition EXPANDED_ASSEMBLY_LINE = null;
    public static MultiblockMachineDefinition ROCK_PROCESSING_PLANT = null;
    public static MultiblockMachineDefinition INDUSTRIAL_GREENHOUSE = null;
    public static MultiblockMachineDefinition TREE_GROWING_CHAMBER = null;
    public static MultiblockMachineDefinition DISASSEMBLER = null;
    public static MultiblockMachineDefinition LARGE_GAS_COLLECTOR = null;
    public static MultiblockMachineDefinition MATRIX_DATA_RELAY = null;
    public static MultiblockMachineDefinition EXPANDABLE_ACTIVE_TRANSFORMER = null;

    static {
        ExtendedFeaturesRegister.creativeModeTab(() -> CreativeTabs.MULTIBLOCKS_TAB);
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.RobustAlloyMaterializer || GTCEu.isDataGen()) {
            ROBUST_ALLOY_MATERIALIZER = ExtendedFeaturesRegister
                    .multiblock("robust_alloy_materializer", CoilWorkableMultiblockLaser::new)
                    .tooltips(EFTooltipHelper.RAMTooltip)
                    .tooltipBuilder(EFTooltipHelper.RAMTooltipExtra)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GCYMRecipeTypes.ALLOY_BLAST_RECIPES)
                    .recipeModifiers(
                            GTRecipeModifiers.PARALLEL_HATCH,
                            GTRecipeModifiers.OC_NON_PERFECT,
                            GTRecipeModifiers.BATCH_MODE,
                            GTRecipeModifiers::ebfOverclock)
                    .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("   CCC   ", "   XXX   ", "   XXX   ", "   EEE   ", "   XXX   ", "   XXX   ", "   CCC   ")
                            .aisle(" BBBCBBB ", " XXTTTXX ", " XXTTTXX ", " EETFTEE ", " XXTTTXX ", " XXTTTXX ", " BBBCBBB ")
                            .aisle(" BBBCBBB ", " XETTTEX ", " XETTTEX ", " EETFTEE ", " XETTTEX ", " XETTTEX ", " BEECEEB ")
                            .aisle("CBBCCCBBC", "XTTTTTTTX", "XTTTTTTTX", "ETTTFTTTE", "XTTTTTTTX", "XTTTTTTTX", "CBECCCEBC")
                            .aisle("CCCCECCCC", "XTTTFTTTX", "XTTTFTTTX", "EFFFFFFFE", "XTTTFTTTX", "XTTTFTTTX", "CCCCLCCCC")
                            .aisle("CBBCCCBBC", "XTTTTTTTX", "XTTTTTTTX", "ETTTFTTTE", "XTTTTTTTX", "XTTTTTTTX", "CBECCCEBC")
                            .aisle(" BBBCBBB ", " XETTTEX ", " XETTTEX ", " EETFTEE ", " XETTTEX ", " XETTTEX ", " BEECEEB ")
                            .aisle(" BBBCBBB ", " XXTTTXX ", " XXTTTXX ", " EETFTEE ", " XXTTTXX ", " XXTTTXX ", " BBBCBBB ")
                            .aisle("   C@C   ", "   XXX   ", "   XXX   ", "   EEE   ", "   XXX   ", "   XXX   ", "   CCC   ")
                            .where('@', controller(blocks(definition.get())))
                            .where('X', heatingCoils())
                            .where('T', air())
                            .where(' ', any())
                            .where('C', blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS, PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_FLUIDS))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(1).setPreviewCount(1)
                                            .or(Predicates.abilities(PartAbility.INPUT_LASER).setMaxGlobalLimited(1).setPreviewCount(1)))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1)))
                            .where('B', Predicates.blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get()))
                            .where('F', Predicates.blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                            .where('E', Predicates.blocks(GCYMBlocks.HEAT_VENT.get()))
                            .where('L', ability(PartAbility.MUFFLER).setExactLimit(1))
                            .build())
                    .recoveryItems(() -> new ItemLike[]{
                            MATERIAL_ITEMS.get(dustTiny, Ash)
                    })
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                            GTCEu.id("block/multiblock/gcym/blast_alloy_smelter")
                    )
                    .additionalDisplay(EFDisplayHelper.EBFDisplay)
                    .shapeInfos(EFShapeInfosHelper::RobustAlloyMaterializer)
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.LargeCrackingMachine || GTCEu.isDataGen()) {
            LARGE_CRACKING_MACHINE = ExtendedFeaturesRegister
                    .multiblock("large_cracking_machine", CoilWorkableElectricMultiblockMachine::new)
                    .tooltips(EFTooltipHelper.LCMTooltip)
                    .tooltipBuilder(EFTooltipHelper.ParallelTooltip)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GTRecipeTypes.CRACKING_RECIPES)
                    .recipeModifiers(
                            GTRecipeModifiers.PARALLEL_HATCH,
                            GTRecipeModifiers.OC_NON_PERFECT,
                            GTRecipeModifiers.BATCH_MODE,
                            GTRecipeModifiers::crackerOverclock)
                    .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("FIIFIIF", "FIIFIIF", "FFFYFFF", "FIIFIIF", "FIIFIIF")
                            .aisle("FIIFIIF", "FKKFKKF", "FKKDKKF", "FKKFKKF", "FIIFIIF")
                            .aisle("FIIFIIF", "FKKDKKF", "D##D##D", "FKKDKKF", "FIIFIIF")
                            .aisle("FIIFIIF", "FKKFKKF", "FKKDKKF", "FKKFKKF", "FIIFIIF")
                            .aisle("FIIFIIF", "FIIFIIF", "FFF@FFF", "FIIFIIF", "FIIFIIF")
                            .where('@', controller(blocks(definition.get())))
                            .where('K', heatingCoils())
                            .where('#', air())
                            .where(' ', any())
                            .where('I', blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where('D', blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                            .where('F', blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                                    .or(Predicates.abilities(
                                            PartAbility.IMPORT_ITEMS,
                                            PartAbility.IMPORT_FLUIDS,
                                            PartAbility.EXPORT_FLUIDS))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1)))
                            .where('Y', ability(PartAbility.MUFFLER).setExactLimit(1))
                            .build())
                    .recoveryItems(() -> new ItemLike[]{
                            MATERIAL_ITEMS.get(dustTiny, Ash)
                    })
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                            GTCEu.id("block/multiblock/cracking_unit")
                    )
                    .additionalDisplay(EFDisplayHelper.CrackerDisplay)
                    .shapeInfos(EFShapeInfosHelper::LargeCrackingMachine)
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.SynthesisVessel || GTCEu.isDataGen()) {
            SYNTHESIS_VESSEL = ExtendedFeaturesRegister
                    .multiblock("synthesis_vessel", WorkableElectricMultiblockMachine::new)
                    .tooltips(EFTooltipHelper.SVTooltip)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeTypes(EFRecipeTypes.CHEMICAL_REDUCTION)
                    .recipeModifiers(
                            GTRecipeModifiers.OC_PERFECT,
                            GTRecipeModifiers.BATCH_MODE)
                    .appearanceBlock(GTBlocks.CASING_PTFE_INERT)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle(" FDDDF ", " FDDDF ", " FDDDF ")
                            .aisle("FNDDDNF", "FCK#KCF", "FNDDDNF")
                            .aisle("DDDDDDD", "DKK#KKD", "DDDDDDD")
                            .aisle("DDDDDDD", "D##K##D", "DDDDDDD")
                            .aisle("DDDDDDD", "DKK#KKD", "DDDDDDD")
                            .aisle("FNDDDNF", "FCK#KCF", "FNDDDNF")
                            .aisle(" FDDDF ", " FD@DF ", " FDDDF ")
                            .where('@', controller(blocks(definition.get())))
                            .where('#', air())
                            .where(' ', any())
                            .where('F', frames(GTMaterials.Polytetrafluoroethylene))
                            .where('K', blocks(GTBlocks.CASING_POLYTETRAFLUOROETHYLENE_PIPE.get()))
                            .where('C', heatingCoils())
                            .where('D', blocks(GTBlocks.CASING_PTFE_INERT.get())
                                    .or(Predicates.abilities(
                                            PartAbility.IMPORT_ITEMS, PartAbility.EXPORT_ITEMS,
                                            PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_FLUIDS))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .where('N', blocks(GCYMBlocks.HEAT_VENT.get()))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_inert_ptfe"),
                            GTCEu.id("block/multiblock/large_chemical_reactor"))
                    .shapeInfos(EFShapeInfosHelper::SynthesisVessel)
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.LargePyrolysisOven || GTCEu.isDataGen()) {
            LARGE_PYROLYSE_OVEN = ExtendedFeaturesRegister
                    .multiblock("large_pyrolysis_oven", CoilWorkableElectricMultiblockMachine::new)
                    .tooltips(EFTooltipHelper.LPOTooltip)
                    .tooltipBuilder(EFTooltipHelper.ParallelTooltip)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GTRecipeTypes.PYROLYSE_RECIPES)
                    .recipeModifiers(
                            GTRecipeModifiers.PARALLEL_HATCH,
                            GTRecipeModifiers.OC_PERFECT,
                            GTRecipeModifiers::pyrolyseOvenOverclock)
                    .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("  EEE  ", "  HHH  ", "  HHH  ", "  HHH  ", "  EEE  ")
                            .aisle(" FGGGF ", " GKKKG ", " GKKKG ", " GKKKG ", " FGGGF ")
                            .aisle("EGEEEGE", "HK###KH", "HK###KH", "HK###KH", "EGEEEGE")
                            .aisle("EGEEEGE", "HK#O#KH", "HK#O#KH", "HK#O#KH", "EGEYEGE")
                            .aisle("EGEEEGE", "HK###KH", "HK###KH", "HK###KH", "EGEEEGE")
                            .aisle(" FGGGF ", " GKKKG ", " GKKKG ", " GKKKG ", " FGGGF ")
                            .aisle("  E@E  ", "  HHH  ", "  HHH  ", "  HHH  ", "  EEE  ")
                            .where('@', controller(blocks(definition.get())))
                            .where('#', air())
                            .where(' ', any())
                            .where('K', heatingCoils())
                            .where('G', blocks(GCYMBlocks.CASING_HIGH_TEMPERATURE_SMELTING.get()))
                            .where('F', blocks(GCYMBlocks.HEAT_VENT.get()))
                            .where('O', blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                            .where('H', blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where('E', blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                                    .or(Predicates.abilities(
                                            PartAbility.IMPORT_ITEMS, PartAbility.EXPORT_ITEMS,
                                            PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_FLUIDS))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1)))
                            .where('Y', ability(PartAbility.MUFFLER).setExactLimit(1))
                            .build())
                    .recoveryItems(() -> new ItemLike[]{
                            MATERIAL_ITEMS.get(dustTiny, Ash)
                    })
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                            ExtendedFeaturesCore.id("block/multiblock/pyrolyse_oven")
                    )
                    .additionalDisplay(EFDisplayHelper.PyroDisplay)
                    .shapeInfos(EFShapeInfosHelper::LargePyrolysisOven)
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.ExpandedAssemblyLine || GTCEu.isDataGen()) {
            EXPANDED_ASSEMBLY_LINE = ExtendedFeaturesRegister
                    .multiblock("compact_assembly_line", ExpandedAssemblyLineMachine::new)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GTRecipeTypes.ASSEMBLY_LINE_RECIPES)
                    .recipeModifiers(
                            GTRecipeModifiers.PARALLEL_HATCH,
                            GTRecipeModifiers.OC_NON_PERFECT)
                    .appearanceBlock(CASING_STEEL_SOLID)
                    // RIGHT, UP and BACK are required to allow terminal to build the multiblock in the correct way
                    .pattern(definition -> FactoryBlockPattern.start(RelativeDirection.RIGHT, RelativeDirection.UP, RelativeDirection.BACK)
                            .aisle("EE@EE", "RLKLR", "HHEHH")
                            .aisle("EEDEE", "RLKLR", "HHEHH").setRepeatable(4, 16)
                            .aisle("EENEE", "RLKLR", "HHEHH")
                            .where('@', controller(blocks(definition.get())))
                            .where('E', blocks(GTBlocks.CASING_STEEL_SOLID.get())
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY))
                                    .or(Predicates.abilities(PartAbility.IMPORT_FLUIDS, PartAbility.IMPORT_FLUIDS_4X))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                                    .or(dataHatchPredicate(blocks(GTBlocks.CASING_STEEL_SOLID.get())))
                            )
                            .where('L', blocks(GTBlocks.CASING_ASSEMBLY_CONTROL.get()))
                            .where('K', blocks(GTBlocks.CASING_ASSEMBLY_LINE.get()))
                            .where('H', blocks(GTBlocks.CASING_GRATE.get()))
                            .where('R', blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where('D', Predicates.abilities(PartAbility.IMPORT_ITEMS))
                            .where('N', Predicates.abilities(PartAbility.EXPORT_ITEMS))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                            GTCEu.id("block/multiblock/assembly_line"))
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.RockProcessingPlant || GTCEu.isDataGen()) {
            ROCK_PROCESSING_PLANT = ExtendedFeaturesRegister
                    .multiblock("rock_processing_plant", WorkableElectricMultiblockMachine::new)
                    .tooltips(EFTooltipHelper.RPPTooltip)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeTypes(EFRecipeTypes.ROCK_PROCESSING_RECIPES)
                    .recipeModifiers(
                            GTRecipeModifiers.OC_NON_PERFECT,
                            GTRecipeModifiers.BATCH_MODE)
                    .appearanceBlock(GCYMBlocks.CASING_SECURE_MACERATION)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("DDDDDDD", "DDDDDDD", "DDDDDDD", "DDDDDDD", "       ")
                            .aisle("DDDDDDD", "DJDJDJD", "DJDJDJD", "DJDJDJD", "       ")
                            .aisle("DDDDDDD", "DJDJDJD", "DJDJDJD", "DJDJDJD", "       ")
                            .aisle("DDDDDDD", "DJDJDJD", "DJDJDJD", "DJDJDJD", "       ")
                            .aisle("DDDDDDD", "DDDIDDD", "DDDDDDD", "DDDDDDD", "       ")
                            .aisle("       ", "   I   ", "       ", "       ", "       ")
                            .aisle("  CCC  ", "  CIC  ", "  CCC  ", "  CCC  ", "  CCC  ")
                            .aisle(" CCCCC ", " C#E#C ", " CFFFC ", " C###C ", " CGGGC ")
                            .aisle(" CCCCC ", " CEEEC ", " CFFFC ", " C###C ", " CGGGC ")
                            .aisle("CCCCCCC", "C##E##C", "CFFFFFC", "C#####C", "CGGGGGC")
                            .aisle("CCCCCCC", "CEEEEEC", "HFFFFFH", "C#####C", "CGGGGGC")
                            .aisle("CCCCCCC", "C##E##C", "CFFFFFC", "C#####C", "CGGGGGC")
                            .aisle(" CCCCC ", " CEEEC ", " CFFFC ", " C###C ", " CGGGC ")
                            .aisle(" CCCCC ", " C#E#C ", " CFFFC ", " C###C ", " CGGGC ")
                            .aisle("  CCC  ", "  C@C  ", "  CCC  ", "  CCC  ", "  CCC  ")
                            .where('@', controller(blocks(definition.get())))
                            .where(' ', any())
                            .where('#', air())
                            .where('E', blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                            .where('I', blocks(GTBlocks.LD_ITEM_PIPE.get()))
                            .where('D', blocks(GCYMBlocks.CASING_NONCONDUCTING.get()))
                            .where('J', blocks(GCYMBlocks.ELECTROLYTIC_CELL.get()))
                            .where('F', blocks(GCYMBlocks.CRUSHING_WHEELS.get()))
                            .where('G', blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where('H', abilities(PartAbility.ROTOR_HOLDER))
                            .where('C', blocks(GCYMBlocks.CASING_SECURE_MACERATION.get())
                                    .or(Predicates.abilities(
                                            PartAbility.IMPORT_ITEMS,
                                            PartAbility.IMPORT_FLUIDS,
                                            PartAbility.EXPORT_FLUIDS,
                                            PartAbility.EXPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/gcym/secure_maceration_casing"),
                            GTCEu.id("block/multiblock/gcym/large_maceration_tower"))
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.IndustrialGreenhouse || GTCEu.isDataGen()) {
            INDUSTRIAL_GREENHOUSE = ExtendedFeaturesRegister
                    .multiblock("industrial_greenhouse", WorkableElectricMultiblockMachine::new)
                    .tooltips(EFTooltipHelper.IGTooltip)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeTypes(GREENHOUSE_CROPS, GREENHOUSE_WOOD)
                    .recipeModifiers(
                            MACHINE_PARALLEL(8),
                            GTRecipeModifiers.OC_NON_PERFECT,
                            GTRecipeModifiers.BATCH_MODE)
                    .appearanceBlock(CASING_STEEL_SOLID)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("    BBB    ", "    DDD    ", "    EDE    ", "    EDE    ", "    EDE    ", "    EDE    ", "    EDE    ", "    EDE    ", "    EDE    ", "           ", "           ", "           ")
                            .aisle("  BBDDDBB  ", "  DDCCCDD  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "    EDE    ", "    EDE    ", "           ")
                            .aisle(" BDDDDDDDB ", " DCCCCCCCD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", "  DE###ED  ", "  DEE#EED  ", "     D     ")
                            .aisle(" BDDDDDDDB ", " DCCGGGCCD ", " E#######E ", " E#######E ", " E#######E ", " E#######E ", " E##HHH##E ", " E##HHH##E ", " E##HHH##E ", "  E#####E  ", "  E#####E  ", "   DEDED   ")
                            .aisle("BDDDDDDDDDB", "DCCGGGGGCCD", "E###III###E", "E####I####E", "E#########E", "E####H####E", "E##HHHHH##E", "E##HHHHH##E", "E##HHHHH##E", " E###H###E ", " EE#####EE ", "   EDDDE   ")
                            .aisle("BDDDDDDDDDB", "DCCGGGGGCCD", "D###III###D", "D###III###D", "D####I####D", "D###HIH###D", "D##HHIHH##D", "D##HHIHH##D", "D##HHIHH##D", " D##HHH##D ", " D#######D ", "  DDDDDDD  ")
                            .aisle("BDDDDDDDDDB", "DCCGGGGGCCD", "E###III###E", "E####I####E", "E#########E", "E####H####E", "E##HHHHH##E", "E##HHHHH##E", "E##HHHHH##E", " E###H###E ", " EE#####EE ", "   EDDDE   ")
                            .aisle(" BDDDDDDDB ", " DCCGGGCCD ", " E#######E ", " E#######E ", " E#######E ", " E#######E ", " E##HHH##E ", " E##HHH##E ", " E##HHH##E ", "  E#####E  ", "  E#####E  ", "   DEDED   ")
                            .aisle(" BDDDDDDDB ", " DCCCCCCCD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", "  DE###ED  ", "  DEE#EED  ", "     D     ")
                            .aisle("  BBDDDBB  ", "  DDCCCDD  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "    EDE    ", "    EDE    ", "           ")
                            .aisle("    BBB    ", "    D@D    ", "    EDE    ", "    EDE    ", "    EDE    ", "    EDE    ", "    EDE    ", "    EDE    ", "    EDE    ", "           ", "           ", "           ")
                            .where('@', controller(blocks(definition.get())))
                            .where(' ', any())
                            .where('#', air())
                            .where('C', blocks(Blocks.GRASS_BLOCK))
                            .where('G', blocks(Blocks.ROOTED_DIRT))
                            .where('I', blocks(Blocks.OAK_WOOD))
                            .where('H', blocks(Blocks.OAK_LEAVES))
                            .where('B', blocks(GTBlocks.FIREBOX_STEEL.get()))
                            .where('E', blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                            .where('F', frames(GTMaterials.Steel))
                            .where('D', blocks(CASING_STEEL_SOLID.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS, PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_solid_steel"),
                            GTCEu.id("block/multiblock/fusion_reactor"))
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.TreeGrowingChamber || GTCEu.isDataGen()) {
            TREE_GROWING_CHAMBER = ExtendedFeaturesRegister
                    .multiblock("tree_growing_chamber", WorkableElectricMultiblockMachine::new)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .tooltips(EFTooltipHelper.TGCTooltip)
                    .tooltipBuilder(EFTooltipHelper.ParallelTooltip)
                    .recipeTypes(
                            EFRecipeTypes.GREENHOUSE_CROPS,
                            EFRecipeTypes.GREENHOUSE_WOOD)
                    .recipeModifiers(
                            GTRecipeModifiers.PARALLEL_HATCH,
                            GTRecipeModifiers.OC_NON_PERFECT,
                            GTRecipeModifiers.BATCH_MODE)
                    .appearanceBlock(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("    BBB    ", "    DDD    ", "    EDE    ", "    EDE    ", "    EKE    ", "    EKE    ", "    EKE    ", "    EDE    ", "    EDE    ", "           ", "           ", "           ")
                            .aisle("  BBDDDBB  ", "  DDCCCDD  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "    EDE    ", "    EDE    ", "           ")
                            .aisle(" BDDDDDDDB ", " DCCCCCCCD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", "  DE###ED  ", "  DEE#EED  ", "     D     ")
                            .aisle(" BDDDDDDDB ", " DCCGGGCCD ", " E#######E ", " E#######E ", " E#######E ", " E#######E ", " E##HHH##E ", " E##HHH##E ", " E##HHH##E ", "  E#####E  ", "  E#####E  ", "   DEDED   ")
                            .aisle("BDDDDDDDDDB", "DCCGGGGGCCD", "E###III###E", "E####I####E", "E#########E", "E####H####E", "E##HHHHH##E", "E##HHHHH##E", "E##HHHHH##E", " E###H###E ", " EE#####EE ", "   EDDDE   ")
                            .aisle("BDDDDDDDDDB", "DCCGGGGGCCD", "D###III###D", "D###III###D", "K####I####K", "K###HIH###K", "K##HHIHH##K", "D##HHIHH##D", "D##HHIHH##D", " D##HHH##D ", " D#######D ", "  DDDDDDD  ")
                            .aisle("BDDDDDDDDDB", "DCCGGGGGCCD", "E###III###E", "E####I####E", "E#########E", "E####H####E", "E##HHHHH##E", "E##HHHHH##E", "E##HHHHH##E", " E###H###E ", " EE#####EE ", "   EDDDE   ")
                            .aisle(" BDDDDDDDB ", " DCCGGGCCD ", " E#######E ", " E#######E ", " E#######E ", " E#######E ", " E##HHH##E ", " E##HHH##E ", " E##HHH##E ", "  E#####E  ", "  E#####E  ", "   DEDED   ")
                            .aisle(" BDDDDDDDB ", " DCCCCCCCD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", " DF#####FD ", "  DE###ED  ", "  DEE#EED  ", "     D     ")
                            .aisle("  BBDDDBB  ", "  DDCCCDD  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "  DE###ED  ", "    EDE    ", "    EDE    ", "           ")
                            .aisle("    BBB    ", "    D@D    ", "    EDE    ", "    EDE    ", "    EKE    ", "    EKE    ", "    EKE    ", "    EDE    ", "    EDE    ", "           ", "           ", "           ")
                            .where('@', controller(blocks(definition.get())))
                            .where(' ', any())
                            .where('#', air())
                            .where('C', blocks(Blocks.GRASS_BLOCK))
                            .where('G', blocks(Blocks.ROOTED_DIRT))
                            .where('I', blocks(Blocks.OAK_WOOD))
                            .where('H', blocks(Blocks.OAK_LEAVES))
                            .where('K', blocks(GTBlocks.CASING_EXTREME_ENGINE_INTAKE.get()))
                            .where('B', blocks(GTBlocks.FIREBOX_TUNGSTENSTEEL.get()))
                            .where('E', blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where('F', frames(GTMaterials.TungstenSteel))
                            .where('D', blocks(GTBlocks.CASING_TUNGSTENSTEEL_ROBUST.get())
                                    .or(Predicates.abilities(PartAbility.IMPORT_ITEMS, PartAbility.IMPORT_FLUIDS, PartAbility.EXPORT_ITEMS))
                                    .or(Predicates.abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(Predicates.abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1)))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/solid/machine_casing_robust_tungstensteel"),
                            GTCEu.id("block/multiblock/fusion_reactor"))
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.Disassembler || GTCEu.isDataGen()) {
            DISASSEMBLER = ExtendedFeaturesRegister
                    .multiblock("disassembler", DisassemblerMachine::new)
                    .tooltips(EFTooltipHelper.UDMTooltip)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeTypes(
                            EFRecipeTypes.DISASSEMBLER_MACHINES,
                            EFRecipeTypes.DISASSEMBER_COMPONENTS)
                    .recipeModifiers(OC_NON_PERFECT)
                    .appearanceBlock(GCYMBlocks.CASING_LARGE_SCALE_ASSEMBLING)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("OOOOOOO", "OOOOOOO", "OOOOOOO")
                            .aisle("OOOOOOO", "OKOKOKO", "ODODODO")
                            .aisle("OOOOOOO", "OKOKOKO", "ODODODO")
                            .aisle("OOOOOOO", "OGOKOGO", "OOODOOO")
                            .aisle("  OOO  ", "  O@O  ", "  OOO  ")
                            .where('@', controller(blocks(definition.get())))
                            .where('#', air())
                            .where('D', blocks(GTBlocks.CASING_GRATE.get()))
                            .where('G', blocks(GTBlocks.CASING_TEMPERED_GLASS.get()))
                            .where('K', blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                            .where('O', blocks(GCYMBlocks.CASING_LARGE_SCALE_ASSEMBLING.get())
                                    .or(Predicates.abilities(
                                            PartAbility.IMPORT_ITEMS, PartAbility.EXPORT_ITEMS,
                                            PartAbility.EXPORT_FLUIDS, PartAbility.INPUT_ENERGY,
                                            PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                                    .or(Predicates.abilities(PartAbility.MAINTENANCE).setExactLimit(1)))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/gcym/large_scale_assembling_casing"),
                            GTCEu.id("block/multiblock/gcym/large_assembler"))
                    .register();
        }
    }

    static {
        if (ConfigClass.INSTANCE.Multiblocks.LargeGasCollector || GTCEu.isDataGen()) {
            LARGE_GAS_COLLECTOR = ExtendedFeaturesRegister
                    .multiblock("large_gas_collector", WorkableElectricMultiblockMachine::new)
                    .tooltips(EFTooltipHelper.LGCTooltip)
                    .tooltipBuilder(EFTooltipHelper.ParallelTooltip)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(EFRecipeTypes.AIR_COLLECTOR)
                    .recipeModifiers(PARALLEL_HATCH, OC_NON_PERFECT)
                    .appearanceBlock(GCYMBlocks.CASING_CORROSION_PROOF)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("ARRRA", " RPR ", "ARRRA")
                            .aisle("AAAAA", "ADEDA", "AFAFA")
                            .aisle("AAAAA", "BDEDB", "AAAAA")
                            .aisle("AAAAA", "ADEDA", "AFAFA")
                            .aisle("AAAAA", " A@A ", "AAAAA")
                            .where('@', controller(blocks(definition.get())))
                            .where(" ", any())
                            .where("A", blocks(GCYMBlocks.CASING_CORROSION_PROOF.get())
                                    .or(abilities(PartAbility.IMPORT_ITEMS).setMaxGlobalLimited(1)) // required for circuit
                                    .or(abilities(PartAbility.EXPORT_FLUIDS).setMaxGlobalLimited(4))
                                    .or(abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(abilities(PartAbility.PARALLEL_HATCH).setMaxGlobalLimited(1))
                            )
                            .where('R', blocks(GCYMBlocks.CASING_CORROSION_PROOF.get())) // Forces the rotor area to be clear, just visuals :)
                            .where("B", blocks(GCYMBlocks.MOLYBDENUM_DISILICIDE_COIL_BLOCK.get()))
                            .where("P", abilities(PartAbility.ROTOR_HOLDER))
                            .where("E", blocks(GTBlocks.CASING_TUNGSTENSTEEL_PIPE.get()))
                            .where("D", blocks(GTBlocks.CASING_TUNGSTENSTEEL_GEARBOX.get()))
                            .where("F", blocks(GTBlocks.CASING_EXTREME_ENGINE_INTAKE.get()))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/gcym/corrosion_proof_casing"),
                            GTCEu.id("block/multiblock/gcym/large_brewer"))
                    .register();
        }
    }

    /*
    static {
        if (ConfigClass.INSTANCE.Multiblocks.ExpandedDatabank || GTCEu.isDataGen()) {
            EXPANDED_DATABANK = ExtendedFeaturesRegister
                    .multiblock("expanded_databank", DataBankMachine::new)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(DUMMY_RECIPES)
                    .appearanceBlock(ADVANCED_COMPUTER_CASING)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("   BBBBB   ", "   B   B   ", "   B   B   ", "   B   B   ", "   B   B   ", "   BBBBB   ")
                            .aisle(" BBBDBDBBB ", "           ", "           ", "           ", "           ", " BBBDBDBBB ")
                            .aisle("BBDDDBDDDBB", "B         B", "B         B", "B         B", "B         B", "BBDDDBDDDBB")
                            .aisle("BDBBBBBBBDB", "  E E E E  ", "  E E E E  ", "  E E E E  ", "  E E E E  ", "BDBBBBBBBDB")
                            .aisle("BBDDDBDDDBB", "B         B", "B         B", "B         B", "B         B", "BBDDDBDDDBB")
                            .aisle(" BBBDBDBBB ", "           ", "           ", "           ", "           ", " BBBDBDBBB ")
                            .aisle("   BB@BB   ", "   B   B   ", "   B   B   ", "   B   B   ", "   B   B   ", "   BBBBB   ")
                            .where('@', controller(blocks(definition.get())))
                            .where(' ', any())
                            .where('D', blocks(COMPUTER_CASING.get()))
                            .where('B', blocks(ADVANCED_COMPUTER_CASING.get())
                                    .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(abilities(PartAbility.INPUT_ENERGY).setMaxGlobalLimited(2))
                                    .or(abilities(PartAbility.DATA_ACCESS).setMinGlobalLimited(1).setMaxGlobalLimited(6)))
                            .where('E', abilities(PartAbility.OPTICAL_DATA_TRANSMISSION).setMaxGlobalLimited(16))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/hpca/advanced_computer_casing/top"),
                            GTCEu.id("block/multiblock/data_bank"))
                    .register();
        }
    }
     */

    static {
        if (ConfigClass.INSTANCE.Multiblocks.MatrixDataRelay || GTCEu.isDataGen()) {
            MATRIX_DATA_RELAY = ExtendedFeaturesRegister
                    .multiblock("matrix_data_relay", MatrixDataRelayMachine::new)
                    .tooltips(EFTooltipHelper.MDRTooltip)
                    .rotationState(RotationState.NON_Y_AXIS)
                    .recipeType(GTRecipeTypes.DUMMY_RECIPES)
                    .appearanceBlock(GTBlocks.HIGH_POWER_CASING)
                    .pattern(definition -> FactoryBlockPattern.start()
                            .aisle("    CCCCC    ", "    C   C    ", "    C   C    ", "    C   C    ", "    CCCCC    ")
                            .aisle("  DDDDDDDDD  ", "  EEC   CEE  ", "  EEC   CEE  ", "  EEC   CEE  ", "  DDDDDDDDD  ")
                            .aisle(" DDCFFFFFCDD ", " EDEF F FEDE ", " EDEF F FEDE ", " EDEF F FEDE ", " DDCFFFFFCDD ")
                            .aisle(" DCCCCFCCCCD ", " EE       EE ", " EE       EE ", " EE       EE ", " DCCCCFCCCCD ")
                            .aisle("CDFCCCFCCCFDC", "CCF       FCC", "CCX       XCC", "CCF       FCC", "CDFCCCFCCCFDC")
                            .aisle("CDFCCCFCCCFDC", "             ", "             ", "             ", "CDFCCCFCCCFDC")
                            .aisle("CDFFFFFFFFFDC", "  F   F   F  ", "  X   H   X  ", "  F   F   F  ", "CDFFFFFFFFFDC")
                            .aisle("CDFCCCFCCCFDC", "             ", "             ", "             ", "CDFCCCFCCCFDC")
                            .aisle("CDFCCCFCCCFDC", "CCF       FCC", "CCX       XCC", "CCF       FCC", "CDFCCCFCCCFDC")
                            .aisle(" DCCCCFCCCCD ", " EE       EE ", " EE       EE ", " EE       EE ", " DCCCCFCCCCD ")
                            .aisle(" DDCFFFFFCDD ", " EDEF F FEDE ", " EDEF @ FEDE ", " EDEF F FEDE ", " DDCFFFFFCDD ")
                            .aisle("  DDDDDDDDD  ", "  EEC   CEE  ", "  EEC   CEE  ", "  EEC   CEE  ", "  DDDDDDDDD  ")
                            .aisle("    CCCCC    ", "    C   C    ", "    C   C    ", "    C   C    ", "    CCCCC    ")
                            .where('@', controller(blocks(definition.get())))
                            .where(' ', any())
                            .where('#', air())
                            .where('D', blocks(GTBlocks.ADVANCED_COMPUTER_CASING.get()))
                            .where('C', blocks(GTBlocks.COMPUTER_CASING.get()))
                            .where('E', blocks(GTBlocks.CASING_LAMINATED_GLASS.get()))
                            .where('F', blocks(GTBlocks.HIGH_POWER_CASING.get())
                                    .or(abilities(PartAbility.IMPORT_FLUIDS).setMaxGlobalLimited(1))
                                    .or(abilities(PartAbility.MAINTENANCE).setExactLimit(1))
                                    .or(abilities(PartAbility.INPUT_ENERGY).setMinGlobalLimited(1).setMaxGlobalLimited(2)))
                            .where('H', abilities(ExtendedAbilities.WIRELESS_OPTICAL_TRANSMISSOR).setExactLimit(1))
                            .where('X', blocks(GTBlocks.HIGH_POWER_CASING.get())
                                    .or(abilities(PartAbility.DATA_ACCESS).setMinGlobalLimited(1).setMaxGlobalLimited(6)))
                            .build())
                    .workableCasingModel(
                            GTCEu.id("block/casings/hpca/high_power_casing"),
                            GTCEu.id("block/multiblock/hpca"))
                    .register();
        }
    }

    public static void init() {
    }
}