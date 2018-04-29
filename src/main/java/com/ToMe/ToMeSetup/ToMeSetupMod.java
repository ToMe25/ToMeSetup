package com.ToMe.ToMeSetup;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//@EventBusSubscriber
@Mod(modid="tomesetup",name="ToMe Setup", version="1.0")
public class ToMeSetupMod {
	
	@Instance
	public static ToMeSetupMod Instance;
	
	public static ConfigHandler cfg;
	
	protected boolean setuped = false;
	
	private boolean groundError;
	private boolean liquidError;
	
	public static Logger logger;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		cfg = new ConfigHandler(e);
		logger = e.getModLog();
		MinecraftForge.EVENT_BUS.register(this);
		//MinecraftForge.TERRAIN_GEN_BUS.register(this);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent e) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
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
		if(!setuped) {
			if(e.getWorld().provider.getDimension() == 0) {
				setup(e.getWorld());
				setuped = true;
			}
			//setup(e.getWorld());
			//setuped = true;
		}
		//System.out.println("Hello World!");
		//setup(e.getWorld());
		//e.getWorld().provider.
	}
	
	//@SideOnly(Side.SERVER)
	//@SubscribeEvent(priority = EventPriority.LOWEST)
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
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
		if(!w.isRemote) {
			if(!w.isRemote) {
				w.getGameRules().setOrCreateGameRule("keepInventory", "" + ConfigHandler.keepInventory);
				w.getGameRules().setOrCreateGameRule("mobGriefing", "" + ConfigHandler.mobGriefing);
				w.getGameRules().setOrCreateGameRule("doFireTick", "" + ConfigHandler.doFireTick);
				w.getGameRules().setOrCreateGameRule("doMobSpawning", "" + ConfigHandler.doMobSpawning);
				w.getGameRules().setOrCreateGameRule("spawnRadius", "" + ConfigHandler.spawnRadius);
				if(ConfigHandler.setWorldspawn == true) {
					if(ConfigHandler.setBedrock) {
						BlockPos bedrock = new BlockPos(ConfigHandler.worldSpawnX, 0, ConfigHandler.worldSpawnZ);
						//IBlockState bs = Blocks.BEDROCK.getDefaultState();
						//Block ground = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ConfigHandler.groundBlock));
						//IBlockState bs = ground.getDefaultState();
						//w.setBlockState(bedrock, bs);
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
					}
					BlockPos p = new BlockPos(ConfigHandler.worldSpawnX, w.getHeight(), ConfigHandler.worldSpawnZ);
					p = w.getTopSolidOrLiquidBlock(p);
					while(!w.isAirBlock(p)) {
						p = new BlockPos(p.getX(), p.getY() + 1, p.getZ());
					}
					w.setSpawnPoint(p);
					if(ConfigHandler.replaceLiquid) {
						p = new BlockPos(p.getX(), p.getY() - 1, p.getZ());
						IBlockState blockstate = w.getBlockState(p);
						if(blockstate.getBlock() instanceof BlockLiquid) {
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
				}
			}
		}
	}
	
}