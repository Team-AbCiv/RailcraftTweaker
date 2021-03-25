/*
 * This is free and unencumbered software released into the public domain.
 *
 * Anyone is free to copy, modify, publish, use, compile, sell, or
 * distribute this software, either in source code form or as a compiled
 * binary, for any purpose, commercial or non-commercial, and by any
 * means.
 *
 * In jurisdictions that recognize copyright laws, the author or authors
 * of this software dedicate any and all copyright interest in the
 * software to the public domain. We make this dedication for the benefit
 * of the public at large and to the detriment of our heirs and
 * successors. We intend this dedication to be an overt act of
 * relinquishment in perpetuity of all present and future rights to this
 * software under copyright law.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
 * OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * For more information, please refer to <http://unlicense.org/>
 */

import mods.railcraft.BlastFurnace;
import mods.railcraft.CokeOven;
import mods.railcraft.RockCrusher;
import mods.railcraft.RollingMachine;

// Simple blast furnace recipe
BlastFurnace.addRecipe("test_recipe_1", <item:minecraft:diamond>, <item:minecraft:dirt>);
// With custom processing time
BlastFurnace.addRecipe("test_recipe_2", <item:minecraft:diamond>, <item:minecraft:grass>, 23333);
// With custom processing time and 6 slag as byproducts
BlastFurnace.addRecipe("test_recipe_3", <item:minecraft:diamond>, <item:minecraft:stone>, 23333, 6);
BlastFurnace.removeRecipe("railcraft:smelt_horse_armor");

// Simple coke oven recipe
//CokeOven.addRecipe("test_recipe_1", <item:minecraft:diamond>, <item:minecraft:dirt>);
// With custom processing time
CokeOven.addRecipe("test_recipe_2", <item:minecraft:emerald>, <item:minecraft:grass>, 23333);
// With custom processing time and fluid byproducts
CokeOven.addRecipe("test_recipe_3", <item:minecraft:nether_star>, <item:minecraft:stone>, 23333, <liquid:lava> * 1000);
CokeOven.removeRecipe("railcraft:coke_block");

// Simple rock crusher recipe, nothing special
RockCrusher.addRecipe("test_recipe_1", [
    <item:minecraft:diamond>,
    <item:minecraft:diamond> % 50,
    <item:minecraft:diamond> % 25,
    <item:minecraft:diamond> % 12.5,
    <item:minecraft:diamond>.weight(0.0625)
    ], <item:minecraft:dirt>);
RockCrusher.removeRecipe("minecraft:gravel");

// Remove standard rail recipe from rolling machine
RollingMachine.remove(<railcraft:rail:0>);
// Add a new shaped rolling machine recipe
RollingMachine.addShaped("thonk", <item:minecraft:diamond>, [
    [ <item:minecraft:dirt>, null, <item:minecraft:dirt> ],
    [ null, <item:minecraft:dirt>, null ],
    [ <item:minecraft:dirt>, null, <item:minecraft:dirt> ]
]);
// Add a new shapeless rolling machine recipe
RollingMachine.addShapeless("thwonk", <item:minecraft:diamond>, [
    <item:minecraft:dye:*>,
    <item:minecraft:dye:*>,
    <item:minecraft:dye:*>,
    <item:minecraft:dye:*>,
    <item:minecraft:dye:*>,
    <item:minecraft:dye:*>,
    <item:minecraft:dye:*>
]);

