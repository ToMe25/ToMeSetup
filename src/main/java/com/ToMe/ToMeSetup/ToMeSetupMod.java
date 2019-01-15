package com.ToMe.ToMeSetup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.Logger;

import com.ToMe.ToMeSetup.api.Messager;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
//import net.minecraftforge.fml.relauncher.Side;
//import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraft.command.server.CommandSetBlock;

//@EventBusSubscriber
//@Mod(modid="tomesetup",name="ToMe Setup", version="1.0")
@Mod(modid = ToMeSetupMod.MODID, name = ToMeSetupMod.NAME, version = ToMeSetupMod.VERSION, acceptedMinecraftVersions = ToMeSetupMod.MCVERSION)
public class ToMeSetupMod {
	
	@Instance
	public static ToMeSetupMod Instance;
	
	public static final String MODID = "tomesetup";
	public static final String NAME = "ToMe Setup";
	public static final String VERSION = "1.0";
	public static final String MCVERSION = "[1.11,1.11.2]";
	//public static final String API_VERSION = "1.0";
	//public static final String API_OWNER = "ToMeSetup";
	//public static final String API_PROVIDES = "A Api to add and remove StartItems to/from ToMeSetup.";
	
	public static ConfigHandler cfg;
	
	private static StartItemCommand startItemCMD = new StartItemCommand();
	
	//public static StartItemProvider SIP;
	
	protected boolean setuped = false;
	
	private boolean groundError;
	private boolean liquidError;
	private boolean solidError;
	
	public static Logger logger;
	
	protected List<Integer> dims;
	
