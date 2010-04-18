/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofdata;

import ofDataScene.DataScene;
import ofvisual.node.ModelNode;
import ofvisual.scene.ModelScene;

/**
 *
 * @author Jon
 */
class Scene {
    DataScene scene;
    private final ModelScene modelScene;
  //  DataNodeManager creator = new DataNodeManager(scene);

    public Scene(ModelScene ms) {
        modelScene = ms;
        scene = new DataScene(modelScene);
    }

    public void createNode(String string, ModelNode node) {
   //     creator.createObject(string, node);

    }

    public void getNode(int i) {
        scene.getNode(i);
    }

    public void connect(DataNode n1, DataNode n2) {
        scene.connect(n1, n2);
    }
}
