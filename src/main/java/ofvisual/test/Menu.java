/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofvisual.test;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Jon
 */
class Menu extends JToolBar {

    public static String EDIT_MODE = "editMode";
    public static String ALIGN_MODE = "alignMode";

    protected void addButtons() {
        JButton button = null;

        //master toggle between edit mode and user mode
        button = makeTextButton("e", EDIT_MODE, "toggle edit mode / user mode", "toggle state");
        add(button);

        button = makeImageButton("AlignCenter24.gif", ALIGN_MODE, "toggle align mode", "align mode");
        add(button);
    }

    protected JButton makeTextButton(String t, String action, String tooltip, String alt) {
        JButton button = new JButton(t);
        //  button.setAction(action);
        button.setToolTipText(tooltip);
        //    button.addActionListener(this);



        return button;
    }

    protected JButton makeImageButton(String imageName, String action, String tooltip, String alt) {
//        String imgLocation = "jlfgr-1_0.jar/"
//                + imageName
//                + ".gif";
        String imgLocation = "/Rewind24.gif";
        URL imageURL = Menu.class.getResource("/home/jon/Desktop/boner.desktop");



        JButton button = new JButton();
        //  button.setAction(action);
        button.setToolTipText(tooltip);
        //    button.addActionListener(this);

        button.setIcon(new ImageIcon(imageURL, alt));

        return button;
    }

    private class MasterToggle implements ActionListener {

        private MasterToggle() {
        }

        public void actionPerformed(ActionEvent ae) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
