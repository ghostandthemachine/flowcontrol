package atom;

import java.util.ArrayList;

public class AtomInt extends Atom{
	int intValue;
	Float floatValue;
	
	public AtomInt(int iVal){
		atomType = INT;
		intValue = iVal;
	}

        public AtomInt(float fVal) {
            atomType = INT;
            intValue = (int) fVal;
        }
	
    @Override
	public void setValue(Atom atom) {
		// TODO Auto-generated method stub
		intValue = atom.getInt();
	}
	
    @Override
	public void setValue(int i){
		intValue = i;
	}
	
    @Override
	public void setValue(float f){
		intValue = (int)f;
	}
	
    @Override
	public int getInt() {
		return intValue;
	}
    @Override
	public float getFloat() {
		floatValue = new Float(intValue);
		return floatValue;
	}
	public int getType() {
		return 0;
	}
	
    @Override
	public String getString() {
		String s;
		s = Integer.toString(intValue);
		return s;
	}
	
	/**
	 * @return a new AtomArray with the int value set as the only element
	 */
    @Override
	public AtomArray getAtomArray() {
		ArrayList<Atom> atoms = new ArrayList<Atom>();
		atoms.add(new AtomInt(intValue));
		AtomArray array = new AtomArray(atoms);
		return array;
	}
}
