package com.ToMe.ToMeSetup;

import net.minecraft.world.GameRules.ValueType;
import net.minecraft.world.World;

public class GameruleHandler {
	
	//public static final String EXPLOSIONBLOCKDAMAGE = "ExplosionBlockDamage";
	public static final String EXPLOSIONBLOCKDAMAGE = "explosionBlockDamage";
	
	//public static void initGamerules(World w) {
	public static void onWorldLoad(World w) {
		//w.getGameRules().addGameRule("ExplosionBlockDamage", "true", ValueType.BOOLEAN_VALUE);
		//w.getGameRules().addGameRule(EXPLOSIONBLOCKDAMAGE, "true", ValueType.BOOLEAN_VALUE);
		if(!w.getGameRules().hasRule(EXPLOSIONBLOCKDAMAGE)) {
			//w.getGameRules().addGameRule(EXPLOSIONBLOCKDAMAGE, "true", ValueType.BOOLEAN_VALUE);
			if(ConfigHandler.explosionBlockDamage) {
				w.getGameRules().addGameRule(EXPLOSIONBLOCKDAMAGE, "true", ValueType.BOOLEAN_VALUE);
			}
		}
		else {
			ToMeSetupMod.logger.info("Gamerule " + EXPLOSIONBLOCKDAMAGE + " does allready exist!");
		}
	}
	
}