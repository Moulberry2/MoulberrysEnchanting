package io.github.moulberry.customenchants.utilities;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public class DistributedRandomNumberGenerator implements Cloneable{
    private final NavigableMap<Double, Integer> map = new TreeMap<>();
    private final Random random = new Random();
    private double total = 0;
    
    public DistributedRandomNumberGenerator clone(){
    	try {
			return (DistributedRandomNumberGenerator) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
    }

    public void addWeight(int value, double weight) {
        if (weight <= 0)return;
        total+=weight;
        map.put(total, value);
    }
    
    /**
     * @param value The starting value that is to be changed
     * @param weight The weight (chance) of the starting value
     * @param deviation How many numbers on either side of the starting value will be affected by the ripple
     * @param ripple The change between each stepping value
     * @param affectNegative Whether the deviation will affect values in the negative direction
     * @param affectPositive Whether the deviation will affect values in the positive direction
     */
	public void addWeightsWithDeviation(int value, double weight, int deviation, double ripple, boolean affectNegative, boolean affectPositive){
    	addWeight(value, weight);
    	if(affectNegative || affectPositive){		
	    	for(int i=0; i<deviation; i++){
	    		if(affectPositive)addWeight(value+1+i, weight*Math.pow(ripple, i+1));
	    		if(affectNegative)addWeight(value-1-i, weight*Math.pow(ripple, i+1));
	    	}
    	}
    }

    public int nextInteger() {
        double value = random.nextDouble() * total;
        return map.ceilingEntry(value).getValue();
    }
}