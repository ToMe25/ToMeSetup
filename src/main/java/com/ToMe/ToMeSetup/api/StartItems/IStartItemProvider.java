package com.ToMe.ToMeSetup.api.StartItems;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public interface IStartItemProvider {
	
	/**
	 * Gets the Start Items from Config.
	 * Only use for Checks!
	 * @return returns a list of all Start Items
	 */
	//List<String> getStartItems();
	List<ItemStack> getStartItems();
	
	/**
	 * Gets the Start Items from Config.
	 * Only use for Checks!
	 * @param player it will send error messages to this player.
	 * @return returns a list of all Start Items
	 */
	List<ItemStack> getStartItems(EntityPlayer player);
	
	/**
	 * Gets the Start Items from Config.
	 * @param check is this a check or for use?
	 * @param player if this is not a check it will send error messages to this player.
	 * @return returns a list of all Start Items
	 */
	List<ItemStack> getStartItems(EntityPlayer player, boolean check);
	
	///**
	// * Adds the given String to the List of StartItems.
	// * can be OreDict(e.g. "treeSapling") or Item Name(e.g. "minecraft:log").
	// * @param item
	// */
	//void addStartItem(String item);
	
	/**
	 * Adds the given String to the List of StartItems.
	 * can be OreDict or an Item.
	 * @param item
	 */
	void addStartItem(IStartItemContainer item);
	
	///**
	// * Removes the given String from the List of StartItems if available.
	// * can be OreDict(e.g. "treeSapling") or Item Name(e.g. "minecraft:log").
	// * @param item
	// */
	//void removeStartItem(String item);
	
	/**
	 * Removes the given Start Item from the List of StartItems if available.
	 * can be OreDict or an Item.
	 * @param item
	 */
	void removeStartItem(IStartItemContainer item);
	
	/**
	 * validates the Start Item Config.
	 */
	void validateStartItems();
	
	//@SubscribeEvent
	void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent e);
	
	//@SubscribeEvent
	void onPlayerRespawns(PlayerEvent.PlayerRespawnEvent e);
	
}