import java.util.HashSet;
import java.util.Set;

// CS108 HW1 -- String static methods

public class StringCode {

	/**
	 * Given a string, returns the length of the largest run.
	 * A a run is a series of adajcent chars that are the same.
	 * @param str
	 * @return max run length
	 */
	public static int maxRun(String str)
	{
		int currRun = -1;
		char lastChar = 0;
		int maxRun = 0;

		for(int i = 0; i < str.length();i++){
			char currChar = str.charAt(i);
			if(currRun == -1){
				lastChar = currChar;
				currRun = 1;
			}else if(lastChar == currChar){
				currRun++;
			}else{
				if(currRun > maxRun) maxRun = currRun;
				lastChar = currChar;
				currRun = 1;
			}
			lastChar = currChar;
		}
		return maxRun;
	}

	
	/**
	 * Given a string, for each digit in the original string,
	 * replaces the digit with that many occurrences of the character
	 * following. So the string "a3tx2z" yields "attttxzzz".
	 * @param str
	 * @return blown up string
	 */
	public static String blowup(String str) {
		String resString = "";

		for(int i = 0; i < str.length();i++){
			char currChar = str.charAt(i);

			if(currChar >= 48 && currChar <= 57){
				if(i == str.length() - 1) break;

				char nextChar = str.charAt(i+1);
				int intedChar = Character.getNumericValue(currChar);
				for(int j = 0 ; j < intedChar; j++){
					resString += nextChar;
				}
			}else{
				resString += currChar;
			}
		}
		return resString;
	}
	
	/**
	 * Given 2 strings, consider all the substrings within them
	 * of length len. Returns true if there are any such substrings
	 * which appear in both strings.
	 * Compute this in linear time using a HashSet. Len will be 1 or more.
	 */
	public static boolean stringIntersect(String a, String b, int len) {
		HashSet<String> set = new HashSet<>();

		if(a.length() < len || b.length() < len) return false;

		for(int i = 0; i < a.length()-len + 1; i ++){
			String currSub = a.substring(i, i+len);
			set.add(currSub);
		}
		for(int i = 0; i < b.length()-len + 1; i ++){
			String currSub = b.substring(i, i+len);
			if(set.contains(currSub)) return true;
		}
		return false; // YOUR CODE HERE
	}
}
