/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.node;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import visual.scene.VisualScene;
import org.netbeans.api.visual.widget.ComponentWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon Rose
 */
class NodeMenu extends Widget {

    VisualNode parent;
    public NodeMenu(VisualScene scene, VisualNode p) {
        super(scene);
        parent = p;

        JButton button = new JButton("");
        button.setPreferredSize(new Dimension(10,10));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {;
                System.out.println("ownvworgw");
            }
        });

        ComponentWidget comp = new ComponentWidget(scene, button);
        comp.setPreferredLocation(new Point(-15,-5));
        parent.addChild(comp);

    }
}
