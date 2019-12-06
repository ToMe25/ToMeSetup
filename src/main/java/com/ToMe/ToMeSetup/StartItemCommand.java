package com.ToMe.ToMeSetup;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import com.ToMe.ToMeSetup.api.StartItems.impl.StartItemProvider;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;

public class StartItemCommand extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "startitems";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "command.startitems.usage";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		// TODO Auto-generated method stub
		return 2;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		EntityPlayer player = null;
		if(args.length == 0) {
			//player = getCommandSenderAsPlayer(sender);
			for(EntityPlayer p:server.getPlayerList().getPlayerList()) {
				execute(server, sender, new String[] {p.getUniqueID().toString()});
			}
			return;
		}
		else {
			player = getPlayer(server, sender, args[0]);
		}
		boolean flag = false;
		//for(ItemStack itm:StartItemProvider.instance.getStartItems()) {
		for(ItemStack itm:StartItemProvider.instance.getStartItems(player)) {
			int count = itm.stackSize;
			//boolean fl = player.inventory.addItemStackToInventory(itm);
			boolean fl = player.inventory.addItemStackToInventory(itm.copy());
			flag = (fl == true || flag == true);
			//ToMeSetupMod.logger.info("StartItemsCommand: " + Integer.valueOf(itm.stackSize));
			//notifyCommandListener(sender, this, "command.startitems.success", new Object[] {itm.getTextComponent(), Integer.valueOf(itm.stackSize), player.getName()});
			notifyCommandListener(sender, this, "command.startitems.success", new Object[] {itm.getTextComponent(), Integer.valueOf(count), player.getName()});
			if(!fl) {
				EntityItem entityitem = player.dropItem(itm, false);
				if (entityitem != null) {
					entityitem.setNoPickupDelay();
					entityitem.setOwner(player.getName());
				}
			}
		}
		if (flag) {
			player.worldObj.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
			player.inventoryContainer.detectAndSendChanges();
		}
	}
	
	public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}
	
	public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
	}
	
}