/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.scene;

import visual.node.VisualNode;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon
 */
public class SceneMouseAction implements WidgetAction {

    public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
        VisualScene scene = (VisualScene) widget;
        scene.mouseClicked(widget, wme);
        return State.CONSUMED;
    }

    public State mousePressed(Widget widget, WidgetMouseEvent wme) {
        VisualScene scene = (VisualScene) widget;
        scene.mousePressed(widget, wme);
        return State.REJECTED;
    }

    public State mouseReleased(Widget widget, WidgetMouseEvent wme) {
        VisualScene scene = (VisualScene) widget;
        scene.mouseReleased(widget, wme);
        return State.REJECTED;
    }

    public State mouseEntered(Widget widget, WidgetMouseEvent wme) {
        VisualScene scene = (VisualScene) widget;
        scene.mouseEntered(widget, wme);
        return State.CONSUMED;
    }

    public State mouseExited(Widget widget, WidgetMouseEvent wme) {
        VisualScene scene = (VisualScene) widget;
        scene.mouseExited(widget, wme);
        return State.CONSUMED;
    }

    public State mouseDragged(Widget widget, WidgetMouseEvent wme) {
        VisualScene scene = (VisualScene) widget;
        scene.mouseDragged(widget, wme);
        return State.CONSUMED;
    }

    public State mouseMoved(Widget widget, WidgetMouseEvent wme) {
        VisualScene scene = (VisualScene) widget;
        scene.mouseMoved(widget, wme);
        return State.CONSUMED;
    }

    public State mouseWheelMoved(Widget widget, WidgetMouseWheelEvent wmwe) {
        return State.CONSUMED;
    }

    public State keyTyped(Widget widget, WidgetKeyEvent wke) {
        return State.CONSUMED;
    }

    public State keyPressed(Widget widget, WidgetKeyEvent wke) {
        return State.CONSUMED;
    }

    public State keyReleased(Widget widget, WidgetKeyEvent wke) {
        return State.CONSUMED;
    }

    public State focusGained(Widget widget, WidgetFocusEvent wfe) {
        return State.CONSUMED;
    }

    public State focusLost(Widget widget, WidgetFocusEvent wfe) {
        return State.CONSUMED;
    }

    public State dragEnter(Widget widget, WidgetDropTargetDragEvent wdtde) {
        VisualNode node = (VisualNode) widget;
        node.getUINode().dragEnter(widget, wdtde);
        return State.CONSUMED;
    }

    public State dragOver(Widget widget, WidgetDropTargetDragEvent wdtde) {
        VisualNode node = (VisualNode) widget;
        node.getUINode().dragOver(widget, wdtde);
        return State.CONSUMED;
    }

    public State dropActionChanged(Widget widget, WidgetDropTargetDragEvent wdtde) {
        return State.CONSUMED;
    }

    public State dragExit(Widget widget, WidgetDropTargetEvent wdte) {
        VisualNode node = (VisualNode) widget;
        node.getUINode().dragExit(widget, wdte);
        return State.CONSUMED;
    }

    public State drop(Widget widget, WidgetDropTargetDropEvent wdtde) {
        return State.CONSUMED;
    }
}
