/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GroupNode;

import GroupNode.CustomPortGroup.CustomPortType;
import java.awt.Color;
import visual.node.PortGroup.PortType;
import org.netbeans.api.visual.widget.Widget;
import visual.node.VisualNode;

/**
 *
 * @author Jon
 */
public class CustomPort extends Widget {

    private int size = 0;
    private Color highlightPaint = Color.green;
    private Color portPaint = Color.red;
    private boolean isInput = false;
    private boolean isOutput = true;
    CustomPortInteractor portGraphics;
    private CustomPortType portType;
    private BorderPortTestNode node;
    private int id;

    public CustomPort(BorderPortTestNode node, int size, CustomPortType type, int ID) {
        super(node.getScene());
        id = ID;
        portType = type;
        this.size = size;
        this.node = node;

        portGraphics = new CustomPortInteractor(node, size, portType, ID);

        addChild(portGraphics);
        revalidate();
    }

    public CustomPortInteractor getPortInteractor() {
        return portGraphics;
    }

    public int getID() {
        return id;
    }

    public boolean isInput() {
        return isInput;
    }

    public CustomPortType getPortType() {
        return portType;
    }

    public boolean isOutput() {
        return isOutput;
    }

    public Color getHighlightPaint() {
        return highlightPaint;
    }

    public void setHighlightPaint(Color highlightPaint) {
        this.highlightPaint = highlightPaint;
    }

    public Color getPortPaint() {
        return portPaint;
    }

    public void setPortPaint(Color portPaint) {
        this.portPaint = portPaint;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public BorderPortTestNode getParentNode() {
        return node;
    }

    void removeHoverActions() {
        portGraphics.removeHoverActions();
        System.out.println("hover port off");
    }
}
