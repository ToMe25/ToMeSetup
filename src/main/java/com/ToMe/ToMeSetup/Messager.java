package com.ToMe.ToMeSetup;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent.Serializer;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.util.text.translation.LanguageMap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import scala.util.parsing.json.JSONObject;

@Deprecated
public class Messager {
	
	private int Players = 5;
	
	/**
	 * The Message to be send to the first Players!
	 * Format: json!
	 */
	private String Message;
	
	/**
	 * The Translation Map for Server side things because Forge only works at Client.
	 */
	protected static HashMap<String, String> en_us_lang;
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console.
	 * @param Message The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param Message Format: text(not json)!
	 */
	/*public Messager(String Message) {
		//this.Message = Message;
		//this.Message = "{\"text\":\"§cToMeSetup: §4" + Message + "\"}";
		this.Message = textToJson(Message);
		ToMeSetupMod.logger.error(Message);
	}*/
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param Message The Message to send to The Next Joining Players and the Server Console.
	 * @param Message Format: text(not json)!
	 * @param Players how many Players will get The Message.
	 */
	/*public Messager(String Message, int Players) {
		//this.Message = Message;
		this.Message = textToJson(Message);
		this.Players = Players;
		ToMeSetupMod.logger.error(Message);
	}*/
	
	/**
	 * Sends a Message to the Next Joining Players, the Player player and the Server Console.
	 * @param Message The Message to send to The Next Joining Players and the Server Console.
	 * @param Message Format: text(not json)!
	 * @param Players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	/*public Messager(String Message, int Players, EntityPlayer player) {
		//this.Message = Message;
		this.Message = textToJson(Message);
		//player.addChatMessage(Serializer.jsonToComponent("{\"text\":\"§cToMeSetup: §4" + Message + "\"}"));
		player.addChatMessage(Serializer.jsonToComponent(textToJson(Message)));
		this.Players = Players;
		ToMeSetupMod.logger.error(Message);
	}*/
	
