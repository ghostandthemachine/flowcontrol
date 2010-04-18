/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ofvisual.node;

import ofDataScene.DataScene;
import ofdata.DataNode;
import ofvisual.node.ModelNode;
import ofvisual.scene.ModelScene;

/**
 *
 * @author Jon
 */
public class DeadNode extends ModelNode{

    public DeadNode(ModelNode parent, DataScene dataScene) {
        super((ModelScene) parent.getModelScene(), dataScene);
        this.setDataNode(new DataNode(parent, dataScene));

    }

}
