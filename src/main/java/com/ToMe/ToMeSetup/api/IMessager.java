package com.ToMe.ToMeSetup.api;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public interface IMessager {
	
	/**
	 * sends the Message to the Joined Entity if it is a Player
	 * @param e
	 */
	//@SubscribeEvent
	void onPlayerJoin(EntityJoinWorldEvent e);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console(from ToMeSetup).
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 * @return
	 */
	IMessager sendMessage(String msg);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 * @param mod the mod to use as sender.(start of Message in dark red)
	 * @return
	 */
	IMessager sendMessage(String msg, String mod);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 * @param mod the mod to use as sender.(start of Message in dark red)
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendMessage(String msg, String mod, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 * @param mod the mod to use as sender.(start of Message in dark red)
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendMessage(String msg, String mod, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 * @param mod the mod to use as sender.(start of Message in dark red)
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 * @return
	 */
	IMessager sendMessage(String msg, String mod, int players, @Nullable EntityPlayer player, @Nullable String tooltip);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: json!
	 * @return
	 */
	IMessager sendMessageJson(String msg);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: json!
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendMessageJson(String msg, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: json!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendMessageJson(String msg, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says the specified Block is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param block the missing Block!
	 * @return
	 */
	IMessager sendMissingBlock(String block);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Block is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param block the missing Block!
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendMissingBlock(String block, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Block is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param block the missing Block!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendMissingBlock(String block, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Block is missing.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param block the missing Block!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 * @return
	 */
	IMessager sendMissingBlock(String block, int players, @Nullable EntityPlayer player, @Nullable String tooltip);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Block OreDict!
	 * @return
	 */
	IMessager sendMissingBlockOreDict(String oreDict);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Block OreDict!
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendMissingBlockOreDict(String oreDict, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Block OreDict!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendMissingBlockOreDict(String oreDict, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Block OreDict!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 * @return
	 */
	IMessager sendMissingBlockOreDict(String oreDict, int players, @Nullable EntityPlayer player, @Nullable String tooltip);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says there are no Block(only a Item) with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the missing Block Number!
	 * @return
	 */
	IMessager sendBlockOreDictItem(String number);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block(only a Item) with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the missing Block Number!
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendBlockOreDictItem(String number, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block(only a Item) with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the missing Block Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendBlockOreDictItem(String number, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block(only a Item) with the specified OreDict Registration.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the missing Block Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 * @return
	 */
	IMessager sendBlockOreDictItem(String number, int players, @Nullable EntityPlayer player, @Nullable String tooltip);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says the specified Item is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param item the missing Item!
	 * @return
	 */
	IMessager sendMissingItem(String item);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param item the missing Item!
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendMissingItem(String item, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param item the missing Item!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendMissingItem(String item, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item is missing.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param item the missing Item!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 * @return
	 */
	IMessager sendMissingItem(String item, int players, @Nullable EntityPlayer player, @Nullable String tooltip);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says there are no Item with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Item OreDict!
	 * @return
	 */
	IMessager sendMissingItemOreDict(String oreDict);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Item with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Item OreDict!
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendMissingItemOreDict(String oreDict, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Item with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Item OreDict!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendMissingItemOreDict(String oreDict, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Item with the specified OreDict Registration.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Item OreDict!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 * @return
	 */
	IMessager sendMissingItemOreDict(String oreDict, int players, @Nullable EntityPlayer player, @Nullable String tooltip);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says the specified Item can't have so a high Count.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @return
	 */
	IMessager sendExceededStackLimit(String number);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Count.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendExceededStackLimit(String number, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Count.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendExceededStackLimit(String number, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Count.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 * @return
	 */
	IMessager sendExceededStackLimit(String number, int players, @Nullable EntityPlayer player, @Nullable String tooltip);
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says the specified Item can't have so a high Meta.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @return
	 */
	IMessager sendExceededItemMeta(String number);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Meta.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @return
	 */
	IMessager sendExceededItemMeta(String number, int players);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Meta.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @return
	 */
	IMessager sendExceededItemMeta(String number, int players, @Nullable EntityPlayer player);
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Meta.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 * @return
	 */
	IMessager sendExceededItemMeta(String number, int players, @Nullable EntityPlayer player, @Nullable String tooltip);
	
	/**
	 * Converts a text to a json to print in Chat(for mod from the implementing class).
	 * @param text the input text.
	 * @param text Format: text(not json)!
	 * @return the finished json.
	 */
	String textToJson(String text);
	
	/**
	 * Converts a text to a json to print in Chat(for mod from the implementing class).
	 * @param text the input text.
	 * @param text Format: text(not json)!
	 * @param mod the mod to write at the start of the Message
	 * @return the finished json.
	 */
	String textToJson(String text, String mod);
	
	/**
	 * Converts a text to a json to print in Chat(with Tooltip).
	 * @param text the input text.
	 * @param text Format: text(not json)!
	 * @param mod the mod to write at the start of the Message
	 * @param tooltip the Tooltip to be displayed on the Client Chat(if the Server Config "enableErrorTooltips" is set to true).
	 * @param tooltip Format: text(not json)!
	 * @return the finished json.
	 */
	String textToJson(String text, String mod, @Nullable String tooltip);
	
}