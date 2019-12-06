package com.ToMe.ToMeSetup;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ToMe.ToMeSetup.api.Messager;
import com.ToMe.ToMeSetup.api.StartItems.impl.StartItemContainer;
import com.ToMe.ToMeSetup.api.StartItems.impl.StartItemProvider;
import com.electronwill.nightconfig.core.AbstractCommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
//import com.electronwill.nightconfig.core.file.FormatDetector;

import net.minecraft.world.GameRules;
//import net.minecraft.world.GameRules.BooleanValue;
//import net.minecraft.world.GameRules.IntegerValue;
//import net.minecraft.world.GameRules.RuleKey;
//import net.minecraft.world.GameRules.RuleType;
//import net.minecraft.world.GameRules.RuleValue;
//import net.minecraft.world.GameRules.ValueType;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
//import net.minecraftforge.common.ForgeConfigSpec;
//import net.minecraftforge.common.config.ConfigCategory;
//import net.minecraftforge.common.config.Configuration;
//import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.loading.FMLPaths;
//import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	
	//public Configuration config;
	public CommentedFileConfig config;
	//private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
	//GENERAL
	public static final String CATEGORY_GENERAL = "general";
	public static boolean enableTooltips;
	public static boolean explosionBlockDamage = true;
	//GAMERULES
	public static final String CATEGORY_GAMERULES = "gamerules";
	public static boolean enableGamerules = true;
	private static boolean initGamerules = false;
	public static final Map<String, Object> customDefaultValues;
	public static Map<String, Object> configValues = new HashMap<String, Object>();
	public static boolean pvp = true;
	//public static boolean keepInventory;
	//public static boolean mobGriefing;
	//public static boolean doFireTick;
	//public static boolean doMobSpawning;
	//public static int spawnRadius;
	//public static boolean doDaylightCycle;
	//public static boolean doTileDrops;
	//public static boolean doMobLoot;
	//WORLDSPAWN
	public static final String CATEGORY_WORLDSPAWN = "worldspawn";
	public static boolean setWorldspawn;
	public static int worldSpawnX;
	public static int worldSpawnZ;
	public static boolean setBedrock;
	public static boolean replaceLiquid;
	public static boolean replaceSolid;
	public static String groundBlock;
	public static String liquidReplace;
	public static String solidReplace;
	public static boolean groundEnableOreDict;
	public static boolean liquidEnableOreDict;
	public static boolean solidEnableOreDict;
	//public static int groundMeta;
	//public static int liquidMeta;
	//public static int solidMeta;
	public static String groundOreDict;
	public static String liquidOreDict;
	public static String solidOreDict;
	//public static int groundNumber;
	//public static int liquidNumber;
	//public static int solidNumber;
	//SPAWN ITEMS
	public static final String CATEGORY_SPAWN_ITEMS = "start_items";
	public static boolean enableStartItems;
	public static boolean startItemsOnRespawn;
	//public static String[] StartItems = {"minecraft:log"};
	public static String[] StartItems = {"minecraft:oak_log"};
	public static String[] StartItemMeta = {"-1"};
	public static int[] StartItemMetas;
	public static String[] StartItemCount = {"32"};
	public static int[] StartItemCounts;
	public static String[] StartItemOreDicts = {"treeSapling"};
	//public static String[] StartItemOreDictNumber = {"0"};
	//public static int[] StartItemOreDictNumbers;
	//public static String[] StartItemOreDictMeta = {"-1"};
	//public static int[] StartItemOreDictMetas;
	public static String[] StartItemOreDictCount = {"16"};
	public static int[] StartItemOreDictCounts;
	
	static {
		customDefaultValues = new HashMap<String, Object>();
		customDefaultValues.put("keepInventory", true);
		//customDefaultValues.put("mobGriefing", false);
		//customDefaultValues.put(GameruleHandler.EXPLOSIONBLOCKDAMAGE, false);
		//customDefaultValues.put(GameruleHandler.EXPLOSIONBLOCKDAMAGE.toString(), false);
		customDefaultValues.put("explosionBlockDamage", false);
		customDefaultValues.put("spawnRadius", 0);
	}
	
	public ConfigHandler() {
	//public ConfigHandler(FMLPreInitializationEvent e) {
		//config = new Configuration(new File(e.getModConfigurationDirectory(), "ToMeSetup.cfg"));
		//config = CommentedFileConfig.builder(new File(FMLPaths.CONFIGDIR.get().toFile(), "ToMeSetup.cfg")).sync().autosave().build();
		config = CommentedFileConfig.builder(new File(FMLPaths.CONFIGDIR.get().toFile(), "ToMeSetup.toml")).sync().autosave().build();
		//config = CommentedFileConfig.builder(new File(FMLPaths.CONFIGDIR.get().toFile(), "ToMeSetup.cfg"), FormatDetector.detectByName("json.json")).sync().autosave().build();
		ForgeConfigSpec spec = new ForgeConfigSpec.Builder().build();
		//ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec, "/ToMeSetup.toml");
		//ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec);
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec, config.getFile().getName());
		//try {
			//config.load();
            //InitConfig(config);
        //} catch (Exception e1) {
        	//e1.printStackTrace();
        //} finally {
        	//if (config.hasChanged()) {
        		//config.save();
        	//}
        	//useConfig();
        //}
		//InitConfig(config);
	}
	
	public void load() {
		try {
            config.load();
            InitConfig(config);
        } catch (Exception e1) {
        	e1.printStackTrace();
        } finally {
        	useConfig();
        }
	}
	
	//public static void InitConfig(Configuration cfg) {
	//private static void InitConfig(Configuration cfg) {
	private static void InitConfig(CommentedFileConfig cfg) {
		//ConfigCategory gamerules = new ConfigCategory("gamerules");
		//cfg.addCustomCategoryComment("gamerules", "Test");
		
		//GENERAL
		//cfg.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "The General Configs.");
		cfg.setComment(CATEGORY_GENERAL, "The General Configs.");
		//enableTooltips = cfg.getBoolean("enableErrorTooltips", Configuration.CATEGORY_GENERAL, true, "Enables/Disables the Chat Tooltips(Server Side only).");
		enableTooltips = ToMeSetupMod.cfg.getBoolean("enableErrorTooltips", CATEGORY_GENERAL, true, "Enables/Disables the Chat Tooltips(Server Side only).");
		//explosionBlockDamage = cfg.getBoolean("explosionBlockDamage", Configuration.CATEGORY_GENERAL, explosionBlockDamage, "Whether this Mod should add the explosionBlockDamage Gamerule");
		explosionBlockDamage = ToMeSetupMod.cfg.getBoolean("explosionBlockDamage", CATEGORY_GENERAL, explosionBlockDamage, "Whether this Mod should add the explosionBlockDamage Gamerule");
		
		//GAMERULES
		//if(!cfg.hasCategory(CATEGORY_GAMERULES)) {
		if(!cfg.contains(CATEGORY_GAMERULES)) {
			initGamerules = true;
		}
		//cfg.addCustomCategoryComment(CATEGORY_GAMERULES, "How this gamerules should set on World load.");
		cfg.setComment(CATEGORY_GAMERULES, "How this gamerules should set on World load.");
		//enableGamerules = cfg.getBoolean("enableGamerules", CATEGORY_GAMERULES, enableGamerules, "Should this Mod set some Gamerules on world load?");
		enableGamerules = ToMeSetupMod.cfg.getBoolean("enableGamerules", CATEGORY_GAMERULES, enableGamerules, "Should this Mod set some Gamerules on world load?");
		//pvp = cfg.getBoolean("pvp", CATEGORY_GAMERULES, pvp, "Determines wheter pvp should be enabled or not(this isn't realy a Gamerule, but it fits ito that Category).");
		pvp = ToMeSetupMod.cfg.getBoolean("pvp", CATEGORY_GAMERULES, pvp, "Determines wheter pvp should be enabled or not(this isn't realy a Gamerule, but it fits ito that Category).");
		//for(String rule:customDefaultValues.keySet()) {
			//cfg.get(CATEGORY_GAMERULES, rule, (Boolean) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
			//if(customDefaultValues.get(rule) instanceof Boolean) {
				//cfg.get(CATEGORY_GAMERULES, rule, (Boolean) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
			//}
			//else if(customDefaultValues.get(rule) instanceof Integer) {
				//cfg.get(CATEGORY_GAMERULES, rule, (Integer) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
			//}
			//else if(customDefaultValues.get(rule) instanceof String) {
				//cfg.get(CATEGORY_GAMERULES, rule, (String) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
			//}
		//}
		if(initGamerules) {
			for(String rule:customDefaultValues.keySet()) {
				//cfg.get(CATEGORY_GAMERULES, rule, (Boolean) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
				if(customDefaultValues.get(rule) instanceof Boolean) {
					//cfg.get(CATEGORY_GAMERULES, rule, (Boolean) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
					ToMeSetupMod.cfg.getBoolean(rule, CATEGORY_GAMERULES, (Boolean) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
				}
				else if(customDefaultValues.get(rule) instanceof Integer) {
					//cfg.get(CATEGORY_GAMERULES, rule, (Integer) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
					ToMeSetupMod.cfg.getInt(rule, CATEGORY_GAMERULES, (Integer) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
				}
				else if(customDefaultValues.get(rule) instanceof String) {
					//cfg.get(CATEGORY_GAMERULES, rule, (String) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
					ToMeSetupMod.cfg.getString(rule, CATEGORY_GAMERULES, (String) customDefaultValues.get(rule), "How should the Gamerule " + rule + " set?");
				}
			}
		}
		//cfg.addCustomCategoryComment(CATEGORY_GAMERULES, "How this gamerules should set on World load.");
		//keepInventory = cfg.getBoolean("keepInventory", CATEGORY_GAMERULES, true, "The gamerule keepInventory determines whether the Pleyer keeps his Items on death in Inventory. To set this manually do \"/gamerule keepInventory true\"!");
		//mobGriefing = cfg.getBoolean("mobGriefing", CATEGORY_GAMERULES, false, "The gamerule mobGriefing determines whether Mobs con break Blocks or pickup Items. To set this manually do \"/gamerule mobGriefing false\"!");
		//doFireTick = cfg.getBoolean("doFireTick", CATEGORY_GAMERULES, true, "The gamerule doFireTick determines whether Spreads and destroys Blocks. To set this manually do \"/gamerule doFireTick true\"!");
		//doMobSpawning = cfg.getBoolean("doMobSpawning", CATEGORY_GAMERULES, true, "The gamerule doMobSpawning determines whether Mobs Spawn int the World. To set this manually do \"/gamerule doMobSpawning true\"!");
		//spawnRadius = cfg.getInt("spawnRadius", CATEGORY_GAMERULES, 0, 0, Integer.MAX_VALUE, "The gamerule spawnRadius determines the Radius to spawn around the Worldspawn. To set this manually do \"/gamerule spawnRadius 0\"!");
		//doDaylightCycle = cfg.getBoolean("doDaylightCycle", CATEGORY_GAMERULES, true, "The gamerule doDaylightCycle determines whether the Minecraft Sun and Moon moves over the Sky. To Set this manually do \"/gamerule doDaylightCycle true\"!");
		//doTileDrops = cfg.getBoolean("doTileDrops", CATEGORY_GAMERULES, true, "The gamerule doTileDrops determines whether you get Items by Mining Blocks. To set this manually do \"/gamerule doTileDrops true\"!");
		//doMobLoot = cfg.getBoolean("doMobLoot", CATEGORY_GAMERULES, true, "The gamerule doMobLoot determines whether you get Items by Killing Mobs. To set this manually do \"/gamerule doMobLoot true\"");
		
		//WORLDSPAWN
		//cfg.addCustomCategoryComment(CATEGORY_WORLDSPAWN, "What to do with the Worldspawn.");
		cfg.setComment(CATEGORY_WORLDSPAWN, "What to do with the Worldspawn.");
		//setWorldspawn = cfg.getBoolean("setWorldSpawn", CATEGORY_WORLDSPAWN, true, "Enables / Disables this Category! If enabled it will set the Worldspawn to X:worldSpawnX, Z:worldSpawnZ, Y:1 Block over the Highest non air Block.");
		setWorldspawn = ToMeSetupMod.cfg.getBoolean("setWorldSpawn", CATEGORY_WORLDSPAWN, true, "Enables / Disables this Category! If enabled it will set the Worldspawn to X:worldSpawnX, Z:worldSpawnZ, Y:1 Block over the Highest non air Block.");
		//worldSpawnX = cfg.getInt("worldSpawnX", CATEGORY_WORLDSPAWN, 0, -30000000, 30000000, "The X psoition of the Worldspawn.");
		worldSpawnX = ToMeSetupMod.cfg.getInt("worldSpawnX", CATEGORY_WORLDSPAWN, 0, -30000000, 30000000, "The X psoition of the Worldspawn.");
		//worldSpawnZ = cfg.getInt("worldSpawnZ", CATEGORY_WORLDSPAWN, 0, -30000000, 30000000, "The Z psoition of the Worldspawn.");
		worldSpawnZ = ToMeSetupMod.cfg.getInt("worldSpawnZ", CATEGORY_WORLDSPAWN, 0, -30000000, 30000000, "The Z psoition of the Worldspawn.");
		//setBedrock = cfg.getBoolean("setBedrock", CATEGORY_WORLDSPAWN, true, "Replace the block at X:worldSpawnX, Z:worldSpawnZ, Y:0 with Bedrock.");
		//setBedrock = cfg.getBoolean("setGround", CATEGORY_WORLDSPAWN, true, "Replace the block at X:worldSpawnX, Z:worldSpawnZ, Y:0 with the Block defined by groundBlock.");
		//setBedrock = cfg.getBoolean("replaceGround", CATEGORY_WORLDSPAWN, true, "Replace the block at X:worldSpawnX, Z:worldSpawnZ, Y:0 with the Block defined by ground2Replace.");
		setBedrock = ToMeSetupMod.cfg.getBoolean("replaceGround", CATEGORY_WORLDSPAWN, true, "Replace the block at X:worldSpawnX, Z:worldSpawnZ, Y:0 with the Block defined by ground2Replace.");
		//replaceLiquid = cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with Grass if it is a Liquid Block.");
		//replaceLiquid = cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with the Block defined by liquidReplace if it is a Liquid Block.");
		//replaceLiquid = cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block direct under the Worldspawn with the Block defined by liquid2Replace if it is a Liquid Block.");
		replaceLiquid = ToMeSetupMod.cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block direct under the Worldspawn with the Block defined by liquid2Replace if it is a Liquid Block.");
		//replaceSolid = cfg.getBoolean("replaceSolid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with the Block defined by solidReplace if it is not a Liquid Block.");
		//replaceSolid = cfg.getBoolean("replaceSolid", CATEGORY_WORLDSPAWN, true, "Replace the block direct under the Worldspawn with the Block defined by solid2Replace if it is not a Liquid Block.");
		replaceSolid = ToMeSetupMod.cfg.getBoolean("replaceSolid", CATEGORY_WORLDSPAWN, true, "Replace the block direct under the Worldspawn with the Block defined by solid2Replace if it is not a Liquid Block.");
		//groundBlock = cfg.getString("groundBlock", CATEGORY_WORLDSPAWN, "minecraft:bedrock", "The Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 with this Block.");
		//liquidReplace = cfg.getString("liquidReplace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to set by replaceLiquid.");
		//solidReplace = cfg.getString("solidReplace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to set by replaceSolid.");
		//groundEnableOreDict = cfg.getBoolean("ground1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set at X:worldSpawnX, Z:worldSpawnZ, Y:0. Dissable to Use the Registry Name.");
		groundEnableOreDict = ToMeSetupMod.cfg.getBoolean("ground1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set at X:worldSpawnX, Z:worldSpawnZ, Y:0. Dissable to Use the Registry Name.");
		//liquidEnableOreDict = cfg.getBoolean("liquid1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set direct under the Worldspawn if it is a Liquid Block. Dissable to Use the Registry Name.");
		liquidEnableOreDict = ToMeSetupMod.cfg.getBoolean("liquid1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set direct under the Worldspawn if it is a Liquid Block. Dissable to Use the Registry Name.");
		//solidEnableOreDict = cfg.getBoolean("solid1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set direct under the Worldspawn if it is a not Liquid Block. Dissable to Use the Registry Name.");
		solidEnableOreDict = ToMeSetupMod.cfg.getBoolean("solid1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set direct under the Worldspawn if it is a not Liquid Block. Dissable to Use the Registry Name.");
		//groundBlock = cfg.getString("ground2Replace", CATEGORY_WORLDSPAWN, "minecraft:bedrock", "The Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 if ground1EnableOreDict is dissabled.");
		groundBlock = ToMeSetupMod.cfg.getString("ground2Replace", CATEGORY_WORLDSPAWN, "minecraft:bedrock", "The Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 if ground1EnableOreDict is dissabled.");
		//liquidReplace = cfg.getString("liquid2Replace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to Set direct under the Worldspawn if it is a Liquid Block and liquid1EnableOreDict is dissabled.");
		liquidReplace = ToMeSetupMod.cfg.getString("liquid2Replace", CATEGORY_WORLDSPAWN, "minecraft:grass_block", "The Block to Set direct under the Worldspawn if it is a Liquid Block and liquid1EnableOreDict is dissabled.");
		//solidReplace = cfg.getString("solid2Replace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to Set direct under the Worldspawn if it is not a Liquid Block and solid1EnableOreDict is dissabled.");
		solidReplace = ToMeSetupMod.cfg.getString("solid2Replace", CATEGORY_WORLDSPAWN, "minecraft:grass_block", "The Block to Set direct under the Worldspawn if it is not a Liquid Block and solid1EnableOreDict is dissabled.");
		//groundOreDict = cfg.getString("ground3OreDict", CATEGORY_WORLDSPAWN, "bedrock", "The OreDictinary Name with the Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 if ground1EnableOreDict is enabled.");
		groundOreDict = ToMeSetupMod.cfg.getString("ground3OreDict", CATEGORY_WORLDSPAWN, "bedrock", "The OreDictinary Name with the Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 if ground1EnableOreDict is enabled. (This should be compatible with all tag names and most oredictionary names, however the meta configuration option got removed because there is no metadata in 1.13+ and the number configuration option got removed because tag sorting is inconsistent.)");
		//liquidOreDict = cfg.getString("liquid3OreDict", CATEGORY_WORLDSPAWN, "grass", "The OreDictinary Name with the Block to Set direct under the Worldspawn if it is a Liquid Block and liquid1EnableOreDict is enabled.");
		liquidOreDict = ToMeSetupMod.cfg.getString("liquid3OreDict", CATEGORY_WORLDSPAWN, "grass", "The OreDictinary Name with the Block to Set direct under the Worldspawn if it is a Liquid Block and liquid1EnableOreDict is enabled. (This should be compatible with all tag names and most oredictionary names, however the meta configuration option got removed because there is no metadata in 1.13+ and the number configuration option got removed because tag sorting is inconsistent.)");
		//solidOreDict = cfg.getString("solid3OreDict", CATEGORY_WORLDSPAWN, "grass", "The OreDictinary Name with the Block to Set direct under the Worldspawn if it is not a Liquid Block and solid1EnableOreDict is enabled.");
		solidOreDict = ToMeSetupMod.cfg.getString("solid3OreDict", CATEGORY_WORLDSPAWN, "grass", "The OreDictinary Name with the Block to Set direct under the Worldspawn if it is not a Liquid Block and solid1EnableOreDict is enabled. (This should be compatible with all tag names and most oredictionary names, however the meta configuration option got removed because there is no metadata in 1.13+ and the number configuration option got removed because tag sorting is inconsistent.)");
		//groundMeta = cfg.getInt("ground4Meta", CATEGORY_WORLDSPAWN, -1, -1, 255, "The Meatdata for the Block to set at X:worldSpawnX, Z:worldSpawnZ, Y:0. Use -1 for the Default Metadata.");
		//liquidMeta = cfg.getInt("liquid4Meta", CATEGORY_WORLDSPAWN, -1, -1, 255, "The Meatdata for the Block to set direct under the Worldspawn if it is a Liquid Block. Use -1 for the Default Metadata.");
		//solidMeta = cfg.getInt("solid4Meta", CATEGORY_WORLDSPAWN, -1, -1, 255, "The Meatdata for the Block to set direct under the Worldspawn if it is not a Liquid Block. Use -1 for the Default Metadata.");
		//groundNumber = cfg.getInt("ground5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use for replaceGround.");
		//groundNumber = ToMeSetupMod.cfg.getInt("ground5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use for replaceGround.");
		//liquidNumber = cfg.getInt("liquid5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use for replaceLiquid.");
		//liquidNumber = ToMeSetupMod.cfg.getInt("liquid5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use for replaceLiquid.");
		//solidNumber = cfg.getInt("solid5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use. for replaceSolid");
		//solidNumber = ToMeSetupMod.cfg.getInt("solid5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use. for replaceSolid");
		
		//SPAWN ITEMS
		//cfg.addCustomCategoryComment(CATEGORY_SPAWN_ITEMS, "Wich Start Items Players should get.");
		cfg.setComment(CATEGORY_SPAWN_ITEMS, "Wich Start Items Players should get.");
		//enableStartItems = cfg.getBoolean("enableStartItems", CATEGORY_SPAWN_ITEMS, true, "Enables/Dissables this Category.");
		enableStartItems = ToMeSetupMod.cfg.getBoolean("enableStartItems", CATEGORY_SPAWN_ITEMS, true, "Enables/Dissables this Category.");
		//startItemsOnRespawn = cfg.getBoolean("RespawnStartItems", CATEGORY_SPAWN_ITEMS, false, "Should the Playerget the Items everytime after respawning?");
		startItemsOnRespawn = ToMeSetupMod.cfg.getBoolean("RespawnStartItems", CATEGORY_SPAWN_ITEMS, false, "Should the Playerget the Items everytime after respawning?");
		//StartItems = cfg.getStringList("Start1Items", CATEGORY_SPAWN_ITEMS, StartItems, "Determine Wich Start Items all Players should get.");
		StartItems = ToMeSetupMod.cfg.getStringList("Start1Items", CATEGORY_SPAWN_ITEMS, StartItems, "Determine Wich Start Items all Players should get.");
		//StartItemMeta = cfg.getStringList("Start2Metas", CATEGORY_SPAWN_ITEMS, StartItemMeta, "The Item Meta for the direct defined Items. Write in the same sequence like the Start1Items.");
		StartItemMeta = ToMeSetupMod.cfg.getStringList("Start2Metas", CATEGORY_SPAWN_ITEMS, StartItemMeta, "The Item Meta for the direct defined Items. Write in the same sequence like the Start1Items. (This is the only not removed meta configuration option and only affects tool damage as there is no longer metadata in 1.13+.)");
		//StartItemCount = cfg.getStringList("Start3Count", CATEGORY_SPAWN_ITEMS, StartItemCount, "How many from this Items should the Player get? Write in the same sequence like the Start1Items.");
		StartItemCount = ToMeSetupMod.cfg.getStringList("Start3Count", CATEGORY_SPAWN_ITEMS, StartItemCount, "How many from this Items should the Player get? Write in the same sequence like the Start1Items.");
		//StartItemOreDicts = cfg.getStringList("Start4OreDicts", CATEGORY_SPAWN_ITEMS, StartItemOreDicts, "Determine Wich Start Items all Players should get. Use OreDictionary.");
		StartItemOreDicts = ToMeSetupMod.cfg.getStringList("Start4OreDicts", CATEGORY_SPAWN_ITEMS, StartItemOreDicts, "Determine Wich Start Items all Players should get. Use OreDictionary. (This should be compatible with all item tag names and most oredictionary names, however the meta configuration option got removed because there is no metadata in 1.13+ and the number configuration option got removed because tag sorting is inconsistent.)");
		//StartItemOreDictNumber = cfg.getStringList("Start5OreNumbers", CATEGORY_SPAWN_ITEMS, StartItemOreDictNumber, "How many Items are before this Item in the same OreDictionary Name? Write in the same sequence like the Start4OreDicts.");
		//StartItemOreDictNumber = ToMeSetupMod.cfg.getStringList("Start5OreNumbers", CATEGORY_SPAWN_ITEMS, StartItemOreDictNumber, "How many Items are before this Item in the same OreDictionary Name? Write in the same sequence like the Start4OreDicts.");
		//StartItemOreDictMeta = cfg.getStringList("Start6OreMetas", CATEGORY_SPAWN_ITEMS, StartItemOreDictMeta, "The Item Meta for the Items defined via OreDict. Write in the same sequence like the Start4OreDicts.");
		//StartItemOreDictMeta = ToMeSetupMod.cfg.getStringList("Start6OreMetas", CATEGORY_SPAWN_ITEMS, StartItemOreDictMeta, "The Item Meta for the Items defined via OreDict. Write in the same sequence like the Start4OreDicts.");
		//StartItemOreDictCount = cfg.getStringList("Start7OreCount", CATEGORY_SPAWN_ITEMS, StartItemOreDictCount, "How many from this Items should the Player get? Write in the same sequence like the Start4OreDicts.");
		StartItemOreDictCount = ToMeSetupMod.cfg.getStringList("Start5OreCount", CATEGORY_SPAWN_ITEMS, StartItemOreDictCount, "How many from this Items should the Player get? Write in the same sequence like the Start4OreDicts.");
		//System.out.println("Config Created!");
		
		StartItemMetas = new int[StartItemMeta.length];
		int i = 0;
		for(String s:StartItemMeta) {
			try {
				//i++;
				StartItemMetas[i] = Integer.parseInt(s);
				i++;
			} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				StartItemMetas[i] = 0;
				i++;
				ToMeSetupMod.logger.catching(e);
			}
		}
		
		StartItemCounts = new int[StartItemCount.length];
		i = 0;
		for(String s:StartItemCount) {
			try {
				//i++;
				StartItemCounts[i] = Integer.parseInt(s);
				i++;
			} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				//StartItemCounts[i] = 0;
				StartItemCounts[i] = 1;
				i++;
				ToMeSetupMod.logger.catching(e);
			}
		}
		
		//StartItemOreDictNumbers = new int[StartItemOreDictNumber.length];
		//i = 0;
		//for(String s:StartItemOreDictNumber) {
			//try {
				//i++;
				//StartItemOreDictNumbers[i] = Integer.parseInt(s);
				//i++;
			//} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				//StartItemOreDictNumbers[i] = 0;
				//i++;
				//ToMeSetupMod.logger.catching(e);
			//}
		//}
		
		//StartItemOreDictMetas = new int[StartItemOreDictMeta.length];
		//i = 0;
		//for(String s:StartItemOreDictMeta) {
			//try {
				//i++;
				//StartItemOreDictMetas[i] = Integer.parseInt(s);
				//i++;
			//} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				//StartItemOreDictMetas[i] = 0;
				//i++;
				//ToMeSetupMod.logger.catching(e);
			//}
		//}
		
		StartItemOreDictCounts = new int[StartItemOreDictCount.length];
		i = 0;
		for(String s:StartItemOreDictCount) {
			try {
				//i++;
				StartItemOreDictCounts[i] = Integer.parseInt(s);
				i++;
			} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				//StartItemOreDictCounts[i] = 0;
				StartItemOreDictCounts[i] = 1;
				i++;
				ToMeSetupMod.logger.catching(e);
			}
		}
	}
	
	public void onWorldLoad(World world) {
		//if(!config.hasCategory(CATEGORY_GAMERULES)) {
		if(initGamerules) {
			try {
	            initGamerulesConfig(config, world);
	        } finally {
	        	//if (config.hasChanged()) {
	        		//config.save();
	            //}
	        }
		}
		readGamerulesConfig(config);
	}
	
	protected static void useConfig() {
		Messager.enableTooltips = enableTooltips;
		StartItemProvider.startItemsOnRespawn = startItemsOnRespawn;
		int i = 0;
		for(String s:StartItems) {
			//if(!s.isEmpty()) {
			//if(s != "" && !s.isEmpty()) {
			if(s != null && !s.isEmpty()) {
				//StartItemProvider.instance.addStartItem(new StartItemContainer(s, StartItemCounts.length > i ? StartItemCounts[i] : 1, StartItemMetas.length > i ? StartItemMetas[i] : 0, 0, false));
				StartItemProvider.instance.addStartItem(new StartItemContainer(s, StartItemCounts.length > i ? StartItemCounts[i] : 1, StartItemMetas.length > i ? StartItemMetas[i] : 0, false));
				i++;
			}
			//StartItemProvider.instance.addStartItem(new StartItemContainer(s, StartItemCounts.length > i ? StartItemCounts[i] : 1, StartItemMetas.length > i ? StartItemMetas[i] : 0, 0, false));
			//i++;
		}
		i = 0;
		for(String s:StartItemOreDicts) {
			if(s != null && !s.isEmpty()) {
				//StartItemProvider.instance.addStartItem(new StartItemContainer(s, StartItemOreDictCounts.length > i ? StartItemOreDictCounts[i] : 1, StartItemOreDictMetas.length > i ? StartItemOreDictMetas[i] : -1, StartItemOreDictNumbers.length > i ? StartItemOreDictNumbers[i] : 0, true));
				StartItemProvider.instance.addStartItem(new StartItemContainer(s, StartItemOreDictCounts.length > i ? StartItemOreDictCounts[i] : 1, 0, true));
				i++;
			}
			//System.out.println("ConfigHandler: " + (StartItemOreDictNumbers.length > i ? StartItemOreDictNumbers[i] : 0));
			//StartItemProvider.instance.addStartItem(new StartItemContainer(s, StartItemOreDictCounts.length > i ? StartItemOreDictCounts[i] : 1, StartItemOreDictMetas.length > i ? StartItemOreDictMetas[i] : -1, StartItemOreDictNumbers.length > i ? StartItemOreDictNumbers[i] : 0, true));
			//i++;
		}
	}
	
	//private void initGamerulesConfig(Configuration cfg, World w) {
	@SuppressWarnings("unchecked")
	private void initGamerulesConfig(CommentedFileConfig cfg, World w) {
		//cfg.addCustomCategoryComment(CATEGORY_GAMERULES, "How this gamerules should set on World load.");
		//enableGamerules = cfg.getBoolean("enableGamerules", CATEGORY_GAMERULES, enableGamerules, "Should this Mod set some Gamerules on world load?");
		//pvp = cfg.getBoolean("pvp", CATEGORY_GAMERULES, pvp, "Determines wheter pvp should be enabled or not(this isn't realy a Gamerule, but it fits ito that Category).");
		//for(String rule:w.getGameRules().getRules()) {
		//for(RuleKey<?> rule:GameRules.GAME_RULES.keySet()) {
		//for(Object rule:GameRules.GAME_RULES.keySet()) {
			//Object val = w.getGameRules().get(rule);
			//if(w.getGameRules().areSameType(rule, ValueType.BOOLEAN_VALUE)) {
			//if(GameRules.GAME_RULES.get(rule).toString().contains("Boolean")) {
			//if(val instanceof BooleanValue) {
			//if(val instanceof GameRules.BooleanValue) {
				//boolean value = w.getGameRules().getBoolean(rule);
				//boolean value = w.getGameRules().getBoolean((RuleKey<BooleanValue>) rule);
				//boolean value = ((BooleanValue)val).get();
				//boolean value = ((GameRules.BooleanValue)val).get();
				//if(customDefaultValues.containsKey(rule) && customDefaultValues.get(rule) instanceof Boolean) {
				//if(customDefaultValues.containsKey(rule.toString()) && customDefaultValues.get(rule.toString()) instanceof Boolean) {
					//value = (Boolean) customDefaultValues.get(rule);
					//value = (Boolean) customDefaultValues.get(rule.toString());
				//}
				//cfg.get(CATEGORY_GAMERULES, rule, value, "How should the Gamerule " + rule + " be set?");
				//ToMeSetupMod.cfg.getBoolean(CATEGORY_GAMERULES, rule, value, "How should the Gamerule " + rule + " be set?");
				//ToMeSetupMod.cfg.getBoolean(rule.toString(), CATEGORY_GAMERULES, value, "How should the Gamerule " + rule + " be set?");
				//ToMeSetupMod.cfg.getBoolean(rule.toString(), CATEGORY_GAMERULES, (boolean) value, "How should the Gamerule " + rule + " be set?");
			//}
			//else if(w.getGameRules().areSameType(rule, ValueType.NUMERICAL_VALUE)) {
			//else if(GameRules.GAME_RULES.get(rule).toString().contains("Integer")) {
			//else if(val instanceof IntegerValue) {
			//else if(val instanceof GameRules.IntegerValue) {
				//int value = w.getGameRules().getInt(rule);
				//int value = w.getGameRules().getInt((RuleKey<IntegerValue>) rule);
				//int value = ((IntegerValue)val).get();
				//int value = ((GameRules.IntegerValue)val).get();
				//if(customDefaultValues.containsKey(rule) && customDefaultValues.get(rule) instanceof Integer) {
				//if(customDefaultValues.containsKey(rule.toString()) && customDefaultValues.get(rule.toString()) instanceof Integer) {
					//value = (Integer) customDefaultValues.get(rule);
					//value = (Integer) customDefaultValues.get(rule.toString());
				//}
				//cfg.get(CATEGORY_GAMERULES, rule, value, "How should the Gamerule " + rule + " be set?");
				//ToMeSetupMod.cfg.getInt(CATEGORY_GAMERULES, rule, value, "How should the Gamerule " + rule + " be set?");
				//ToMeSetupMod.cfg.getInt(rule.toString(), CATEGORY_GAMERULES, value, "How should the Gamerule " + rule + " be set?");
				//ToMeSetupMod.cfg.getInt(rule.toString(), CATEGORY_GAMERULES, (int) value, "How should the Gamerule " + rule + " be set?");
			//}
			//else if(w.getGameRules().areSameType(rule, ValueType.ANY_VALUE)) {
			//else {
				//String value = w.getGameRules().getString(rule);
				//if(customDefaultValues.containsKey(rule) && customDefaultValues.get(rule) instanceof String) {
				//if(customDefaultValues.containsKey(rule.toString()) && customDefaultValues.get(rule.toString()) instanceof String) {
					//value = (String) customDefaultValues.get(rule);
					//value = (String) customDefaultValues.get(rule.toString());
				//}
				//cfg.get(CATEGORY_GAMERULES, rule, value, "How should the Gamerule " + rule + " be set?");
				//ToMeSetupMod.cfg.getString(rule.toString(), CATEGORY_GAMERULES, value, "How should the Gamerule " + rule + " be set?");
				//ToMeSetupMod.cfg.getString(rule.toString(), CATEGORY_GAMERULES, (String) value, "How should the Gamerule " + rule + " be set?");
			//}
		//}
		try {
			for(Object rule:GameRules.GAME_RULES.keySet()) {
				Class<?> RuleKey = Class.forName("net.minecraft.world.GameRules$RuleKey");
				Method get = null;
				try {
					get = GameRules.class.getDeclaredMethod("func_223585_a", RuleKey);
				} catch (Exception e) {
					get = GameRules.class.getDeclaredMethod("get", RuleKey);
				}
				Object val = get.invoke(w.getGameRules(), rule);
				Class<?> BooleanValue = Class.forName("net.minecraft.world.GameRules$BooleanValue");
				Class<?> IntegerValue = Class.forName("net.minecraft.world.GameRules$IntegerValue");
				if(BooleanValue.isInstance(val)) {
					//Method get = null;
					get = null;
					try {
						get = BooleanValue.getMethod("func_223572_a");
					} catch (Exception e) {
						get = BooleanValue.getMethod("get");
					}
					boolean value = (boolean) get.invoke(val);
					if(customDefaultValues.containsKey(rule.toString()) && customDefaultValues.get(rule.toString()) instanceof Boolean) {
						value = (Boolean) customDefaultValues.get(rule.toString());
					}
					ToMeSetupMod.cfg.getBoolean(rule.toString(), CATEGORY_GAMERULES, (boolean) value, "How should the Gamerule " + rule + " be set?");
				}
				else if(IntegerValue.isInstance(val)) {
					//Method get = null;
					get = null;
					try {
						get = IntegerValue.getMethod("func_223560_a");
					} catch (Exception e) {
						get = IntegerValue.getMethod("get");
					}
					int value = (int) get.invoke(val);
					if(customDefaultValues.containsKey(rule.toString()) && customDefaultValues.get(rule.toString()) instanceof Integer) {
						value = (Integer) customDefaultValues.get(rule.toString());
					}
					ToMeSetupMod.cfg.getInt(rule.toString(), CATEGORY_GAMERULES, (int) value, "How should the Gamerule " + rule + " be set?");
				}
			}
		} catch (Throwable e) {
			try {
				Method getDefinitions = null;
				try {
					getDefinitions = GameRules.class.getDeclaredMethod("func_196231_c");
				} catch (Exception e2) {
					getDefinitions = GameRules.class.getDeclaredMethod("getDefinitions");
				}
				for(String rule:((Map<String, ?>)getDefinitions.invoke(w.getGameRules())).keySet()) {
					Class<?> Value = Class.forName("net.minecraft.world.GameRules$Value");
					Method getType = null;
					try {
						getType = Value.getDeclaredMethod("func_180254_e");
					} catch (Exception e2) {
						getType = Value.getDeclaredMethod("getType");
					}
					Method get = null;
					try {
						get = GameRules.class.getDeclaredMethod("func_196230_f", String.class);
					} catch (Exception e2) {
						get = GameRules.class.getDeclaredMethod("get", String.class);
					}
					Class<?> ValueType = Class.forName("net.minecraft.world.GameRules$ValueType");
					Method valueOf = ValueType.getMethod("valueOf", String.class);
					if(getType.invoke(get.invoke(w.getGameRules(), rule)) == valueOf.invoke(null, "BOOLEAN_VALUE")) {
						Method getBoolean = null;
						try {
							getBoolean = GameRules.class.getDeclaredMethod("func_82766_b", String.class);
						} catch (Exception e2) {
							getBoolean = GameRules.class.getDeclaredMethod("getBoolean", String.class);
						}
						boolean value = (boolean) getBoolean.invoke(w.getGameRules(), rule);
						if(customDefaultValues.containsKey(rule) && customDefaultValues.get(rule) instanceof Boolean) {
							value = (Boolean) customDefaultValues.get(rule);
						}
						ToMeSetupMod.cfg.getBoolean(rule.toString(), CATEGORY_GAMERULES, (boolean) value, "How should the Gamerule " + rule + " be set?");
					}
					else if(getType.invoke(get.invoke(w.getGameRules(), rule)) == valueOf.invoke(null, "NUMERICAL_VALUE")) {
						Method getBoolean = null;
						try {
							getBoolean = GameRules.class.getDeclaredMethod("func_180263_c", String.class);
						} catch (Exception e2) {
							getBoolean = GameRules.class.getDeclaredMethod("getInt", String.class);
						}
						boolean value = (boolean) getBoolean.invoke(w.getGameRules(), rule);
						if(customDefaultValues.containsKey(rule) && customDefaultValues.get(rule) instanceof Boolean) {
							value = (Boolean) customDefaultValues.get(rule);
						}
						ToMeSetupMod.cfg.getBoolean(rule.toString(), CATEGORY_GAMERULES, (boolean) value, "How should the Gamerule " + rule + " be set?");
					}
				}
			} catch (Exception e2) {
				ToMeSetupMod.logger.catching(e);
				ToMeSetupMod.logger.catching(e2);
			}
		}
		initGamerules = false;
	}
	
	//private void readGamerulesConfig(Configuration cfg) {
	private void readGamerulesConfig(CommentedFileConfig cfg) {
		//if(cfg.hasCategory(CATEGORY_GAMERULES)) {
		if(cfg.contains(CATEGORY_GAMERULES)) {
			//ConfigCategory rules = cfg.getCategory(CATEGORY_GAMERULES);
			//Map<String, Property> rulesMap = rules.getValues();
			//Map<String, Object> rulesMap = cfg.valueMap();
			Map<String, Object> rulesMap = ((AbstractCommentedConfig)cfg.get(CATEGORY_GAMERULES)).valueMap();
			for(String rule:rulesMap.keySet()) {
				//ToMeSetupMod.logger.info("key: " + rule + " value: " + rulesMap.get(rule).getClass());
				//if(rule.equalsIgnoreCase("enableGamerules")) {
					//enableGamerules = rulesMap.get(rule).getBoolean();
				//}
				//else if(rule.equalsIgnoreCase("pvp")) {
					//pvp = rulesMap.get(rule).getBoolean();
				//}
				//else {
				if(!rule.equalsIgnoreCase("enableGamerules") && !rule.equalsIgnoreCase("pvp")) {
					//Property.Type t = rulesMap.get(rule).getType();
					//if(t.equals(Property.Type.BOOLEAN)) {
					if(rulesMap.get(rule) instanceof Boolean) {
						//configValues.put(rule, rulesMap.get(rule).getBoolean());
						configValues.put(rule, rulesMap.get(rule));
					}
					//else if(t.equals(Property.Type.INTEGER)) {
					else if(rulesMap.get(rule) instanceof Integer) {
						//configValues.put(rule, rulesMap.get(rule).getInt());
						configValues.put(rule, rulesMap.get(rule));
					}
					//else if(t.equals(Property.Type.STRING)) {
						//configValues.put(rule, rulesMap.get(rule).getString());
					//}
				}
			}
		}
	}
	
	public String getString(String name, String category, String defaultValue, String comment) {
		String path = category + "." + name;
		if(!config.contains(path) || !(config.get(path) instanceof String)) {
			config.set(path, defaultValue);
		}
		if(comment != null) {
			config.setComment(path, comment + String.format(" [default: %s]", defaultValue));
		}
		return config.get(path);
	}
	
	public boolean getBoolean(String name, String category, boolean defaultValue, String comment) {
		String path = category + "." + name;
		if(!config.contains(path) || !(config.get(path) instanceof Boolean)) {
			config.set(path, defaultValue);
		}
		if(comment != null) {
			config.setComment(path, comment + String.format(" [default: %b]", defaultValue));
		}
		return config.get(path);
	}
	
	public int getInt(String name, String category, int defaultValue, String comment) {
		String path = category + "." + name;
		if(!config.contains(path) || !(config.get(path) instanceof Integer)) {
			config.set(path, defaultValue);
		}
		if(comment != null) {
			config.setComment(path, comment + String.format(" [default: %d]", defaultValue));
		}
		return config.get(path);
	}
	
	public int getInt(String name, String category, int defaultValue, int min, int max, String comment) {
		String path = category + "." + name;
		if(!config.contains(path) || !(config.get(path) instanceof Integer) || ((int)config.get(path)) < min || ((int)config.get(path)) > max) {
			config.set(path, defaultValue);
		}
		if(comment != null) {
			config.setComment(path, comment + String.format(" [range: %d ~ %d, default: %d]", min, max, defaultValue));
		}
		return config.get(path);
	}
	
	public String[] getStringList(String name, String category, String[] defaultValue, String comment) {
		return getStringList(name, category, Arrays.asList(defaultValue), comment).toArray(new String[0]);
	}
	
	public List<String> getStringList(String name, String category, List<String> defaultValue, String comment) {
		String path = category + "." + name;
		if(!config.contains(path) || !(config.get(path) instanceof List<?>)) {
			config.set(path, defaultValue);
		}
		List<?> list = config.<List<?>>get(path);
		if(comment != null) {
			config.setComment(path, comment + String.format(" [default: %s]", transformList(defaultValue)));
		}
		return list.stream().map(Object::toString).collect(Collectors.toCollection(ArrayList::new));
	}
	
	private String transformList(List<String> strings) {
		String string = "[";
		for(String str:strings) {
			string += str + ", ";
		}
		string = string.substring(0, string.length() - 2) + "]";
		return string;
	}
	
}