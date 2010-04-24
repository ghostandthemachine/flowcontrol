package atom;

public class Atom {
	private boolean isList = false;
        String atomType = Atom.ATOM;

	AtomFloat atomFloat;
	AtomInt atomInt;
	AtomString atomString;

        public static final String INT = "int";
        public static final String FLOAT = "float";
        public static final String STRING = "String";
        public static final String ATOM = "atom";
        public static final String INT_ARRAY = "intArray";
        public static final String FLOAT_ARRAY = "floatArray";
        public static final String STRING_ARRAY = "stringArray";
        public static final String ATOM_ARRAY = "atomArray";

        
	/**
	 * @param empty constructor.
	 */
	public Atom() {
	}
	
	/**
	 * @return the isList boolean.
	 */
	public boolean isList() {
		return isList;
	}

	/**
	 * @param isList set boolean isList if Atom is a list.
	 */
	public void setList(boolean isList) {
		this.isList = isList;
	}
	
	/**
	 * @return the int value of the this atom.
	 */
	public int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * @return the int array of this atom.
	 */
	public int[] getIntArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the float array of this atom.
	 */
	public float getFloat() {
		// TODO Auto-generated method stub
		return 0f;
	}

	/**
	 * @return the string of this atom.
	 */
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the string array of this atom.
	 */
	public String[] getStringArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the type of this Atom
	 */
        public String getAtomType() {
            return atomType;
        }
	
	/**
	 * @return this atom.
	 */
	public Atom getAtom() {
		// TODO Auto-generated method stub
		return this;
	}

	/**
	 * @return returns how many elements in this atom (defaults to one).
	 */
	public int getSize() {
		// TODO Auto-generated method stub
		return 1;
	}
	
	/**
	 * @return return specified atom at given location.
	 */
	public Atom getElementAt(int i){
		return null;
	}

	/**
	 * @return the float array representation of this Atom.
	 */
	public float[] getFloatArray() {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @return the Vector representation of this Atom.
	 */
	public AtomArray getAtomArray() {
		return null;
	}
	
	/**
	 * @return takes in a string then returns the correct Atom type
	 */
	public static Atom parseAtom( String arg ) {
		Atom atom = new Atom();
	  if (arg.matches("[a-zA-Z][a-zA-Z0-9]*")) {
	    atom = new AtomString(arg);
	  } else if (arg.matches("[0-9]+\\.[0-9]+")) {
		    atom = new AtomFloat(Float.parseFloat(arg));
	  }  else if (arg.matches("[0-9]+")) {
	    atom = new AtomInt( Integer.parseInt(arg));
	  }
	  return atom;
	}

	/**
	 * @return the AtomArray representation of this Atom.
	 */
	public AtomArray getArray() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setValue(String s) {
		// TODO Auto-generated method stub
	}
	
	public void setValue(float f) {
		// TODO Auto-generated method stub
	}
	public void setValue(int i) {
		// TODO Auto-generated method stub
	}

	public void setValue(Atom atom) {
		// TODO Auto-generated method stub
		
	}
	
}