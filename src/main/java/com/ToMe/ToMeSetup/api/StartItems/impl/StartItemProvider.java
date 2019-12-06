package com.ToMe.ToMeSetup.api.StartItems.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
//import java.util.Collections;
import java.util.List;

import com.ToMe.ToMeSetup.api.Messager;
//import com.ToMe.ToMeSetup.ConfigHandler;
//import com.ToMe.ToMeSetup.ToMeSetupMod;
import com.ToMe.ToMeSetup.api.StartItems.IStartItemContainer;
import com.ToMe.ToMeSetup.api.StartItems.IStartItemProvider;

import net.minecraft.entity.player.PlayerEntity;
//import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
//import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
//import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
//import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class StartItemProvider implements IStartItemProvider {
	
	//private List<String> startItems = new ArrayList<String>();
	private List<IStartItemContainer> startItems = new ArrayList<IStartItemContainer>();
	
	public static boolean startItemsOnRespawn = false;
	
	private static final String GOT_START_ITEMS = "tomesetup:gotstartitems";
	
	//public static IStartItemProvider instance = new StartItemProvider();
	public static StartItemProvider instance = new StartItemProvider();
	
	static {
		//MinecraftForge.EVENT_BUS.register(instance);
		//try {
			//MinecraftForge.EVENT_BUS.addListener(instance::onPlayerJoinNew);
		//} catch (Exception e) {
			//try {
				//MinecraftForge.EVENT_BUS.addListener(instance::onPlayerJoinOld);
			//} catch (Exception e2) {
				//e.printStackTrace();
				//e2.printStackTrace();
			//}
		//}
		//try {
			//MinecraftForge.EVENT_BUS.addListener(instance::onPlayerRespawnsNew);
		//} catch (Exception e) {
			//try {
				//MinecraftForge.EVENT_BUS.addListener(instance::onPlayerRespawnsOld);
			//} catch (Exception e2) {
				//e.printStackTrace();
				//e2.printStackTrace();
			//}
		//}
		MinecraftForge.EVENT_BUS.addListener(instance::onPlayerJoin);
		MinecraftForge.EVENT_BUS.addListener(instance::onPlayerRespawns);
	}
	
	//@Override
	//public List<String> getStartItems() {
		// TODO Auto-generated method stub
		//return null;
		//List<String> copy = new ArrayList<String>();
		//Collections.copy(copy, startItems);
		//return copy;
	//}
	
	@Override
	public List<ItemStack> getStartItems() {
		// TODO Auto-generated method stub
		return getStartItems(null, true);
	}
	
	@Override
	//public List<ItemStack> getStartItems(EntityPlayer player) {
	public List<ItemStack> getStartItems(PlayerEntity player) {
		// TODO Auto-generated method stub
		return getStartItems(player, false);
	}
	
	@Override
	//public List<ItemStack> getStartItems(EntityPlayer player, boolean check) {
	public List<ItemStack> getStartItems(PlayerEntity player, boolean check) {
		// TODO Auto-generated method stub
		List<ItemStack> startItems = new ArrayList<ItemStack>();
		for(IStartItemContainer startItem:this.startItems) {
			//startItems.add(startItem.getItem());
			if(check) {
				ItemStack itm = startItem.getItem();
				//startItems.add(startItem.getItem());
				if(itm != null) {
					startItems.add(itm);
				}
			}
			else {
				ItemStack itm = startItem.getItem(player);
				//startItems.add(startItem.getItem(player));
				if(itm != null) {
					startItems.add(itm);
				}
			}
		}
		return startItems;
	}
	
	@Override
	//public void addStartItem(String item) {
	public void addStartItem(IStartItemContainer item) {
		// TODO Auto-generated method stub
		startItems.add(item);
	}
	
	@Override
	//public void removeStartItem(String item) {
	public void removeStartItem(IStartItemContainer item) {
		// TODO Auto-generated method stub
		//if(startItems.contains(item)) {
			//startItems.remove(item);
		//}
		for(IStartItemContainer con:startItems) {
			if(item.equals(con)) {
				startItems.remove(con);
			}
		}
	}
	
	@Override
	public void validateStartItems() {
		// TODO Auto-generated method stub
		getStartItems();
	}
	
	//public void onPlayerJoinNew(net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent e) {
		//onPlayerJoin(e.getPlayer());
	//}
	
	//public void onPlayerJoinOld(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent e) {
		
	//}
	
	//@Override
	//@SubscribeEvent
	//public void onPlayerJoin(PlayerLoggedInEvent e) {
	//public void onPlayerJoin(PlayerEntity player) {
	public <T extends Event> void onPlayerJoin(T e) {
		// TODO Auto-generated method stub
		PlayerEntity player = null;
		try {
			Class<?> EventClass = e.getClass();
			if(!EventClass.getName().endsWith("PlayerLoggedInEvent")) {
				return;
			}
			Method getPlayer = EventClass.getMethod("getPlayer");
			player = (PlayerEntity) getPlayer.invoke(e);
		} catch (Exception e2) {
			Messager.log.catching(e2);
			return;
		}
		//NBTTagCompound playerData = e.player.getEntityData();
		//NBTTagCompound data = getTag(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		CompoundNBT playerData = null;
		//CompoundNBT data = e.getPlayer().getPersistentData();
		//CompoundNBT data = player.getPersistentData();
		CompoundNBT data = null;
		try {
			data = player.getPersistentData();
		} catch (Throwable e2) {
			try {
				Method getEntityData = PlayerEntity.class.getMethod("getEntityData");
				//data = getTag((CompoundNBT) getEntityData.invoke(player), PlayerEntity.PERSISTED_NBT_TAG);
				playerData = (CompoundNBT) getEntityData.invoke(player);
				data = getTag(playerData, PlayerEntity.PERSISTED_NBT_TAG);
			} catch (Exception e3) {
				Messager.log.catching(e2);
				Messager.log.catching(e3);
			}
		}
		if(!data.getBoolean(GOT_START_ITEMS)) {
			//for(ItemStack itm:getStartItems(e.player)) {
			//for(ItemStack itm:getStartItems(e.getPlayer())) {
			for(ItemStack itm:getStartItems(player)) {
				try {
					//ItemHandlerHelper.giveItemToPlayer(e.player, itm);
					//ItemHandlerHelper.giveItemToPlayer(e.getPlayer(), itm);
					ItemHandlerHelper.giveItemToPlayer(player, itm);
				} catch (Exception e2) {
					// TODO: handle exception
					//ToMeSetupMod.logger.catching(e2);
					Messager.log.catching(e2);
				}
			}
			//data.setBoolean(GOT_START_ITEMS, true);
			data.putBoolean(GOT_START_ITEMS, true);
			//playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
			//playerData.setTag(PlayerEntity.PERSISTED_NBT_TAG, data);
			if(playerData != null) {
				playerData.put(PlayerEntity.PERSISTED_NBT_TAG, data);
			}
		}
	}
	
	//public void onPlayerRespawnsNew(net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent e) {
		//onPlayerRespawns(e.getPlayer());
	//}
	
	//public void onPlayerRespawnsOld(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent e) {
		
	//}
	
	//@Override
	//@SubscribeEvent
	//public void onPlayerRespawns(PlayerRespawnEvent e) {
	//public void onPlayerRespawns(PlayerEntity player) {
	public <T extends Event> void onPlayerRespawns(T e) {
		// TODO Auto-generated method stub
		if(startItemsOnRespawn) {
			PlayerEntity player = null;
			try {
				Class<?> EventClass = e.getClass();
				if(!EventClass.getName().endsWith("PlayerRespawnEvent")) {
					return;
				}
				Method getPlayer = EventClass.getMethod("getPlayer");
				player = (PlayerEntity) getPlayer.invoke(e);
			} catch (Exception e2) {
				Messager.log.catching(e2);
				return;
			}
			//for(ItemStack itm:getStartItems(e.player)) {
			//for(ItemStack itm:getStartItems(e.getPlayer())) {
			for(ItemStack itm:getStartItems(player)) {
				try {
					//ItemHandlerHelper.giveItemToPlayer(e.player, itm);
					//ItemHandlerHelper.giveItemToPlayer(e.getPlayer(), itm);
					ItemHandlerHelper.giveItemToPlayer(player, itm);
				} catch (Exception e2) {
					// TODO: handle exception
					//ToMeSetupMod.logger.catching(e2);
					Messager.log.catching(e2);
				}
			}
		}
	}

	@SuppressWarnings("unused")
	//private static NBTTagCompound getTag(NBTTagCompound tag, String key) {
	private static CompoundNBT getTag(CompoundNBT tag, String key) {
		//if(tag == null || !tag.hasKey(key)) {
		if(tag == null || !tag.contains(key)) {
			//return new NBTTagCompound();
			return new CompoundNBT();
		}
		//return tag.getCompoundTag(key);
		return tag.getCompound(key);
	}
	
}