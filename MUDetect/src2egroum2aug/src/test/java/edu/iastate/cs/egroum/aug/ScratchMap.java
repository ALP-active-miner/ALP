package edu.iastate.cs.egroum.aug;

import java.util.HashMap;
import java.util.Map;

public class ScratchMap {

	public static void main(String...strings ) {
		Map<Integer, Float>  map = new HashMap<>();
		
		if (map.containsKey(0)) {
			float test = map.get(0);
		}
		
		if (map.get(0) != null) {
			float test = map.get(0);
		}
	}
}
