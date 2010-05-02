/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GroupNode;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import atom.AtomFloat;
import dataScene.DataScene;
import data.DataNode;
import java.awt.Font;
import java.util.ArrayList;
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
import org.netbeans.api.visual.laf.LookFeel;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.model.ObjectState;
import org.netbeans.api.visual.widget.LabelWidget;
import org.netbeans.api.visual.widget.Widget;
import overtoneinterface.IUGen;
import overtoneinterface.IUGenInfo;
import overtoneinterface.TestUGen;
import trie.Trie;
import visual.UINodes.NodeMenu;
import visual.node.NodeCreator;
import visual.node.UINode;
import visual.scene.Connection;

/**
 *
 * @author Jon Rose
 */
public class BorderPortTestNode extends Widget {

    private WidgetAction editorAction;
    protected LabelWidget labelWidget;
    LookFeel lookFeel = getScene().getLookFeel();
    protected int numInputs = 0;
    protected int numOutputs = 0;
    protected int portWidth;
    protected int borderRadius = 10;
    protected Rectangle area;
    protected VisualScene visualScene;
    NodeMenu menu;
    DataScene dataScene;
    DataNode dataNode;
    NodeCreator dataNodeCreator;
    protected float value = 0;
    protected double mouseX;
    protected double mouseY;
    protected double lastX;
    protected double lastY;
    protected double xVelocity;
    protected double yVelocity;
    protected double width = 1;
    public static String UI = "ui";
    public static String DATA = "data";
    protected String type;
    private boolean mouseDragged = false;
    PortBorder portBorder;
    private DataNode lastDataNode;
    protected Color fillColor = Color.white;
    protected Color selectedFillColor = Color.lightGray;
    protected Color textColor = Color.BLACK;
    private TestUGen ugen;

    public BorderPortTestNode(VisualScene scene) {
        super(scene);
        visualScene = scene;
        editorAction = ActionFactory.createInplaceEditorAction(new LabelTextFieldEditor(this));

        this.setLayout(LayoutFactory.createAbsoluteLayout());
        createLabelWidget(scene);

        scene.addNode(this);
    }

    /**
     * Implements the widget-state specific look of the widget.
     * @param previousState the previous state
     * @param state the new state
     */
    @Override
    public void notifyStateChanged(ObjectState previousState, ObjectState state) {
        LookFeel lookFeel = getScene().getLookFeel();
        labelWidget.setBorder(portBorder);
        labelWidget.setForeground(state.isSelected() ? Color.black : Color.black);
        portBorder.setFill(state.isSelected() ? new Color(200, 200, 200) : Color.white);
    }

    private void createLabelWidget(VisualScene scene) {
        this.setBackground(new Color(0, 0, 0, 0));

        labelWidget = new LabelWidget(visualScene);
        labelWidget.setFont(new Font("verdana", Font.BOLD, 13));
        labelWidget.getActions().addAction(editorAction);
        labelWidget.setLabel("            ");
        portBorder = new PortBorder(this, borderRadius, 5, 3, new Color(255, 255, 255), new Color(250, 250, 250), 3);
        portBorder.setFill(Color.white);
        portBorder.setDraw(Color.gray);
        labelWidget.getActions().addAction(ActionFactory.createResizeAction());
        System.out.println(labelWidget.getBounds());
        labelWidget.setPreferredLocation(new Point(borderRadius / 2, 0));

        addChild(labelWidget);
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

    /**
     * Set node tool tip
     * @param string
     */
    public void setToolTip(String string) {
        this.setToolTipText(string);
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
        return portBorder.getOutputs().getPort(i);
    }

    public Widget getInputPort(int i) {
        return portBorder.getInputs().getPort(i);
    }

    public void setOutputPortToolTip(int i, String s) {
        portBorder.getOutputs().setPortToolTip(i, s);
    }

    public void setInputPortToolTip(int i, String s) {
        portBorder.getInputs().setPortToolTip(i, s);
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

    public void setParameters(IUGen source) {
        removeChild(labelWidget);

        labelWidget.setLabel(source.getName());
        addChild(labelWidget);

        width = labelWidget.getBounds().getHeight();

        numInputs = source.numInputs();
        numOutputs = source.numOutputs();

        portBorder.setParameters(this);

        visualScene.getModelScene().addNode(this);
    }

    public Widget getLabelWidget() {
        return labelWidget;
    }

    public void update() {
        updateNode();
    }

    public void updateNode() {
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
        return visualScene;
    }

    public void render() {
        //    this.repaint();
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

    public DataNode getLastDataNode() {
        return lastDataNode;
    }

    public int getNumOutputs() {
        return numOutputs;
    }

    public int getNumInputs() {
        return numInputs;
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
        portBorder.getOutputs().removeHoverActions();
        //      System.out.println("hover model node off");
    }

    public Connection[] getConnections() {
        ArrayList<Connection> connections = new ArrayList<Connection>();
        Connection[] conns;
        for (int i = 0; i < visualScene.getConnections().size(); i++) {
            BorderPortTestNode source = (BorderPortTestNode) visualScene.getConnections().get(i).getSource();
            BorderPortTestNode target = (BorderPortTestNode) visualScene.getConnections().get(i).getTarget();
            if (source.equals(this) || target.equals(this)) {
                connections.add(visualScene.getConnections().get(i));
            }
        }
        conns = new Connection[connections.size()];
        return connections.toArray(conns);
    }

    public void createNewIUGenNode(IUGenInfo info) {
        if (info != null) {
            this.ugen = new TestUGen(info);
            this.setParameters(ugen);
            this.setToolTip(info.getDoc());
        } else {
            System.out.println("that is not a valid ugen");
        }
    }

    int getWidth() {
        if (labelWidget.getBounds() != null) {
            return (int) this.getBounds().getWidth();
        }
        return (int) width;
    }

    private class LabelTextFieldEditor implements TextFieldInplaceEditor {

        private BorderPortTestNode node;

        private LabelTextFieldEditor(BorderPortTestNode customNode) {
            node = customNode;
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
            LabelWidget label = (LabelWidget) widget;
            label.setLabel(text);

            try {
                //Trie returns the object if there is a single match, otherwisr it returns a tri of elements
                createNewIUGenNode((IUGenInfo) visualScene.getTrie().searchExact(text));
            } catch (java.lang.ClassCastException e) {
                Trie trie = (Trie) visualScene.getTrie().getTrieFor(text);
                //  trie.searchExact(text)
                System.out.println(trie.contents());
            }
            //VisualNode newNode = (VisualNode) widget.getParentWidget();
            //newNode.createDataNode(text);
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
//        if(wme.getPoint().x > labelWidget.getBounds().getWidth() - borderRadius/2){
//            this.getActions().addAction(ActionFactory.createResizeAction());
//        } else {
//            labelWid.getActions().removeAction(ActionFactory.createResizeAction());
//        }
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
        System.out.println("focus is lost on " + widget);
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
        //       System.out.println("update");
    }
}

