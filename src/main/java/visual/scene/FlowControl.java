/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.scene;

import GroupNode.GroupNode;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Collection;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import overtoneinterface.IUGenInfo;
import overtoneinterface.TestUGenInfo;

/**
 *
 * @author Jon
 */
public class FlowControl extends JPanel {

    Collection<IUGenInfo> info = new ArrayList<IUGenInfo>();
    VisualScene scene = new VisualScene();

    public FlowControl() {
        initComponents();
    }

    public FlowControl(Collection<IUGenInfo> info) {
        this.info = info;
        initComponents();
    }

    private void initComponents() {
        //Set the layout:
        setLayout(new BorderLayout());
        //Create a JScrollPane:
        JScrollPane scrollPane = new JScrollPane();
        //Add the JScrollPane to the JPanel:
        add(scrollPane, BorderLayout.CENTER);
        //  add(new ToolBar(scene, this), BorderLayout.SOUTH);

        scrollPane.setViewportView(scene.createView());
        add(scene.createSatelliteView(), BorderLayout.WEST);
    }

    public void loadIUGenLibrary(Collection<IUGenInfo> collection) {
        scene.addIUGenInfo(collection);
    }
}
