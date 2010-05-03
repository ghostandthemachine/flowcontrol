/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.test;

import GroupNode.BorderPortTestNode;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import org.openide.util.ImageUtilities;
import visual.scene.VisualScene;

/**
 *
 * @author jon
 */
public class ToolBar extends JToolBar {

    public static String EDIT_MODE = "editMode";
    public static String ALIGN_MODE = "alignMode";
    private VisualScene visualScene;
    private JPanel parentPanel;

    public ToolBar(VisualScene s, JPanel p) {
        visualScene = s;
        parentPanel = p;
        addButtons();
        this.setMargin(new Insets(3, 3, -3, 0));
    }

    protected void addButtons() {
        JButton button = null;

        button = makeImageButton("Play16", ALIGN_MODE, "toggle align mode", "align mode");
        button.setToolTipText("toggle master state to user mode");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                visualScene.setEditMode();
            }
        });
        add(button);

        button = makeImageButton("Stop16", ALIGN_MODE, "toggle align mode", "align mode");
        button.setToolTipText("toggle master state to use edit mode");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                visualScene.setAlignMode();
            }
        });
        add(button);

        button = makeImageButton("BeanAdd16", ALIGN_MODE, "toggle align mode", "align mode");
        button.setToolTipText("add a new node");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                BorderPortTestNode node = new BorderPortTestNode((VisualScene) visualScene.getScene());
                node.setPreferredLocation(new Point(400, 400));
                visualScene.addNode(node);
                System.out.println((VisualScene) visualScene.getFocusedWidget());
                parentPanel.grabFocus();
            }
        });
        add(button);


        button = makeImageButton("AlignCenter16", ALIGN_MODE, "toggle align mode", "align mode");
        button.setToolTipText("toggle aligment mode");
        add(button);


        button = makeImageButton("Help16", ALIGN_MODE, "toggle align mode", "align mode");
        button.setToolTipText("add a new node");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                parentPanel.remove(visualScene.createSatelliteView());
                parentPanel.grabFocus();
            }
        });
        add(button);

    }

    protected JButton makeImageButton(String imageName, String action, String tooltip, String alt) {

        String imgLocation = "images/"
                + imageName
                + ".gif";
        System.out.println(imgLocation);

      //  URL imageURL = ToolBar.class.getResource(imgLocation);



        JButton button = new JButton();
        //  button.setAction(action);
        button.setToolTipText(tooltip);
        //    button.addActionListener(this);

        button.setIcon(new ImageIcon(ImageUtilities.loadImage(imgLocation)));

        return button;
    }
}
