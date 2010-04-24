/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual.UINodes;

import java.awt.Color;
import java.awt.Graphics2D;
import dataScene.DataScene;
import visual.node.UINode;
import visual.scene.VisualScene;

/**
 *
 * @author Jon
 */
public class NodeMenu extends UINode{


    public NodeMenu(VisualScene ms, DataScene ds){
        super(ms, ds);

        
    }


    @Override
    public void paintWidget() {
        Graphics2D g = getGraphics();
        g.setColor(Color.red);
        g.fillRoundRect(0, 600, 800, 80, 10, 10);
    }
}
