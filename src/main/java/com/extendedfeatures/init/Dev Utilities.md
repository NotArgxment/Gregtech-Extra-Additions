# Developer utilities

### Laser Hatch-capable multiblocks

Custom formation logic for multiblocks that can accept either Laser Hatches or Energy Hatches, available for both
`Workable` and `CoilWorkable` multiblock types:

|   Laser    | Energy Hatch |              Valid formation?              |
|:----------:|:------------:|:------------------------------------------:|
| Not Placed |  Not Placed  |            Will not form (duh)             |
| Placed One |  Not Placed  |    Only the laser hatch will be allowed    |
| Not Placed |  Placed One  |   Only the energy hatch will be allowed    |
| Placed One |  Placed One  | You cannot have both on the same structure |

```java
    public static MultiblockMachineDefinition TEST_MULTIBLOCK = REGISTER
            .multiblock("coil_laser_multiblock", CoilWorkableMultiblockLaser::new)

    public static MultiblockMachineDefinition TEST_MULTIBLOCK_2 = REGISTER
            .multiblock("regular_laser_multiblock", WorkableMultiblockLaser::new)

```

> There's no KubeJS example for this because on the KubeJS side it already works fine — the formation issue only shows
> up for multiblocks made on Java.

## Recipe Modifiers

### Machine Parallels

Allows any multiblock to run parallels **without a parallel hatch part in its structure**. EU/t stays the same, but
duration is multiplied ×2 for each parallel achieved.

```java
// Java
.recipeModifiers(MACHINE_PARALLEL(n))
```

```javascript
// KubeJS
const CustomModifier = Java.loadClass('com.extendedfeatures.init.contents.modifiers')

.recipeModifiers(ExtendedFeatures.MACHINE_PARALLEL(n))
```

### Gradients based on the GTCEu Energy Tiers (LV -> MAX)

<img width="482" height="458" alt="Howeachlooks-ezgif com-video-to-gif-converter (1)" src="https://github.com/user-attachments/assets/f8a2ef73-22e1-43cf-aab7-d034d8df3da2" />

### How to use

```java
    // Basic tooltip builder
    .tooltipBuilder((stack, list) -> list.add(
            Component.translatable("extendedfeatures.regular.tooltip.0")
                .append(
                    Component.translatable("extendedfeatures.styled.tooltip.1")
                        .withStyle(CustomTooltipStyles.HV_GRADIENT))
        // Where "HV_GRADIENT" can be replaced with other styles from CustomTooltipStyles or TooltipHelper from GTCEu
        // Check CustomTooltipStyles.java for the tier you want to use as tooltip
        );
    })
```
