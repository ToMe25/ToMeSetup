package com.ToMe.ToMeSetup.api;

import java.util.HashMap;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import com.ToMe.ToMeSetup.ConfigHandler;
//import com.ToMe.ToMeSetup.ToMeSetupMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent.Serializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Messager implements IMessager {
	
	/**
	 * how many Players should get this Message.
	 */
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
	
	public static boolean enableTooltips = true;
	
	//private static Logger log = LogManager.getLogger(ApiProps.MODID);
	public static Logger log = LogManager.getLogger(ApiProps.MODID);
	
	public Messager(String json, int players, @Nullable EntityPlayer player) {
		Message = json;
		if(player != null) {
			player.addChatMessage(Serializer.jsonToComponent(json));
		}
		Players = players;
	}
	
	@SubscribeEvent
	public void onPlayerJoin(EntityJoinWorldEvent e) {
		if(Players > 0) {
			if(!e.getWorld().isRemote) {
				Entity entity = e.getEntity();
				if(entity instanceof EntityPlayer) {
					EntityPlayer p = (EntityPlayer)entity;
					try {
						p.addChatMessage(Serializer.jsonToComponent(Message));
					} catch (Exception e2) {
						// TODO: handle exception
						//ToMeSetupMod.logger.catching(e2);
						log.catching(e2);
					}
					Players --;
				}
			}
		}
	}
	
	@Override
	public IMessager sendMessage(String msg) {
		// TODO Auto-generated method stub
		return sendMessage(msg, "ToMeSetup", 5, null, null);
	}
	
	@Override
	public IMessager sendMessage(String msg, String mod) {
		// TODO Auto-generated method stub
		return sendMessage(msg, mod, 5, null, null);
	}
	
	@Override
	public IMessager sendMessage(String msg, String mod, int players) {
		// TODO Auto-generated method stub
		return sendMessage(msg, mod, players, null, null);
	}
	
	@Override
	public IMessager sendMessage(String msg, String mod, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		return sendMessage(msg, mod, players, player, null);
	}
	
	@Override
	public IMessager sendMessage(String msg, String mod, int players, EntityPlayer player, String tooltip) {
		// TODO Auto-generated method stub
		IMessager mess = new Messager(tooltip == null ? textToJson(msg, mod) : textToJson(msg, mod, tooltip), players, player);
		MinecraftForge.EVENT_BUS.register(mess);
		//ToMeSetupMod.logger.error(msg);
		log.error(msg);
		return mess;
	}
	
	@Override
	public IMessager sendMessageJson(String msg) {
		// TODO Auto-generated method stub
		return sendMessageJson(msg, 5, null);
	}
	
	@Override
	public IMessager sendMessageJson(String msg, int players) {
		// TODO Auto-generated method stub
		return sendMessageJson(msg, players, null);
	}
	
	@Override
	public IMessager sendMessageJson(String msg, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		IMessager mess = new Messager(msg, players, player);
		MinecraftForge.EVENT_BUS.register(mess);
		loggJSON(msg);
		return mess;
	}
	
	@Override
	public IMessager sendMissingBlock(String block) {
		// TODO Auto-generated method stub
		return sendMissingBlock(block, 5, null, null);
	}
	
	@Override
	public IMessager sendMissingBlock(String block, int players) {
		// TODO Auto-generated method stub
		return sendMissingBlock(block, players, null, null);
	}
	
	@Override
	public IMessager sendMissingBlock(String block, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		return sendMissingBlock(block, players, player, null);
	}
	
	@Override
	public IMessager sendMissingBlock(String block, int players, EntityPlayer player, String tooltip) {
		// TODO Auto-generated method stub
		if(enableTooltips && tooltip != null) {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + block + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.missing\"},{\"text\":\"§4" + block + "!\"}]", players, player);
		}
	}
	
	@Override
	public IMessager sendMissingBlockOreDict(String oreDict) {
		// TODO Auto-generated method stub
		return sendMissingBlockOreDict(oreDict, 5, null, null);
	}
	
	@Override
	public IMessager sendMissingBlockOreDict(String oreDict, int players) {
		// TODO Auto-generated method stub
		return sendMissingBlockOreDict(oreDict, players, null, null);
	}
	
	@Override
	public IMessager sendMissingBlockOreDict(String oreDict, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		return sendMissingBlockOreDict(oreDict, players, player, null);
	}
	
	@Override
	public IMessager sendMissingBlockOreDict(String oreDict, int players, EntityPlayer player, String tooltip) {
		// TODO Auto-generated method stub
		if(enableTooltips && tooltip != null) {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		}
	}
	
	@Override
	public IMessager sendBlockOreDictItem(String number) {
		// TODO Auto-generated method stub
		return sendBlockOreDictItem(number, 5, null, null);
	}
	
	@Override
	public IMessager sendBlockOreDictItem(String number, int players) {
		// TODO Auto-generated method stub
		return sendBlockOreDictItem(number, players, null, null);
	}
	
	@Override
	public IMessager sendBlockOreDictItem(String number, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		return sendBlockOreDictItem(number, players, player, null);
	}
	
	@Override
	public IMessager sendBlockOreDictItem(String number, int players, EntityPlayer player, String tooltip) {
		// TODO Auto-generated method stub
		if(enableTooltips && tooltip != null) {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.block.oredict.item\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	@Override
	public IMessager sendMissingItem(String item) {
		// TODO Auto-generated method stub
		return sendMissingItem(item, 5, null, null);
	}
	
	@Override
	public IMessager sendMissingItem(String item, int players) {
		// TODO Auto-generated method stub
		return sendMissingItem(item, players, null, null);
	}
	
	@Override
	public IMessager sendMissingItem(String item, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		return sendMissingItem(item, players, player, null);
	}
	
	@Override
	public IMessager sendMissingItem(String item, int players, EntityPlayer player, String tooltip) {
		// TODO Auto-generated method stub
		if(enableTooltips && tooltip != null) {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + item + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.missing\"},{\"text\":\"§4" + item + "!\"}]", players, player);
		}
	}
	
	@Override
	public IMessager sendMissingItemOreDict(String oreDict) {
		// TODO Auto-generated method stub
		return sendMissingItemOreDict(oreDict, 5, null, null);
	}
	
	@Override
	public IMessager sendMissingItemOreDict(String oreDict, int players) {
		// TODO Auto-generated method stub
		return sendMissingItemOreDict(oreDict, players, null, null);
	}
	
	@Override
	public IMessager sendMissingItemOreDict(String oreDict, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		return sendMissingItemOreDict(oreDict, players, player, null);
	}
	
	@Override
	public IMessager sendMissingItemOreDict(String oreDict, int players, EntityPlayer player, String tooltip) {
		// TODO Auto-generated method stub
		if(enableTooltips && tooltip != null) {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + oreDict + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.oredict.missing\"},{\"text\":\"§4" + oreDict + "!\"}]", players, player);
		}
	}
	
	@Override
	public IMessager sendExceededStackLimit(String number) {
		// TODO Auto-generated method stub
		return sendExceededStackLimit(number, 5, null, null);
	}
	
	@Override
	public IMessager sendExceededStackLimit(String number, int players) {
		// TODO Auto-generated method stub
		return sendExceededStackLimit(number, players, null, null);
	}
	
	@Override
	public IMessager sendExceededStackLimit(String number, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		return sendExceededStackLimit(number, players, player, null);
	}
	
	@Override
	public IMessager sendExceededStackLimit(String number, int players, EntityPlayer player, String tooltip) {
		// TODO Auto-generated method stub
		if(enableTooltips && tooltip != null) {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.number.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	@Override
	public IMessager sendExceededItemMeta(String number) {
		// TODO Auto-generated method stub
		return sendExceededItemMeta(number, 5, null, null);
	}
	
	@Override
	public IMessager sendExceededItemMeta(String number, int players) {
		// TODO Auto-generated method stub
		return sendExceededItemMeta(number, players, null, null);
	}
	
	@Override
	public IMessager sendExceededItemMeta(String number, int players, EntityPlayer player) {
		// TODO Auto-generated method stub
		return sendExceededItemMeta(number, players, player, null);
	}
	
	@Override
	public IMessager sendExceededItemMeta(String number, int players, EntityPlayer player, String tooltip) {
		// TODO Auto-generated method stub
		if(enableTooltips && tooltip != null) {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}},{\"text\":\"§4" + number + "!\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]", players, player);
		}
		else {
			return sendMessageJson("[{\"text\":\"§cToMeSetup: \"},{\"translate\":\"tomesetup.item.meta.exceeded\"},{\"text\":\"§4" + number + "!\"}]", players, player);
		}
	}
	
	@Override
	public String textToJson(String text) {
		// TODO Auto-generated method stub
		return "{\"text\":\"§cToMeSetup: \"},{\"text\":\"§4" + text + "\"}";
	}
	
	@Override
	public String textToJson(String text, String mod) {
		// TODO Auto-generated method stub
		return "{\"text\":\"§c" + mod + ": \"},{\"text\":\"§4" + text + "\"}";
	}
	
	@Override
	public String textToJson(String text, String mod, String tooltip) {
		// TODO Auto-generated method stub
		if(enableTooltips) {
			return "[{\"text\":\"§c" + mod + ": \"},{\"text\":\"§4" + text + "\",\"hoverEvent\":{\"action\":\"show_text\",\"value\":\"" + tooltip + "\"}}]";
		}
		else {
			return "{\"text\":\"§c" + mod + ": \"},{\"text\":\"§4" + text + "\"}";
		}
	}
	
	/**
	 * Only Optimized for this Class, do not use!
	 * @param json the json to log.
	 */
	private static void loggJSON(String json) {
		try {
			//ToMeSetupMod.logger.error(getMessageFormJson(json));
			log.error(getMessageFormJson(json));
		} catch (Exception e) {
			// TODO: handle exception
			//ToMeSetupMod.logger.catching(e);
			log.catching(e);
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
	 * Only Optimized for this Class, do not use!
	 * @param json the json to get the Message form.
	 * @return the Message.
	 */
	private static String getMessageFormJson(String json) {
		//ToMeSetupMod.logger.debug("Messager: " + "Starting Message reading form: " + json);
		log.debug("Messager: " + "Starting Message reading form: " + json);
		JsonParser parser = new JsonParser();
		JsonElement jsonele = parser.parse(json);
		String Text = "";
		if(jsonele.isJsonObject()) {
			JsonObject jsonObj = jsonele.getAsJsonObject();
			for(Entry<String, JsonElement> e:jsonObj.entrySet()) {
				JsonElement element = e.getValue();
				if(e.getKey().equals("text")) {
					Text += element.getAsString();
				}
				else if(e.getKey().equals("translate")) {
					Text += Translate(element.getAsString());
				}
				else if(element.isJsonObject() || element.isJsonArray()) {
					Text += getMessageFormJson(element.toString());
				}
			}
		}
		else if(jsonele.isJsonArray()) {
			JsonArray jsonArr = jsonele.getAsJsonArray();
			for(JsonElement jsonElem:jsonArr) {
				if(jsonElem.isJsonObject() || jsonElem.isJsonArray()) {
					Text += getMessageFormJson(jsonElem.toString());
				}
			}
		}
		return Text;
	}
	
	/**
	 * Translate things to en_us because I18n don't want to do it.(I have Tested it with I18n but it don't worked :( )
	 * <br><s>DO NOT USE!!!</s><br>
	 * ONLY FOR THIS MOD!!!
	 * <br>Change the en_us_lang HashMap if you want to use this however.
	 * @param key Translation key.
	 * @return Translated String.
	 */
	protected static String Translate(String key) {
		//TODO make this using the .lang Files!!!
		if(en_us_lang == null) {
			initLangMap();
		}
		String Value;
		if(en_us_lang.containsKey(key)) {
			Value = en_us_lang.get(key);
		}
		else {
			Value = key + " ";
			//ToMeSetupMod.logger.warn("Couldn't Translate " + key  + "!");
			log.warn("Couldn't Translate " + key  + "!");
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
			en_us_lang.put("tomesetup.item.number.exceeded", "§4The Tried Item count was higher than the stack Limit of that Item, it was ");
			en_us_lang.put("tomesetup.item.meta.exceeded", "§4The Tried Item meta was higher than the meta Limit of that Item, it was ");
		}
	}
	
}