package com.ToMe.ToMeSetup.api.StartItems.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ToMe.ToMeSetup.api.Messager;
//import com.ToMe.ToMeSetup.ConfigHandler;
//import com.ToMe.ToMeSetup.ToMeSetupMod;
import com.ToMe.ToMeSetup.api.StartItems.IStartItemContainer;
import com.ToMe.ToMeSetup.api.StartItems.IStartItemProvider;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.items.ItemHandlerHelper;

public class StartItemProvider implements IStartItemProvider {
	
	//private List<String> startItems = new ArrayList<String>();
	private List<IStartItemContainer> startItems = new ArrayList<IStartItemContainer>();
	
	public static boolean startItemsOnRespawn = false;
	
	private static final String GOT_START_ITEMS = "tomesetup:gotstartitems";
	
	public static IStartItemProvider instance = new StartItemProvider();
	
	static {
		MinecraftForge.EVENT_BUS.register(instance);
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
	public List<ItemStack> getStartItems(EntityPlayer player) {
		// TODO Auto-generated method stub
		return getStartItems(player, false);
	}
	
	@Override
	public List<ItemStack> getStartItems(EntityPlayer player, boolean check) {
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
	
	@Override
	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent e) {
		// TODO Auto-generated method stub
		NBTTagCompound playerData = e.player.getEntityData();
		NBTTagCompound data = getTag(playerData, EntityPlayer.PERSISTED_NBT_TAG);
		if(!data.getBoolean(GOT_START_ITEMS)) {
			for(ItemStack itm:getStartItems(e.player)) {
				try {
					ItemHandlerHelper.giveItemToPlayer(e.player, itm);
				} catch (Exception e2) {
					// TODO: handle exception
					//ToMeSetupMod.logger.catching(e2);
					Messager.log.catching(e2);
				}
			}
			data.setBoolean(GOT_START_ITEMS, true);
			playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
		}
	}
	
	@Override
	@SubscribeEvent
	public void onPlayerRespawns(PlayerRespawnEvent e) {
		// TODO Auto-generated method stub
		if(startItemsOnRespawn) {
			for(ItemStack itm:getStartItems(e.player)) {
				try {
					ItemHandlerHelper.giveItemToPlayer(e.player, itm);
				} catch (Exception e2) {
					// TODO: handle exception
					//ToMeSetupMod.logger.catching(e2);
					Messager.log.catching(e2);
				}
			}
		}
	}
	
	private static NBTTagCompound getTag(NBTTagCompound tag, String key) {
		if(tag == null || !tag.hasKey(key)) {
			return new NBTTagCompound();
		}
		return tag.getCompoundTag(key);
	}
	
}