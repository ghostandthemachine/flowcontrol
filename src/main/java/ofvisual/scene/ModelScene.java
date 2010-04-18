/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofvisual.scene;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import ofDataScene.DataScene;
import ofdata.DataNodeManager;
import ofvisual.UINodes.FloatDisplay;
import ofvisual.node.ModelNode;
import ofvisual.node.Port;
import ofvisual.node.PortInteractor;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.ConnectProvider;
import org.netbeans.api.visual.action.ConnectorState;
import org.netbeans.api.visual.action.MoveProvider;
import org.netbeans.api.visual.action.TwoStateHoverProvider;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.anchor.AnchorFactory;
import org.netbeans.api.visual.anchor.AnchorShape;
import org.netbeans.api.visual.anchor.PointShape;
import org.netbeans.api.visual.graph.GraphScene;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.router.RouterFactory;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LayerWidget;
import org.netbeans.api.visual.widget.Scene;
import org.netbeans.api.visual.widget.Widget;
import org.netbeans.api.visual.widget.general.IconNodeWidget;

/**
 *
 * @author Jon Rose
 */
public class ModelScene extends GraphScene {

    public LayerWidget forgroundLayer = new LayerWidget(this);
    public LayerWidget mainLayer = new LayerWidget(this);
    public LayerWidget connectionLayer = new LayerWidget(this);
    public LayerWidget interactionLayer = new LayerWidget(this);
    public LayerWidget backgroundLayer = new LayerWidget(this);
    public static Port currentPort;
    private ArrayList<Widget> inputs = new ArrayList();
    private ArrayList<Widget> outputs = new ArrayList();
    private WidgetAction multiMoveProvider = ActionFactory.createMoveAction(null, new MultiMoveProvider());
    private WidgetAction moveAction = ActionFactory.createAlignWithMoveAction(mainLayer, interactionLayer, null, false);
    private WidgetAction resizeAction = ActionFactory.createAlignWithResizeAction(mainLayer, interactionLayer, null, false);
    private WidgetAction hoverAction = ActionFactory.createHoverAction(new MyHoverProvider(this));
    private UIMouseAction dragAction = new UIMouseAction();
    private SceneMouseAction sceneAction = new SceneMouseAction();
    public static final String EDIT = "editMode";
    public static final String BONER = "boner";
    public static final String CONNECTION_EDIT_TOOL = "connectionEditTool";
    public static final String USE = "alignMode";
    public static final String EDGE_CONTROL_MODE = "edgeControlMode";
    private DataNodeManager dataNodeCreator;
    private DataScene dataScene = new DataScene(this);
    private boolean over = false;
    private double mouseX;
    private double mouseY;
    private double lastX;
    private double lastY;
    private double xVelocity;
    private double yVelocity;
    private Point lastPointCreatedAt = new Point(400, 400);
    private boolean mouseDragged = false;

    public ModelScene() {
        addChild(backgroundLayer);
        addChild(mainLayer);
        addChild(connectionLayer);
        addChild(interactionLayer);
        addChild(forgroundLayer);

        //set the initial tool
        this.setActiveTool(EDIT);

        getActions().addAction(ActionFactory.createZoomAction());
        getActions().addAction(ActionFactory.createPanAction());
        getActions().addAction(hoverAction);
        getActions().addAction(new SceneMouseAction());


        getActions().addAction(ActionFactory.createRectangularSelectAction(this, backgroundLayer));

        getPriorActions().addAction(new KeyEventAction());  //add the scene key event listener


    }

    public boolean isMouseDragged() {
        return mouseDragged;
    }

    public void setMouseDragged(boolean mouseDragged) {
        this.mouseDragged = mouseDragged;
    }

    public double getLastX() {
        return lastX;
    }

