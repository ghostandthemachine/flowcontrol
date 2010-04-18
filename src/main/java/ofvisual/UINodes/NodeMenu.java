/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ofvisual.UINodes;

import java.awt.Color;
import java.awt.Graphics2D;
import ofDataScene.DataScene;
import ofvisual.node.UINode;
import ofvisual.scene.ModelScene;

/**
 *
 * @author Jon
 */
public class NodeMenu extends UINode{


    public NodeMenu(ModelScene ms, DataScene ds){
        super(ms, ds);

        
    }


    @Override
    public void paintWidget() {
        Graphics2D g = getGraphics();
        g.setColor(Color.red);
        g.fillRoundRect(0, 600, 800, 80, 10, 10);
    }
}
