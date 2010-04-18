/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofvisual.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import ofvisual.scene.ModelScene;

/**
 *
 * @author Jon Rose
 */
public class Main extends JPanel {
    //Create the JFrame:

    public static void main(String[] args) {
        JFrame frame = new JFrame("flow editor");
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Main());
        frame.pack();
        frame.setVisible(true);
    }

    public Main() {
        initComponents();
    }

    private void initComponents() {
        ModelScene scene = new ModelScene();
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
