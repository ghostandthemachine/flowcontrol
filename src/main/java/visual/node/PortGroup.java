/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.node;

import java.awt.Point;
import java.util.ArrayList;
import visual.scene.VisualScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Widget;
import visual.test.Tools;

/**
 *
 * @author Jon
 */
public class PortGroup extends Widget {

    private int nPorts;
    private VisualScene visualScene;
    private VisualNode node;
    private int portSize;
    private final PortType portType;
    private int spacing = 4;
    private ArrayList<Port> ports = new ArrayList();

    public enum PortType {

        INPUT,
        OUTPUT,
        NULL;
    }

    public PortGroup(VisualNode node, int numPorts, PortType type) {
        super(node.getScene());
        visualScene = (VisualScene) node.getScene();
        this.node = node;
        nPorts = numPorts;
        portType = type;
        this.setLayout(LayoutFactory.createAbsoluteLayout());

        int tempNodeWidth = (node.getWidith() - node.borderRadius * 2);

        if (nPorts > 0) {
            if (portType.equals(PortType.INPUT)) {
                portSize = (tempNodeWidth / node.getNumInputs()) / 2;
            } else if (portType.equals(PortType.OUTPUT)) {
                portSize = (tempNodeWidth / node.getNumOutputs()) / 2;
            }
            if (nPorts > 1) {
                spacing = (tempNodeWidth - portSize) / (nPorts - 1);
            } else {
                spacing = 10;
            }
            portSize = Tools.constrain(portSize, 2, 6);
        }
        buildPorts();
    }

    public void buildPorts() {
        for (int i = 0; i < nPorts; i++) {
            Port port = new Port(visualScene, node, portSize, portType, i);
            if (i != 0) {
                port.setPreferredLocation(new Point(i * spacing, 0));
            } else {
                port.setPreferredLocation(new Point(i * spacing, 0));
            }
            ports.add(port);
            this.addChild(port);
        }
        if (nPorts == 2) {
        }
    }

    public void setPortToolTip(int i, String s) {
        ports.get(i).setToolTipText(s);
    }

    public void removeHoverActions() {
        for (int i = 0; i < ports.size() - 1; i++) {
            Port port = ports.get(i);
            port.removeHoverActions();
            System.out.println("hover group off");
        }
    }

    public int getPortSize() {
        int parentWidth = (int) node.getBounds().getWidth();
        portSize = parentWidth / nPorts;
        return portSize;
    }

    public Widget getPort(int i) {
        return ports.get(i).getPortInteractor();
    }

    public void setSapcing(int gapSize) {
        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, gapSize));
    }
}
