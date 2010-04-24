/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.awt.event.ActionEvent;
import java.util.HashSet;
import java.util.Iterator;
import javax.swing.AbstractAction;
import atom.Atom;
import dataScene.DataScene;
import visual.node.VisualNode;

/**
 *
 * @author Jon
 */
public class DataNode {

    protected String name;
    protected String atomType;
    protected int nOutputs;
    protected int nInputs;
    protected Atom[] output;
    protected Atom[] input;
    private HashSet<DataConnection> connections = new HashSet<DataConnection>();
    private Atom value;
    private VisualNode parent;
    private DataScene dataScene;
    protected String type = VisualNode.DATA;

    public DataNode(String t, VisualNode p, DataScene ds, int ins, int outs) {
        name = t;
        nInputs = ins;
        nOutputs = outs;
        parent = p;

        //create the Atom output/intput arrays
        output = new Atom[nOutputs];
        input = new Atom[nInputs];
        dataScene = ds;
    }

    public DataNode(String t, VisualNode p, DataScene ds, int ins, int outs, String tp) {
        name = tp;
        nInputs = ins;
        nOutputs = outs;
        parent = p;
        type = t;

        //create the Atom output/intput arrays
        output = new Atom[nOutputs];
        input = new Atom[nInputs];
        dataScene = ds;
    }

    public DataNode(VisualNode p, DataScene d) {
        nInputs = 0;
        nOutputs = 0;
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

    public VisualNode getParent() {
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
        return nInputs;
    }

    public void setNumInputs(int numInputs) {
        this.nInputs = numInputs;
    }

    public int getNumOutputs() {
        return nOutputs;
    }

    public String getTitle() {
        return name;
    }

    public void setNumOutputs(int numOutputs) {
        this.nOutputs = numOutputs;
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
        return nOutputs;
    }

    public int returnInputs() {
        return nInputs;
    }

    public String returnTitle() {
        return name;
    }

    private class OutputAction extends AbstractAction {

        public void actionPerformed(ActionEvent e) {
        }
    }
}
