/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import dataScene.DataScene;
import visual.node.VisualNode;
import visual.scene.VisualScene;

/**
 *
 * @author Jon
 */
class Scene {
    DataScene scene;
    private final VisualScene modelScene;
  //  DataNodeManager creator = new DataNodeManager(scene);

    public Scene(VisualScene ms) {
        modelScene = ms;
        scene = new DataScene(modelScene);
    }

    public void createNode(String string, VisualNode node) {
   //     creator.createObject(string, node);

    }

    public void getNode(int i) {
        scene.getNode(i);
    }

    public void connect(DataNode n1, DataNode n2) {
        scene.connect(n1, n2);
    }
}
