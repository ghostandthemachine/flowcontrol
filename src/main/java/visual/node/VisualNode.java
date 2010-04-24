/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.node;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.HashSet;
import atom.AtomFloat;
import dataScene.DataScene;
import data.DataNode;
import data.DataNodeCreator;
import java.awt.Dimension;
import visual.node.PortGroup.PortType;
import visual.scene.VisualScene;
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
import overtoneinterface.IUGenInfo;

/**
 *
 * @author Jon
 */
public class VisualNode extends Widget {

    private WidgetAction editorAction;
    private LabelWidget labelWidget;
    LookFeel lookFeel = getScene().getLookFeel();
    PortGroup outputPorts;
    PortGroup inputPorts;
    protected int numInputs = 0;
    protected int numOutputs = 0;
    protected int portWidth;
    protected int borderRadius = 10;
    protected Rectangle area;
    protected VisualScene parentScene;
    NodeMenu menu;
    DataScene dataScene;
    DataNode dataNode;
    DataNodeCreator dataNodeCreator;
    protected HashSet connections = new HashSet();
    protected float value = 0;
    protected double mouseX;
    protected double mouseY;
    protected double lastX;
    protected double lastY;
    protected double xVelocity;
    protected double yVelocity;
    public static String UI = "ui";
    public static String DATA = "data";
    protected String type;
    private boolean mouseDragged = false;

    public VisualNode(VisualScene scene, DataScene dScene) {
        super(scene);
        parentScene = scene;
        dataScene = dScene;
        type = VisualNode.DATA;
        dataNodeCreator = new DataNodeCreator(dScene, this, parentScene);
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

    public VisualNode(VisualScene scene, DataScene dScene, String label) {
        super(scene);
        parentScene = scene;
        dataScene = dScene;
        type = UI;
        dataNodeCreator = new DataNodeCreator(dScene, this, parentScene);
        dataNode = dataNodeCreator.createDataNode(label, this);
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

        //after construction, reset the label to be current with the dataNode
        this.setLabel(dataNode.getTitle());
        this.setParameters(dataNode.getNumInputs(), dataNode.getNumOutputs(), dataNode.getTitle(), dataNode.getType());
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
        //   parentScene.removeNode(this);
        removeChild(inputPorts);
        removeChild(outputPorts);
        removeChild(labelWidget);

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
        addChild(labelWidget);
        outputPorts.setSapcing((int) (50 / (outs * 6)));
        inputPorts.setSapcing((int) (50 / (ins * 6)));

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
        dataNode = dataNodeCreator.createDataNode(text, this);
        this.setParameters(dataNode.getNumInputs(), dataNode.getNumOutputs(), dataNode.getTitle(), dataNode.getType());
    }

    private void createDataNode(IUGenInfo ugen) {
        dataNode = dataNodeCreator.createDataNode(ugen.getName(), this);
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

        private VisualNode node;

        public LabelTextFieldEditor(VisualNode n) {
            node = n;

        }

        @Override
        public boolean isEnabled(Widget widget) {
            return true;
        }

        @Override
        public String getText(Widget widget) {
            return ((LabelWidget) widget).getLabel();
        }

        @Override
        public void setText(Widget widget, String text) {
            ((LabelWidget) widget).setLabel(text);
            VisualNode newNode = (VisualNode) widget.getParentWidget();
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

