/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.objects;

import data.DataNode;
import dataScene.DataScene;
import java.util.ArrayList;
import visual.node.VisualNode;
import overtoneinterface.IUGenInput;

/**
 *
 * @author Jon
 */
public class IUGenDataObject extends DataNode{

    protected int rate;
    protected ArrayList<IUGenInput> uGenInputs;

    public IUGenDataObject(String name, VisualNode visNode, DataScene ds, IUGenInput[] ins, int outs, int rate) {
        super(name, visNode, ds, ins.length, outs);
        this.rate = rate;
        this.uGenInputs = new ArrayList<IUGenInput>();

        for (int i = 0; i < ins.length; i++) { //add from array to an arraylist so that inputs can be dynamically added
            uGenInputs.add(0, ins[i]);
        }
    }
}
