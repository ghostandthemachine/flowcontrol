package atom;

import java.util.ArrayList;

public class AtomString extends Atom {
	String stringValue;

	public AtomString(String value) {
		atomType = STRING;
		stringValue = value;

	}
	
    @Override
	public void setValue(String s){
		stringValue = s;
	}


	/**
	 * @return the integer value
	 */
    @Override
	public String getString() {
		return stringValue;
	}

	/**
	 * @return the integer value
	 */
    @Override
	public int getInt() {
		int val = 0;
		try {
			val = Integer.parseInt(stringValue);
		} catch (java.lang.NumberFormatException e) {
			
		}
		return val;
	}

	/**
	 * @return the integer value
	 */
    @Override
	public float getFloat() {
		float val = 0;
		try {
			val = Float.parseFloat(stringValue);
		} catch (java.lang.NumberFormatException e) {

		}
		return val;
	}
	
	/**
	 * @return a new AtomArray with the string value set as the only element
	 */
    @Override
	public AtomArray getAtomArray() {
		ArrayList<Atom> atoms = new ArrayList<Atom>();
		atoms.add(new AtomString(stringValue));
		AtomArray array = new AtomArray(atoms);
		return array;
	}
}
