package GroupNode;

import GroupNode.CustomPortGroup.CustomPortType;
import org.netbeans.api.visual.border.Border;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.api.visual.widget.Widget;
import visual.node.PortGroup;
import visual.node.PortGroup.PortType;

/**
 * @author Jon Rose
 */
public class PortBorder extends Widget implements Border {

    private BorderPortTestNode node;
    private int arcWidth;
    private int arcHeight;
    private int insetWidth;
    private int insetHeight;
    private Color fillColor;
    private Color drawColor;
    private DrawResourceTableListener drawListener = null;
    private FillResourceTableListener fillListener = null;
    private float thickness;
    CustomPortGroup outputPorts;
    CustomPortGroup inputPorts;
    int numInputs = 0;
    int numOutputs = 0;
    float x = 0;
    float y = 0;
    float width = 0;
    float height = 0;

    public PortBorder(BorderPortTestNode node, int arc, int insetWidth, int insetHeight, Color fillColor, Color drawColor, int thickness) {
        super(node.getScene());
        this.node = node;
        this.arcWidth = arc;
        this.arcHeight = arc;
        this.insetWidth = insetWidth;
        this.insetHeight = insetHeight;
        this.fillColor = fillColor;
        this.drawColor = drawColor;
        this.thickness = thickness;

        createInputPorts();
        createOutputPorts();

        CustomPort port = new CustomPort(node, 20, CustomPortType.INPUT, 0);
        port.setPreferredLocation(new Point(10,10));
        this.addChild(port);
        port.bringToFront();

    }

    public Insets getInsets() {
        return new Insets(insetHeight, insetWidth, insetHeight, insetWidth);
    }

    public void paint(Graphics2D gr, Rectangle bounds) {
        if (fillColor != null) {
            gr.setColor(fillColor);
            gr.fill(new RoundRectangle2D.Float(bounds.x, bounds.y, bounds.width, bounds.height, arcWidth, arcHeight));
        }
        if (drawColor != null) {
            gr.setColor(drawColor);
            gr.setStroke(new BasicStroke(thickness));
            gr.draw(new RoundRectangle2D.Float(bounds.x + thickness / 2, bounds.y + thickness / 2, bounds.width - thickness, bounds.height - thickness, arcWidth, arcHeight));

            x = bounds.x + thickness / 2;
            y = bounds.y + thickness / 2;
            width = bounds.width - thickness;
            height = bounds.height - thickness;
        }
    }

    public CustomPortGroup getOutputs() {
        return outputPorts;
    }

    public CustomPortGroup getInputs() {
        return inputPorts;
    }

    void setParameters(BorderPortTestNode n) {
        this.removeChild(inputPorts);
        this.removeChild(outputPorts);
        
        numInputs = n.getNumInputs();
        numOutputs = n.getNumOutputs();
        
        inputPorts = new CustomPortGroup(node, numInputs, CustomPortType.INPUT);
        inputPorts.setPreferredLocation(new Point((int) (x  + arcWidth), (int) (y + arcHeight)));
        outputPorts = new CustomPortGroup(node, numOutputs, CustomPortType.OUTPUT);
        outputPorts.setPreferredLocation(new Point((int) (x  + arcWidth), (int) (y + height)));

        this.addChild(inputPorts);
        this.addChild(outputPorts);
    }

    public class DrawResourceTableListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent event) {
            drawColor = (Color) event.getNewValue();
        }
    }

    public class FillResourceTableListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent event) {
            fillColor = (Color) event.getNewValue();
        }
    }

    public void setFill(Color c) {
        fillColor = c;
    }

    public void setDraw(Color c) {
        drawColor = c;
    }

    private void createOutputPorts() {
        outputPorts = new CustomPortGroup(node, numOutputs, CustomPortType.OUTPUT);
        addChild(outputPorts);
        outputPorts.bringToFront();
    }

    private void createInputPorts() {
        inputPorts = new CustomPortGroup(node, numInputs, CustomPortType.INPUT);
        inputPorts.setPreferredLocation(new Point(0, -22));
        addChild(inputPorts);
        inputPorts.bringToFront();
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
}
