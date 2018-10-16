package com.ToMe.ToMeSetup.api.StartItems.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

//import com.ToMe.ToMeSetup.ConfigHandler;
//import com.ToMe.ToMeSetup.ToMeSetupMod;
import com.ToMe.ToMeSetup.api.IMessager;
import com.ToMe.ToMeSetup.api.Messager;
import com.ToMe.ToMeSetup.api.StartItems.IStartItemContainer;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class StartItemContainer implements IStartItemContainer {
	
	private String name;
	private int count;
	private int meta;
	private int number;
	private boolean oreDict;
	private IMessager messager;
	
	/**
	 * Creates a StartItemContainer with a Standard Messager
	 * @param name
	 * @param count
	 * @param meta
	 * @param number
	 * @param oreDict
	 */
	public StartItemContainer(String name, int count, int meta, int number, boolean oreDict) {
		this(name, count, meta, number, oreDict, null);
		//this.name = name;
		//this.count = count;
		//this.meta = meta;
		//this.number = number;
		//this.oreDict = oreDict;
	}
	
	/**
	 * Creates a StartItemContainer with a Custom Messager
	 * @param name
	 * @param count
	 * @param meta
	 * @param number
	 * @param oreDict
	 * @param messager
	 */
	public StartItemContainer(String name, int count, int meta, int number, boolean oreDict, @Nullable IMessager messager) {
		//if(oreDict && !name.contains(":")) {
			//name = "minecraft:" + name;
		//}
		this.name = name;
		this.count = count;
		this.meta = meta;
		this.number = number;
		this.oreDict = oreDict;
		if(messager == null) {
			this.messager = new Messager("{\"text\":\"Just for creating new ones.\"}", 0, null);
		}
		else {
			this.messager = messager;
		}
	}
	
	@Override
	public ItemStack getItem() {
		// TODO Auto-generated method stub
		return getItem(null);
	}
	
	@Override
	public ItemStack getItem(EntityPlayer player) {
		// TODO Auto-generated method stub
		ItemStack ret = null;
		try {
			if(oreDict) {
				List<ItemStack> oreDict = OreDictionary.getOres(name, false);
				if(!oreDict.isEmpty()) {
					int num = number;
					if(num >= oreDict.size()) {
						num = oreDict.size() - 1;
					}
					if(num < 0) {
						num = 0;
					}
					//ret = oreDict.get(num);
					ret = oreDict.get(num).copy();
				}
				else {
					if(player != null) {
						messager.sendMissingItemOreDict(name, 0, player);
					}
					else {
						messager.sendMissingItemOreDict(name, 6);
					}
				}
			}
			else {
				if(Item.REGISTRY.containsKey(new ResourceLocation(name))) {
					ret = new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(name)));
				}
				else {
					if(player != null) {
						messager.sendMissingItem(name, 0, player);
					}
					else {
						messager.sendMissingItem(name, 6);
					}
				}
			}
			if(ret != null) {
				int count = this.count;
				if(count < 1) {
					count = 1;
				}
				if(count > ret.getMaxStackSize()) {
					count = ret.getMaxStackSize();
					if(player != null) {
						messager.sendExceededStackLimit("" + this.count, 0, player);
					}
					else {
						messager.sendExceededStackLimit("" + this.count, 6);
					}
				}
				//ret.stackSize = count;
				ret.setCount(count);
				int meta = this.meta;
				//NonNullList<ItemStack> list = NonNullList.<ItemStack>create();
				//List<ItemStack> list = new ArrayList<ItemStack>();
				//ret.getItem().getSubItems(CreativeTabs.SEARCH, list);
				//ret.getItem().getSubItems(ret.getItem(), CreativeTabs.SEARCH, list);
				//List<Integer> metas = new ArrayList<Integer>();
				//ItemStack metaStack = ret.copy();
				//ItemStack metaStack = new ItemStack(ret.getItem());
				//int i = 0;
				//for(ItemStack s:list) {
				//while(i <= Integer.MAX_VALUE) {
					//metas.add(s.getMetadata());
					//metaStack.setItemDamage(i);
					//if(!metaStack.getDisplayName().equalsIgnoreCase(metaStack.getUnlocalizedName())) {
						//System.out.println("StartItemContainer: " + "Found Meta with localized Name " + metaStack.getDisplayName() + ", unlocalized Name " + metaStack.getUnlocalizedName() + " and Meta " + i);
						//metas.add(i);
					//}
					//i++;
				//}
				//if(ret.getHasSubtypes()) {
					//int i = 0;
					//while(i <= Integer.MAX_VALUE) {
						//metaStack.setItemDamage(i);
						//if(!metaStack.getDisplayName().equalsIgnoreCase(metaStack.getUnlocalizedName())) {
							//System.out.println("StartItemContainer: " + "Found Meta with localized Name " + metaStack.getDisplayName() + ", unlocalized Name " + metaStack.getUnlocalizedName() + " and Meta " + i);
							//metas.add(i);
						//}
						//i++;
					//}
				//}
				//if(meta > ret.getMaxDamage()) {
				//if(meta > ret.getItem().getMaxDamage(ret)) {
				if(meta > ret.getItem().getMaxDamage(ret) && !ret.getHasSubtypes()) {
				//if(meta > ret.getMaxDamage() && !metas.contains(meta)) {
					//meta = ret.getMaxDamage();
					meta = ret.getItem().getMaxDamage(ret);
					//meta = ret.getItem().getMetadata(meta);
					if(player != null) {
						messager.sendExceededItemMeta("" + this.meta, 0, player);
					}
					else {
						messager.sendExceededItemMeta("" + this.meta, 6);
					}
				}
				if(meta >= 0) {
					ret.setItemDamage(meta);
				}
				if(ret.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
					ret.setItemDamage(0);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			if(player != null) {
				messager.sendMessage("An Unknown Error occures while creating a ItemStack!", "ToMeSetup", 0, player, "Maybe its a Bug?");
			}
			else {
				messager.sendMessage("An Unknown Error occures while creating a ItemStack!", "ToMeSetup", 6, null, "Maybe its a Bug?");
			}
			//ToMeSetupMod.logger.catching(e);
			Messager.log.catching(e);
		}
		return ret;
	}
	
	@Override
	public void setItem(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}
	
	@Override
	public void setMeta(int meta) {
		// TODO Auto-generated method stub
		this.meta = meta;
	}
	
	@Override
	public void setCount(int count) {
		// TODO Auto-generated method stub
		this.count = count;
	}
	
	@Override
	public void setNumber(int number) {
		// TODO Auto-generated method stub
		this.number = number;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		//return super.equals(obj);
		if(obj == null) {
			return false;
		}
		if(obj instanceof StartItemContainer) {
			StartItemContainer sic = (StartItemContainer) obj;
			return sic.getItem().equals(getItem());
		}
		return false;
	}
	
}