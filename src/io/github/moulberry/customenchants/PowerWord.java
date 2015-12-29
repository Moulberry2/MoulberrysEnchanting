package io.github.moulberry.customenchants;

public enum PowerWord {

	DEFENCE("negare", 25), ASSAULT("impetus", 25), DIVERT("derivatio", 15),
	WATER("aqua", 10), FIRE("ignis", 10), WIND("ventus", 10), MIND("sensus", 20),
	VOLATILITY("laevitas", 30), SPEED("rapiditas", 20), HEALTH("valetudo", 20),
	RANGED("remotius", 10), POISON("venenum", 10), DESTROY("perditio", 15),
	DAMAGE("iniuria", 15), CONTROL("imperium", 20), LUCK("fortuna", 20),
	GROUND("terra", 20);
		
	private String name;
	private int power;
		
	private PowerWord(String name, int power){
		this.name = name;
		this.power = power;
	}
		
	public String getLatinName(){
		return name;
	}
		
	public int getArcanePower(){
		return power;
	}
	
}
