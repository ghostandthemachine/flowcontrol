package ofAtom;

public class AtomStringArray extends Atom{
	
	String[] stringArray;
	
		AtomStringArray(String[] sArray){
			atomType = STRING_ARRAY;
			stringArray = sArray;
		}

    @Override
		public String[] getStringArray() {
			return stringArray;
		}
}
