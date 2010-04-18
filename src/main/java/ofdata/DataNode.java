/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofdata;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.AbstractAction;
import ofAtom.Atom;
import ofDataScene.DataScene;
import ofvisual.node.ModelNode;

/**
 *
 * @author Jon
 */
public class DataNode {

    private String title;
    private String atomType;
    private int numOutputs;
    private int numInputs;
    private Atom[] output;
    private Atom[] input;
    private HashSet<DataConnection> connections = new HashSet<DataConnection>();
    private Atom value;
    private ModelNode parent;
    private DataScene dataScene;
    private String type = ModelNode.DATA;

    public DataNode(String t, ModelNode p, DataScene ds, int ins, int outs) {
        title = t;
        numInputs = ins;
        numOutputs = outs;
        parent = p;

        //create the Atom output/intput arrays
        output = new Atom[numOutputs];
        input = new Atom[numInputs];
        dataScene = ds;
    }

    public DataNode(String t, ModelNode p, DataScene ds, int ins, int outs, String tp) {
        title = tp;
        numInputs = ins;
        numOutputs = outs;
        parent = p;
        type = t;

        //create the Atom output/intput arrays
        output = new Atom[numOutputs];
        input = new Atom[numInputs];
        dataScene = ds;
    }

    public DataNode(ModelNode p, DataScene d) {
        numInputs = 0;
        numOutputs = 0;
        parent = p;
        dataScene = d;
    }

    public void update() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void updateConnections() {
        for (Iterator<DataConnection> i = connections.iterator(); i.hasNext();) {
            DataConnection con = (DataConnection) i.next();
            con.update();
        }
    }

    public DataScene getScene() {
        return dataScene;
    }

    public ModelNode getParent() {
        return parent;
    }

    public void addDataConnection(DataConnection connection) {
        connections.add(connection);
    }

    public void removeDataConnection(DataConnection connection) {
        connections.remove(connection);
    }

    public void clearConnections() {
        connections.clear();
    }

    public Atom getInput(int i) {
        return input[i];
    }

    public void setInput(int i, Atom input) {
        this.input[i] = input;
    }

    public Atom getOutput(int i) {
        return output[i];
    }

    public int getNumInputs() {
        return numInputs;
    }

    public void setNumInputs(int numInputs) {
        this.numInputs = numInputs;
    }

    public int getNumOutputs() {
        return numOutputs;
    }

    public String getTitle() {
        return title;
    }

    public void setNumOutputs(int numOutputs) {
        this.numOutputs = numOutputs;
    }

    public void setOutput(int i, Atom a) {
        this.output[i] = a;
    }

    public void setValue(Atom a) {
        value = a;
    }

    public Atom getValue() {
        return value;
    }

    public int returnOutputs() {
        return numOutputs;
    }

    public int returnInputs() {
        return numInputs;
    }

    public String returnTitle() {
        return title;
    }

    private class OutputAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
        }
    }
}
