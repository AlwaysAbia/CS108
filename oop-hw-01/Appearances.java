import java.util.*;

public class Appearances {
	
	/**
	 * Returns the number of elements that appear the same number
	 * of times in both collections. Static method. (see handout).
	 * @return number of same-appearance elements
	 */
	public static <T> int sameCount(Collection<T> a, Collection<T> b) {
		int ans = 0;
		HashMap<T, Integer> freqMap1 = new HashMap<T, Integer>();
		HashMap<T, Integer> freqMap2 = new HashMap<T, Integer>();

		for(T i : a){
			freqMap1.put(i, freqMap1.getOrDefault(i, 0) + 1);
		}
		for(T i : b){
			freqMap2.put(i, freqMap2.getOrDefault(i, 0) + 1);
		}

		for(T i : freqMap1.keySet()){
			if(freqMap1.get(i).equals(freqMap2.get(i))){
				ans++;
			}
		}

		return ans; // YOUR CODE HERE
	}
	
}