	private boolean gamerulesInitialzed = false;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		logger = e.getModLog();
		cfg = new ConfigHandler(e);
		//SIP = new StartItemProvider();
		//logger = e.getModLog();
		MinecraftForge.EVENT_BUS.register(this);
		//MinecraftForge.EVENT_BUS.register(SIP);
		dims = new ArrayList<Integer>();
		OreDictionary.registerOre("bedrock", Blocks.BEDROCK);
		//MinecraftForge.TERRAIN_GEN_BUS.register(this);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent e) {
		//Messager.initLangMap();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		if(ConfigHandler.enableStartItems) {
			//StartItemProvider.validateStartItemConfig();
			com.ToMe.ToMeSetup.api.StartItems.impl.StartItemProvider.instance.validateStartItems();
		}
	}
	
	/*public static void registerMessager(Messager m) {
		MinecraftForge.EVENT_BUS.register(m);
	}*/
	
	@EventHandler
	public void onServerLoad(FMLServerStartingEvent e) {
		e.registerServerCommand(startItemCMD);
		if(ConfigHandler.enableGamerules) {
			e.getServer().setAllowPvp(ConfigHandler.pvp);
		}
	}
	
	@SubscribeEvent
	//@EventHandler
	//public void onWorldCreated(WorldEvent.CreateSpawnPosition e) {
	//@SubscribeEvent(priority = EventPriority.LOWEST)
	//public void onWorldCreated(Decorate e) {
	//@SubscribeEvent
	//public void onWorldCreated(DecorateBiomeEvent.Pre e) {
	//public void onWorldCreated(DecorateBiomeEvent.Post e) {
	//public void onWorldCreated(DecorateBiomeEvent.Decorate e) {
	//public void onWorldCreated(DecorateBiomeEvent e) {
	public void onWorldCreated(EntityJoinWorldEvent e) {
		if(!gamerulesInitialzed) {
			GameruleHandler.onWorldLoad(e.getWorld());
			gamerulesInitialzed = true;
		}
		//System.out.println("Create");
		WorldProvider pro = e.getWorld().provider;
		if(!dims.contains(pro.getDimension())) {
			setup(e.getWorld());
			dims.add(pro.getDimension());
		}
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
	}
	
	@SubscribeEvent
	public void onExplosion(ExplosionEvent.Detonate e) {
		//if(!e.getWorld().getGameRules().getBoolean(GameruleHandler.EXPLOSIONBLOCKDAMAGE)) {
		if(ConfigHandler.explosionBlockDamage && !e.getWorld().getGameRules().getBoolean(GameruleHandler.EXPLOSIONBLOCKDAMAGE)) {
			e.getAffectedBlocks().clear();
		}
	}
	
	//@SideOnly(Side.SERVER)
	//@SubscribeEvent(priority = EventPriority.LOWEST)
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		//System.out.println("Load");
		cfg.onWorldLoad(e.getWorld());
		GameruleHandler.onWorldLoad(e.getWorld());
		//if(!setuped) {
			setup(e.getWorld());
			//setuped = true;
		//}
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
	
	public void setup(World w) {
		//Messager.sendMissingBlock("Test", 4);//A Test!
		if(!w.isRemote) {
			//if(!w.isRemote) {
				if(ConfigHandler.enableGamerules == true) {
					for(String rule:ConfigHandler.configValues.keySet()) {
						w.getGameRules().setOrCreateGameRule(rule, "" + ConfigHandler.configValues.get(rule));
					}
				}
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
						groundError = placeBlock(w, ConfigHandler.groundBlock, ConfigHandler.groundOreDict, ConfigHandler.groundEnableOreDict, ConfigHandler.groundMeta, ConfigHandler.groundNumber, 0, groundError);
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
					p = w.getTopSolidOrLiquidBlock(p);
					while(!w.isAirBlock(p)) {
						p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
					}
					boolean liquid = false;
					w.setSpawnPoint(p);
					if(ConfigHandler.replaceLiquid) {
						p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
						if(p.getY() > 0) {
							IBlockState blockstate = w.getBlockState(p);
							if(blockstate.getBlock() instanceof BlockLiquid) {
								liquid = true;
								liquidError = placeBlock(w, ConfigHandler.liquidReplace, ConfigHandler.liquidOreDict, ConfigHandler.liquidEnableOreDict, ConfigHandler.liquidMeta, ConfigHandler.liquidNumber, p.getY(), liquidError);
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
							IBlockState blockstate = w.getBlockState(p);
							if(!(blockstate.getBlock() instanceof BlockLiquid) && !liquid) {
									solidError = placeBlock(w, ConfigHandler.solidReplace, ConfigHandler.solidOreDict, ConfigHandler.solidEnableOreDict, ConfigHandler.solidMeta, ConfigHandler.solidNumber, p.getY(), solidError);
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
	
	public boolean placeBlock(World w, String registryName, String oreDictName, boolean useOreDict, int meta, int oreDictNumber, int height, boolean errored) {
		//boolean ret = false;
		boolean ret = errored;
		com.ToMe.ToMeSetup.api.Messager mess = new com.ToMe.ToMeSetup.api.Messager("{\"text\":\"Just for creating new ones.\"}", 0, null);
		//oreDictNumber --;
		try {
			//throw new NullPointerException("Test Exception!!!");//TEST(Crashes with Unresolved compilation problem)
			//if(true) {
				//throw new NullPointerException("Test Exception!!!");//Test
			//}
			BlockPos place = new BlockPos(ConfigHandler.worldSpawnX, height, ConfigHandler.worldSpawnZ);
			if(useOreDict) {
				List<ItemStack> oreDict = OreDictionary.getOres(oreDictName);
				if(!oreDict.isEmpty()) {
					IBlockState bs;
					//Block ore;
					//Block ore = null;
					Block ore = Blocks.AIR;
					//int defaultMeta;
					int defaultMeta = 0;
					if(oreDictNumber < oreDict.size()) {
						//ItemBlock ib = (ItemBlock)oreDict.get(oreDictNumber).getItem();
						Item it = oreDict.get(oreDictNumber).getItem();
						if(it instanceof ItemBlock) {
							ItemBlock ib = (ItemBlock)it;
							ore = ib.getBlock();
							defaultMeta = oreDict.get(oreDictNumber).getItemDamage();
						}
						else {
							if(!errored) {
								//Messager.sendBlockOreDictItem(oreDictName);
								//Messager.sendBlockOreDictItem("" + oreDictNumber);
								//Messager.sendBlockOreDictItem(oreDictName + oreDictNumber);
								//Messager.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
								mess.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
								ret = true;
							}
						}
						//ore = ib.block;
						//ore = ib.getBlock();
						//defaultMeta = oreDict.get(oreDictNumber).getItemDamage();
					}
					else {
						logger.info("oreDict Number too high!");
						oreDictNumber = oreDict.size() - 1;
						Item it = oreDict.get(oreDictNumber).getItem();
						if(it instanceof ItemBlock) {
							ItemBlock ib = (ItemBlock)it;
							ore = ib.getBlock();
							defaultMeta = oreDict.get(oreDictNumber).getItemDamage();
						}
						else {
							if(!errored) {
								//Messager.sendBlockOreDictItem(oreDictName);
								//Messager.sendBlockOreDictItem("" + oreDictNumber);
								//Messager.sendBlockOreDictItem(oreDictName + oreDictNumber);
								//Messager.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
								mess.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
								ret = true;
							}
						}
						//oreDictNumber = 1;
						//ItemBlock ib = (ItemBlock)oreDict.get(oreDict.size() - 1).getItem();
						//ore = ib.block;
						//ore = ib.getBlock();
						//defaultMeta = oreDict.get(oreDict.size() - 1).getItemDamage();
					}
					if(meta >= 0) {
						bs = ore.getStateFromMeta(meta);
					}
					else {
						if(defaultMeta != OreDictionary.WILDCARD_VALUE) {
							bs = ore.getStateFromMeta(defaultMeta);
						}
						else {
							bs = ore.getDefaultState();
						}
					}
					//w.setBlockState(place, bs);
					if(!ret) {
						w.setBlockState(place, bs);
					}
				}
				else {
					if(!errored) {
						//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find any Item in the OreDictionary Named " + oreDictName + "!"));
						//registerMessager(new Messager("Could not Find any Item in the OreDictionary Named " + oreDictName + "!"));
						//TODO Rebuild after a Test!
						//Messager.sendMissingBlockOreDict(oreDictName);
						mess.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
						ret = true;
					}
				}
			}
			else {
				if(ForgeRegistries.BLOCKS.containsKey(new ResourceLocation(registryName))) {
					Block groundBlock = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(registryName));
					IBlockState bs;
					if(meta >= 0) {
						bs = groundBlock.getStateFromMeta(meta);
					}
					else {
						bs = groundBlock.getDefaultState();
					}
					//w.setBlockState(place, bs);
					if(!ret) {
						w.setBlockState(place, bs);
					}
				}
				else {
					if(!errored) {
						//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find Block Named " + registryName + "!"));
						//registerMessager(new Messager("Could not Find Block Named " + registryName + "!"));
						//Messager.sendMissingBlock(registryName);
						mess.sendBlockOreDictItem(oreDictName + ":" + oreDictNumber);
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
			mess.sendMessage("An Unknown Error occures while Replacing a Block!", "ToMeSetup", 5, null, "Maybe its a Bug?");
			logger.catching(e);
		}
		return ret;
	}
	
}