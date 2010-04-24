package atom;

import java.util.ArrayList;

public class AtomFloat extends Atom{
	float floatValue;
	Float fVal;
	
	public AtomFloat(float value){
                atomType = FLOAT;
		floatValue = value;
		fVal = new Float(value);
	}

	public AtomFloat(double value){
		floatValue = (float)value;
		fVal = new Float(value);
	}

    @Override
	public void setValue(int i){
		floatValue = i;
	}
	
    @Override
	public void setValue(float f){
		floatValue = f;
	}
    @Override
	public void setValue(Atom atom) {
		// TODO Auto-generated method stub
		floatValue = atom.getFloat();
	}
	/**
	 * @return the float value
	 */
    @Override
	public float getFloat() {
		return floatValue;
	}

	/**
	 * @return the integer value
	 */
    @Override
	public int getInt() {
		Integer valueInt = fVal.intValue();
		return valueInt;
	}	
	
	/**
	 * @return the integer value
	 */
    @Override
	public String getString() {
		String sVal = fVal.toString();
		return sVal;
	}
	
	/**
	 * @return return this Atom
	 */
    @Override
	public Atom getAtom() {
		return this;
	}

	/**
	 * @return a new AtomArray with the float value set as the only element
	 */
    @Override
	public AtomArray getAtomArray() {
		ArrayList<Atom> atoms = new ArrayList<Atom>();
		atoms.add(new AtomFloat(floatValue));
		AtomArray array = new AtomArray(atoms);
		return array;
	}
}
