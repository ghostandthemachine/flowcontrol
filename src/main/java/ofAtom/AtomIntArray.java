package ofAtom;

import java.util.ArrayList;

public class AtomIntArray extends Atom {

    int[] intArray;
    ArrayList<AtomInt> atomIntArray = new ArrayList<AtomInt>();

    public AtomIntArray(int[] array) {
        atomType = INT_ARRAY;
        intArray = array;
        setList(true);	//set boolean that this is a list type Atom

        for (int i = 0; i < intArray.length - 1; i++) {
            atomIntArray.set(0, new AtomInt(intArray[i]));
        }
    }

    @Override
    public float getFloat() {
        return (float) intArray[0];
    }

    @Override
    public int[] getIntArray() {
        return intArray;
    }

    public ArrayList<AtomInt> getAtomIntArray() {
        return atomIntArray;
    }

    @Override
    public AtomInt getElementAt(int i) {
        return atomIntArray.get(i);
    }

    @Override
    public int getSize() {
        return intArray.length;
    }
}
