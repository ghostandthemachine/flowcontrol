/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.objects;

import data.DataNode;
import dataScene.DataScene;
import visual.node.VisualNode;

/**
 *
 * @author Jon
 */
public class DeadDataNode extends DataNode {

    public DeadDataNode(String name, VisualNode node, DataScene dataScene, int in, int out) {
        super(name, node, dataScene, 0, 0);
    }
}
