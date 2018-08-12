package com.ToMe.ToMeSetup;

import java.util.ArrayList;
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
	
	public static void validateStartItemConfig() {
		getStartItems();
		/**int i = 0;
		while(i < ConfigHandler.StartItems.length && i < ConfigHandler.StartItemMetas.length && i < ConfigHandler.StartItemCounts.length) {
			try {
				String itemName = ConfigHandler.StartItems[i];
				if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName))) {
					ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), 1, ConfigHandler.StartItemMetas[i]);
					if(ConfigHandler.StartItemCounts[i] <= itm.getMaxStackSize()) {
						//itm.stackSize = ConfigHandler.StartItemCounts[i];
						itm.setCount(ConfigHandler.StartItemCounts[i]);
					}
					else {
						Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i], 6);
					}
				}
				else {
					Messager.sendMissingItem(itemName, 6);
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
						//itm.stackSize = ConfigHandler.StartItemOreDictCounts[i];
						itm.setCount(ConfigHandler.StartItemOreDictCounts[i]);
					}
					else {
						//itm.stackSize = 1;
						itm.setCount(1);
						Messager.sendExceededStackLimit("" + ConfigHandler.StartItemOreDictCounts[i], 6);
					}
				}
				else {
					Messager.sendMissingItemOreDict(OreDictName, 6);
				}
				i++;
			} catch (Exception e2) {
				// TODO: handle exception
				ToMeSetupMod.logger.catching(e2);
			}
		}*/
	}
	
	@SubscribeEvent
	public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e) {
		if(ConfigHandler.enableStartItems) {
			NBTTagCompound playerData = e.player.getEntityData();
			NBTTagCompound data = getTag(playerData, EntityPlayer.PERSISTED_NBT_TAG);
			if(!data.getBoolean(GOT_START_ITEMS)) {
				//for(ItemStack itm:getStartItems()) {
				for(ItemStack itm:getStartItems(e.player)) {
					try {
						ItemHandlerHelper.giveItemToPlayer(e.player, itm);
					} catch (Exception e2) {
						// TODO: handle exception
						ToMeSetupMod.logger.catching(e2);
					}
				}
				/**int i = 0;
				while(i < ConfigHandler.StartItems.length && i < ConfigHandler.StartItemMetas.length && i < ConfigHandler.StartItemCounts.length) {
					try {
						String itemName = ConfigHandler.StartItems[i];
						if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName))) {
							//ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), ConfigHandler.StartItemCounts[i], ConfigHandler.StartItemMetas[i]);
							ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), 1, ConfigHandler.StartItemMetas[i]);
							if(ConfigHandler.StartItemCounts[i] <= itm.getMaxStackSize()) {
								//itm.stackSize = ConfigHandler.StartItemCounts[i];
								itm.setCount(ConfigHandler.StartItemCounts[i]);
							}
							else {
								//ToMeSetupMod.registerMessager(new Messager(ConfigHandler.StartItemCounts[i] + " are more than the Stack limit!", 6, e.player));
								//TODO Rebuild after a Test!
								//Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i]);
								Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i], 0, e.player);
							}
							ItemHandlerHelper.giveItemToPlayer(e.player, itm);
							//System.out.println("Start Item Given");
						}
						else {
							//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find any Item Named " + itemName + "!"));
							//ToMeSetupMod.registerMessager(new Messager("Could not Find any Item Named " + itemName + "!"));
							//ToMeSetupMod.registerMessager(new Messager("Could not Find any Item Named " + itemName + "!", 6, e.player));
							//TODO Rebuild after a Test!
							Messager.sendMissingItem(itemName, 0, e.player);
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
								//itm.stackSize = ConfigHandler.StartItemOreDictCounts[i];
								itm.setCount(ConfigHandler.StartItemOreDictCounts[i]);
							}
							else {
								//itm.stackSize = 1;
								itm.setCount(1);
								//ToMeSetupMod.registerMessager(new Messager(ConfigHandler.StartItemOreDictCounts[i] + " are more than the Stack limit!", 6, e.player));
								//TODO Rebuild after a Test!
								Messager.sendExceededStackLimit("" + ConfigHandler.StartItemOreDictCounts[i], 0, e.player);
							}
							ItemHandlerHelper.giveItemToPlayer(e.player, itm);
							//System.out.println("Start Item Given");
						}
						else {
							//MinecraftForge.EVENT_BUS.register(new Messager("Could not Find any Item in the OreDictionary Named " + itemName + "!"));
							//ToMeSetupMod.registerMessager(new Messager("Could not Find any Item in the OreDictionary Named " + itemName + "!"));
							//ToMeSetupMod.registerMessager(new Messager("Could not Find any Item in the OreDictionary Named " + OreDictName + "!", 6, e.player));
							//TODO Rebuild after a Test!
							Messager.sendMissingItemOreDict(OreDictName, 0, e.player);
						}
						i++;
					} catch (Exception e2) {
						// TODO: handle exception
						ToMeSetupMod.logger.catching(e2);
					}
				}*/
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
			//for(ItemStack itm:getStartItems()) {
			for(ItemStack itm:getStartItems(e.player)) {
				try {
					ItemHandlerHelper.giveItemToPlayer(e.player, itm);
				} catch (Exception e2) {
					// TODO: handle exception
					ToMeSetupMod.logger.catching(e2);
				}
			}
			/**int i = 0;
			while(i < ConfigHandler.StartItems.length && i < ConfigHandler.StartItemMetas.length && i < ConfigHandler.StartItemCounts.length) {
				try {
					String itemName = ConfigHandler.StartItems[i];
					if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName))) {
						ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), 1, ConfigHandler.StartItemMetas[i]);
						if(ConfigHandler.StartItemCounts[i] <= itm.getMaxStackSize()) {
							//itm.stackSize = ConfigHandler.StartItemCounts[i];
							itm.setCount(ConfigHandler.StartItemCounts[i]);
						}
						else {
							//ToMeSetupMod.registerMessager(new Messager(ConfigHandler.StartItemCounts[i] + " are more than the Stack limit!", 6, e.player));
							//TODO Rebuild after a Test!
							//Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i]);
							Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i], 0, e.player);
						}
						ItemHandlerHelper.giveItemToPlayer(e.player, itm);
					}
					else {
						//ToMeSetupMod.registerMessager(new Messager("Could not Find any Item Named " + itemName + "!", 6, e.player));
						//TODO Rebuild after a Test!
						Messager.sendMissingItem(itemName, 0, e.player);
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
							//itm.stackSize = ConfigHandler.StartItemOreDictCounts[i];
							itm.setCount(ConfigHandler.StartItemOreDictCounts[i]);
						}
						else {
							//itm.stackSize = 1;
							itm.setCount(1);
							//ToMeSetupMod.registerMessager(new Messager(ConfigHandler.StartItemOreDictCounts[i] + " are more than the Stack limit!", 6, e.player));
							//TODO Rebuild after a Test!
							Messager.sendExceededStackLimit("" + ConfigHandler.StartItemOreDictCounts[i], 0, e.player);
						}
						ItemHandlerHelper.giveItemToPlayer(e.player, itm);
					}
					else {
						//ToMeSetupMod.registerMessager(new Messager("Could not Find any Item in the OreDictionary Named " + OreDictName + "!", 6, e.player));
						//TODO Rebuild after a Test!
						Messager.sendMissingItemOreDict(OreDictName, 0, e.player);
					}
					i++;
				} catch (Exception e2) {
					// TODO: handle exception
					ToMeSetupMod.logger.catching(e2);
				}
			}*/
		}
	}
	
	public static NBTTagCompound getTag(NBTTagCompound tag, String key) {
		if(tag == null || !tag.hasKey(key)) {
			return new NBTTagCompound();
		}
		return tag.getCompoundTag(key);
	}
	
	/**
	 * Gets the Start Items from Config.
	 * Only use for Checks!
	 * @return a list of the start Items.
	 */
	private static ArrayList<ItemStack> getStartItems() {
		//return getStartItems(false);
		return getStartItems(true, null);
	}
	
	/**
	 * Gets the Start Items from Config.
	 * Do not use for Checks!
	 * @param player it will send error messages to this player.
	 * @return a list of the start Items.
	 */
	private static ArrayList<ItemStack> getStartItems(EntityPlayer player) {
		return getStartItems(false, player);
	}
	
	/**
	 * Gets the Start Items from Config.
	 * @param check is this a check or for use?
	 * @param player if this is not a check it will send error messages to this player.
	 * @return a list of the start Items.
	 */
	//private static ArrayList<ItemStack> getStartItems() {
	//private static ArrayList<ItemStack> getStartItems(boolean check) {
	private static ArrayList<ItemStack> getStartItems(boolean check, EntityPlayer player) {
		ArrayList<ItemStack> aitemstack = new ArrayList<ItemStack>();
		int i = 0;
		while(i < ConfigHandler.StartItems.length) {
			try {
				String itemName = ConfigHandler.StartItems[i];
				if(ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName))) {
					//int Meta = 0;
					//if(ConfigHandler.StartItemMetas.length > i) {
						//Meta = ConfigHandler.StartItemMetas[i];
					//}
					//ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), 1, ConfigHandler.StartItemMetas[i]);
					//ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), 1, Meta);
					ItemStack itm = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(itemName)), 1, 0);
					//int Meta = 0;
					int Meta = itm.getItemDamage();
					if(ConfigHandler.StartItemMetas.length > i) {
						//Meta = ConfigHandler.StartItemMetas[i];
						//if(ConfigHandler.StartItemMetas[i] <= itm.getMaxDamage()) {
							//Meta = ConfigHandler.StartItemMetas[i];
						//}
						if(ConfigHandler.StartItemMetas[i] >= 0) {
							if(ConfigHandler.StartItemMetas[i] <= itm.getMaxDamage()) {
								Meta = ConfigHandler.StartItemMetas[i];
							}
							else {
								if(!check) {
									Messager.sendExceededItemMeta("" + ConfigHandler.StartItemMetas[i], 0, player);
								}
								else {
									Messager.sendExceededItemMeta("" + ConfigHandler.StartItemMetas[i], 6);
								}
							}
						}
					}
					itm.setItemDamage(Meta);
					int Count = 1;
					if(ConfigHandler.StartItemCounts.length > i) {
						//Count = ConfigHandler.StartItemCounts[i];
						if(ConfigHandler.StartItemCounts[i] <= itm.getMaxStackSize()) {
						//if(ConfigHandler.StartItemCounts[i] <= itm.getMaxStackSize() && ConfigHandler.StartItemCounts[i] > 0) {
							//Count = ConfigHandler.StartItemCounts[i];
							if(ConfigHandler.StartItemCounts[i] > 0) {
								Count = ConfigHandler.StartItemCounts[i];
							}
						}
						else {
							//Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i], 6);
							if(!check) {
								Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i], 0, player);
							}
							else {
								Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i], 6);
							}
						}
					}
					itm.setCount(Count);
					//itm.stackSize = Count;
					//if(ConfigHandler.StartItemCounts[i] <= itm.getMaxStackSize()) {
						//itm.stackSize = ConfigHandler.StartItemCounts[i];
					//}
					//else {
						//Messager.sendExceededStackLimit("" + ConfigHandler.StartItemCounts[i], 6);
					//}
					aitemstack.add(itm);
				}
				else {
					//Messager.sendMissingItem(itemName, 6);
					if(!check) {
						Messager.sendMissingItem(itemName, 0, player);
					}
					else {
						Messager.sendMissingItem(itemName, 6);
					}
				}
				i++;
			} catch (Exception e2) {
				// TODO: handle exception
				//Messager.sendMessage("An Unknown Error occures while creating a ItemStack!", 5, null, "Maybe its a Bug?");
				if(!check) {
					Messager.sendMessage("An Unknown Error occures while creating a ItemStack!", 0, player, "Maybe its a Bug?");
				}
				else {
					Messager.sendMessage("An Unknown Error occures while creating a ItemStack!", 6, null, "Maybe its a Bug?");
				}
				ToMeSetupMod.logger.catching(e2);
			}
		}
		i = 0;
		while(i < ConfigHandler.StartItemOreDicts.length) {
			try {
				String OreDictName = ConfigHandler.StartItemOreDicts[i];
				List<ItemStack> ores = OreDictionary.getOres(OreDictName);
				if(!ores.isEmpty()) {
					ItemStack itm = null;
					/*if(ConfigHandler.StartItemOreDictNumbers[i] < ores.size()) {
						itm = ores.get(ConfigHandler.StartItemOreDictNumbers[i]).copy();
					}*/
					if(ConfigHandler.StartItemOreDictNumbers.length > i) {
						if(ConfigHandler.StartItemOreDictNumbers[i] < ores.size()) {
							itm = ores.get(ConfigHandler.StartItemOreDictNumbers[i]).copy();
						}
						else {
							itm = ores.get(ores.size() - 1).copy();
						}
					}
					else {
						itm = ores.get(ores.size() - 1).copy();
					}
					//if(ConfigHandler.StartItemOreDictMetas.length > i) {
						//if(ConfigHandler.StartItemOreDictMetas[i] >= 0) {
							//itm.setItemDamage(ConfigHandler.StartItemOreDictMetas[i]);
						//}
					//}
					//if(ConfigHandler.StartItemOreDictMetas[i] > 0) {
						//itm.setItemDamage(ConfigHandler.StartItemOreDictMetas[i]);
					//}
					if(itm.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
						itm.setItemDamage(0);
					}
					//int Meta = 0;
					int Meta = itm.getItemDamage();
					if(ConfigHandler.StartItemOreDictMetas.length > i) {
						if(ConfigHandler.StartItemOreDictMetas[i] >= 0) {
							if(ConfigHandler.StartItemOreDictMetas[i] <= itm.getMaxDamage()) {
								Meta = ConfigHandler.StartItemOreDictMetas[i];
							}
							else {
								if(!check) {
									Messager.sendExceededItemMeta("" + ConfigHandler.StartItemOreDictMetas[i], 0, player);
								}
								else {
									Messager.sendExceededItemMeta("" + ConfigHandler.StartItemOreDictMetas[i], 6);
								}
							}
						}
					}
					itm.setItemDamage(Meta);
					int Count = 1;
					if(ConfigHandler.StartItemOreDictCounts.length > i) {
						//Count = ConfigHandler.StartItemOreDictCounts[i];
						if(ConfigHandler.StartItemOreDictCounts[i] <= itm.getMaxStackSize()) {
						//if(ConfigHandler.StartItemOreDictCounts[i] <= itm.getMaxStackSize() && ConfigHandler.StartItemOreDictCounts[i] > 0) {
							//Count = ConfigHandler.StartItemOreDictCounts[i];
							if(ConfigHandler.StartItemOreDictCounts[i] > 0) {
								Count = ConfigHandler.StartItemOreDictCounts[i];
							}
						}
						else {
							//Messager.sendExceededStackLimit("" + ConfigHandler.StartItemOreDictCounts[i], 6);
							if(!check) {
								Messager.sendExceededStackLimit("" + ConfigHandler.StartItemOreDictCounts[i], 0, player);
							}
							else {
								Messager.sendExceededStackLimit("" + ConfigHandler.StartItemOreDictCounts[i], 6);
							}
						}
					}
					itm.setCount(Count);
					//itm.stackSize = Count;
					//if(ConfigHandler.StartItemOreDictCounts[i] <= itm.getMaxStackSize()) {
						//itm.stackSize = ConfigHandler.StartItemOreDictCounts[i];
					//}
					//else {
						//itm.stackSize = 1;
						//Messager.sendExceededStackLimit("" + ConfigHandler.StartItemOreDictCounts[i], 6);
					//}
					aitemstack.add(itm);
				}
				else {
					//Messager.sendMissingItemOreDict(OreDictName, 6);
					if(!check) {
						Messager.sendMissingItemOreDict(OreDictName, 0, player);
					}
					else {
						Messager.sendMissingItemOreDict(OreDictName, 6);
					}
				}
				i++;
			} catch (Exception e2) {
				// TODO: handle exception
				//Messager.sendMessage("An Unknown Error occures while creating a ItemStack!", 5, null, "Maybe its a Bug?");
				if(!check) {
					Messager.sendMessage("An Unknown Error occures while creating a ItemStack!", 0, player, "Maybe its a Bug?");
				}
				else {
					Messager.sendMessage("An Unknown Error occures while creating a ItemStack!", 6, null, "Maybe its a Bug?");
				}
				ToMeSetupMod.logger.catching(e2);
			}
		}
		return aitemstack;
	}
	
}