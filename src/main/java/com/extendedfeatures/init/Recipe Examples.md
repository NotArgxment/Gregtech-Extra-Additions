## Available recipe types for custom recipes from KubeJS Scripts

1. Circuits go from 1 to 32
2. All available tiers: LV, MV, HV, EV, IV, LuV, ZPM, UV, UHV, UEV, UIV, UXV, OpV, MAX
3. Duration must be written in ticks, 1t = 50ms
4. Most multiblocks inside the mod re-use the same recipe types as GTCEu, so any recipe added for those GTCEu multiblocks are mirrored into the main multiblocks
5. Avoid making recipes for the disassembler, its logic automatically takes any existing machine to decompose that into the crafting/processing recipe that made it. BUT! feel free to make recipes with the CASING_DISASSEMBLY mode

### Wood Recipes (Greenhouse)
```javascript
    ServerEvents.recipes(event => {
        event.recipes.extendedfeatures.greenhouse_wood_recipes('recipe_name')
            .notConsumable('...')
            .itemInputs('...')
            .inputFluids('...')
            .itemOutputs('...')
            .circuit()
            .duration()
            .EUt(GTValues.VA[GTValues.TIER])
    });
```

### Crop Recipes (Greenhouse)
```javascript
    ServerEvents.recipes(event => {
        event.recipes.extendedfeatures.greenhouse_crop_recipes('recipe_name')
            .itemInputs('...')
            .inputFluids('...')
            .itemOutputs('...')
            .notConsumable('...')
            .circuit()
            .duration()
            .EUt(GTValues.VA[GTValues.TIER])
    });
```

### Rock Processing Plant
```javascript
    ServerEvents.recipes(event => {
        event.recipes.extendedfeatures.rock_processing_plant('recipe_name')
            .itemInputs()
            .inputFluids()
            .itemOutputs()
            .outputFluids()
            .duration()
            .EUt(GTValues.VA[GTValues.TIER])
    });
```

### Large Gas Collector
```javascript
    ServerEvents.recipes(event => {
        event.recipes.extendedfeatures.air_collection('recipe_name')
            .circuit()
            .outputFluid()
            .duration()
            .EUt(GTValues.VA[GTValues.TIER])
    });
```

### Chemical Skips
```javascript
    ServerEvents.recipes(event => {
        event.recipes.extendedfeatures.chemical_skips('recipe_name')
            .itemInputs()
            .inputFluids()
            .itemOutputs()
            .outputFluids()
            .circuit()
            .duration()
            .EUt(GTValues.VA[GTValues.TIER])
    });
```

### Casing Disassembly
```javascript
    ServerEvents.recipes(event => {
        event.recipes.extendedfeatures.casing_disassembly('recipe_name')
            .itemInputs()
            .itemOutputs()
            .outputFluids()
            .circuit()
            .duration()
            .EUt(GTValues.VA[GTValues.TIER])
    });
```