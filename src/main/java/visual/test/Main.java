/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.test;

import java.awt.Dimension;
import javax.swing.JFrame;
import visual.scene.FlowControl;

/**
 *
 * @author Jon Rose
 */
public class Main {
    //Create the JFrame:

    public static void main(String[] args) {
        JFrame frame = new JFrame("flow editor");
        frame.setPreferredSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new FlowControl());
        frame.pack();
        frame.setVisible(true);
    }

    public Main() {

    }
}
