package GroupNode;

import GroupNode.CustomPortGroup.CustomPortType;
import org.netbeans.api.visual.border.Border;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import org.netbeans.api.visual.widget.Widget;
import visual.test.Tools;

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
    int x = 0;
    int y = 0;
    int width = 0;
    int height = 0;
    int area = 0;
    int inputSpacing = 1;
    int outputSpacing = 1;
    Point[] inputPoints;
    Point[] outputPoints;
    private int lastArea = 0;
    private int portArea = 0;

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

        outputPoints = new Point[node.getNumOutputs()];
        inputPoints = new Point[node.getNumInputs()];

        createInputPorts();
        createOutputPorts();
    }

    public Collection<Widget> getPorts() {
        ArrayList<Widget> list = new ArrayList<Widget>();
        list.add(outputPorts);
        list.add(inputPorts);
        return list;
    }

    private void revalidatePorts() {
        float inputWidth = 0;
        if (node.getNumInputs() > 1) {
            inputWidth = portArea / numInputs;
        } else {
            inputWidth = 10;
        }

        float outputWidth = 0;
        if (node.getNumOutputs() > 1) {
            outputWidth = portArea / (node.getNumOutputs());
        } else {
            outputWidth = 10;
        }

        //local ints to store the next 0 locations
        int itx = (int) -((14 - inputWidth) / 2);
        int otx = (int) -((14 - outputWidth) / 2);

        outputWidth = Tools.constrain(outputWidth / 2, 2, 6);
        inputWidth = Tools.constrain(inputWidth / 2, 2, 6);

        for (int i = 0; i < inputPoints.length; i++) {
            if (i == 0) {
                inputPoints[i] = new Point(2 + (arcWidth / 2), y);
            } else if (i == 1) {
                inputPoints[1] = new Point(itx/2 + (i * (portArea / node.getNumInputs())), y);
            } else {
                inputPoints[i] = new Point(itx + (i * ((portArea / node.getNumInputs()))) - (arcWidth / 2), y);
            }
            CustomPortInteractor port = (CustomPortInteractor) inputPorts.getPort(i);
            port.setSize((int) inputWidth);
            port.setStrokeWidth(thickness);
            port.setPreferredLocation(inputPoints[i]);
            port.repaint();
        }

        for (int i = 0; i < outputPoints.length; i++) {
            if (i == 0) {
                outputPoints[i] = new Point(2 + arcWidth / 2, y + height);
            } else {
                outputPoints[i] = new Point(i * (portArea / node.getNumOutputs()) + (2 + arcWidth / 2), y + height);
            }
            CustomPortInteractor port = (CustomPortInteractor) outputPorts.getPort(i);
            port.setSize((int) outputWidth);
            port.setPreferredLocation(outputPoints[i]);
            port.repaint();
        }


    }

    public Insets getInsets() {
        return new Insets(insetHeight, insetWidth, insetHeight, insetWidth);
    }

    @Override
    protected Rectangle calculateClientArea() {
        revalidatePorts();
        return super.calculateClientArea();
    }

    /*
     * all the paint values are being cast to int's because the setPrefferedBounds(point) method which sets
     * the location of the PortInteractor visual nodes which are the ports. The method only takes ints, so
     * if the border is painted with float, it doens't line up right
     */
    public void paint(Graphics2D gr, Rectangle bounds) {
        if (fillColor != null) {
            gr.setColor(fillColor);
            gr.fill(new RoundRectangle2D.Float((int) bounds.x + 1, (int) bounds.y + 1, (int) bounds.width - 4, (int) bounds.height - 4, (int) arcWidth, (int) arcHeight));
        }
        if (drawColor != null) {
            gr.setColor(drawColor);
            gr.setStroke(new BasicStroke(thickness));
            gr.draw(new RoundRectangle2D.Float((int) (bounds.x + thickness / 2), (int) (bounds.y + thickness / 2), (int) (bounds.width - thickness - 2), (int) (bounds.height - thickness - 2), (int) arcWidth, (int) arcHeight));

            x = (int) (bounds.x + thickness / 2);
            y = (int) (bounds.y + thickness / 2);
            width = (int) (bounds.width - thickness);
            height = (int) (bounds.height - thickness) - 2;
            portArea = width - arcWidth * 2;
            lastArea = area;
            area = width * height;

            //if the size chagnes, update the port locations
            if (lastArea != area) {
                revalidatePorts();
            }
        }
    }



    public CustomPortGroup getOutputs() {
        return outputPorts;
    }

    public CustomPortGroup getInputs() {
        return inputPorts;
    }

    void setParameters(BorderPortTestNode n) {
        outputPoints = new Point[node.getNumOutputs()];
        inputPoints = new Point[node.getNumInputs()];

        node.removeChild(inputPorts);
        node.removeChild(outputPorts);

        numInputs = n.getNumInputs();
        numOutputs = n.getNumOutputs();

        inputPorts = new CustomPortGroup(node, numInputs, CustomPortType.INPUT);
        outputPorts = new CustomPortGroup(node, numOutputs, CustomPortType.OUTPUT);

        revalidatePorts();

        node.addChild(inputPorts);
        node.addChild(outputPorts);
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
