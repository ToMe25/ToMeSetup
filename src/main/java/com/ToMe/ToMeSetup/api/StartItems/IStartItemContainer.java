package com.ToMe.ToMeSetup.api.StartItems;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;

//import java.util.List;

import net.minecraft.item.ItemStack;

public interface IStartItemContainer {
	
	///**
	// * if this Contains a oreDict this Method will return the List with all Items from that oreDict
	// * if this contains a Item this will return a List with only that Item.
	// * @return
	// */
	//List<ItemStack> getItems();
	
	/**
	 * this will return the ItemStack represented by this Container
	 * @return
	 */
	ItemStack getItem();
	
	/**
	 * this will return the ItemStack represented by this Container
	 * @param player the player that will get the error Messages. If null the errors will be send to the next 6 Joining/Respawning Players
	 * @return
	 */
	ItemStack getItem(@Nullable EntityPlayer player);
	
	/**
	 * sets the Item Name or oreDict to the given String.
	 * @param name
	 */
	void setItem(String name);
	
	/**
	 * sets the Item Meta.
	 * @param meta
	 */
	void setMeta(int meta);
	
	/**
	 * sets how many Items this should return.
	 * @param Count
	 */
	void setCount(int count);
	
	/**
	 * only usefully for OreDict.
	 * sets the Number of that Item in the List of all Items registered with this OreDictionary Name
	 * @param number
	 */
	void setNumber(int number);
	
}