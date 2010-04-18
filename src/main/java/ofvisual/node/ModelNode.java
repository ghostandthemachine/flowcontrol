/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofvisual.node;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import ofAtom.AtomFloat;
import ofDataScene.DataScene;
import ofdata.DataNode;
import ofdata.DataNodeManager;
import ofvisual.node.PortGroup.PortType;
import ofvisual.scene.ModelScene;
import org.netbeans.api.visual.action.ActionFactory;
import org.netbeans.api.visual.action.TextFieldInplaceEditor;
import org.netbeans.api.visual.action.WidgetAction;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetDragEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetDropEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetDropTargetEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetFocusEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetKeyEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseEvent;
import org.netbeans.api.visual.action.WidgetAction.WidgetMouseWheelEvent;
import org.netbeans.api.visual.border.Border;
import org.netbeans.api.visual.border.BorderFactory;
import org.netbeans.api.visual.laf.LookFeel;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon
 */
public class ModelNode extends Widget {

    private WidgetAction editorAction;
    private LabelWidget labelWidget;
    LookFeel lookFeel = getScene().getLookFeel();
    PortGroup outputPorts;
    PortGroup inputPorts;
    private int numInputs = 0;
    private int numOutputs = 0;
    private int portWidth;
    private int borderRadius = 10;
    private Rectangle area;
    private ModelScene parentScene;
    NodeMenu menu;
    DataScene dataScene;
    DataNode dataNode;
    DataNodeManager dataNodeCreator;
    private HashSet connections = new HashSet();
    private float value = 0;
    private double mouseX;
    private double mouseY;
    private double lastX;
    private double lastY;
    private double xVelocity;
    private double yVelocity;
    public static String UI = "ui";
    public static String DATA = "data";
    private String type;
    private boolean mouseDragged = false;

    public ModelNode(ModelScene scene, DataScene dScene) {
        super(scene);
        parentScene = scene;
        dataScene = dScene;
        type = ModelNode.DATA;
        dataNodeCreator = new DataNodeManager(dScene, this, parentScene);
        dataNode = new DataNode("     ", this, dataScene, 1, 1);
        editorAction = ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor(this));

        Border border = BorderFactory.createRoundedBorder(borderRadius, borderRadius, 5, 2, Color.white, Color.black);

        labelWidget = new LabelWidget(scene);
        labelWidget.setFont(scene.getDefaultFont().deriveFont(13.0f));
        labelWidget.getActions().addAction(editorAction);
        labelWidget.setOpaque(true);
        labelWidget.setLabel("            ");
        labelWidget.setBorder(border);
        // labelWidget.setBorder(BorderFactory.createResizeBorder(5));
        // labelWidget.setBorder(BorderFactory.createResizeBorder(8, Color.BLACK, true));
        addChild(labelWidget);

        inputPorts = new PortGroup(scene, this, 1, PortType.INPUT);
        inputPorts.setPreferredLocation(new Point(0, -22));
        addChild(inputPorts);

        outputPorts = new PortGroup(scene, this, 1, PortType.OUTPUT);
        outputPorts.setPreferredLocation(new Point(0, 0));
        addChild(outputPorts);

