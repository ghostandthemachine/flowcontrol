/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.scene;

import data.DataNode;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import dataScene.DataScene;
import data.objects.DataFloat;
import data.objects.IUGenDataObject;
import data.objects.Metro;
import data.objects.Print;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import visual.UINodes.FloatDisplay;
import visual.node.VisualNode;
import visual.node.Port;
import visual.node.PortInteractor;
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
import overtoneinterface.IUGen;
import overtoneinterface.IUGenInfo;
import overtoneinterface.IUGenInput;
import overtoneinterface.TestUGen;
import overtoneinterface.TestUGenInput;

/**
 *
 * @author Jon Rose
 */
public class VisualScene extends GraphScene {

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
    public static final String CONNECTION_EDIT_TOOL = "connectionEditTool";
    public static final String USE = "alignMode";
    public static final String EDGE_CONTROL_MODE = "edgeControlMode";
    private NodeCreator dataNodeCreator;
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

    public VisualScene() {
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


//        VisualNode node3 = new VisualNode(this, dataScene, "metro 1000");
//        node3.setPreferredLocation(new Point(100, 100));
//
//        VisualNode node4 = new VisualNode(this, dataScene, "f");
//        node4.setPreferredLocation(new Point(100, 150));
//
//        VisualNode node1 = new VisualNode(this, dataScene, "f");
//        node1.setPreferredLocation(new Point(60, 200));
//
//        VisualNode node2 = new VisualNode(this, dataScene, "f");
//        node2.setPreferredLocation(new Point(140, 200));
//
//        VisualNode node5 = new VisualNode(this, dataScene, "f");
//        node2.setPreferredLocation(new Point(100, 250));
//
//        this.addChild(node1);
//        this.addChild(node2);
//        this.addChild(node3);
//        this.addChild(node4);
//        this.addChild(node5);
//
//
//        this.connect(node4, 0, node3, 0);
//        this.connect(node3, 0, node2, 0);
//        this.connect(node3, 0, node1, 0);
//        this.connect(node1, 0, node5, 0);


        TestUGen[] ugen = new TestUGen[10];
        for (int i = 0; i < 9; i++) {
            ugen[i] = new TestUGen("test", 500, 2, 2);
            if (i != 0) {
                //create a new UGen connection difinition by just adding a new source ugen and port id
                //this example just connects this ugen to the last one if it isn't the first one, then
                //connects to port 0
                ugen[i].addUGenInput(new TestUGenInput(ugen[i - 1], 0));
            }
        }
        setUGens(ugen);
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

    public VisualScene getModelScene() {
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

    public void createNode(VisualNode node) {
        mainLayer.addChild(node);
    }

    public VisualScene getVisualScene() {
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
        VisualNode node = (VisualNode) n;

        if (node.getType().equals(VisualNode.UI)) {
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



        System.out.println(connection.getSourceAnchor().getRelatedWidget().getParentWidget());
        //create the data connection
        Port srcPort = (Port) connection.getSourceAnchor().getRelatedWidget().getParentWidget();
        VisualNode src = srcPort.getParentNode();

        Port tgtPort = (Port) connection.getTargetAnchor().getRelatedWidget().getParentWidget();
        VisualNode tgt = tgtPort.getParentNode();

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

    private void connect(VisualNode source, int sourcePort, VisualNode target, int targetPort) {
        ConnectionWidget connection = new ConnectionWidget(this);
        Widget src = source.getOutputPort(sourcePort);
        Widget tgt = target.getInputPort(targetPort);
        System.out.println(src + "  " + tgt);
        connection.setSourceAnchor(AnchorFactory.createCircularAnchor(src, 2));
        connection.setTargetAnchor(AnchorFactory.createCircularAnchor(tgt, 2));
        addEdge(connection);
        //create data connection
        dataScene.connect(source.getDataNode(), sourcePort, target.getDataNode(), targetPort);
    }

    public void connect(Widget sourceWidget, Widget targetWidget) {
        ConnectionWidget connection = new ConnectionWidget(this);
        connection.setSourceAnchor(AnchorFactory.createCircularAnchor(sourceWidget, 1));
        connection.setTargetAnchor(AnchorFactory.createCircularAnchor(targetWidget, 1));
        addEdge(connection);
    }

    public void setDataScene(NodeCreator d) {
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
            VisualNode node = (VisualNode) nodes[i];
            node.removeHoverActions();
            System.out.println("hover scene off");
        }
    }

    private class MyConnectProvider implements ConnectProvider {

        @Override
        public boolean isSourceWidget(Widget source) {
            return source instanceof IconNodeWidget && source != null ? true : false;
        }

        @Override
        public ConnectorState isTargetWidget(Widget src, Widget trg) {
            return src != trg && trg instanceof IconNodeWidget ? ConnectorState.ACCEPT : ConnectorState.REJECT;
        }

        @Override
        public boolean hasCustomTargetWidgetResolver(Scene arg0) {
            return false;
        }

        @Override
        public Widget resolveTargetWidget(Scene arg0, Point arg1) {
            return null;
        }

        @Override
        public void createConnection(Widget source, Widget target) {
            ConnectionWidget conn = new ConnectionWidget(VisualScene.this);
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

        @Override
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

        @Override
        public void movementFinished(Widget widget) {
            originals.clear();
            original = null;
        }

        @Override
        public Point getOriginalLocation(Widget widget) {
            original = widget.getPreferredLocation();
            return original;
        }

        @Override
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
            VisualScene scene = (VisualScene) widget;

            if (event.getKeyCode() == KeyEvent.VK_N) {      //type 'n' to create a new object
                VisualNode node = new VisualNode(scene.getModelScene(), scene.getDataScene());
                node.setPreferredLocation(new Point((int) scene.getMouseX(), (int) scene.getMouseY()));
                scene.setLastPointCreatedAt(new Point((int) scene.getMouseX(), (int) scene.getMouseY()));
                scene.addNode(node);

            }

            if (event.getKeyCode() == KeyEvent.VK_F) {      //type 'f' to create a new object
                VisualNode node = new FloatDisplay(scene.getModelScene(), scene.getDataScene());
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

        @Override
        public void unsetHovering(Widget widget) {
            if (widget != null) {
                widget.setBackground(scene.getLookFeel().getBackground(ObjectState.createNormal()));
                widget.setForeground(new Color(0, 0, 0, 0));
                PortInteractor port = (PortInteractor) widget;
                port.setOver(false);
            }
        }

        @Override
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

    /**
     *
     * @param ugens - an array of IUGenInfo objects sent from overtone/supercollider
     */
    private void setUGens(IUGen[] ugens) {

      //  VisualNode[] reffNodes = new VisualNode[ugens.length];
        Hashtable newNodes = new Hashtable();
        /*
         * create the nodes before connecting them
         */
        for (int i = 0; i < ugens.length - 1; i++) {
            IUGen ugen = ugens[i];
            VisualNode visualNode = new VisualNode(this, dataScene, ugen);

            int tx = 50;
            int ty = i * 40;
            visualNode.setPreferredLocation(new Point(tx, ty));

          //  reffNodes[i] = visualNode;
            newNodes.put(ugen, visualNode);

            this.createNode(visualNode);
        }

        /*
         * connect the nodes based on the IUGenInfo object
         */
        for (int i = 0; i < ugens.length - 1; i++) {
            VisualNode node = (VisualNode) newNodes.get(ugens[i]);
            IUGenInput[] nodeInputs = ugens[i].getInputs();

            for (int j = 0; j < nodeInputs.length - 1; j++) {
                IUGenInput input = nodeInputs[j];
                VisualNode sourceNode = (VisualNode) newNodes.get(input.getUGen());
                int sourcePortId = input.getPortNumber();
                System.out.println(sourceNode + "   " + sourcePortId + "   " +  node + "  0");

                ///////////
                ////
                int targetPortId = 0;
                ////
                //////////


                this.connect(sourceNode, sourcePortId, node, targetPortId);
            }
        }


    }
}
