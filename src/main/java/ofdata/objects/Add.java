/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofdata.objects;

import ofAtom.Atom;
import ofAtom.AtomFloat;
import ofAtom.AtomInt;
import ofDataScene.DataScene;
import ofdata.DataNode;
import ofvisual.node.ModelNode;

/**
 *
 * @author Jon
 */
public class Add extends DataNode {

    private int intAddition = 0;
    private float floatAddition = 0f;

    public Add(String t, ModelNode parent, DataScene ds) {
        super(" +     ", parent,ds, 2, 1);
        setInputs();
    }

    public Add(String t, ModelNode parent, DataScene ds, int i) {
        super(" +    " + Integer.toString(i),parent, ds, 2, 1);
        intAddition = i;
        setInputs();
    }

    public Add(String t, ModelNode parent, DataScene ds, float f) {
        super(t, parent, ds, 2, 1);
        floatAddition = f;
        setInputs();
    }

    @Override
    public void update() {
        if (this.getInput(1) != null) {
            if (this.getInput(1).getAtomType().equalsIgnoreCase(Atom.INT)) {
                intAddition = this.getInput(1).getInt();
            } else if (this.getInput(1).getAtomType().equalsIgnoreCase(Atom.FLOAT)) {
                floatAddition = this.getInput(1).getFloat();
            }
        }
        if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.INT)) {
            this.setOutput(0, new AtomInt(this.getInput(0).getInt() + intAddition + floatAddition));
        } else if (this.getInput(0).getAtomType().equalsIgnoreCase(Atom.FLOAT)) {
            this.setOutput(0, new AtomFloat(this.getInput(0).getFloat() + intAddition + floatAddition));
        }
        updateConnections();
    }

    private void setInputs() {
        this.setInput(0, new AtomInt(0));
        this.setInput(1, new AtomInt(0));
    }
}
