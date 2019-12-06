package com.ToMe.ToMeSetup;

//import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Random;
//import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.ToMe.ToMeSetup.api.Messager;

import net.minecraft.block.Block;
//import net.minecraft.block.BlockHorizontal;
//import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
//import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.BlockItem;
//import net.minecraft.block.properties.IProperty;
//import net.minecraft.block.properties.PropertyInteger;
//import net.minecraft.block.state.IBlockState;
//import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
//import net.minecraft.resources.ResourcePackInfo;
//import net.minecraft.resources.SimpleReloadableResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tags.BlockTags;
//import net.minecraft.item.ItemBlock;
//import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
//import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
//import net.minecraft.world.chunk.IChunk;
//import net.minecraft.world.GameRules.BooleanValue;
//import net.minecraft.world.GameRules.IntegerValue;
//import net.minecraft.world.GameRules.RuleKey;
//import net.minecraft.world.IWorld;
import net.minecraft.world.dimension.Dimension;
//import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.MinecraftForge;
//import net.minecraftforge.event.world.ChunkEvent;
//import net.minecraftforge.event.entity.EntityJoinWorldEvent;
//import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
//import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
//import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
//import net.minecraftforge.fml.common.Mod.EventHandler;
//import net.minecraftforge.fml.common.Mod.Instance;
//import net.minecraftforge.fml.common.event.FMLInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
//import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
//import net.minecraftforge.fml.common.eventhandler.EventPriority;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
//import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
//import net.minecraftforge.fml.packs.ResourcePackLoader;
import net.minecraftforge.registries.ForgeRegistries;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
//import net.minecraftforge.oredict.OreDictionary;
//import net.minecraft.command.server.CommandSetBlock;
//import net.minecraftforge.fml.config.ModConfig;

//@EventBusSubscriber
//@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
//@Mod(ToMeSetupMod.MODID)
@Mod(value = ToMeSetupMod.MODID)
//@Mod(modid="tomesetup",name="ToMe Setup", version="1.0")
//@Mod(modid = ToMeSetupMod.MODID, name = ToMeSetupMod.NAME, version = ToMeSetupMod.VERSION, acceptedMinecraftVersions = ToMeSetupMod.MCVERSION)
public class ToMeSetupMod {
	
	//@Instance
	public static ToMeSetupMod Instance;
	
	public static final String MODID = "tomesetup";
	public static final String NAME = "ToMe Setup";
	public static final String VERSION = "1.0";
	public static final String MCVERSION = "[1.14,1.14.4]";
	//public static final String API_VERSION = "1.0";
	//public static final String API_OWNER = "ToMeSetup";
	//public static final String API_PROVIDES = "A Api to add and remove StartItems to/from ToMeSetup.";
	
	public static ConfigHandler cfg;
	
	//private static StartItemCommand startItemCMD = new StartItemCommand();
	
	//public static StartItemProvider SIP;
	
	//protected boolean setuped = false;
	
	private boolean groundError;
	private boolean liquidError;
	private boolean solidError;
	
	//public static Logger logger;
	public static Logger logger = LogManager.getLogger();
	
	//protected List<Integer> dims;
	protected List<Dimension> dims;
	
	/**
	 * A map used to parse old OreDictionary names to new Tags
	 */
	protected static Map<String, String> oreDictPrefixes;
	
	//private boolean gamerulesInitialzed = false;
	
	public ToMeSetupMod() {
		MinecraftForge.EVENT_BUS.register(this);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
		initOreDictPrefixes();
	}
	
	//@SubscribeEvent
	//@EventHandler
	//public void preInit(FMLPreInitializationEvent e) {
	public void commonSetup(FMLCommonSetupEvent e) {
		//logger = e.getModLog();
		//cfg = new ConfigHandler(e);
		cfg = new ConfigHandler();
		cfg.load();
		GameruleHandler.registerGamerules();
		//SIP = new StartItemProvider();
		//logger = e.getModLog();
		//MinecraftForge.EVENT_BUS.register(this);
		//MinecraftForge.EVENT_BUS.register(SIP);
		//dims = new ArrayList<Integer>();
		dims = new ArrayList<Dimension>();
		//OreDictionary.registerOre("bedrock", Blocks.BEDROCK);
		//MinecraftForge.TERRAIN_GEN_BUS.register(this);
	}
	
	//@EventHandler
	//public void Init(FMLInitializationEvent e) {
		//Messager.initLangMap();
	//}
	
	//@EventHandler
	//public void postInit(FMLPostInitializationEvent e) {
		//if(ConfigHandler.enableStartItems) {
			//StartItemProvider.validateStartItemConfig();
			//com.ToMe.ToMeSetup.api.StartItems.impl.StartItemProvider.instance.validateStartItems();
		//}
	//}
	
	/*public static void registerMessager(Messager m) {
		MinecraftForge.EVENT_BUS.register(m);
	}*/
	
	//@SubscribeEvent
	//public void serverAboutToStart(FMLServerAboutToStartEvent e) {
		//if(!e.getServer().getResourceManager().getResourceNamespaces().contains(MODID)) {
		//if(e.getServer().isDedicatedServer()) {
			//e.getServer().getResourceManager().addResourcePack(ResourcePackLoader.getResourcePackFor(MODID).get());
		//}
		//Collection<ResourcePackInfo> enabled = e.getServer().resourcePacks.getEnabledPacks();
		//for(ResourcePackInfo info:e.getServer().resourcePacks.getAllPacks()) {
			//if(info.getName() == "mod:" + MODID && !enabled.contains(info)) {
				//enabled.add(info);
			//}
		//}
		//e.getServer().resourcePacks.setEnabledPacks(enabled);
	//}
	
