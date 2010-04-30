/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.node;

import org.netbeans.api.visual.action.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import visual.node.PortGroup.PortType;
import visual.scene.VisualScene;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon
 */
public class PortInteractor extends Widget {

    VisualScene parentScene;
    private int size = 0;
    private Border lineBorder = BorderFactory.createLineBorder(1, Color.BLACK);
    private WidgetAction hoverAction = ActionFactory.createHoverAction(new MyHoverProvider());
    private WidgetAction moveAction = ActionFactory.createMoveAction();
    private WidgetAction zoomAction = ActionFactory.createZoomAction();
    private WidgetAction panAction = ActionFactory.createPanAction();
    private WidgetAction connectAction;
    private PortType portType;
    private boolean over = false;
    private Color highlightColor;
    private VisualNode node;
    private int portId;

    public PortInteractor(VisualScene p, int s, PortType type, VisualNode node, int portId) {
        super(p);
        this.node = node;
        this.portId = portId;
        portType = type;
        size = s;
        parentScene = p;
        connectAction = ActionFactory.createConnectAction(parentScene.connectionLayer, new SceneConnectProvider());

        getActions().addAction(connectAction);
        getActions().addAction(parentScene.createObjectHoverAction());
        if (portType == PortType.INPUT) {
            parentScene.addInputConnection(this);
            highlightColor = Color.orange;
        } else {
            parentScene.addOutputConnection(this);
            highlightColor = Color.GREEN;
        }

        getActions().addAction(parentScene.getHoverAction());
        this.setForeground(new Color(0, 0, 0, 0));
    }

    @Override
    protected Rectangle calculateClientArea() {
        return new Rectangle(-size, -size, 2 * size + 1, 2 * size + 1);
    }

    @Override
    protected void paintWidget() {
        Graphics2D g = getGraphics();

        //draw line
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(2f));
        g.drawLine(-size + (size / 3), 0, size - (size / 3), 0);

        if (over) {
            g.setColor(highlightColor);
        } else {
            g.setColor(new Color(0, 0, 0, 0));
        }

        g.drawOval(-size, -size, 2 * size, 2 * size);
        g.setStroke(new BasicStroke(1f));
        this.revalidate();

    }

    public void setOver(boolean b) {
        over = b;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public VisualNode getNode() {
        return node;
    }

    public int getPortNumber() {
        return portId;
    }

    private PortType getPortType() {
        return portType;
    }

    void removeHoverActions() {
        this.getActions().removeAction(hoverAction);
        System.out.println("hover off interactor");
    }

    private static class MyHoverProvider implements TwoStateHoverProvider {

        @Override
        public void unsetHovering(Widget widget) {
            widget.setForeground(new Color(0, 0, 0, 0));
        }

        @Override
        public void setHovering(Widget widget) {
            widget.setForeground(new Color(200, 255, 200));
        }
    }

    private class SceneConnectProvider implements ConnectProvider {

        @Override
        public boolean isSourceWidget(Widget sourceWidget) {
            boolean bool = parentScene.isOutput(sourceWidget);
            return bool;
        }

        @Override
        public ConnectorState isTargetWidget(Widget sourceWidget, Widget targetWidget) {
            //if tgt is an input, and is not from the same node then resolve
            if (parentScene.isInput(targetWidget) && (sourceWidget.getParentWidget().getParentWidget().getParentWidget() != targetWidget.getParentWidget().getParentWidget().getParentWidget())) {
                return ConnectorState.ACCEPT;
            }
            return ConnectorState.REJECT_AND_STOP;
        }

        @Override
        public boolean hasCustomTargetWidgetResolver(Scene scene) {
            return false;
        }

        @Override
        public Widget resolveTargetWidget(Scene scene, Point sceneLocation) {
            return null;
        }

        @Override
        public void createConnection(Widget sourceWidget, Widget targetWidget) {
            parentScene.connect((PortInteractor) sourceWidget, (PortInteractor) targetWidget);
        }
    }
}
