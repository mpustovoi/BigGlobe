# Json structure

* `type` - must be `bigglobe:ore`
* `config`:
	* `seed` - a unique number or string. I recommend setting this to the name of the file.
	* `blocks` - a map of which blocks to find, and which blocks to replace them with. The keys and values of this map use the same syntax as /setblock. The keys may omit block properties. The values will log a warning on missing properties, and choose the default value for each missing property.
	* `chance` - a script returning a double between 0 and 1 which determines how likely the ore is to spawn at any given location. Default generation will attempt to place one ore inside every chunk section (16x16x16 area), and fail randomly based on chance. If the script returns a number outside the 0-1 range, the ore will behave as if the number was clamped to the 0-1 range first. If the script returns NaN, it will be treated as 0. This script has the following environments available:
		* MathScriptEnvironment
		* StatelessRandomScriptEnvironment
		* GridScriptEnvironment (with an implicit seed)
		* MinecraftScriptEnvironment
		* BaseColumnScriptEnvironment
		* ColumnEntryRegistry
		* ColorScriptEnvironment
		* ExternalImageScriptEnvironment
		* ExternalDataScriptEnvironment

		And the following additional variable:
		* y - the Y level the ore is attempting to spawn at.

		Note that x and z are provided by BaseColumnScriptEnvironment.
	* `radius` - a RandomSource returning a number between 0 and 16 which determines how big the ore is.

# Other requirements

The chunk generator must know to place this ore in the world, otherwise it won't spawn. This feature is a rock replacer, and must be directly or indirectly present in the `rock_replacers` section of the feature dispatcher. All built-in scripted chunk generators have a tag for this purpose, meaning that you can add this feature to the tag that your chunk generator uses for ores, and it'll work.

# Generic ores

If the above requirements are too strict and you need to place an ore feature in a non-scripted world, or with commands, you can change the `type` to `bigglobe:generic_ore`, and it will behave just like any other configured feature. Generic ores have the following json differences compared to normal ores:
* `seed` is ignored.
* `chance` is ignored. If you tell the ore to spawn somewhere with a placed feature or command, it'll always spawn there.
* `blocks` is renamed to `states`.

# Reasoning

Regular ores, when used as a rock replacer, are more efficient than generic ores, and can be multi-threaded. Generic ores are less efficient, and single-threaded.

# Interactions with molten rock

When cooling molten rock, it can turn into a random ore. But you might wonder, how does it decide which ore to turn into? Well, the answer is simple: it can turn into any ore present in the feature dispatcher for the current dimension, weighted based on the chance of the ore spawning at the molten rock's location. This also means that it will never turn into a generic ore, and it will never turn into anything except stone in non-scripted worlds.