/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ofvisual.scene;

import ofvisual.node.ModelNode;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon
 */
  public class UIMouseAction implements WidgetAction {

        public State mouseClicked(Widget widget, WidgetMouseEvent wme) {
            ModelNode node = (ModelNode) widget;
            node.mouseClicked(widget, wme);
            return State.CONSUMED;
        }

        public State mousePressed(Widget widget, WidgetMouseEvent wme) {
            ModelNode node = (ModelNode) widget;
            node.mousePressed(widget, wme);
            return State.CONSUMED;
        }

        public State mouseReleased(Widget widget, WidgetMouseEvent wme) {
            ModelNode node = (ModelNode) widget;
            node.mouseReleased(widget, wme);
            return State.CONSUMED;
        }

        public State mouseEntered(Widget widget, WidgetMouseEvent wme) {
            ModelNode node = (ModelNode) widget;
            node.mouseEntered(widget, wme);
            return State.CONSUMED;
        }

        public State mouseExited(Widget widget, WidgetMouseEvent wme) {
            ModelNode node = (ModelNode) widget;
            node.mouseExited(widget, wme);
            return State.CONSUMED;
        }

        public State mouseDragged(Widget widget, WidgetMouseEvent wme) {
            System.out.println("dragging");
            ModelNode node = (ModelNode) widget;
            node.mouseDragged(widget, wme);
            return State.CONSUMED;
        }

        public State mouseMoved(Widget widget, WidgetMouseEvent wme) {
            ModelNode node = (ModelNode) widget;
            node.mouseMoved(widget, wme);
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
            ModelNode node = (ModelNode) widget;
            node.dragEnter(widget, wdtde);
            return State.CONSUMED;
        }

        public State dragOver(Widget widget, WidgetDropTargetDragEvent wdtde) {
            ModelNode node = (ModelNode) widget;
            node.dragOver(widget, wdtde);
            return State.CONSUMED;
        }

        public State dropActionChanged(Widget widget, WidgetDropTargetDragEvent wdtde) {
            return State.CONSUMED;
        }

        public State dragExit(Widget widget, WidgetDropTargetEvent wdte) {
            ModelNode node = (ModelNode) widget;
            node.dragExit(widget, wdte);
            return State.CONSUMED;
        }

        public State drop(Widget widget, WidgetDropTargetDropEvent wdtde) {
            return State.CONSUMED;
        }
    }