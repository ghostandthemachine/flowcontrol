package ofAtom;

import java.util.ArrayList;

public class AtomArray extends Atom {
	
	ArrayList<Atom> atomArray = new ArrayList<Atom>();
	
	/**
	 * @param construct empty AtomArray
	 */
	public AtomArray()	{
                atomType = ATOM_ARRAY;
		setList(true);
	}
	
	/**
	 * @param construct with a Vector list of Atoms
	 */
	public AtomArray(ArrayList<Atom> aArray) {
		atomType = ATOM_ARRAY;
		atomArray = aArray;
		setList(true);
	}

	/**
	 * return the string representation
	 */
    @Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * return the string representation in an Array
	 */
    @Override
	public String[] getStringArray() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the data type of this atom. (0-7, int, int[], float, float[], string, string[], Atom, AtomArray).
	 */
	public String getType() {
		return atomType;
	}

    @Override
	public int getSize() {
		return atomArray.size();
	}
	
	public void add(Atom atom) {
		atomArray.add(atom);
	}

    @Override
	public AtomArray getArray() {
		// TODO Auto-generated method stub
		return this;
	}
	
	public void insertElementAt(Atom atom, int i){
		atomArray.set(i, atom);
	}

	public Atom get(int i){
		return atomArray.get(i);
	}
	
    @Override
	public Atom getElementAt(int i){
		return atomArray.get(i);
	}
	
	public ArrayList<Atom> getVectorArray() {
		return atomArray;
	}
	
	public void remove(int i){
		atomArray.remove(i);
	}
	
	public void remove(Atom atom){
		atomArray.remove(atom);
	}
	
	public void moveElementTo(int object, int index){
		Atom atom = atomArray.get(object);
		atomArray.remove(object);
		atomArray.set(index, atom);
	}
	
 
}
