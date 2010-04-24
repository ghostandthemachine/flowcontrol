/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.test;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.net.URL;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import visual.node.VisualNode;
import visual.scene.VisualScene;

/**
 *
 * @author jon
 */
class ToolBar extends JToolBar {

    public static String EDIT_MODE = "editMode";
    public static String ALIGN_MODE = "alignMode";
    private VisualScene modelScene;
    private JPanel parentPanel;

    public ToolBar(VisualScene s, JPanel p) {
        modelScene = s;
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
                modelScene.setEditMode();
            }
        });
        add(button);

        button = makeImageButton("Stop16", ALIGN_MODE, "toggle align mode", "align mode");
        button.setToolTipText("toggle master state to use edit mode");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                modelScene.setAlignMode();
            }
        });
        add(button);

        button = makeImageButton("BeanAdd16", ALIGN_MODE, "toggle align mode", "align mode");
        button.setToolTipText("add a new node");
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                VisualNode node = new VisualNode(modelScene.getModelScene(), modelScene.getDataScene());
                node.setPreferredLocation(new Point(400,400));
                modelScene.addNode(node);
                System.out.println(modelScene.getFocusedWidget());
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
                parentPanel.remove(modelScene.createSatelliteView());
                parentPanel.grabFocus();
            }
        });
        add(button);

    }

    protected JButton makeImageButton(String imageName, String action, String tooltip, String alt) {
        String imgLocation = "images/"
                + imageName
                + ".gif";
        URL imageURL = ToolBar.class.getResource(imgLocation);
        System.out.println(imageURL);



        JButton button = new JButton();
        //  button.setAction(action);
        button.setToolTipText(tooltip);
        //    button.addActionListener(this);

        button.setIcon(new ImageIcon(imageURL, alt));

        return button;
    }
}
