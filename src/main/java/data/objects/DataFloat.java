/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.objects;

import atom.AtomFloat;
import dataScene.DataScene;
import data.DataNode;
import visual.node.VisualNode;

/**
 *
 * @author Jon
 */
public class DataFloat extends DataNode {

    private float floatValue = 0f;

    public DataFloat(VisualNode parent, DataScene ds) {
        super(" 0.00 ", parent, ds, 2, 1);
        setInputs();
    }

    @Override
    public void update() {
        floatValue = this.getInput(0).getFloat();
        this.setOutput(0, new AtomFloat(floatValue));
        this.getParent().setLabel(Float.toString(floatValue));
        VisualNode node = (VisualNode) this.getParent();
        node.renderLabel();
        updateConnections();
    }

    private void setInputs() {
        this.setInput(0, new AtomFloat(0));
        this.setInput(1, new AtomFloat(0));
    }
}