    public void setLastX(double lastX) {
        this.lastX = lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public void setLastY(double lastY) {
        this.lastY = lastY;
    }

    public double getMouseX() {
        return mouseX;
    }

    public void setMouseX(double mouseX) {
        this.mouseX = mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void setMouseY(double mouseY) {
        this.mouseY = mouseY;
    }

    public double getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(double xVelocity) {
        this.xVelocity = xVelocity;
    }

    public double getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(double yVelocity) {
        this.yVelocity = yVelocity;
    }

    public Point getLastPointCreatedAt() {
        return lastPointCreatedAt;
    }

    public void setLastPointCreatedAt(Point lastPointCreatedAt) {
        this.lastPointCreatedAt = lastPointCreatedAt;
    }

    public void setDataScene(DataScene ds) {
        dataScene = ds;
    }

    public DataScene getDataScene() {
        return dataScene;
    }

    public ModelScene getModelScene() {
        return this;
    }

    /**
     * Checks whether an object is registered as a node in the graph model.
     * @param object the object; must not be a Widget
     * @return true, if the object is registered as a node
     */
    public LayerWidget getBackgroundLayer() {
        return backgroundLayer;
    }

    public LayerWidget getConnectionLayer() {
        return connectionLayer;
    }

    public LayerWidget getForgroundLayer() {
        return forgroundLayer;
    }

    public LayerWidget getInteractionLayer() {
        return interactionLayer;
    }

    public LayerWidget getMainLayer() {
        return mainLayer;
    }

    public boolean isInput(Object object) {
        return inputs.contains((Widget) object);
    }

    public boolean isOutput(Widget targetWidget) {
        return outputs.contains(targetWidget);
    }

    public void addConnection(ConnectionWidget conn) {
        this.addEdge(conn);
    }

    public void addInputConnection(PortInteractor input) {
        inputs.add(input);
    }

    public void addOutputConnection(PortInteractor output) {
        outputs.add(output);
    }

    private Widget findOutput(Object sourceNode) {
        int id = outputs.indexOf(sourceNode);
        return outputs.get(id);
    }

    private Widget findInput(Object sourceNode) {
        int id = inputs.indexOf(sourceNode);
        return inputs.get(id);
    }

    public WidgetAction getHoverAction() {
        return hoverAction;
    }

    //not working
    public void deleteSelected() {
        Set objects = getSelectedObjects();
    }

    public void createNode(ModelNode node) {
        mainLayer.addChild(node);
    }

    public ModelScene getVisualScene() {
        return this;
    }

    @Override
    public void paintChildren() {
        super.paintChildren();
    }

    @Override
    protected Widget attachNodeWidget(Object n) {
        Widget widget = (Widget) n;
        widget.setToolTipText("flow node");
        ModelNode node = (ModelNode) n;

        //   System.out.println(node.getType() + "  " + widget);
        widget.createActions(BONER).addAction(0, dragAction);

        if (node.getType().equals(ModelNode.UI)) {
//            widget.getActions().addAction();
            System.out.println("ui node action created");
        }
        widget.createActions(USE).addAction(0, dragAction);

        widget.createActions(EDIT).addAction(0, createObjectHoverAction());
        widget.createActions(EDIT).addAction(1, ActionFactory.createMoveAction());
        widget.createActions(EDIT).addAction(2, ActionFactory.createResizeAction());
        // widget.createActions(EDIT).addAction(3, multiMoveProvider);


        mainLayer.addChild(widget);
        return widget;
    }

    @Override
    protected Widget attachEdgeWidget(Object e) {
        //create the visual connection
        ConnectionWidget connection = (ConnectionWidget) e;
        connection.setPaintControlPoints(true);
        connection.setRouter(RouterFactory.createFreeRouter());
        connection.setControlPointShape(PointShape.SQUARE_FILLED_SMALL);

        //      connection.createActions(EDIT).addAction(createSelectAction());          //when this is on it consumes the mouse events and doesn't allow selectedObjects to easily be created over the port intetractor

        //Normal Edit Mode actions
        connection.createActions(EDGE_CONTROL_MODE).addAction(0, ActionFactory.createAddRemoveControlPointAction());
        connection.createActions(EDGE_CONTROL_MODE).addAction(1, ActionFactory.createFreeMoveControlPointAction());
        connection.createActions(EDGE_CONTROL_MODE).addAction(2, ActionFactory.createAddRemoveControlPointAction());
        connection.createActions(EDGE_CONTROL_MODE).addAction(3, ActionFactory.createFreeMoveControlPointAction());
        connection.createActions(EDGE_CONTROL_MODE).addAction(4, createSelectAction());
        connection.createActions(EDGE_CONTROL_MODE).addAction(5, createObjectHoverAction());
        connectionLayer.addChild(connection);

        //create the data connection
        ModelNode src = (ModelNode) connection.getSourceAnchor().getRelatedWidget().getParentWidget().getParentWidget().getParentWidget();
        Port srcPort = (Port) connection.getSourceAnchor().getRelatedWidget().getParentWidget();
        ModelNode tgt = (ModelNode) connection.getTargetAnchor().getRelatedWidget().getParentWidget().getParentWidget().getParentWidget();
        Port tgtPort = (Port) connection.getTargetAnchor().getRelatedWidget().getParentWidget();
        dataScene.connect(src.getDataNode(), srcPort.getID(), tgt.getDataNode(), tgtPort.getID());

        return connection;
    }

    @Override
    protected void attachEdgeSourceAnchor(Object edge, Object sourceNode, Object n1) {
        Widget w = sourceNode != null ? findOutput(sourceNode) : null;
        ((ConnectionWidget) findWidget(edge)).setSourceAnchor(AnchorFactory.createRectangularAnchor(w));
    }

    @Override
    protected void attachEdgeTargetAnchor(Object edge, Object n, Object targetNode) {
        Widget w = targetNode != null ? findInput(targetNode) : null;
        ((ConnectionWidget) findWidget(edge)).setTargetAnchor(AnchorFactory.createRectangularAnchor(w));
    }

    private void connect(ModelNode first, int i, ModelNode second, int i0) {
        ConnectionWidget connection = new ConnectionWidget(this);
        Widget src = first.getOutputPort(i);
        Widget tgt = second.getInputPort(i0);
        connection.setSourceAnchor(AnchorFactory.createCircularAnchor(src, 2));
        connection.setTargetAnchor(AnchorFactory.createCircularAnchor(tgt, 2));
        addEdge(connection);
    }

    public void connect(Widget sourceWidget, Widget targetWidget) {
        ConnectionWidget connection = new ConnectionWidget(this);
        connection.setSourceAnchor(AnchorFactory.createCircularAnchor(sourceWidget, 1));
        connection.setTargetAnchor(AnchorFactory.createCircularAnchor(targetWidget, 1));
        addEdge(connection);
    }

    public void setDataScene(DataNodeManager d) {
        dataNodeCreator = d;
    }

    public void setEditMode() {
        this.setActiveTool(EDIT);
    }

    public void setAlignMode() {
        if (this.getActiveTool().equals(EDIT)) {
            setActiveTool(USE);
            removeHoverActions();
        } else {
            setActiveTool(EDIT);
            getActions().addAction(hoverAction);
        }
        printActiveToolMode();
    }

    private void printActiveToolMode() {
        System.out.println(this.getActiveTool());
        System.out.println(this.getActions());
    }

    private void routeSelectedEdges() {
        Object[] selectedObjects = this.getSelectedObjects().toArray();
        ConnectionWidget conn = new ConnectionWidget(this.getModelScene());
        for (int i = 0; i < selectedObjects.length; i++) {
            if (selectedObjects[i].getClass() == conn.getClass()) {
                ConnectionWidget connection = (ConnectionWidget) selectedObjects[i];
                connection.setRouter(RouterFactory.createOrthogonalSearchRouter(mainLayer));
            }
        }
    }

    private void unrouteSelectedEdges() {
        Object[] selectedObjects = this.getSelectedObjects().toArray();
        ConnectionWidget conn = new ConnectionWidget(this.getModelScene());
        for (int i = 0; i < selectedObjects.length; i++) {
            if (selectedObjects[i].getClass() == conn.getClass()) {
                ConnectionWidget connection = (ConnectionWidget) selectedObjects[i];
                connection.setRouter(RouterFactory.createDirectRouter());
            }
        }
    }

    public void mouseClicked(Widget widget, WidgetMouseEvent wme) {
        this.setLastX(this.getMouseX());
        this.setLastY(this.getMouseY());
        this.setMouseX(wme.getPoint().getX());
        this.setMouseY(wme.getPoint().getY());
    }

    public void mousePressed(Widget widget, WidgetMouseEvent wme) {
        this.setMouseDragged(true);
        this.setLastX(this.getMouseX());
        this.setLastY(this.getMouseY());
        this.setMouseX(wme.getPoint().getX());
        this.setMouseY(wme.getPoint().getY());
    }

    public void mouseReleased(Widget widget, WidgetMouseEvent wme) {
        this.setMouseDragged(false);
    }

    public void mouseEntered(Widget widget, WidgetMouseEvent wme) {
        this.setLastX(this.getMouseX());
        this.setLastY(this.getMouseY());
        this.setMouseX(wme.getPoint().getX());
        this.setMouseY(wme.getPoint().getY());
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
    }

    void mouseMoved(Widget widget, WidgetMouseEvent wme) {
        this.setLastX(this.getMouseX());
        this.setLastY(this.getMouseY());
        this.setMouseX(wme.getPoint().getX());
        this.setMouseY(wme.getPoint().getY());
        this.setxVelocity(this.getLastX() - this.getMouseX());
        this.setyVelocity(this.getMouseY() - this.getLastY());
    }

    private void removeHoverActions() {
        this.getActions().removeAction(this.getHoverAction());
        Object[] nodes = this.getNodes().toArray();
        for (int i = 0; i < this.getNodes().size() - 1; i++) {
            ModelNode node = (ModelNode) nodes[i];
            node.removeHoverActions();
            System.out.println("hover scene off");
        }
    }

    private class MyConnectProvider implements ConnectProvider {

        public boolean isSourceWidget(Widget source) {
            return source instanceof IconNodeWidget && source != null ? true : false;
        }

        public ConnectorState isTargetWidget(Widget src, Widget trg) {
            return src != trg && trg instanceof IconNodeWidget ? ConnectorState.ACCEPT : ConnectorState.REJECT;
        }

        public boolean hasCustomTargetWidgetResolver(Scene arg0) {
            return false;
        }

        public Widget resolveTargetWidget(Scene arg0, Point arg1) {
            return null;
        }

        public void createConnection(Widget source, Widget target) {
            ConnectionWidget conn = new ConnectionWidget(ModelScene.this);
            conn.setTargetAnchorShape(AnchorShape.TRIANGLE_FILLED);
            conn.setTargetAnchor(AnchorFactory.createRectangularAnchor(target));
            conn.setSourceAnchor(AnchorFactory.createRectangularAnchor(source));
            conn.getActions().addAction(createObjectHoverAction());
            conn.getActions().addAction(createSelectAction());
            connectionLayer.addChild(conn);
        }
    }

//multi object moving
    private class MultiMoveProvider implements MoveProvider {

        private HashMap<Widget, Point> originals = new HashMap<Widget, Point>();
        private Point original;

        public void movementStarted(Widget widget) {
            Object object = findObject(widget);
            if (isNode(object)) {
                for (Object o : getSelectedObjects()) {
                    if (isNode(o)) {
                        Widget w = findWidget(o);
                        if (w != null) {
                            originals.put(w, w.getPreferredLocation());
                        }
                    }
                }
            } else {
                originals.put(widget, widget.getPreferredLocation());
            }
        }

        public void movementFinished(Widget widget) {
            originals.clear();
            original = null;
        }

        public Point getOriginalLocation(Widget widget) {
            original = widget.getPreferredLocation();
            return original;
        }

        public void setNewLocation(Widget widget, Point location) {
            int dx = location.x - original.x;
            int dy = location.y - original.y;
            for (Map.Entry<Widget, Point> entry : originals.entrySet()) {
                Point point = entry.getValue();
                entry.getKey().setPreferredLocation(new Point(point.x + dx, point.y + dy));
            }
        }
    }

    private static final class KeyEventAction extends WidgetAction.Adapter {

        @Override
        public State keyPressed(Widget widget, WidgetKeyEvent event) {
            ModelScene scene = (ModelScene) widget;

            if (event.getKeyCode() == KeyEvent.VK_N) {      //type 'n' to create a new object
                ModelNode node = new ModelNode(scene.getModelScene(), scene.getDataScene());
                node.setPreferredLocation(new Point((int) scene.getMouseX(), (int) scene.getMouseY()));
                scene.setLastPointCreatedAt(new Point((int) scene.getMouseX(), (int) scene.getMouseY()));
                scene.addNode(node);

            }

            if (event.getKeyCode() == KeyEvent.VK_F) {      //type 'f' to create a new object
                ModelNode node = new FloatDisplay(scene.getModelScene(), scene.getDataScene());
                node.setPreferredLocation(new Point((int) scene.getMouseX(), (int) scene.getMouseY()));
                scene.setLastPointCreatedAt(new Point((int) scene.getMouseX(), (int) scene.getMouseY()));
                scene.addNode(node);
            }

            if (event.isAltDown() && event.getKeyCode() == KeyEvent.VK_A) {    //alt + 'a' for align mode toggle
                scene.setAlignMode();
                scene.removeHoverActions();
            }

            if (event.isAltDown() && event.getKeyCode() == KeyEvent.VK_E) {    //alt + 'e' for edit/use mode toggle
                scene.setEditMode();
            }

            if (event.isAltDown() && event.getKeyCode() == KeyEvent.VK_R) {    //alt + 'r' route selected selectedObjects
                scene.routeSelectedEdges();
            }

            if (event.isAltDown() && event.isShiftDown() && event.getKeyCode() == KeyEvent.VK_R) {
                scene.unrouteSelectedEdges();
            }


            if (event.getKeyCode() == KeyEvent.VK_E) {
                widget.getScene().setActiveTool(EDGE_CONTROL_MODE);

            } else if (event.getKeyCode() == KeyEvent.VK_DELETE) {
                //need to add object and edge deletion
            }

            return State.REJECTED;
        }

        @Override
        public State keyReleased(Widget widget, WidgetKeyEvent event) {
            if (widget.getScene().getActiveTool().equals(EDGE_CONTROL_MODE)) {
                widget.getScene().setActiveTool(EDIT);
            }
            //      System.out.println(widget.getScene().getActiveTool());
            return State.REJECTED;
        }
    }

    private static class MyHoverProvider implements TwoStateHoverProvider {

        private Scene scene;

        public MyHoverProvider(Scene scene) {
            this.scene = scene;
        }

        public void unsetHovering(Widget widget) {
            if (widget != null) {
                widget.setBackground(scene.getLookFeel().getBackground(ObjectState.createNormal()));
                widget.setForeground(new Color(0, 0, 0, 0));
                PortInteractor port = (PortInteractor) widget;
                port.setOver(false);
            }
        }

        public void setHovering(Widget widget) {
            if (widget != null) {
                System.out.println(widget + "   is done with the hover shit");

                ObjectState state = ObjectState.createNormal().deriveSelected(true);
                widget.setBackground(scene.getLookFeel().getBackground(state));
                widget.setForeground(Color.BLUE);
                PortInteractor port = (PortInteractor) widget;
                port.setOver(true);
            }
        }
    }
}
