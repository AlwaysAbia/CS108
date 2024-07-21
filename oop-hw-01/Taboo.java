
/*
 HW1 Taboo problem class.
 Taboo encapsulates some rules about what objects
 may not follow other objects.
 (See handout).
*/

import java.util.*;

public class Taboo<T> {
	
	/**
	 * Constructs a new Taboo using the given rules (see handout.)
	 * @param rules rules for new Taboo
	 */
	private List<T> rules;
	public Taboo(List<T> rules) {
		this.rules = rules;
	}
	
	/**
	 * Returns the set of elements which should not follow
	 * the given element.
	 * @param elem
	 * @return elements which should not follow the given element
	 */
	public Set<T> noFollow(T elem) {
		T lastElem = null;
		Set<T> retSet = new HashSet<T>();
		for(T i : rules){
			if(lastElem == null){
				lastElem = i;
			}else if(lastElem.equals(elem)){
				retSet.add(i);
				lastElem = i;
			}
			lastElem = i;
		}
		return retSet;
	}
	
	/**
	 * Removes elements from the given list that
	 * violate the rules (see handout).
	 * @param list collection to reduce
	 */
	public void reduce(List<T> list) {
		T lastElem = null;
		Iterator<T> iterator = list.iterator();

		while (iterator.hasNext()) {
			T currentElem = iterator.next();
			if (lastElem == null) {
				lastElem = currentElem;
			} else if (noFollow(lastElem).contains(currentElem)) {
				iterator.remove(); // Safely remove the current element
			} else {
				lastElem = currentElem;
			}
		}
	}
}