	@SubscribeEvent
	//@EventHandler
	public void onServerLoad(FMLServerStartingEvent e) {
		//e.registerServerCommand(startItemCMD);
		//e.getCommandDispatcher().register(startItemCMD);
		StartItemCommand.register(e.getCommandDispatcher());
		if(ConfigHandler.enableGamerules) {
			e.getServer().setAllowPvp(ConfigHandler.pvp);
		}
		if(ConfigHandler.enableStartItems) {
			com.ToMe.ToMeSetup.api.StartItems.impl.StartItemProvider.instance.validateStartItems();
		}
	}
	
	//@SubscribeEvent
	//@EventHandler
	//public void onWorldCreated(WorldEvent.CreateSpawnPosition e) {
	//@SubscribeEvent(priority = EventPriority.LOWEST)
	//public void onWorldCreated(Decorate e) {
	//@SubscribeEvent
	//public void onWorldCreated(DecorateBiomeEvent.Pre e) {
	//public void onWorldCreated(DecorateBiomeEvent.Post e) {
	//public void onWorldCreated(DecorateBiomeEvent.Decorate e) {
	//public void onWorldCreated(DecorateBiomeEvent e) {
	//public void onWorldCreated(EntityJoinWorldEvent e) {
		//if(!gamerulesInitialzed) {
			//GameruleHandler.onWorldLoad(e.getWorld());
			//gamerulesInitialzed = true;
		//}
		//System.out.println("Create");
		//WorldProvider pro = e.getWorld().provider;
		//if(!dims.contains(pro.getDimension())) {
		//if(!dims.contains(e.getWorld().getDimension())) {
			//setup(e.getWorld());
			//dims.add(pro.getDimension());
			//dims.add(e.getWorld().getDimension());
		//}
		//if(!setuped) {
			//if(e.getWorld().provider.getDimension() == 0) {
				//setup(e.getWorld());
				//setuped = true;
			//}
			//setup(e.getWorld());
			//setuped = true;
		//}
		//System.out.println("Hello World!");
		//setup(e.getWorld());
		//e.getWorld().provider.
	//}
	
	/*@SubscribeEvent
	public void onChunkLoad(ChunkEvent.Load e) {
		IWorld world = null;
		if(e.getWorld() != null) {
			world = e.getWorld();
		}
		else if (e.getChunk().getWorldForge() != null) {
			world = e.getChunk().getWorldForge();
		}
		if(world != null && world.getChunkProvider().chunkExists(ConfigHandler.worldSpawnX / 16, ConfigHandler.worldSpawnZ / 16)) {
			//if(e.getChunk().getPos().getXStart() == ConfigHandler.worldSpawnX && e.getChunk().getPos().getZStart() == ConfigHandler.worldSpawnZ && !dims.contains(world.getDimension())) {
			if(chunkContainsCoords(e.getChunk(), ConfigHandler.worldSpawnX, ConfigHandler.worldSpawnZ) && !dims.contains(world.getDimension())) {
				//System.out.println("cload");
				dims.add(world.getDimension());
				setup(e.getWorld().getWorld());
				//dims.add(world.getDimension());
			}
		}
	}*/
	
	@SubscribeEvent
	public void onExplosion(ExplosionEvent.Detonate e) {
		//if(!e.getWorld().getGameRules().getBoolean(GameruleHandler.EXPLOSIONBLOCKDAMAGE)) {
		//if(ConfigHandler.explosionBlockDamage && !e.getWorld().getGameRules().getBoolean(GameruleHandler.EXPLOSIONBLOCKDAMAGE)) {
			//e.getAffectedBlocks().clear();
		//}
		try {
			Class<?> RuleKey = Class.forName("net.minecraft.world.GameRules$RuleKey");
			//Method getBoolean = GameRules.class.getDeclaredMethod("getBoolean", RuleKey.class);
			Method getBoolean = null;
			try {
				getBoolean = GameRules.class.getDeclaredMethod("func_223586_b", RuleKey);
			} catch (Exception e2) {
				getBoolean = GameRules.class.getDeclaredMethod("getBoolean", RuleKey);
			}
			if(ConfigHandler.explosionBlockDamage && !(boolean)getBoolean.invoke(e.getWorld().getGameRules(), GameruleHandler.EXPLOSIONBLOCKDAMAGE)) {
				e.getAffectedBlocks().clear();
			}
		} catch (Exception e2) {
			try {
				Method getBoolean = null;
				try {
					getBoolean = GameRules.class.getDeclaredMethod("func_82766_b", String.class);
				} catch (Exception e3) {
					getBoolean = GameRules.class.getDeclaredMethod("getBoolean", String.class);
				}
				//if(ConfigHandler.explosionBlockDamage && !(boolean)getBoolean.invoke(e.getWorld().getGameRules(), GameruleHandler.EXPLOSIONBLOCKDAMAGE.toString())) {
				if(ConfigHandler.explosionBlockDamage && !(boolean)getBoolean.invoke(e.getWorld().getGameRules(), GameruleHandler.EXPLOSIONBLOCKDAMAGE)) {
					e.getAffectedBlocks().clear();
				}
			} catch (Exception e3) {
				logger.catching(e2);
				logger.catching(e3);
			}
		}
	}
	
