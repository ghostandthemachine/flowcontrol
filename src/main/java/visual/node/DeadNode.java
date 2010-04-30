/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual.node;

import dataScene.DataScene;
import visual.scene.VisualScene;

/**
 *
 * @author Jon
 */
public class DeadNode extends VisualNode{

    public DeadNode(VisualNode parent, DataScene dataScene) {
        super((VisualScene) parent.getModelScene());
//        this.setDataNode(new DataNode(parent, dataScene));

    }

}
