package com.ToMe.ToMeSetup;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent.Serializer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Messager {
	
	private int Players = 5;
	private String Message;
	
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console.
	 * @param Message The Message to send to The Next 5 Joining Players and the Server Console.
	 */
	public Messager(String Message) {
		this.Message = Message;
		ToMeSetupMod.logger.error(Message);
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param Message The Message to send to The Next Joining Players and the Server Console.
	 * @param Players how many Players will get The Message.
	 */
	public Messager(String Message, int Players) {
		this.Message = Message;
		this.Players = Players;
		ToMeSetupMod.logger.error(Message);
	}
	
	/**
	 * Sends a Message to the Next Joining Players, the Player player and the Server Console.
	 * @param Message The Message to send to The Next Joining Players and the Server Console.
	 * @param Players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public Messager(String Message, int Players, EntityPlayer player) {
		this.Message = Message;
		player.sendMessage(Serializer.jsonToComponent("{\"text\":\"§cToMeSetup: §4" + Message + "\"}"));
		//player.addChatMessage(Serializer.jsonToComponent("{\"text\":\"§cToMeSetup: §4" + Message + "\"}"));
		this.Players = Players;
		ToMeSetupMod.logger.error(Message);
	}
	
	@SubscribeEvent
	public void onPlayerJoins(EntityJoinWorldEvent e) {
		if(Players > 0) {
			if(!e.getWorld().isRemote) {
				Entity entity = e.getEntity();
				if(entity instanceof EntityPlayer) {
					EntityPlayer p = (EntityPlayer)entity;
					p.sendMessage(Serializer.jsonToComponent("{\"text\":\"§cToMeSetup: §4" + Message + "\"}"));
					Players -= 1;
				}
			}
			//Entity entity = e.getEntity();
			//if(entity instanceof EntityPlayer) {
				//EntityPlayer p = (EntityPlayer)entity;
				//p.addChatMessage(Serializer.jsonToComponent("{\"text\":\"§cToMeSetup: §4" + Message + "\"}"));
				//Players -= 1;
			//}
		}
		//Entity entity = e.getEntity();
		//if(entity instanceof EntityPlayer) {
			//EntityPlayer p = (EntityPlayer)entity;
			//p.addChatMessage(Serializer.jsonToComponent("{\"text\":\"§cToMeSetup: §4" + Message + "\"}"));
		//}
	}
	
}