	/**
	 * Sends a Message to the Next Joining Players, the Player player and the Server Console.
	 * @param json The Message to send to The Next Joining Players and the Server Console.
	 * @param json Format: json!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param player can be null!
	 */
	public Messager(String json, int players, @Nullable EntityPlayer player) {
		Message = json;
		if(player != null) {
			player.addChatMessage(Serializer.jsonToComponent(json));
		}
		Players = players;
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 */
	public static void sendMessage(String msg) {
		MinecraftForge.EVENT_BUS.register(new Messager(textToJson(msg), 5, null));
		ToMeSetupMod.logger.error(msg);
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 * @param players how many Players will get The Message.
	 */
	public static void sendMessage(String msg, int players) {
		MinecraftForge.EVENT_BUS.register(new Messager(textToJson(msg), players, null));
		ToMeSetupMod.logger.error(msg);
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendMessage(String msg, int players, EntityPlayer player) {
		MinecraftForge.EVENT_BUS.register(new Messager(textToJson(msg), players, player));
		ToMeSetupMod.logger.error(msg);
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: text(not json)!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 */
	public static void sendMessage(String msg, int players, EntityPlayer player, String tooltip) {
		MinecraftForge.EVENT_BUS.register(new Messager(textToJson(msg, tooltip), players, player));
		ToMeSetupMod.logger.error(msg);
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: json!
	 */
	public static void sendMessageJson(String msg) {
		MinecraftForge.EVENT_BUS.register(new Messager(msg, 5, null));
		loggJSON(msg);
		//ToMeSetupMod.logger.error(msg);
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: json!
	 * @param players how many Players will get The Message.
	 */
	public static void sendMessageJson(String msg, int players) {
		MinecraftForge.EVENT_BUS.register(new Messager(msg, players, null));
		loggJSON(msg);
		//ToMeSetupMod.logger.error(msg);
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console.
	 * @param msg The Message to send to The Next 5 Joining Players and the Server Console.
	 * @param msg Format: json!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendMessageJson(String msg, int players, EntityPlayer player) {
		MinecraftForge.EVENT_BUS.register(new Messager(msg, players, player));
		loggJSON(msg);
		//ToMeSetupMod.logger.error(new msg);
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says the specified Block is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param block the missing Block!
	 */
	public static void sendMissingBlock(String block) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.missing\"},{\"text\":\"§4" + block + "!\"}]");
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\"},{\"text\":\"§4" + block + "!\"}]");
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + block + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]");
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\"},{\"text\":\"§4" + block + "!\"}]");
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Block is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param block the missing Block!
	 * @param players how many Players will get The Message.
	 */
	public static void sendMissingBlock(String block, int players) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.missing\"},{\"text\":\"§4" + block + "!\"}]", players);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\"},{\"text\":\"§4" + block + "!\"}]", players);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + block + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\"},{\"text\":\"§4" + block + "!\"}]", players);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Block is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param block the missing Block!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendMissingBlock(String block, int players, EntityPlayer player) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.missing\"},{\"text\":\"§4" + block + "!\"}]", players, player);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\"},{\"text\":\"§4" + block + "!\"}]", players, player);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + block + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\"},{\"text\":\"§4" + block + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Block is missing.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param block the missing Block!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 */
	public static void sendMissingBlock(String block, int players, EntityPlayer player, String tooltip) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"},{\"text\":\"§4" + block + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + block + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\"},{\"text\":\"§4" + block + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Block OreDict!
	 */
	public static void sendMissingBlockOreDict(String oreDict) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]");
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]");
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]");
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]");
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Block OreDict!
	 * @param players how many Players will get The Message.
	 */
	public static void sendMissingBlockOreDict(String oreDict, int players) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Block OreDict!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendMissingBlockOreDict(String oreDict, int players, EntityPlayer player) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Block OreDict!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 */
	public static void sendMissingBlockOreDict(String oreDict, int players, EntityPlayer player, String tooltip) {
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the missing Block Number!
	 */
	public static void sendBlockOreDictItem(String number) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]");
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]");
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]");
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\"},{\"text\":\"§4" + number + "!\"}]");
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the missing Block Number!
	 * @param players how many Players will get The Message.
	 */
	public static void sendBlockOreDictItem(String number, int players) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\"},{\"text\":\"§4" + number + "!\"}]", players);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the missing Block Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendBlockOreDictItem(String number, int players, EntityPlayer player) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Block with the specified OreDict Registration.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the missing Block Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 */
	public static void sendBlockOreDictItem(String number, int players, EntityPlayer player, String tooltip) {
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says the specified Item is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param item the missing Item!
	 */
	public static void sendMissingItem(String item) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.missing\"},{\"text\":\"§4" + item + "!\"}]");
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\"},{\"text\":\"§4" + item + "!\"}]");
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + item + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]");
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\"},{\"text\":\"§4" + item + "!\"}]");
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param item the missing Item!
	 * @param players how many Players will get The Message.
	 */
	public static void sendMissingItem(String item, int players) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.missing\"},{\"text\":\"§4" + item + "!\"}]", players);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\"},{\"text\":\"§4" + item + "!\"}]", players);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + item + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\"},{\"text\":\"§4" + item + "!\"}]", players);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item is missing.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param item the missing Item!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendMissingItem(String item, int players, EntityPlayer player) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.missing\"},{\"text\":\"§4" + item + "!\"}]", players, player);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\"},{\"text\":\"§4" + item + "!\"}]", players, player);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + item + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\"},{\"text\":\"§4" + item + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item is missing.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param item the missing Item!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 */
	public static void sendMissingItem(String item, int players, EntityPlayer player, String tooltip) {
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + item + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\"},{\"text\":\"§4" + item + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says there are no Item with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Item OreDict!
	 */
	public static void sendMissingItemOreDict(String oreDict) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]");
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]");
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]");
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]");
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Item with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Item OreDict!
	 * @param players how many Players will get The Message.
	 */
	public static void sendMissingItemOreDict(String oreDict, int players) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Item with the specified OreDict Registration.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Item OreDict!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendMissingItemOreDict(String oreDict, int players, EntityPlayer player) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says there are no Item with the specified OreDict Registration.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param oreDict the missing Item OreDict!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 */
	public static void sendMissingItemOreDict(String oreDict, int players, EntityPlayer player, String tooltip) {
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says the specified Item can't have so a high Count.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 */
	public static void sendExceededStackLimit(String number) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]");
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]");
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]");
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]");
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Count.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 */
	public static void sendExceededStackLimit(String number, int players) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Count.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendExceededStackLimit(String number, int players, EntityPlayer player) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Count.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 */
	public static void sendExceededStackLimit(String number, int players, EntityPlayer player, String tooltip) {
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next 5 Joining Players and the Server Console that says the specified Item can't have so a high Meta.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 */
	public static void sendExceededItemMeta(String number) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]");
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]");
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]");
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\"},{\"text\":\"§4" + number + "!\"}]");
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Meta.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 */
	public static void sendExceededItemMeta(String number, int players) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Meta.
	 * With the Tooltip "Probably not a Bug(most likely its a Config issue)!"!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 */
	public static void sendExceededItemMeta(String number, int players, EntityPlayer player) {
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		//sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"Probably not a Bug(most likely its a Config issue)!\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	/**
	 * Sends a Message to the Next Joining Players and the Server Console that says the specified Item can't have so a high Meta.
	 * With a custom Tooltip!
	 * The Tooltip will be only visible if "enableErrorTooltips" is enabled in the Server Config File!
	 * @param number the tried Item Number!
	 * @param players how many Players will get The Message.
	 * @param player an additional player to send the Message.
	 * @param tooltip a tooltip that will be send to the players(Console not) if the Config "enableErrorTooltips" is set to true.
	 * @param tooltip Format: text(not json)!
	 */
	public static void sendExceededItemMeta(String number, int players, EntityPlayer player, String tooltip) {
		if(ConfigHandler.enableTooltips) {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	@SubscribeEvent
	public void onPlayerJoins(EntityJoinWorldEvent e) {
		if(Players > 0) {
			if(!e.getWorld().isRemote) {
				Entity entity = e.getEntity();
				if(entity instanceof EntityPlayer) {
					EntityPlayer p = (EntityPlayer)entity;
					//p.addChatMessage(Serializer.jsonToComponent("{\"text\":\"§cToMeSetup: §4" + Message + "\"}"));
					//Players -= 1;
					//p.addChatMessage(Serializer.jsonToComponent(textToJson(Message)));
					//p.addChatMessage(Serializer.jsonToComponent(Message));
					try {
						p.addChatMessage(Serializer.jsonToComponent(Message));
					} catch (Exception e2) {
						// TODO: handle exception
						ToMeSetupMod.logger.catching(e2);
					}
					Players --;
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
	
	/**
	 * Converts a text to a json to print in Chat.
	 * @param text the input text.
	 * @param text Format: text(not json)!
	 * @return the finished json.
	 */
	protected static String textToJson(String text) {
		//return "{\"text\":\"§cToMeSetup: §4" + text + "\"}";
		return "{\"text\":\"§cToMeSetup: \"},{\"text\":\"§4" + text + "\"}";
	}
	
	/**
	 * Converts a text to a json to print in Chat(with Tooltip).
	 * @param text the input text.
	 * @param text Format: text(not json)!
	 * @param tooltip the Tooltip to be displayed on the Client Chat(if the Server Config "enableErrorTooltips" is set to true).
	 * @param tooltip Format: text(not json)!
	 * @return the finished json.
	 */
	protected static String textToJson(String text, String tooltip) {
		if(ConfigHandler.enableTooltips) {
			//return "{\"text\":\"§cToMeSetup: §4" + text + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}";
			return "[{\"text\":\"§cToMeSetup: \"},{\"text\":\"§4" + text + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]";
		}
		else {
			//return "{\"text\":\"§cToMeSetup: §4" + text + "\"}";
			return "{\"text\":\"§cToMeSetup: \"},{\"text\":\"§4" + text + "\"}";
		}
	}
	
	/**
	 * Only Optimized for this Class, do not use!
	 * @param json the json to get the Message form.
	 * @return the Message.
	 */
	private static String getMessageFormJson(String json) {
		ToMeSetupMod.logger.debug("Messager: " + "Starting Message reading form: " + json);
		JsonParser parser = new JsonParser();
		//JsonObject jsonObj = parser.parse(json).getAsJsonObject();
		JsonElement jsonele = parser.parse(json);
		String Text = "";
		if(jsonele.isJsonObject()) {
			JsonObject jsonObj = jsonele.getAsJsonObject();
			for(Entry<String, JsonElement> e:jsonObj.entrySet()) {
				//System.out.println("Messager: " + "KEY = " + e.getKey() + ", VALUE = " + e.getValue());
				JsonElement element = e.getValue();
				if(e.getKey().equals("text")) {
					Text += element.getAsString();
					//System.out.println("Messager: " + "text!");
					//System.out.println("Messager: " + element.getAsString());
				}
				else if(e.getKey().equals("translate")) {
					//Text += I18n.translateToFallback(element.getAsString());
					//System.out.println("Messager: " + I18n.translateToFallback(element.getAsString()));
					Text += Translate(element.getAsString());
				}
				else if(element.isJsonObject() || element.isJsonArray()) {
				//else if(element instanceof JsonObject || element instanceof JsonArray) {
					//getMessageFormJson(element.getAsString());
					//getMessageFormJson(element.toString());
					//System.out.println("Messager: " + element.toString());
					Text += getMessageFormJson(element.toString());
				}
			}
		}
		else if(jsonele.isJsonArray()) {
			JsonArray jsonArr = jsonele.getAsJsonArray();
			for(JsonElement jsonElem:jsonArr) {
				if(jsonElem.isJsonObject() || jsonElem.isJsonArray()) {
					//getMessageFormJson(jsonElem.getAsString());
					//getMessageFormJson(jsonElem.toString());
					//System.out.println("Messager: " + jsonElem.toString());
					Text += getMessageFormJson(jsonElem.toString());
				}
				/**if(jsonElem.isJsonObject()) {
					JsonObject jsonObj = jsonElem.getAsJsonObject();
					for(Entry<String, JsonElement> e:jsonObj.entrySet()) {
						JsonElement element = e.getValue();
						if(e.getKey().equals("text")) {
							Text += element.getAsString();
						}
						else if(e.getKey().equals("translate")) {
							//Text += I18n.translateToFallback(element.getAsString());
						}
						else if(element instanceof JsonObject || element instanceof JsonArray) {
							getMessageFormJson(element.getAsString());
						}
					}
				}*/
			}
		}
		/**for(Entry<String, JsonElement> e:jsonObj.entrySet()) {
			JsonElement element = e.getValue();
			if(e.getKey().equals("text")) {
				Text += element.getAsString();
			}
			else if(e.getKey().equals("translateable")) {
				Text += I18n.translateToFallback(element.getAsString());
			}
			else if(element instanceof JsonObject || element instanceof JsonArray) {
				getMessageFormJson(element.getAsString());
			}
		}*/
		//System.out.println("Text = " + Text);
		return Text;
	}
	
	/**
	 * Only Optimized for this Class, do not use!
	 * @param json the json to log.
	 */
	private static void loggJSON(String json) {
		try {
			ToMeSetupMod.logger.error(getMessageFormJson(json));
		} catch (Exception e) {
			// TODO: handle exception
			ToMeSetupMod.logger.catching(e);
		}
		//ToMeSetupMod.logger.error(getMessageFormJson(json));
		/**JsonParser parser = new JsonParser();
		JsonObject jsonObj = parser.parse(json).getAsJsonObject();
		String Text = "";
		for(Entry<String, JsonElement> e:jsonObj.entrySet()) {
			JsonElement element = e.getValue();
			if(e.getKey().equals("text")) {
				Text += element.getAsString();
			}
			else if(e.getKey().equals("translateable")) {
				Text += I18n.translateToFallback(element.getAsString());
			}
			else {
				for(Entry<String, JsonElement> ele:jsonObj.entrySet()) {
					JsonElement elem = ele.getValue();
					if(ele.getKey().equals("text")) {
						Text += elem.getAsString();
					}
					else if(ele.getKey().equals("translateable")) {
						Text += I18n.translateToFallback(elem.getAsString());
					}
				}
			}
		}
		ToMeSetupMod.logger.error(Text);*/
	}
	
	/**
	 * Translate things from this mod to en_us because Forge does it only Client Side.
	 * @param key Translation key.
	 * @return Translated String.
	 */
	/*protected static String Translate(String key) {
		return Translate(ToMeSetupMod.MODID, key);
	}*/
	
	/**
	 * Translate things to en_us because Forge does it only Client Side.
	 * @param key Translation key.
	 * @return Translated String.
	 */
	//public static String Translate(String modid, String key) {
		/**LanguageMap en_us = new LanguageMap();
		System.out.println("Messager: " + en_us.isKeyTranslated(key));
		System.out.println("Messager: " + en_us.isKeyTranslated(modid + "." + key));
		System.out.println("Messager: " + en_us.isKeyTranslated(modid + ":" + key));
		System.out.println("I18n: " + I18n.translateToFallback(key));
		System.out.println("I18n: " + I18n.translateToFallback(modid + "." + key));
		System.out.println("I18n: " + I18n.translateToFallback(modid + ":" + key));
		//return en_us.translateKey(key);
		return en_us.translateKey(modid + "." + key);
		//return en_us.translateKey(modid + ":" + key);*/
	//}
	
	/**
	 * Translate things to en_us because I18n don't want to do it.(I have Tested it with I18n but it don't worked :( )
	 * <br><s>DO NOT USE!!!</s><br>
	 * ONLY FOR THIS MOD!!!
	 * <br>Change the en_us_lang HashMap if you want to use this however.
	 * @param key Translation key.
	 * @return Translated String.
	 */
	protected static String Translate(String key) {
	//private static String Translate(String key) {
		//TODO make this using the .lang Files!!!
		/**if(key.equals("block.missing")) {
			return "§4Could not Find a Block Named ";
		}
		else if(key.equals("block.oredict.missing")) {
			return "§4Could not Find a Block with the OreDict Name ";
		}
		else if(key.equals("item.missing")) {
			return "§4Could not Find a Item Named ";
		}
		else if(key.equals("item.oredict.missing")) {
			return "§4Could not Find a Item with the OreDict Name ";
		}
		else if(key.equals("item.number.exceeded")) {
			return "§4The Tried Item Number was higher than the Stack Limit, it was ";
		}
		else {
			return key;
		}*/
		if(en_us_lang == null) {
			initLangMap();
		}
		String Value;
		if(en_us_lang.containsKey(key)) {
			Value = en_us_lang.get(key);
		}
		else {
			//Value = key;
			Value = key + " ";
			ToMeSetupMod.logger.warn("Couldn't Translate " + key  + "!");
		}
		return Value;
	}
	
	/**
	 * works only once!
	 * not able to reset the Map!
	 */
	protected static void initLangMap() {
		if(en_us_lang == null) {
			en_us_lang = new HashMap<String, String>();
			en_us_lang.put("tomesetup.block.missing", "§4Could not Find a Block Named ");
			en_us_lang.put("tomesetup.block.oredict.missing", "§4Could not Find a Block with the OreDict Name ");
			en_us_lang.put("tomesetup.block.oredict.item", "§4Could only Find a Item, at OreDict:Number ");
			en_us_lang.put("tomesetup.item.missing", "§4Could not Find a Item Named ");
			en_us_lang.put("tomesetup.item.oredict.missing", "§4Could not Find a Item with the OreDict Name ");
			//en_us_lang.put("tomesetup.item.number.exceeded", "§4The Tried Item Number was higher than the Stack Limit, it was ");
			//en_us_lang.put("tomesetup.item.number.exceeded", "§4The Tried Item count was higher than the Stack Limit, it was ");
			en_us_lang.put("tomesetup.item.number.exceeded", "§4The Tried Item count was higher than the stack Limit of that Item, it was ");
			en_us_lang.put("tomesetup.item.meta.exceeded", "§4The Tried Item meta was higher than the meta Limit of that Item, it was ");
		}
	}
	
}