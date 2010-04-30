/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.scene;

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

    public FlowControl() {
        //
        //create test ugens
        TestUGenInfo ugen0 = new TestUGenInfo("test0", 500, 2, 1);
        TestUGenInfo ugen1 = new TestUGenInfo("shitty", 500, 3, 1);
        TestUGenInfo ugen2 = new TestUGenInfo("addition", 500, 8, 2);
        TestUGenInfo ugen3 = new TestUGenInfo("add", 500, 2, 4);
        info.add(ugen0);
        info.add(ugen1);
        info.add(ugen2);
        info.add(ugen3);
        initComponents();
    }

    public FlowControl(Collection<IUGenInfo> info) {
        this.info = info;
        initComponents();
    }

    private void initComponents() {
        VisualScene scene = new VisualScene();
        scene.addIUGenInfo(info);
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
}
