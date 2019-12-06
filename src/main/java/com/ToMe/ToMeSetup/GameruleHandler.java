package com.ToMe.ToMeSetup;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
//import java.lang.reflect.Modifier;
import java.util.Map;

//import net.minecraft.world.GameRules.ValueType;
//import net.minecraft.world.World;
//import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;
//import net.minecraft.world.GameRules.BooleanValue;
//import net.minecraft.world.GameRules.RuleKey;
//import net.minecraft.world.GameRules.RuleType;

public class GameruleHandler {
	
	//public static final String EXPLOSIONBLOCKDAMAGE = "ExplosionBlockDamage";
	//public static final String EXPLOSIONBLOCKDAMAGE = "explosionBlockDamage";
	//public static RuleKey<BooleanValue> EXPLOSIONBLOCKDAMAGE;
	//public static RuleKey<?> EXPLOSIONBLOCKDAMAGE;
	public static Object EXPLOSIONBLOCKDAMAGE;
	//public static final RuleKey<BooleanValue> EXPLOSIONBLOCKDAMAGE = BooleanValue.create(false);
	//public static final RuleKey<BooleanValue> EXPLOSIONBLOCKDAMAGE = GameRules.register("explosionBlockDamage", BooleanValue.create(false));
	
	@SuppressWarnings({ "null", "unchecked" })
	public static void registerGamerules() {
		if(ConfigHandler.explosionBlockDamage) {
			//EXPLOSIONBLOCKDAMAGE = GameRules.register("explosionBlockDamage", BooleanValue.create(true));
			try {
				Class<?> RuleType = Class.forName("net.minecraft.world.GameRules$RuleType");
				//Method register = GameRules.class.getDeclaredMethod("register", String.class, RuleType.class);
				Method register = null;
				try {
					register = GameRules.class.getDeclaredMethod("func_223595_a", String.class, RuleType);
				} catch(Exception e2) {
					register = GameRules.class.getDeclaredMethod("register", String.class, RuleType);
				}
				Class<?> BooleanValue = Class.forName("net.minecraft.world.GameRules$BooleanValue");
				Method create = null;
				try {
					create = BooleanValue.getMethod("func_223568_b", boolean.class);
				} catch (Exception e) {
					create = BooleanValue.getMethod("create", boolean.class);
				}
				//EXPLOSIONBLOCKDAMAGE = (RuleKey<?>) register.invoke(null, "explosionBlockDamage", create.invoke(null, true));
				EXPLOSIONBLOCKDAMAGE = register.invoke(null, "explosionBlockDamage", create.invoke(null, true));
			} catch (Exception e) {
				//EXPLOSIONBLOCKDAMAGE = new RuleKey("explosionBlockDamage");
				EXPLOSIONBLOCKDAMAGE = "explosionBlockDamage";
				try {
					Method getDefinitions = null;
					try {
						getDefinitions = GameRules.class.getDeclaredMethod("func_196231_c");
					} catch (Exception e2) {
						getDefinitions = GameRules.class.getDeclaredMethod("getDefinitions");
					}
					Map<String, ?> definitions = (Map<String, ?>) getDefinitions.invoke(null);
					Class<?> ValueDefinition = Class.forName("net.minecraft.world.GameRules$ValueDefinition");
					Class<?> ValueType = Class.forName("net.minecraft.world.GameRules$ValueType");
					Constructor<?> DefinitionConstructor = ValueDefinition.getConstructor(String.class, ValueType);
					Method valueOf = ValueType.getMethod("valueOf", String.class);
					Method put = Map.class.getDeclaredMethod("put", Object.class, Object.class);
					put.invoke(definitions, EXPLOSIONBLOCKDAMAGE, DefinitionConstructor.newInstance(EXPLOSIONBLOCKDAMAGE, valueOf.invoke(null, "BOOLEAN_VALUE")));
				} catch (Exception e2) {
					ToMeSetupMod.logger.catching(e);
					ToMeSetupMod.logger.catching(e2);
				}
			}
		}
	}
	
	/*//public static void initGamerules(World w) {
	public static void onWorldLoad(World w) {
		//w.getGameRules().addGameRule("ExplosionBlockDamage", "true", ValueType.BOOLEAN_VALUE);
		//w.getGameRules().addGameRule(EXPLOSIONBLOCKDAMAGE, "true", ValueType.BOOLEAN_VALUE);
		//if(!w.getGameRules().hasRule(EXPLOSIONBLOCKDAMAGE)) {
			//w.getGameRules().addGameRule(EXPLOSIONBLOCKDAMAGE, "true", ValueType.BOOLEAN_VALUE);
			//if(ConfigHandler.explosionBlockDamage) {
				//w.getGameRules().addGameRule(EXPLOSIONBLOCKDAMAGE, "true", ValueType.BOOLEAN_VALUE);
				//GameRules.register("explosionBlockDamage", EXPLOSIONBLOCKDAMAGE);
			//}
		//}
		//else {
			//ToMeSetupMod.logger.info("Gamerule " + EXPLOSIONBLOCKDAMAGE + " does allready exist!");
		//}
		if(ConfigHandler.explosionBlockDamage) {
			try {
				Method setOrCreateGameRule = null;
				try {
					setOrCreateGameRule = GameRules.class.getDeclaredMethod("func_82764_b", String.class, String.class, MinecraftServer.class);
				} catch (Exception e) {
					setOrCreateGameRule = GameRules.class.getDeclaredMethod("setOrCreateGameRule", String.class, String.class, MinecraftServer.class);
				}
				setOrCreateGameRule.invoke(w.getGameRules(), EXPLOSIONBLOCKDAMAGE, "true", w.getServer());
			} catch (Exception e) {
				// ignore exception
			}
		}
	}*/
	
}