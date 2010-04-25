/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.objects;

import data.DataNode;
import dataScene.DataScene;
import java.util.ArrayList;
import visual.node.VisualNode;
import overtoneinterface.IUGenConnection;

/**
 *
 * @author Jon
 */
public class IUGenDataObject extends DataNode{

    protected int rate;
    protected ArrayList<IUGenConnection> uGenInputs;

    public IUGenDataObject(String name, VisualNode visNode, DataScene ds, int ins, int outs, int rate) {
        super("  " + name + "  ", visNode, ds, ins, outs);
        this.rate = rate;
        this.uGenInputs = new ArrayList<IUGenConnection>();
    }
}
