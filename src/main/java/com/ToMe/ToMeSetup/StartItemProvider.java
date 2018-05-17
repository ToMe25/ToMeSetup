package com.ToMe.ToMeSetup;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;

public class StartItemProvider {
	
	private static final String GOT_START_ITEMS = "tomesetup:gotstartitems";
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e) {
		if(ConfigHandler.enableStartItems) {
			NBTTagCompound playerData = e.player.getEntityData();
			NBTTagCompound data = getTag(playerData, EntityPlayer.PERSISTED_NBT_TAG);
			if(!data.getBoolean(GOT_START_ITEMS)) {
				int i = 0;
				while(i < ConfigHandler.StartItems.length && i < ConfigHandler.StartItemMetas.length && i < ConfigHandler.StartItemCounts.length) {
					try {
						String itemName = ConfigHandler.StartItems[i];
						if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName))) {
							//ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), ConfigHandler.StartItemCounts[i], ConfigHandler.StartItemMetas[i]);
							ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), 1, ConfigHandler.StartItemMetas[i]);
							if(ConfigHandler.StartItemCounts[i] <= itm.getMaxStackSize()) {
								itm.stackSize = ConfigHandler.StartItemCounts[i];
							}
							else {
								ToMeSetupMod.registerMessager(new Messager(ConfigHandler.StartItemCounts[i] + " are more than the Stack limit!", 6, e.player));
							}
							ItemHandlerHelper.giveItemToPlayer(e.player, itm);
							//System.out.println("Start Item Given");
						}
						else {
							//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find any Item Named " + itemName + "!"));
							//ToMeSetupMod.registerMessager(new Messager("Could not Find any Item Named " + itemName + "!"));
							ToMeSetupMod.registerMessager(new Messager("Could not Find any Item Named " + itemName + "!", 6, e.player));
						}
						//ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(ConfigHandler.StartItems[i])), ConfigHandler.StartItemCounts[i], ConfigHandler.StartItemMetas[i]);
						//ItemHandlerHelper.giveItemToPlayer(e.player, itm);
						i++;
					} catch (Exception e2) {
						// TODO: handle exception
						ToMeSetupMod.logger.catching(e2);
					}
				}
				i = 0;
				while(i < ConfigHandler.StartItemOreDicts.length && i < ConfigHandler.StartItemOreDictMetas.length && i < ConfigHandler.StartItemOreDictCounts.length) {
					try {
						String OreDictName = ConfigHandler.StartItemOreDicts[i];
						List<ItemStack> ores = OreDictionary.getOres(OreDictName);
						if(!ores.isEmpty()) {
							ItemStack itm = null;
							if(ConfigHandler.StartItemOreDictNumbers[i] < ores.size()) {
								itm = ores.get(ConfigHandler.StartItemOreDictNumbers[i]).copy();
							}
							else {
								itm = ores.get(ores.size() - 1).copy();
							}
							if(ConfigHandler.StartItemOreDictMetas[i] > 0) {
								itm.setItemDamage(ConfigHandler.StartItemOreDictMetas[i]);
							}
							if(itm.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
								itm.setItemDamage(0);
							}
							if(ConfigHandler.StartItemOreDictCounts[i] <= itm.getMaxStackSize()) {
								itm.stackSize = ConfigHandler.StartItemOreDictCounts[i];
							}
							else {
								itm.stackSize = 1;
								ToMeSetupMod.registerMessager(new Messager(ConfigHandler.StartItemOreDictCounts[i] + " are more than the Stack limit!", 6, e.player));
							}
							ItemHandlerHelper.giveItemToPlayer(e.player, itm);
							//System.out.println("Start Item Given");
						}
						else {
							//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find any Item in the OreDictionary Named " + itemName + "!"));
							//ToMeSetupMod.registerMessager(new Messager("Could not Find any Item in the OreDictionary Named " + itemName + "!"));
							ToMeSetupMod.registerMessager(new Messager("Could not Find any Item in the OreDictionary Named " + OreDictName + "!", 6, e.player));
						}
						i++;
					} catch (Exception e2) {
						// TODO: handle exception
						ToMeSetupMod.logger.catching(e2);
					}
				}
				//ItemHandlerHelper.giveItemToPlayer(e.player, new ItemStack(Items.APPLE));
				data.setBoolean(GOT_START_ITEMS, true);
				playerData.setTag(EntityPlayer.PERSISTED_NBT_TAG, data);
				//System.out.println("StartItemsGiven");
			}
		}
	}
	
	@SubscribeEvent
	public void onPlayerRespawns(PlayerEvent.PlayerRespawnEvent e) {
		if(ConfigHandler.enableStartItems && ConfigHandler.startItemsOnRespawn) {
			int i = 0;
			while(i < ConfigHandler.StartItems.length && i < ConfigHandler.StartItemMetas.length && i < ConfigHandler.StartItemCounts.length) {
				try {
					String itemName = ConfigHandler.StartItems[i];
					if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName))) {
						ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), 1, ConfigHandler.StartItemMetas[i]);
						if(ConfigHandler.StartItemCounts[i] <= itm.getMaxStackSize()) {
							itm.stackSize = ConfigHandler.StartItemCounts[i];
						}
						else {
							ToMeSetupMod.registerMessager(new Messager(ConfigHandler.StartItemCounts[i] + " are more than the Stack limit!", 6, e.player));
						}
						ItemHandlerHelper.giveItemToPlayer(e.player, itm);
					}
					else {
						ToMeSetupMod.registerMessager(new Messager("Could not Find any Item Named " + itemName + "!", 6, e.player));
					}
					i++;
				} catch (Exception e2) {
					// TODO: handle exception
					ToMeSetupMod.logger.catching(e2);
				}
			}
			i = 0;
			while(i < ConfigHandler.StartItemOreDicts.length && i < ConfigHandler.StartItemOreDictMetas.length && i < ConfigHandler.StartItemOreDictCounts.length) {
				try {
					String OreDictName = ConfigHandler.StartItemOreDicts[i];
					List<ItemStack> ores = OreDictionary.getOres(OreDictName);
					if(!ores.isEmpty()) {
						ItemStack itm = null;
						if(ConfigHandler.StartItemOreDictNumbers[i] < ores.size()) {
							itm = ores.get(ConfigHandler.StartItemOreDictNumbers[i]).copy();
						}
						else {
							itm = ores.get(ores.size() - 1).copy();
						}
						if(ConfigHandler.StartItemOreDictMetas[i] > 0) {
							itm.setItemDamage(ConfigHandler.StartItemOreDictMetas[i]);
						}
						if(itm.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
							itm.setItemDamage(0);
						}
						if(ConfigHandler.StartItemOreDictCounts[i] <= itm.getMaxStackSize()) {
							itm.stackSize = ConfigHandler.StartItemOreDictCounts[i];
						}
						else {
							itm.stackSize = 1;
							ToMeSetupMod.registerMessager(new Messager(ConfigHandler.StartItemOreDictCounts[i] + " are more than the Stack limit!", 6, e.player));
						}
						ItemHandlerHelper.giveItemToPlayer(e.player, itm);
					}
					else {
						ToMeSetupMod.registerMessager(new Messager("Could not Find any Item in the OreDictionary Named " + OreDictName + "!", 6, e.player));
					}
					i++;
				} catch (Exception e2) {
					// TODO: handle exception
					ToMeSetupMod.logger.catching(e2);
				}
			}
		}
	}
	
	public static NBTTagCompound getTag(NBTTagCompound tag, String key) {
		if(tag == null || !tag.hasKey(key)) {
			return new NBTTagCompound();
		}
		return tag.getCompoundTag(key);
	}
	
}