        scene.addNode(this);

    }

    public ModelNode(ModelScene scene, DataScene dScene, String t) {
        super(scene);
        parentScene = scene;
        dataScene = dScene;
        type = UI;
        dataNodeCreator = new DataNodeManager(dScene, this, parentScene);
        dataNode = new DataNode("     ", this, dataScene, 1, 1);
        editorAction = ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor(this));

        Border border = BorderFactory.createRoundedBorder(borderRadius, borderRadius, 5, 2, Color.white, Color.black);

        labelWidget = new LabelWidget(scene);
        labelWidget.setFont(scene.getDefaultFont().deriveFont(13.0f));
        labelWidget.getActions().addAction(editorAction);
        labelWidget.setLabel("            ");
        labelWidget.setBorder(border);
        addChild(labelWidget);

        inputPorts = new PortGroup(scene, this, 1, PortType.INPUT);
        inputPorts.setPreferredLocation(new Point(0, -22));
        addChild(inputPorts);

        outputPorts = new PortGroup(scene, this, 1, PortType.OUTPUT);
        outputPorts.setPreferredLocation(new Point(0, 0));
        addChild(outputPorts);

        scene.addNode(this);

    }

    public void removeLabelEditor() {
        labelWidget.getActions().removeAction(editorAction);
    }

    public UINode getUINode() {
        return null;
    }

    public void setValue(float f) {
        dataNode.setValue(new AtomFloat(f));
        value = dataNode.getValue().getFloat();
    }

    public float getValue() {
        return value;
    }

    public void addConnection(ConnectionWidget c) {
        connections.add(c);
    }

    /**
     * Set node tool tip
     * @param string
     */
    public void setToolTip(String string) {
        this.setToolTip(string);
    }

    public void setLabel(String s) {
        labelWidget.setLabel(s);
        labelWidget.paint();
    }

    public void setPortWidth(int w) {
        portWidth = w;
    }

    private void setArea(Rectangle bounds) {
        area = bounds;
    }

    Object getLabel() {
        return labelWidget;
    }

    public void setDataNode(DataNode d) {
        dataNode = d;
    }

    public Widget getOutputPort(int i) {
        return outputPorts.getPort(i);
    }

    public Widget getInputPort(int i) {
        return inputPorts.getPort(i);
    }

    public void setOutputPortToolTip(int i, String s) {
        outputPorts.setPortToolTip(i, s);
    }

    public void setInputPortToolTip(int i, String s) {
        inputPorts.setPortToolTip(i, s);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void isEditable() {
        labelWidget.isEnabled();
    }

    @Override
    public void paintWidget() {
        updateModel();
    }

    public void setParameters(int ins, int outs, String title, String t) {
        parentScene.removeNode(this);
        removeChild(inputPorts);
        removeChild(outputPorts);

        numInputs = ins;
        numOutputs = outs;
        type = t;

        inputPorts = new PortGroup(parentScene, this, ins, PortType.INPUT);
        inputPorts.setPreferredLocation(new Point(0, -22));
        addChild(inputPorts);

        outputPorts = new PortGroup(parentScene, this, outs, PortType.OUTPUT);
        outputPorts.setPreferredLocation(new Point(0, 0));
        addChild(outputPorts);

        labelWidget.setLabel(title);
        outputPorts.setSapcing((int) (labelWidget.getBounds().getWidth() / (outs * 6)));
        inputPorts.setSapcing((int) (labelWidget.getBounds().getWidth() / (ins * 6)));

        parentScene.getModelScene().addNode(this);
    }

    public Widget getLabelWidget() {
        return labelWidget;
    }

    public void update() {
        updateNode();
    }

    public void updateNode() {
    }

    private void createDataNode(String text) {
        dataNode = dataNodeCreator.createObject(text, this);
        this.setParameters(dataNode.getNumInputs(), dataNode.getNumOutputs(), dataNode.getTitle(), dataNode.getType());
    }

    public int getBorderRadius() {
        return borderRadius;
    }

    public void updateModel() {
    }

    public DataNode getDataNode() {
        return dataNode;
    }

    public Object getModelScene() {
        return parentScene;
    }

    public void render() {
        this.repaint();
    }

    public void setMouseDragged(boolean b) {
        mouseDragged = b;
    }

    public boolean getMouseDragged() {
        return mouseDragged;
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

    public void renderLabel() {
        labelWidget.repaint();
    }

    public void setMouseEvent(WidgetMouseEvent wme) {
        mouseX = wme.getPoint().getX();
        mouseY = wme.getPoint().getY();
        xVelocity = lastX - mouseX;
        yVelocity = lastY - mouseY;

        lastX = mouseX;
        lastY = mouseY;
    }

    public void removeHoverActions() {
        outputPorts.removeHoverActions();
        System.out.println("hover model node off");
    }

    private class LabelTextFieldEditor implements TextFieldInplaceEditor {

        private ModelNode node;

        public LabelTextFieldEditor(ModelNode n) {
            node = n;

        }

        public boolean isEnabled(Widget widget) {
            return true;
        }

        public String getText(Widget widget) {
            return ((LabelWidget) widget).getLabel();
        }

        public void setText(Widget widget, String text) {
            ((LabelWidget) widget).setLabel(text);
            ModelNode newNode = (ModelNode) widget.getParentWidget();
            newNode.createDataNode(text);
        }
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