	//@SideOnly(Side.SERVER)
	//@SubscribeEvent(priority = EventPriority.LOWEST)
	@SubscribeEvent(priority = EventPriority.LOW)
	//@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		//System.out.println("load");
		//System.out.println("Load");
		//cfg.onWorldLoad(e.getWorld());
		cfg.onWorldLoad(e.getWorld().getWorld());
		//GameruleHandler.onWorldLoad(e.getWorld());
		//GameruleHandler.onWorldLoad(e.getWorld().getWorld());
		initWorld(e.getWorld().getWorld());
		//if(!setuped) {
			//setup(e.getWorld());
			setup(e.getWorld().getWorld());
			//setuped = true;
		//}
		//Set<String> namespaces = ((SimpleReloadableResourceManager)e.getWorld().getWorld().getServer().getResourceManager()).resourceNamespaces;
		//if(!namespaces.contains(MODID)) {
			//namespaces.add(MODID);
		//}
		//if(!e.getWorld().getWorld().getServer().getResourceManager().getResourceNamespaces().contains(MODID)) {
		//if(e.getWorld().getWorld().getServer().isDedicatedServer()) {
			//e.getWorld().getWorld().getServer().getResourceManager().addResourcePack(ResourcePackLoader.getResourcePackFor(MODID).get());
		//}
		//Collection<ResourcePackInfo> enabled = e.getWorld().getWorld().getServer().resourcePacks.getEnabledPacks();
		//for(ResourcePackInfo info:e.getWorld().getWorld().getServer().resourcePacks.getAllPacks()) {
			//if(info.getName() == "mod:" + MODID && !enabled.contains(info)) {
				//enabled.add(info);
			//}
		//}
		//e.getWorld().getWorld().getServer().resourcePacks.setEnabledPacks(enabled);
		/*if(!e.getWorld().isRemote) {
			if(!e.getWorld().isRemote) {
				e.getWorld().getGameRules().setOrCreateGameRule("keepInventory", "" + ConfigHandler.keepInventory);
				e.getWorld().getGameRules().setOrCreateGameRule("mobGriefing", "" + ConfigHandler.mobGriefing);
				e.getWorld().getGameRules().setOrCreateGameRule("doFireTick", "" + ConfigHandler.doFireTick);
				e.getWorld().getGameRules().setOrCreateGameRule("doMobSpawning", "" + ConfigHandler.doMobSpawning);
				e.getWorld().getGameRules().setOrCreateGameRule("spawnRadius", "" + ConfigHandler.spawnRadius);
				if(ConfigHandler.setWorldspawn == true) {
					if(ConfigHandler.setBedrock) {
						BlockPos bedrock = new BlockPos(ConfigHandler.worldSpawnX, 0, ConfigHandler.worldSpawnZ);
						IBlockState bs = Blocks.BEDROCK.getDefaultState();
						e.getWorld().setBlockState(bedrock, bs);
					}
					BlockPos p = new BlockPos(ConfigHandler.worldSpawnX, e.getWorld().getHeight(), ConfigHandler.worldSpawnZ);
					//System.out.println("ToMeSetup: " + p.getY());
					p = e.getWorld().getTopSolidOrLiquidBlock(p);
					//System.out.println("ToMeSetup: " + p.getY());
					//p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
					while(!e.getWorld().isAirBlock(p)) {
						p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
					}
					//p.down(-1);
					//p.add(0, 1, 0);
					//System.out.println("ToMeSetup: " + p.getY());
					//System.out.println(e.getWorld().getBlockState(p).);
					e.getWorld().setSpawnPoint(p);
					//System.out.println("World Spawn Y = " + e.getWorld().getSpawnPoint().getY());
					if(ConfigHandler.replaceLiquid) {
						p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
						IBlockState blockstate = e.getWorld().getBlockState(p);
						if(blockstate.getBlock() instanceof BlockLiquid) {
							IBlockState bs = Blocks.GRASS.getDefaultState();
							e.getWorld().setBlockState(p, bs);
						}
					}
				}
			}
		}*/
		/*e.getWorld().getGameRules().setOrCreateGameRule("keepInventory", "" + ConfigHandler.keepInventory);
		e.getWorld().getGameRules().setOrCreateGameRule("mobGriefing", "" + ConfigHandler.mobGriefing);
		e.getWorld().getGameRules().setOrCreateGameRule("doFireTick", "" + ConfigHandler.doFireTick);
		e.getWorld().getGameRules().setOrCreateGameRule("doMobSpawning", "" + ConfigHandler.doMobSpawning);
		e.getWorld().getGameRules().setOrCreateGameRule("spawnRadius", "" + ConfigHandler.spawnRadius);
		if(ConfigHandler.setWorldspawn == true) {
			BlockPos p = new BlockPos(ConfigHandler.worldSpawnX, e.getWorld().getHeight(), ConfigHandler.worldSpawnZ);
			//System.out.println("ToMeSetup: " + p.getY());
			p = e.getWorld().getTopSolidOrLiquidBlock(p);
			//System.out.println("ToMeSetup: " + p.getY());
			p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
			//p.down(-1);
			//p.add(0, 1, 0);
			//System.out.println("ToMeSetup: " + p.getY());
			e.getWorld().setSpawnPoint(p);
			System.out.println("World Spawn Y = " + e.getWorld().getSpawnPoint().getY());
		}*/
	}
	
	//@SuppressWarnings("unchecked")
	public void setup(World w) {
		//Messager.sendMissingBlock("Test", 4);//A Test!
		if(!w.isRemote) {
			//if(!w.isRemote) {
				//if(ConfigHandler.enableGamerules == true) {
					//for(String rule:ConfigHandler.configValues.keySet()) {
					//for(RuleKey<?> rule:GameRules.GAME_RULES.keySet()) {
						//w.getGameRules().setOrCreateGameRule(rule, "" + ConfigHandler.configValues.get(rule));
						//w.getGameRules().get(GameruleHandler.EXPLOSIONBLOCKDAMAGE).set((boolean)ConfigHandler.configValues.get(rule), w.getServer());
						//if(ConfigHandler.configValues.containsKey(rule.toString())) {
							//Object value = ConfigHandler.configValues.get(rule.toString());
							//if(value instanceof Boolean) {
								//try {
									//w.getGameRules().get((RuleKey<BooleanValue>) rule).set((boolean) value, w.getServer());
								//} catch (Exception e) {
									//logger.catching(e);
								//}
							//}
							//else if(value instanceof Integer) {
								//try {
									//setIntegerValue(w.getGameRules().get((RuleKey<IntegerValue>) rule), (int) value, w.getServer());
								//} catch (Exception e) {
									//logger.catching(e);
								//}
							//}
						//}
					//}
				//}
				//w.getGameRules().setOrCreateGameRule("keepInventory", "" + ConfigHandler.keepInventory);
				//w.getGameRules().setOrCreateGameRule("mobGriefing", "" + ConfigHandler.mobGriefing);
				//w.getGameRules().setOrCreateGameRule("doFireTick", "" + ConfigHandler.doFireTick);
				//w.getGameRules().setOrCreateGameRule("doMobSpawning", "" + ConfigHandler.doMobSpawning);
				//w.getGameRules().setOrCreateGameRule("spawnRadius", "" + ConfigHandler.spawnRadius);
				//w.getGameRules().setOrCreateGameRule("doDaylightCycle", "" + ConfigHandler.doDaylightCycle);
				//w.getGameRules().setOrCreateGameRule("doTileDrops", "" + ConfigHandler.doTileDrops);
				//w.getGameRules().setOrCreateGameRule("doMobLoot", "" + ConfigHandler.doMobLoot);
				if(ConfigHandler.setWorldspawn == true) {
					if(ConfigHandler.setBedrock) {
						//groundError = placeBlock(w, ConfigHandler.groundBlock, ConfigHandler.groundOreDict, ConfigHandler.groundEnableOreDict, ConfigHandler.groundMeta, ConfigHandler.groundNumber, 0, groundError);
						groundError = placeBlock(w, ConfigHandler.groundBlock, ConfigHandler.groundOreDict, ConfigHandler.groundEnableOreDict, 0, groundError);
						/**try {
							BlockPos ground = new BlockPos(ConfigHandler.worldSpawnX, 0, ConfigHandler.worldSpawnZ);
							if(ConfigHandler.groundEnableOreDict) {
								List<ItemStack> oreDict = OreDictionary.getOres(ConfigHandler.groundOreDict);
								if(!oreDict.isEmpty()) {
									IBlockState bs;
									Block ore;
									int defaultMeta;
									if(ConfigHandler.groundNumber < oreDict.size()) {
										ItemBlock ib = (ItemBlock)oreDict.get(ConfigHandler.groundNumber).getItem();
										ore = ib.block;
										//bs = oreDict.get(ConfigHandler.groundNumber).state;
										defaultMeta = oreDict.get(ConfigHandler.groundNumber).getItemDamage();
									}
									else {
										ItemBlock ib = (ItemBlock)oreDict.get(oreDict.size() - 1).getItem();
										ore = ib.block;
										defaultMeta = oreDict.get(oreDict.size() - 1).getItemDamage();
									}
									if(ConfigHandler.groundMeta >= 0) {
										bs = ore.getStateFromMeta(ConfigHandler.groundMeta);
									}
									else {
										if(defaultMeta != OreDictionary.WILDCARD_VALUE) {
											bs = ore.getStateFromMeta(defaultMeta);
										}
										else {
											bs = ore.getDefaultState();
										}
										//bs = ore.getDefaultState();
									}
									w.setBlockState(ground, bs);
								}
								else {
									if(!groundError) {
										MinecraftForge.EVENT_BUS.register(new Messager("Could not Find any Item in the OreDictionary Named " + ConfigHandler.groundOreDict + "!"));
										groundError = true;
									}
								}
							}
							else {
								if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.groundBlock))) {
									Block groundBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.groundBlock));
									IBlockState bs;
									if(ConfigHandler.groundMeta >= 0) {
										bs = groundBlock.getStateFromMeta(ConfigHandler.groundMeta);
										//bs = groundBlock.Pro
									}
									else {
										bs = groundBlock.getDefaultState();
									}
									//IBlockState bs = groundBlock.getDefaultState();
									//bs.withProperty(BlockHorizontal.FACING, ConfigHandler.groundMeta);
									w.setBlockState(ground, bs);
								}
								else {
									if(!groundError) {
										MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.groundBlock + "!"));
										groundError = true;
									}
								}
								//throw new NullPointerException("This is a Test Error");
							}
						} catch (Exception e) {
							// TODO: handle exception
							MinecraftForge.EVENT_BUS.register(new Messager("An Unknown Error occures while Replacing a Block!"));
							logger.catching(e);
						}*/
						/**try {
							BlockPos bedrock = new BlockPos(ConfigHandler.worldSpawnX, 0, ConfigHandler.worldSpawnZ);
							if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.groundBlock))) {
								Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.groundBlock));
								IBlockState bs = ground.getDefaultState();
								w.setBlockState(bedrock, bs);
							}
							else {
								if(!groundError) {
									MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.groundBlock + "!"));
									groundError = true;
								}
								//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.groundBlock + "!"));
							}
						} catch (Exception e) {
							// TODO: handle exception
							MinecraftForge.EVENT_BUS.register(new Messager("An Unknown Error occures while Replacing a Block!"));
						}*/
						//BlockPos bedrock = new BlockPos(ConfigHandler.worldSpawnX, 0, ConfigHandler.worldSpawnZ);
						//IBlockState bs = Blocks.BEDROCK.getDefaultState();
						//Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.groundBlock));
						//IBlockState bs = ground.getDefaultState();
						//w.setBlockState(bedrock, bs);
						/*if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.groundBlock))) {
							Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.groundBlock));
							IBlockState bs = ground.getDefaultState();
							w.setBlockState(bedrock, bs);
						}
						else {
							if(!groundError) {
								MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.groundBlock + "!"));
								groundError = true;
							}
							//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.groundBlock + "!"));
						}*/
					}
					BlockPos p = new BlockPos(ConfigHandler.worldSpawnX, w.getHeight(), ConfigHandler.worldSpawnZ);
					//p = w.getTopSolidOrLiquidBlock(p);
					p = getTopBlockOrFluid(w, ConfigHandler.worldSpawnX, ConfigHandler.worldSpawnZ);
					while(!w.isAirBlock(p)) {
						p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
					}
					boolean liquid = false;
					//w.setSpawnPoint(p);
					if(ConfigHandler.replaceLiquid) {
						p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
						if(p.getY() > 0) {
							//IBlockState blockstate = w.getBlockState(p);
							//BlockState blockstate = w.getBlockState(p);
							IFluidState blockstate = w.getFluidState(p);
							//if(blockstate.getBlock() instanceof BlockLiquid) {
							//if(blockstate.getFluid() != null) {
							if(!blockstate.isEmpty()) {
								liquid = true;
								//liquidError = placeBlock(w, ConfigHandler.liquidReplace, ConfigHandler.liquidOreDict, ConfigHandler.liquidEnableOreDict, ConfigHandler.liquidMeta, ConfigHandler.liquidNumber, p.getY(), liquidError);
								liquidError = placeBlock(w, ConfigHandler.liquidReplace, ConfigHandler.liquidOreDict, ConfigHandler.liquidEnableOreDict, p.getY(), liquidError);
							}
							
						}
						p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
						/**try {
							p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
							if(p.getY() > 0) {
								IBlockState blockstate = w.getBlockState(p);
								if(blockstate.getBlock() instanceof BlockLiquid) {
									liquid = true;
									//IBlockState bs = Blocks.GRASS.getDefaultState();
									//Block replace = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.liquidReplace));
									//IBlockState bs = replace.getDefaultState();
									//w.setBlockState(p, bs);
									if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.liquidReplace))) {
										Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.liquidReplace));
										IBlockState bs = ground.getDefaultState();
										w.setBlockState(p, bs);
									}
									else {
										if(!liquidError) {
											MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.liquidReplace + "!"));
											liquidError = true;
										}
										//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.liquidReplace + "!"));
									}
								}
							}
							p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
						} catch (Exception e) {
							// TODO: handle exception
							MinecraftForge.EVENT_BUS.register(new Messager("An Unknown Error occures while Replacing a Block!"));
						}*/
						/*p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
						if(p.getY() > 0) {
							IBlockState blockstate = w.getBlockState(p);
							if(blockstate.getBlock() instanceof BlockLiquid) {
								liquid = true;
								//IBlockState bs = Blocks.GRASS.getDefaultState();
								//Block replace = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.liquidReplace));
								//IBlockState bs = replace.getDefaultState();
								//w.setBlockState(p, bs);
								if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.liquidReplace))) {
									Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.liquidReplace));
									IBlockState bs = ground.getDefaultState();
									w.setBlockState(p, bs);
								}
								else {
									if(!liquidError) {
										MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.liquidReplace + "!"));
										liquidError = true;
									}
									//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.liquidReplace + "!"));
								}
							}
						}*/
						/**IBlockState blockstate = w.getBlockState(p);
						if(blockstate.getBlock() instanceof BlockLiquid) {
							liquid = true;
							//IBlockState bs = Blocks.GRASS.getDefaultState();
							//Block replace = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.liquidReplace));
							//IBlockState bs = replace.getDefaultState();
							//w.setBlockState(p, bs);
							if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.liquidReplace))) {
								Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.liquidReplace));
								IBlockState bs = ground.getDefaultState();
								w.setBlockState(p, bs);
							}
							else {
								if(!liquidError) {
									MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.liquidReplace + "!"));
									liquidError = true;
								}
								//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.liquidReplace + "!"));
							}
						}*/
					}
					if(ConfigHandler.replaceSolid) {
						p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
						if(p.getY() > 0) {
							//IBlockState blockstate = w.getBlockState(p);
							BlockState blockstate = w.getBlockState(p);
							//if(!(blockstate.getBlock() instanceof BlockLiquid) && !liquid) {
							if(blockstate.getBlock() != null && !liquid) {
								//solidError = placeBlock(w, ConfigHandler.solidReplace, ConfigHandler.solidOreDict, ConfigHandler.solidEnableOreDict, ConfigHandler.solidMeta, ConfigHandler.solidNumber, p.getY(), solidError);
								solidError = placeBlock(w, ConfigHandler.solidReplace, ConfigHandler.solidOreDict, ConfigHandler.solidEnableOreDict, p.getY(), solidError);
							}
							
						}
						p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
						/**try {
							p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
							if(p.getY() > 0) {
								IBlockState blockstate = w.getBlockState(p);
								if(!liquid) {
									if(!(blockstate.getBlock() instanceof BlockLiquid)) {
										if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.solidReplace))) {
											Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.solidReplace));
											IBlockState bs = ground.getDefaultState();
											w.setBlockState(p, bs);
										}
										else {
											if(!solidError) {
												MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.solidReplace + "!"));
												solidError = true;
											}
										}
									}
								}
							}
							p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
						} catch (Exception e) {
							// TODO: handle exception
							MinecraftForge.EVENT_BUS.register(new Messager("An Unknown Error occures while Replacing a Block!"));
						}*/
						/*p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
						if(p.getY() > 0) {
							IBlockState blockstate = w.getBlockState(p);
							if(!liquid) {
								if(!(blockstate.getBlock() instanceof BlockLiquid)) {
									if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.solidReplace))) {
										Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.solidReplace));
										IBlockState bs = ground.getDefaultState();
										w.setBlockState(p, bs);
									}
									else {
										if(!solidError) {
											MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.solidReplace + "!"));
											solidError = true;
										}
									}
								}
							}
						}*/
						/**IBlockState blockstate = w.getBlockState(p);
						if(!liquid) {
							if(!(blockstate.getBlock() instanceof BlockLiquid)) {
								if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(ConfigHandler.solidReplace))) {
									Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.solidReplace));
									IBlockState bs = ground.getDefaultState();
									w.setBlockState(p, bs);
								}
								else {
									if(!solidError) {
										MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + ConfigHandler.solidReplace + "!"));
										solidError = true;
									}
								}
							}
						}*/
					}
				}
			//}
		}
	}
	
	//public boolean placeBlock(World w, String registryName, String oreDictName, boolean useOreDict, int meta, int oreDictNumber, int height, boolean errored) {
	public boolean placeBlock(World w, String registryName, String oreDictName, boolean useOreDict, int height, boolean errored) {
		//boolean ret = false;
		boolean ret = errored;
		com.ToMe.ToMeSetup.api.Messager mess = new com.ToMe.ToMeSetup.api.Messager("{\"text\":\"Just for creating new ones.\"}", 0, null);
		//oreDictNumber --;
		try {
			//throw new NullPointerException("Test Exception!!!");//TEST(Crashes with Unresolved compilation problem)
			//if(true) {
				//throw new NullPointerException("Test Exception!!!");//Test
			//}
			//System.out.println(w.getDimension());
			BlockPos place = new BlockPos(ConfigHandler.worldSpawnX, height, ConfigHandler.worldSpawnZ);
			//System.out.println(w.getBlockState(place));
			if(useOreDict) {
				Tag<Block> blockTag = BlockTags.getCollection().get(new ResourceLocation(parseOreDict(oreDictName)));
				Collection<Block> blocks;
				boolean block = false;
				if(blockTag != null && !(blocks = blockTag.getAllElements()).isEmpty()) {
					BlockState bs = blocks.iterator().next().getDefaultState();
					block = true;
					if(!ret) {
						w.setBlockState(place, bs);
						//if(w.getBlockState(place) != bs) {
							//w.setBlockState(place, bs);
						//}
					}
				}
				//List<ItemStack> oreDict = OreDictionary.getOres(oreDictName);
				//Collection<Item> oreDict = ItemTags.getCollection().get(new ResourceLocation("forge", parseOreDict(oreDictName))).getAllElements();
				//Collection<Item> oreDict = ItemTags.getCollection().get(new ResourceLocation(parseOreDict(oreDictName))).getAllElements();
				Tag<Item> tag = ItemTags.getCollection().get(new ResourceLocation(parseOreDict(oreDictName)));
				//Collection<Item> oreDict;
				Collection<Item> items;
				//if(!oreDict.isEmpty()) {
				//if(tag != null && !(oreDict = tag.getAllElements()).isEmpty()) {
				if(!block && tag != null && !(items = tag.getAllElements()).isEmpty()) {
					//IBlockState bs;
					BlockState bs;
					//Block ore;
					//Block ore = null;
					Block ore = Blocks.AIR;
					//int defaultMeta;
					//int defaultMeta = 0;
					//if(oreDictNumber < oreDict.size()) {
						//ItemBlock ib = (ItemBlock)oreDict.get(oreDictNumber).getItem();
						//Item it = oreDict.get(oreDictNumber).getItem();
						//Item it = null;
						//Item it = oreDict.iterator().next();
						Item it = items.iterator().next();
						//int i = 0;
						//for(Item itm:oreDict) {
							//if(i == oreDictNumber) {
								//it = itm;
							//}
							//i++;
						//}
						//if(it instanceof ItemBlock) {
						if(it instanceof BlockItem) {
							//ItemBlock ib = (ItemBlock)it;
							BlockItem ib = (BlockItem)it;
							ore = ib.getBlock();
							//defaultMeta = oreDict.get(oreDictNumber).getItemDamage();
						}
						else {
							if(!errored) {
								//Messager.sendBlockOreDictItem(oreDictName);
								//Messager.sendBlockOreDictItem("" + oreDictNumber);
								//Messager.sendBlockOreDictItem(oreDictName + oreDictNumber);
								//Messager.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
								//mess.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
								mess.sendBlockOreDictItem(oreDictName);
								ret = true;
							}
						}
						//ore = ib.block;
						//ore = ib.getBlock();
						//defaultMeta = oreDict.get(oreDictNumber).getItemDamage();
					//}
					//else {
						//logger.info("oreDict Number too high!");
						//oreDictNumber = oreDict.size() - 1;
						//Item it = oreDict.get(oreDictNumber).getItem();
						//Item it = null;
						//int i = 0;
						//for(Item itm:oreDict) {
							//if(i == oreDictNumber) {
								//it = itm;
							//}
							//i++;
						//}
						//if(it instanceof ItemBlock) {
						//if(it instanceof BlockItem) {
							//ItemBlock ib = (ItemBlock)it;
							//BlockItem ib = (BlockItem)it;
							//ore = ib.getBlock();
							//defaultMeta = oreDict.get(oreDictNumber).getItemDamage();
						//}
						//else {
							//if(!errored) {
								//Messager.sendBlockOreDictItem(oreDictName);
								//Messager.sendBlockOreDictItem("" + oreDictNumber);
								//Messager.sendBlockOreDictItem(oreDictName + oreDictNumber);
								//Messager.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
								//mess.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
								//ret = true;
							//}
						//}
						//oreDictNumber = 1;
						//ItemBlock ib = (ItemBlock)oreDict.get(oreDict.size() - 1).getItem();
						//ore = ib.block;
						//ore = ib.getBlock();
						//defaultMeta = oreDict.get(oreDict.size() - 1).getItemDamage();
					//}
					//if(meta >= 0) {
						//bs = ore.getStateFromMeta(meta);
					//}
					//else {
						//if(defaultMeta != OreDictionary.WILDCARD_VALUE) {
							//bs = ore.getStateFromMeta(defaultMeta);
						//}
						//else {
							//bs = ore.getDefaultState();
						//}
					//}
					bs = ore.getDefaultState();
					//w.setBlockState(place, bs);
					if(!ret) {
						w.setBlockState(place, bs);
						//if(w.getBlockState(place) != bs) {
							//w.setBlockState(place, bs);
						//}
					}
				}
				//else {
				else if(!block) {
					if(!errored) {
						//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find any Item in the OreDictionary Named " + oreDictName + "!"));
						//registerMessager(new Messager("Could not Find any Item in the OreDictionary Named " + oreDictName + "!"));
						//TODO Rebuild after a Test!
						//Messager.sendMissingBlockOreDict(oreDictName);
						//mess.sendMissingBlockOreDict(oreDictName);
						mess.sendMissingBlockOreDict(oreDictName + "(" + ToMeSetupMod.parseOreDict(oreDictName) + ")");
						ret = true;
					}
				}
			}
			else {
				if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(registryName))) {
					Block groundBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(registryName));
					//IBlockState bs;
					//BlockState bs;
					BlockState bs = groundBlock.getDefaultState();
					//if(meta >= 0) {
						//bs = groundBlock.getStateFromMeta(meta);
					//}
					//else {
						//bs = groundBlock.getDefaultState();
					//}
					//w.setBlockState(place, bs);
					if(!ret) {
						w.setBlockState(place, bs);
						//if(w.getBlockState(place) != bs) {
							//w.setBlockState(place, bs);
						//}
					}
				}
				else {
					if(!errored) {
						//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + registryName + "!"));
						//registerMessager(new Messager("Could not Find Block Named " + registryName + "!"));
						//Messager.sendMissingBlock(registryName);
						mess.sendMissingBlock(registryName);
						ret = true;
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			//MinecraftForge.EVENT_BUS.register(new Messager("An Unknown Error occures while Replacing a Block!"));
			//registerMessager(new Messager("An Unknown Error occures while Replacing a Block!"));
			//Messager.sendMessage("An Unknown Error occures while Replacing a Block!");
			//Messager.sendMessage("An Unknown Error occures while Replacing a Block!", 5, null, "Maybe its a Bug?");
			//mess.sendMessage("An Unknown Error occures while Replacing a Block!", "ToMeSetup", 5, null, "Maybe its a Bug?");
			mess.sendUnknownBlockError();
			logger.catching(e);
		}
		return ret;
	}
	
	//@SuppressWarnings("unchecked")
	@SuppressWarnings("unchecked")
	private void initWorld(World w) {
		if(!w.isRemote) {
			if(ConfigHandler.enableGamerules == true) {
				//for(RuleKey<?> rule:GameRules.GAME_RULES.keySet()) {
				//for(Object rule:GameRules.GAME_RULES.keySet()) {
					//if(ConfigHandler.configValues.containsKey(rule.toString())) {
						//Object value = ConfigHandler.configValues.get(rule.toString());
						//if(value instanceof Boolean) {
							//try {
								//w.getGameRules().get((RuleKey<BooleanValue>) rule).set((boolean) value, w.getServer());
								//w.getGameRules().get((RuleKey<GameRules.BooleanValue>) rule).set((boolean) value, w.getServer());
							//} catch (Exception e) {
								//logger.catching(e);
							//}
						//}
						//else if(value instanceof Integer) {
							//try {
								//setIntegerValue(w.getGameRules().get((RuleKey<IntegerValue>) rule), (int) value, w.getServer());
								//setIntegerValue(w.getGameRules().get((RuleKey<GameRules.IntegerValue>) rule), (int) value, w.getServer());
							//} catch (Exception e) {
								//logger.catching(e);
							//}
						//}
					//}
				//}
				try {
					for(Object rule:GameRules.GAME_RULES.keySet()) {
						if(ConfigHandler.configValues.containsKey(rule.toString())) {
							Object value = ConfigHandler.configValues.get(rule.toString());
							Class<?> RuleKey = Class.forName("net.minecraft.world.GameRules$RuleKey");
							Method get = null;
							try {
								//get = GameRules.class.getDeclaredMethod("func_223585_a", RuleKey.class);
								get = GameRules.class.getDeclaredMethod("func_223585_a", RuleKey);
							} catch (Exception e) {
								//get = GameRules.class.getDeclaredMethod("get", RuleKey.class);
								get = GameRules.class.getDeclaredMethod("get", RuleKey);
							}
							if(value instanceof Boolean) {
								Class<?> BooleanValue = Class.forName("net.minecraft.world.GameRules$BooleanValue");
								Method set = null;
								try {
									set = BooleanValue.getDeclaredMethod("func_223570_a", boolean.class, MinecraftServer.class);
								} catch (Exception e) {
									set = BooleanValue.getDeclaredMethod("set", boolean.class, MinecraftServer.class);
								}
								//set.invoke(get.invoke(w.getGameRules(), rule), value, w.getServer());
								Object gamerule = get.invoke(w.getGameRules(), rule);
								set.invoke(gamerule, value, w.getServer());
							}
							else if(value instanceof Integer) {
								Class<?> IntegerValue = Class.forName("net.minecraft.world.GameRules$IntegerValue");
								Field val = null;
								try {
									val = IntegerValue.getDeclaredField("field_223566_a");
								} catch (Exception e) {
									val = IntegerValue.getDeclaredField("value");
								}
								Object IntVal = get.invoke(w.getGameRules(), rule);
								val.set(IntVal, value);
								Method func_223556_a = IntegerValue.getMethod("func_223556_a", MinecraftServer.class);
								func_223556_a.invoke(IntVal, w.getServer());
							}
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
							if(ConfigHandler.configValues.containsKey(rule.toString())) {
								Object value = ConfigHandler.configValues.get(rule.toString());
								Method setOrCreateGameRule = null;
								try {
									setOrCreateGameRule = GameRules.class.getDeclaredMethod("func_82764_b", String.class, String.class, MinecraftServer.class);
								} catch (Exception e2) {
									setOrCreateGameRule = GameRules.class.getDeclaredMethod("setOrCreateGameRule", String.class, String.class, MinecraftServer.class);
								}
								setOrCreateGameRule.invoke(w.getGameRules(), rule.toString(), "" + value, w.getServer());
							}
						}
					} catch (Exception e2) {
						logger.catching(e);
						logger.catching(e2);
					}
				}
			}
			if(ConfigHandler.setWorldspawn == true) {
				BlockPos p = new BlockPos(ConfigHandler.worldSpawnX, w.getHeight(), ConfigHandler.worldSpawnZ);
				p = getTopBlockOrFluid(w, ConfigHandler.worldSpawnX, ConfigHandler.worldSpawnZ);
				while(!w.isAirBlock(p)) {
					p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
				}
				w.setSpawnPoint(p);
			}
		}
	}
	
	private BlockPos getTopBlockOrFluid(World world, int x, int z) {
		BlockPos pos = new BlockPos(x, 255, z);
		while(pos.getY() > 0) {
			//pos = pos.offset(Direction.DOWN);
			//pos = pos.add(0, -1, 0);
			pos = pos.down();
			if(!world.isAirBlock(pos)) {
				return pos;
			}
		}
		//return new BlockPos(x, 0, z);﻿
		return pos;
	}
	
	private void initOreDictPrefixes() {
		oreDictPrefixes = new HashMap<String, String>();
		oreDictPrefixes.put("block", "storage_blocks");
		oreDictPrefixes.put("blockGlass", "glass");
		oreDictPrefixes.put("blockWool", "minecraft:wool");
		oreDictPrefixes.put("blockPrismarine", "");//There is no direct replacement for this OreDictionary entry/entries.
		oreDictPrefixes.put("blockSlime", "");//There is no direct replacement for this OreDictionary entry/entries.
		oreDictPrefixes.put("blockCactus", "");//There is no direct replacement for this OreDictionary entry/entries.
		oreDictPrefixes.put("ingot", "ingots");
		oreDictPrefixes.put("gem", "gems");
		oreDictPrefixes.put("nugget", "nuggets");
		oreDictPrefixes.put("ore", "ores");
		oreDictPrefixes.put("dust", "dusts");
		oreDictPrefixes.put("stick", "rods");
		oreDictPrefixes.put("rod", "rods");
		oreDictPrefixes.put("chest", "chests");
		oreDictPrefixes.put("tree", "minecraft:");
		oreDictPrefixes.put("logWood", "minecraft:logs");
		oreDictPrefixes.put("plankWood", "minecraft:planks");
		oreDictPrefixes.put("slab", "minecraft:slabs");
		oreDictPrefixes.put("slabWood", "minecraft:wooden_slabs");
		oreDictPrefixes.put("stair", "minecraft:stairs");
		oreDictPrefixes.put("stairWood", "minecraft:wooden_stairs");
		oreDictPrefixes.put("fence", "fences");
		oreDictPrefixes.put("fenceGate", "fence_gates");
		oreDictPrefixes.put("crop", "");//There is no direct replacement for this OreDictionary entry/entries.
		oreDictPrefixes.put("paneGlass", "glass_panes");
	}
	
	/**
	 * parses an OreDictionary entry name to the new Tag system.
	 * @param oreDict the oreDict name
	 * @return the parsed tag name
	 */
	public static String parseOreDict(String oreDict) {
		//if the name contains a slash its probably already a tag name
		//if(oreDict.contains("/")) {
		if(oreDict.contains("/") || oreDict.contains(":")) {
			return oreDict;
		}
		for(String prefix:oreDictPrefixes.keySet()) {
			if(oreDict.startsWith(prefix)) {
				String oreDictPrefix = oreDictPrefixes.get(prefix);
				//oreDict = oreDict.replaceFirst(prefix, oreDictPrefixes.get(prefix) + "/");
				//oreDict = oreDict.replaceFirst(prefix, oreDictPrefixes.get(prefix) + "/").toLowerCase();
				//oreDict = oreDict.replaceFirst(prefix, oreDictPrefixes.get(prefix) + (oreDictPrefixes.get(prefix).length() > 0 ? "/" : "")).toLowerCase();
				//oreDict = oreDict.replaceFirst(prefix, oreDictPrefix + (oreDictPrefix.length() > 0 && !oreDictPrefix.endsWith(":") ? "/" : "")).toLowerCase();
				oreDict = oreDict.replaceFirst(prefix, oreDictPrefix + (oreDictPrefix.length() > 0 && !oreDictPrefix.contains(":") ? "/" : "")).toLowerCase();
				//return oreDict;
				break;
			}
		}
		if(!oreDict.contains(":")) {
			oreDict = "forge:" + oreDict;
		}
		else if(!oreDict.endsWith("s")) {
			oreDict += "s";
		}
		return oreDict;
	}
	
	//private static void setIntegerValue(IntegerValue field, int value, MinecraftServer server) {
	//private static void setIntegerValue(GameRules.IntegerValue field, int value, MinecraftServer server) {
		//field.value = value;
		//field.func_223556_a(server);
	//}
	
	/*private boolean chunkContainsCoords(IChunk c, int x, int z) {
		ChunkPos pos = c.getPos();
		return pos.getXStart() <= x && pos.getXEnd() >= x && pos.getZStart() <= z && pos.getZEnd() >= z;
	}*/
	
}