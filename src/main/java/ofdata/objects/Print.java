package ofdata.objects;

import java.util.ArrayList;
import ofAtom.Atom;
import ofAtom.AtomArray;
import ofDataScene.DataScene;
import ofdata.DataNode;
import ofvisual.node.ModelNode;

public class Print extends DataNode {

    private boolean prepend;
    private String prependString;

    public Print(ModelNode parent, DataScene ds) {
        super("print", parent, ds, 1, 0);
        this.setNumOutputs(0);
        this.setNumInputs(1);
    }

    public Print(String pString, ModelNode parent, DataScene ds) {
        super(pString, parent, ds, 1, 0);
        prepend = true;
        prependString = pString;
        this.setNumOutputs(0);
        this.setNumInputs(1);
    }

    @Override
    public void update() {
        try {
            if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.INT)) {
                messageToPrint(this.getInput(0).getInt());
            } else if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.INT_ARRAY)) {
                messageToPrint(this.getInput(0).getIntArray());
            } else if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.FLOAT)) {
                messageToPrint(this.getInput(0).getFloat());
            } else if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.FLOAT_ARRAY)) {
                messageToPrint(this.getInput(0).getFloatArray());
            } else if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.STRING)) {
                messageToPrint(this.getInput(0).getString());
            } else if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.STRING_ARRAY)) {
                messageToPrint(this.getInput(0).getStringArray());
            } else if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.STRING_ARRAY)) {
                messageToPrint(this.getInput(0).getStringArray());
            } else if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.ATOM_ARRAY)) {
                messageToPrint(this.getInput(0).getArray());
            }
        } catch (NullPointerException e) {
        }
    }

    void messageToPrint(int[] i) {
        if (prepend) {
            System.out.print(prepend + " ");
            for (int j = 0; j < i.length; j++) {
                System.out.print(i[j]);
                System.out.print(" ");
            }
            System.out.println();
        } else {
            for (int j = 0; j < i.length; j++) {
                System.out.print(i[j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    void messageToPrint(int i) {
        if (prepend) {
            System.out.println(prependString + " " + i);
        } else {
            System.out.println(i);
        }
    }

    void messageToPrint(float[] f) {
        if (prepend) {
            System.out.print(prepend + " ");
            for (int i = 0; i < f.length; i++) {
                System.out.print(f[i]);
                System.out.print(" ");
            }
            System.out.println();
        } else {
            for (int i = 0; i < f.length; i++) {
                System.out.print(f[i]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    void messageToPrint(float f) {
        if (prepend) {
            System.out.println(prependString + " " + f);
        } else {
            System.out.println(f);
        }
    }

    void messageToPrint(String[] s) {
        if (prepend) {
            System.out.print(prepend + " ");
            for (int i = 0; i < s.length; i++) {
                System.out.print(s[i]);
                System.out.print(" ");
            }
            System.out.println();
        } else {
            for (int i = 0; i < s.length; i++) {
                System.out.print(s[i]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    void messageToPrint(String s) {
        if (prepend) {
            System.out.println(prependString + " " + s);
        } else {
            System.out.println(s);
        }
    }

    void messageToPrint(AtomArray atomArray) {
        for (int i = 0; i < atomArray.getSize(); i++) {
            System.out.print(atomArray.get(i).getString());
            System.out.print(" ");
        }
        System.out.println();
    }

    void messageToPrint(ArrayList<Atom> atomArray) {
        for (int i = 0; i < atomArray.size(); i++) {
            System.out.print(atomArray.get(i).getString());
            System.out.print(" ");
        }
        System.out.println();
    }
}
