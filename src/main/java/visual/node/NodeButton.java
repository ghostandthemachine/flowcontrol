/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual.node;

import visual.scene.VisualScene;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.modules.visual.action.EditAction;

/**
 *
 * @author Jon
 */
public class NodeButton extends Widget{
    private int size;
    public NodeButton(VisualScene scene, int s) {
        super(scene);
        size = s;

        this.getActions().addAction(ActionFactory.createAcceptAction(null));
    }

}
