package ofAtom;

import java.util.ArrayList;

public class AtomFloatArray extends Atom {

    float[] floatArray;
    ArrayList<AtomFloat> atomFloatArray = new ArrayList<AtomFloat>();

    public AtomFloatArray(float[] array) {
        atomType = FLOAT_ARRAY;
        floatArray = array;
        setList(true);	//set boolean that this is a list type Atom

        for (int i = 0; i < floatArray.length - 1; i++) {
            atomFloatArray.set(0, new AtomFloat(floatArray[i]));
        }
    }

    @Override
    public float getFloat() {
        return floatArray[0];
    }

    @Override
    public float[] getFloatArray() {
        return floatArray;
    }

    public ArrayList<AtomFloat> getAtomFloatArray() {
        return atomFloatArray;
    }

    @Override
    public AtomFloat getElementAt(int i) {
        return atomFloatArray.get(i);
    }

    @Override
    public int getInt() {
        return (int) floatArray[0];
    }

    @Override
    public int getSize() {
        return floatArray.length;
    }

    @Override
    public AtomArray getArray() {
        // TODO Auto-generated method stub
        AtomArray array = new AtomArray();
        for (int i = 0; i < atomFloatArray.size(); i++) {
            array.add(atomFloatArray.get(i));
        }
        return array;
    }
}
