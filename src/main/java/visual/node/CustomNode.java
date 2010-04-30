/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual.node;

import dataScene.DataScene;
import java.awt.Color;
import java.awt.Graphics2D;
import visual.scene.VisualScene;

/**
 *
 * @author Jon
 */
public class CustomNode extends VisualNode{

       public CustomNode(VisualScene scene, DataScene dScene) {
        super(scene);
}

    @Override
    public void paintWidget() {
        super.paintWidget();
        Graphics2D g = this.getGraphics();
        g.setPaint(Color.black);
        g.fillOval(0, 0, this.getBounds().width, this.getBounds().height);
    }





}
