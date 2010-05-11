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

    IUGenInfo[] info;
    VisualScene scene = new VisualScene();

    public FlowControl() {
        //
        //create test ugens
        TestUGenInfo ugen0 = new TestUGenInfo("test_this", 500, 2, 1);
        TestUGenInfo ugen1 = new TestUGenInfo("test@", 500, 3, 1);
        TestUGenInfo ugen2 = new TestUGenInfo("-", 500, 8, 2);
        TestUGenInfo ugen3 = new TestUGenInfo("+", 500, 2, 4);
        info = new IUGenInfo[4];
        info[0] = (ugen0);
        info[1] = (ugen1);
        info[2] = (ugen2);
        info[3] = (ugen3);


        initComponents();

    }

    public FlowControl(IUGenInfo[] info) {
        this.info = info;
        initComponents();
    }

    private void initComponents() {
        loadIUGenLibrary(info);
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

    public void loadIUGenLibrary(IUGenInfo[] collection) {
        ArrayList<IUGenInfo> array = new ArrayList<IUGenInfo>();
        for(int i = 0; i < collection.length; i++){
            array.add(collection[i]);
        }
        scene.addIUGenInfo(array);
    }
}
