package com.ToMe.ToMeSetup;

//import java.util.ArrayList;
import java.util.Collection;
//import java.util.Collections;
//import java.util.List;

//import javax.annotation.Nullable;

import com.ToMe.ToMeSetup.api.StartItems.impl.StartItemProvider;
import com.mojang.brigadier.CommandDispatcher;
//import com.mojang.brigadier.arguments.IntegerArgumentType;

//import net.minecraft.command.CommandBase;
//import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
//import net.minecraft.command.arguments.ItemArgument;
//import net.minecraft.entity.Entity;
//import net.minecraft.command.ICommandSender;
//import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
//import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
//import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;

public class StartItemCommand {
//public class StartItemCommand extends CommandBase {
	
	public static void register(CommandDispatcher<CommandSource> dispatcher) {
		  dispatcher.register(Commands.literal("startitems").requires((source) -> {
	         return source.hasPermissionLevel(2);
	      }).executes((context) -> {
		     return startitems(context.getSource(), null);
		  }).then(Commands.argument("targets", EntityArgument.players()).executes((context) -> {
	         return startitems(context.getSource(), EntityArgument.getPlayers(context, "targets"));
	      })));
	   }

	/*@Override
	//public String getCommandName() {
	public String getName() {
		// TODO Auto-generated method stub
		return "startitems";
	}*/

	/*@Override
	//public String getCommandUsage(ICommandSender sender) {
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "command.startitems.usage";
	}*/
	
	/*@Override
	public int getRequiredPermissionLevel() {
		// TODO Auto-generated method stub
		return 2;
	}*/

	//@Override
	//public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
	//private static int startitems(CommandSource source, Collection<? extends Entity> targets) {
	private static int startitems(CommandSource source, Collection<ServerPlayerEntity> targets) {
		// TODO Auto-generated method stub
		if(targets == null) {
			//targets = new ArrayList<ServerPlayerEntity>();
			//try {
				//targets.add(source.asPlayer());
			//} catch (Exception e) {
				//ToMeSetupMod.logger.catching(e);
			//}
			targets = source.getServer().getPlayerList().getPlayers();
		}
		//EntityPlayer player = null;
		//PlayerEntity player = null;
		//if(args.length == 0) {
			//player = getCommandSenderAsPlayer(sender);
			//for(EntityPlayer p:server.getPlayerList().getPlayerList()) {
			//for(EntityPlayer p:server.getPlayerList().getPlayers()) {
				//execute(server, sender, new String[] {p.getUniqueID().toString()});
			//}
		//}
		//else {
			//player = getPlayer(server, sender, args[0]);
		//}
		//boolean flag = false;
		//for(ItemStack itm:StartItemProvider.instance.getStartItems()) {
		//for(ItemStack itm:StartItemProvider.instance.getStartItems(player)) {
			//int count = itm.stackSize;
			//int count = itm.getCount();
			//boolean fl = player.inventory.addItemStackToInventory(itm);
			//boolean fl = player.inventory.addItemStackToInventory(itm.copy());
			//flag = (fl == true || flag == true);
			//ToMeSetupMod.logger.info("StartItemsCommand: " + Integer.valueOf(itm.stackSize));
			//notifyCommandListener(sender, this, "command.startitems.success", new Object[] {itm.getTextComponent(), Integer.valueOf(itm.stackSize), player.getName()});
			//notifyCommandListener(sender, this, "command.startitems.success", new Object[] {itm.getTextComponent(), Integer.valueOf(count), player.getName()});
			//if(!fl) {
				//EntityItem entityitem = player.dropItem(itm, false);
				//ItemEntity entityitem = player.dropItem(itm, false);
				//if (entityitem != null) {
					//entityitem.setNoPickupDelay();
					//entityitem.setOwner(player.getName());
					//entityitem.setOwnerId(player.getUniqueID());
				//}
			//}
		//}
		//if (flag) {
			//player.worldObj.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
			//player.world.playSound((EntityPlayer)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
			//player.world.playSound((PlayerEntity)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
			//player.inventoryContainer.detectAndSendChanges();
			//player.container.detectAndSendChanges();
		//}
		//for(Entity e:targets) {
		for(PlayerEntity player:targets) {
			//if(e instanceof PlayerEntity) {
				//PlayerEntity player = (PlayerEntity) e;
				boolean flag = false;
				for(ItemStack itm:StartItemProvider.instance.getStartItems(player)) {
					int count = itm.getCount();
					boolean fl = player.inventory.addItemStackToInventory(itm.copy());
					flag = (fl == true || flag == true);
					source.sendFeedback(new TranslationTextComponent("command.startitems.success", count, itm.getTextComponent(), player.getDisplayName()), true);
					if(!fl) {
						ItemEntity entityitem = player.dropItem(itm, false);
						if (entityitem != null) {
							entityitem.setNoPickupDelay();
							entityitem.setOwnerId(player.getUniqueID());
						}
					}
				}
				if (flag) {
					player.world.playSound((PlayerEntity)null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, ((player.getRNG().nextFloat() - player.getRNG().nextFloat()) * 0.7F + 1.0F) * 2.0F);
					player.container.detectAndSendChanges();
				}
			//}
		}
		
		//if (targets.size() == 1) {
			//source.sendFeedback(new TranslationTextComponent("command.startitems.success", targets.iterator().next().getDisplayName()), true);
		//} else {
			//source.sendFeedback(new TranslationTextComponent("command.startitems.success", targets.size()), true);
		//}
		return targets.size();
	}
	
	/*public boolean isUsernameIndex(String[] args, int index) {
		return index == 0;
	}*/
	
	/*//public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
		//return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getAllUsernames()) : Collections.<String>emptyList();
		return args.length == 1 ? getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()) : Collections.<String>emptyList();
	}*/
	
}