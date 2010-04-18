/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofvisual.node;

import ofDataScene.DataScene;
import ofvisual.scene.ModelScene;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetDragEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetDropEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetFocusEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetKeyEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseWheelEvent;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon
 */
public class UINode extends ModelNode {

    public UINode(ModelScene scene, DataScene ds) {
        super(scene, ds);
        this.setType(UI);
    }


    @Override
    public UINode getUINode() {
        return this;
    }

    public void mouseClicked(Widget widget, WidgetMouseEvent wme) {
        this.setLastX(this.getMouseX());
        this.setLastY(this.getMouseY());
        this.setMouseX(wme.getPoint().getX());
        this.setMouseY(wme.getPoint().getY());
        updateUI();
    }

    public void mousePressed(Widget widget, WidgetMouseEvent wme) {
        this.setMouseDragged(true);
        this.setLastX(this.getMouseX());
        this.setLastY(this.getMouseY());
        this.setMouseX(wme.getPoint().getX());
        this.setMouseY(wme.getPoint().getY());
        updateUI();
    }

    public void mouseReleased(Widget widget, WidgetMouseEvent wme) {
        this.setMouseDragged(false);
    }

    public void mouseEntered(Widget widget, WidgetMouseEvent wme) {
        this.setLastX(this.getMouseX());
        this.setLastY(this.getMouseY());
        this.setMouseX(wme.getPoint().getX());
        this.setMouseY(wme.getPoint().getY());
        updateUI();
    }

    public void mouseExited(Widget widget, WidgetMouseEvent wme) {
    }

    public void mouseDragged(Widget widget, WidgetMouseEvent wme) {
        this.setLastX(this.getMouseX());
        this.setLastY(this.getMouseY());
        this.setMouseX(wme.getPoint().getX());
        this.setMouseY(wme.getPoint().getY());
        this.setxVelocity(this.getLastX() - this.getMouseX());
        this.setyVelocity(this.getMouseY() - this.getLastY());
        updateUI();
    }

    public void mouseMoved(Widget widget, WidgetMouseEvent wme) {
        updateUI();
    }

    public void mouseWheelMoved(Widget widget, WidgetMouseWheelEvent wmwe) {
    }

    public void keyTyped(Widget widget, WidgetKeyEvent wke) {
    }

    public void keyPressed(Widget widget, WidgetKeyEvent wke) {
    }

    public void keyReleased(Widget widget, WidgetKeyEvent wke) {
    }

    public void focusGained(Widget widget, WidgetFocusEvent wfe) {
    }

    public void focusLost(Widget widget, WidgetFocusEvent wfe) {
    }

    public void dragEnter(Widget widget, WidgetDropTargetDragEvent wdtde) {
    }

    public void dragOver(Widget widget, WidgetDropTargetDragEvent wdtde) {
    }

    public void dropActionChanged(Widget widget, WidgetDropTargetDragEvent wdtde) {
    }

    public void dragExit(Widget widget, WidgetDropTargetEvent wdte) {
    }

    public void drop(Widget widget, WidgetDropTargetDropEvent wdtde) {
    }

    public void updateUI() {
        //mouse event driven methods go here
        System.out.println("update");

    }
}
