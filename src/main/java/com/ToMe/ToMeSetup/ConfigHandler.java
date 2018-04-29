package com.ToMe.ToMeSetup;

import java.io.File;

import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ConfigHandler {
	
	public Configuration config;
	public static final String CATEGORY_GAMERULES = "gamerules";
	public static boolean keepInventory;
	public static boolean mobGriefing;
	public static boolean doFireTick;
	public static boolean doMobSpawning;
	public static int spawnRadius;
	public static boolean doDaylightCycle;
	public static boolean doTileDrops;
	public static boolean doMobLoot;
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
        }
		//InitConfig(config);
	}
	
	public static void InitConfig(Configuration cfg) {
		//ConfigCategory gamerules = new ConfigCategory("gamerules");
		//cfg.addCustomCategoryComment("gamerules", "Test");
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
		setBedrock = cfg.getBoolean("setGround", CATEGORY_WORLDSPAWN, true, "Replace the block at X:worldSpawnX, Z:worldSpawnZ, Y:0 with the Block defined by groundBlock.");
		//replaceLiquid = cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with Grass if it is a Liquid Block.");
		replaceLiquid = cfg.getBoolean("replaceLiquid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with the Block defined by liquidReplace if it is a Liquid Block.");
		replaceSolid = cfg.getBoolean("replaceSolid", CATEGORY_WORLDSPAWN, true, "Replace the block under the Worldspawn with the Block defined by solidReplace if it is not a Liquid Block.");
		groundBlock = cfg.getString("groundBlock", CATEGORY_WORLDSPAWN, "minecraft:bedrock", "The Block to Set at Position X:worldSpawnX, Z:worldSpawnZ, Y:0 with this Block.");
		liquidReplace = cfg.getString("liquidReplace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to set by replaceLiquid.");
		solidReplace = cfg.getString("solidReplace", CATEGORY_WORLDSPAWN, "minecraft:grass", "The Block to set by solidLiquid.");
		//System.out.println("Config Created!");
	}
	
}