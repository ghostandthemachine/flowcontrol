/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofvisual.UINodes;

import java.awt.Color;
import java.awt.Graphics2D;
import ofDataScene.DataScene;
import ofvisual.node.ModelNode;
import ofvisual.scene.ModelScene;

/**
 *
 * @author Jon
 */
public class ModelToggle extends ModelNode {

    private boolean toggle = false;
    private Color toggleColor = Color.black;

    public ModelToggle(ModelScene parentScene, DataScene dataScene) {
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
