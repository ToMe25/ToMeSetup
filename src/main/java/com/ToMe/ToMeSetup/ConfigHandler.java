package com.ToMe.ToMeSetup;

import java.io.File;

import com.ToMe.ToMeSetup.api.Messager;
import com.ToMe.ToMeSetup.api.StartItems.impl.StartItemContainer;
import com.ToMe.ToMeSetup.api.StartItems.impl.StartItemProvider;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	
	public Configuration config;
	//GENERAL
	public static boolean enableTooltips;
	//GAMERULES
	public static final String CATEGORY_GAMERULES = "gamerules";
	public static boolean keepInventory;
	public static boolean mobGriefing;
	public static boolean doFireTick;
	public static boolean doMobSpawning;
	public static int spawnRadius;
	public static boolean doDaylightCycle;
	public static boolean doTileDrops;
	public static boolean doMobLoot;
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
	public static int groundMeta;
	public static int liquidMeta;
	public static int solidMeta;
	public static String groundOreDict;
	public static String liquidOreDict;
	public static String solidOreDict;
	public static int groundNumber;
	public static int liquidNumber;
	public static int solidNumber;
	//SPAWN ITEMS
	public static final String CATEGORY_SPAWN_ITEMS = "start_items";
	public static boolean enableStartItems;
	public static boolean startItemsOnRespawn;
	public static String[] StartItems = {"minecraft:log"};
	public static String[] StartItemMeta = {"-1"};
	public static int[] StartItemMetas;
	public static String[] StartItemCount = {"32"};
	public static int[] StartItemCounts;
	public static String[] StartItemOreDicts = {"treeSapling"};
	public static String[] StartItemOreDictNumber = {"0"};
	public static int[] StartItemOreDictNumbers;
	public static String[] StartItemOreDictMeta = {"-1"};
	public static int[] StartItemOreDictMetas;
	public static String[] StartItemOreDictCount = {"16"};
	public static int[] StartItemOreDictCounts;
	
	public ConfigHandler(FMLPreInitializationEvent e) {
		config = new Configuration(new File(e.getModConfigurationDirectory(), "ToMeSetup.cfg"));
		try {
            config.load();
            InitConfig(config);
        } catch (Exception e1) {
        	e1.printStackTrace();
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
            useConfig();
        }
		//InitConfig(config);
	}
	
	public static void InitConfig(Configuration cfg) {
		//ConfigCategory gamerules = new ConfigCategory("gamerules");
		//cfg.addCustomCategoryComment("gamerules", "Test");
		
		//GENERAL
		cfg.addCustomCategoryComment(Configuration.CATEGORY_GENERAL, "The General Configs.");
		enableTooltips = cfg.getBoolean("enableErrorTooltips", Configuration.CATEGORY_GENERAL, true, "Enables/Disables the Chat Tooltips(Server Side only).");
		
		//GAMERULES
		cfg.addCustomCategoryComment(CATEGORY_GAMERULES, "How this gamerules should set on World load.");
		keepInventory = cfg.getBoolean("keepInventory", CATEGORY_GAMERULES, true, "The gamerule keepInventory determines whether the Pleyer keeps his Items on death in Inventory. To set this manually do \"/gamerule keepInventory true\"!");
		mobGriefing = cfg.getBoolean("mobGriefing", CATEGORY_GAMERULES, false, "The gamerule mobGriefing determines whether Mobs con break Blocks or pickup Items. To set this manually do \"/gamerule mobGriefing false\"!");
		doFireTick = cfg.getBoolean("doFireTick", CATEGORY_GAMERULES, true, "The gamerule doFireTick determines whether Spreads and destroys Blocks. To set this manually do \"/gamerule doFireTick true\"!");
		doMobSpawning = cfg.getBoolean("doMobSpawning", CATEGORY_GAMERULES, true, "The gamerule doMobSpawning determines whether Mobs Spawn int the World. To set this manually do \"/gamerule doMobSpawning true\"!");
		spawnRadius = cfg.getInt("spawnRadius", CATEGORY_GAMERULES, 0, 0, Integer.MAX_VALUE, "The gamerule spawnRadius determines the Radius to spawn around the Worldspawn. To set this manually do \"/gamerule spawnRadius 0\"!");
		doDaylightCycle = cfg.getBoolean("doDaylightCycle", CATEGORY_GAMERULES, true, "The gamerule doDaylightCycle determines whether the Minecraft Sun and Moon moves over the Sky. To Set this manually do \"/gamerule doDaylightCycle true\"!");
		doTileDrops = cfg.getBoolean("doTileDrops", CATEGORY_GAMERULES, true, "The gamerule doTileDrops determines whether you get Items by Mining Blocks. To set this manually do \"/gamerule doTileDrops true\"!");
		doMobLoot = cfg.getBoolean("doMobLoot", CATEGORY_GAMERULES, true, "The gamerule doMobLoot determines whether you get Items by Killing Mobs. To set this manually do \"/gamerule doMobLoot true\"");
		
		//WORLDSPAWN
		cfg.addCustomCategoryComment(CATEGORY_WORLDSPAWN, "What to do with the Worldspawn.");
		setWorldspawn = cfg.getBoolean("setWorldSpawn", CATEGORY_WORLDSPAWN, true, "Enables / Disables this Category! If enabled it will set the Worldspawn to X:worldSpawnX, Z:worldSpawnZ, Y:1 Block over the Highest non air Block.");
		worldSpawnX = cfg.getInt("worldSpawnX", CATEGORY_WORLDSPAWN, 0, -30000000, 30000000, "The X psoition of the Worldspawn.");
		worldSpawnZ = cfg.getInt("worldSpawnZ", CATEGORY_WORLDSPAWN, 0, -30000000, 30000000, "The Z psoition of the Worldspawn.");
		//setBedrock = cfg.getBoolean("setBedrock", CATEGORY_WORLDSPAWN, true, "Replace the block at X:worldSpawnX, Z:worldSpawnZ, Y:0 with Bedrock.");
		//setBedrock = cfg.getBoolean("setGround", CATEGORY_WORLDSPAWN, true, "Replace the block at X:worldSpawnX, Z:worldSpawnZ, Y:0 with the Block defined by groundBlock.");
		setBedrock = cfg.getBoolean("replaceGround", CATEGORY_WORLDSPAWN, true, "Replace the block at X:worldSpawnX, Z:worldSpawnZ, Y:0 with the Block defined by ground2Replace.");
		//replaceLiquid = cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with Grass if it is a Liquid Block.");
		//replaceLiquid = cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with the Block defined by liquidReplace if it is a Liquid Block.");
		replaceLiquid = cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block direct under the Worldspawn with the Block defined by liquid2Replace if it is a Liquid Block.");
		//replaceSolid = cfg.getBoolean("replaceSolid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with the Block defined by solidReplace if it is not a Liquid Block.");
		replaceSolid = cfg.getBoolean("replaceSolid", CATEGORY_WORLDSPAWN, true, "Replace the block direct under the Worldspawn with the Block defined by solid2Replace if it is not a Liquid Block.");
		//groundBlock = cfg.getString("groundBlock", CATEGORY_WORLDSPAWN, "minecraft:bedrock", "The Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 with this Block.");
		//liquidReplace = cfg.getString("liquidReplace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to set by replaceLiquid.");
		//solidReplace = cfg.getString("solidReplace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to set by replaceSolid.");
		groundEnableOreDict = cfg.getBoolean("ground1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set at X:worldSpawnX, Z:worldSpawnZ, Y:0. Dissable to Use the Registry Name.");
		liquidEnableOreDict = cfg.getBoolean("liquid1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set direct under the Worldspawn if it is a Liquid Block. Dissable to Use the Registry Name.");
		solidEnableOreDict = cfg.getBoolean("solid1EnableOreDict", CATEGORY_WORLDSPAWN, false, "Enable to use the OreDictionary to get the Block to set direct under the Worldspawn if it is a not Liquid Block. Dissable to Use the Registry Name.");
		groundBlock = cfg.getString("ground2Replace", CATEGORY_WORLDSPAWN, "minecraft:bedrock", "The Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 if ground1EnableOreDict is dissabled.");
		liquidReplace = cfg.getString("liquid2Replace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to Set direct under the Worldspawn if it is a Liquid Block and liquid1EnableOreDict is dissabled.");
		solidReplace = cfg.getString("solid2Replace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to Set direct under the Worldspawn if it is a Liquid Block and solid1EnableOreDict is dissabled.");
		groundOreDict = cfg.getString("ground3OreDict", CATEGORY_WORLDSPAWN, "bedrock", "The OreDictinary Name with the Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 if ground1EnableOreDict is enabled.");
		liquidOreDict = cfg.getString("liquid3OreDict", CATEGORY_WORLDSPAWN, "grass", "The OreDictinary Name with the Block to Set direct under the Worldspawn if it is a Liquid Block and liquid1EnableOreDict is enabled.");
		solidOreDict = cfg.getString("solid3OreDict", CATEGORY_WORLDSPAWN, "grass", "The OreDictinary Name with the Block to Set direct under the Worldspawn if it is not a Liquid Block and solid1EnableOreDict is enabled.");
		groundMeta = cfg.getInt("ground4Meta", CATEGORY_WORLDSPAWN, -1, -1, 255, "The Meatdata for the Block to set at X:worldSpawnX, Z:worldSpawnZ, Y:0. Use -1 for the Default Metadata.");
		liquidMeta = cfg.getInt("liquid4Meta", CATEGORY_WORLDSPAWN, -1, -1, 255, "The Meatdata for the Block to set direct under the Worldspawn if it is a Liquid Block. Use -1 for the Default Metadata.");
		solidMeta = cfg.getInt("solid4Meta", CATEGORY_WORLDSPAWN, -1, -1, 255, "The Meatdata for the Block to set direct under the Worldspawn if it is not a Liquid Block. Use -1 for the Default Metadata.");
		groundNumber = cfg.getInt("ground5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use for replaceGround.");
		liquidNumber = cfg.getInt("liquid5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use for replaceLiquid.");
		solidNumber = cfg.getInt("solid5Number", CATEGORY_WORLDSPAWN, 0, 0, Integer.MAX_VALUE, "The Number of Blocks registered before the Block to use. for replaceSolid");
		
		//SPAWN ITEMS
		cfg.addCustomCategoryComment(CATEGORY_SPAWN_ITEMS, "Wich Start Items Players should get.");
		enableStartItems = cfg.getBoolean("enableStartItems", CATEGORY_SPAWN_ITEMS, true, "Enables/Dissables this Category.");
		startItemsOnRespawn = cfg.getBoolean("RespawnStartItems", CATEGORY_SPAWN_ITEMS, false, "Should the Playerget the Items everytime after respawning?");
		StartItems = cfg.getStringList("Start1Items", CATEGORY_SPAWN_ITEMS, StartItems, "Determine Wich Start Items all Players should get.");
		StartItemMeta = cfg.getStringList("Start2Metas", CATEGORY_SPAWN_ITEMS, StartItemMeta, "The Item Meta for the direct defined Items. Write in the same sequence like the Start1Items.");
		StartItemCount = cfg.getStringList("Start3Count", CATEGORY_SPAWN_ITEMS, StartItemCount, "How many from this Items should the Player get? Write in the same sequence like the Start1Items.");
		StartItemOreDicts = cfg.getStringList("Start4OreDicts", CATEGORY_SPAWN_ITEMS, StartItemOreDicts, "Determine Wich Start Items all Players should get. Use OreDictionary.");
		StartItemOreDictNumber = cfg.getStringList("Start5OreNumbers", CATEGORY_SPAWN_ITEMS, StartItemOreDictNumber, "How many Items are before this Item in the same OreDictionary Name? Write in the same sequence like the Start4OreDicts.");
		StartItemOreDictMeta = cfg.getStringList("Start6OreMetas", CATEGORY_SPAWN_ITEMS, StartItemOreDictMeta, "The Item Meta for the Items defined via OreDict. Write in the same sequence like the Start4OreDicts.");
		StartItemOreDictCount = cfg.getStringList("Start7OreCount", CATEGORY_SPAWN_ITEMS, StartItemOreDictCount, "How many from this Items should the Player get? Write in the same sequence like the Start4OreDicts.");
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
		
		StartItemOreDictNumbers = new int[StartItemOreDictNumber.length];
		i = 0;
		for(String s:StartItemOreDictNumber) {
			try {
				//i++;
				StartItemOreDictNumbers[i] = Integer.parseInt(s);
				i++;
			} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				StartItemOreDictNumbers[i] = 0;
				i++;
				ToMeSetupMod.logger.catching(e);
			}
		}
		
		StartItemOreDictMetas = new int[StartItemOreDictMeta.length];
		i = 0;
		for(String s:StartItemOreDictMeta) {
			try {
				//i++;
				StartItemOreDictMetas[i] = Integer.parseInt(s);
				i++;
			} catch (Exception e) {
				// TODO: handle exception
				//e.printStackTrace();
				StartItemOreDictMetas[i] = 0;
				i++;
				ToMeSetupMod.logger.catching(e);
			}
		}
		
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
	
	protected static void useConfig() {
		Messager.enableTooltips = enableTooltips;
		StartItemProvider.startItemsOnRespawn = startItemsOnRespawn;
		int i = 0;
		for(String s:StartItems) {
			StartItemProvider.instance.addStartItem(new StartItemContainer(s, StartItemCounts.length > i ? StartItemCounts[i] : 1, StartItemMetas.length > i ? StartItemMetas[i] : 0, 0, false));
			i++;
		}
		i = 0;
		for(String s:StartItemOreDicts) {
			//System.out.println("ConfigHandler: " + (StartItemOreDictNumbers.length > i ? StartItemOreDictNumbers[i] : 0));
			StartItemProvider.instance.addStartItem(new StartItemContainer(s, StartItemOreDictCounts.length > i ? StartItemOreDictCounts[i] : 1, StartItemOreDictMetas.length > i ? StartItemOreDictMetas[i] : -1, StartItemOreDictNumbers.length > i ? StartItemOreDictNumbers[i] : 0, true));
			i++;
		}
	}
	
}