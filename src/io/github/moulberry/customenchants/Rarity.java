package io.github.moulberry.customenchants;

public enum Rarity {
	
	COMMON, UNCOMMON, RARE, EPIC, LEGENDARY;
	
	public static Rarity getRarityForArcaneEnergy(int energy){
		if(energy>=400){
			return LEGENDARY;
		} else if(energy>=300){
			return EPIC;
		} else if(energy>=200){
			return RARE;
		} else if(energy>=100){
			return UNCOMMON;
		} else {
			return COMMON;
		}
	}

}
