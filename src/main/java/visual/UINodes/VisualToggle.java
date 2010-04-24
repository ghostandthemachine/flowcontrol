/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.UINodes;

import java.awt.Color;
import java.awt.Graphics2D;
import dataScene.DataScene;
import visual.node.VisualNode;
import visual.scene.VisualScene;

/**
 *
 * @author Jon
 */
public class VisualToggle extends VisualNode {

    private boolean toggle = false;
    private Color toggleColor = Color.black;

    public VisualToggle(VisualScene parentScene, DataScene dataScene) {
               super(parentScene, dataScene);
    }

    @Override
    public void paintWidget() {
        Graphics2D graphics = getGraphics();
        graphics.setColor(toggleColor);
        if (toggle) {
            //set color of the x
            graphics.setColor(toggleColor);
        } else {
            graphics.setColor(new Color(0, 0, 0, 0));
        }

        //paint the x
        graphics.drawLine(0, 0, (int) this.getBounds().getWidth(), (int) this.getBounds().getWidth());
        graphics.drawLine((int) this.getBounds().getWidth(), 0, 0,(int) this.getBounds().getWidth());
    }

    public void setToggle(boolean toggleState) {
        toggle = toggleState;
    }
}
