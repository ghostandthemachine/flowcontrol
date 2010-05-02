/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compositeNodes;

import dataScene.DataScene;
import visual.node.VisualNode;
import visual.scene.VisualScene;

/**
 *
 * @author Jon
 */
public class Add implements OvertoneNode{

    @Override
    public String getName() {
        return this.getName();
    }

    @Override
    public int numOutputs() {
        return 0;
    }

    @Override
    public int numInputs() {
        return 0;
    }

    @Override
    public int getNumConnections() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
