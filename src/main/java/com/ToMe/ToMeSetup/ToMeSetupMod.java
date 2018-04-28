package com.ToMe.ToMeSetup;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
 
@Mod(modid="tomesetup",name="ToMe Setup", version="1.0")
public class ToMeSetupMod {
	
	@Instance
	public static ToMeSetupMod Instance;
	
	public static ConfigHandler cfg;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		cfg = new ConfigHandler(e);
		MinecraftForge.EVENT_BUS.register(this);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent e) {
		
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent e) {
		
	}
	
	//@SideOnly(Side.SERVER)
	@SubscribeEvent
	public void onWorldLoad(WorldEvent.Load e) {
		if(!e.getWorld().isRemote) {
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
		}
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
	
}