/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.node;

import java.awt.Color;
import visual.node.PortGroup.PortType;
import visual.scene.VisualScene;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon
 */
public class Port extends Widget {

    private int size = 0;
    private Color highlightPaint = Color.green;
    private Color portPaint = Color.red;
    private boolean isInput = false;
    private boolean isOutput = true;
    PortLine line;
    PortInteractor highlight;
    private PortType portType;
    private VisualNode parentNode;
    private int id;

    public Port(VisualScene parentScene,VisualNode parent, int s, PortType type, int ID) {
        super(parentScene);
        id = ID;
        portType = type;
        size = s;
        line = new PortLine(parentScene, size);
        highlight = new PortInteractor(parentScene, size, portType);
        parentNode = parent;
        addChild(line);
        addChild(highlight);
    }

    public PortInteractor getPortInteractor() {
        return highlight;
    }
    public int getID(){
        return id;
    }

    public boolean isInput(){
        return isInput;
    }

    public PortType getPortType(){
        return portType;
    }

    public boolean isOutput(){
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

    public VisualNode getParentNode() {
        return parentNode;
    }

    void removeHoverActions() {
        highlight.removeHoverActions();
        System.out.println("hover port off");
    }

}
