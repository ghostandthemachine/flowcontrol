/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.UINodes;

import dataScene.DataScene;
import java.awt.Color;
import visual.node.VisualNode;
import visual.scene.VisualScene;

/**
 *
 * @author Jon
 */
public class DeadNode extends VisualNode {

    public DeadNode(VisualScene s, DataScene ds, String name) {
        super(s, ds, name);
        
        this.setBackground(Color.gray);
    }
}
