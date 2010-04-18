/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofvisual.node;

import java.util.ArrayList;
import ofvisual.scene.ModelScene;
import org.netbeans.api.visual.layout.LayoutFactory;
import org.netbeans.api.visual.widget.Widget;

/**
 *
 * @author Jon
 */
public class PortGroup extends Widget {

    private int numPorts;
    private ModelScene scene;
    private ModelNode parent;
    private int portSize;
    private final PortType portType;
    private int spacing = 4;
    private ArrayList<Port> ports = new ArrayList();

    void setPortToolTip(int i, String s) {
        ports.get(i).setToolTipText(s);
    }

    void removeHoverActions() {
        for(int i = 0; i < ports.size() - 1; i++) {
            Port port = ports.get(i);
            port.removeHoverActions();
            System.out.println("hover group off");
        }
    }

    public enum PortType {

        INPUT,
        OUTPUT,
        NULL;
    }

    public PortGroup(ModelScene s, ModelNode w, int np, PortType type) {
        super(s);
        scene = s;
        parent = w;
        numPorts = np;
        portType = type;
        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, spacing));
        buildPorts();
    }

    public void buildPorts() {
        for (int i = 0; i < numPorts; i++) {
            Port port = new Port(scene, parent, 6, portType, i);
            ports.add(port);
            this.addChild(port);
        }
        if (numPorts == 2) {
            setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, (int) (parent.getBounds().getWidth() / numPorts)));
        }
    }

    public int getPortSize() {
        int parentWidth = (int) parent.getBounds().getWidth();
        portSize = parentWidth / numPorts;
        return portSize;
    }

    Widget getPort(int i) {
        return ports.get(i);
    }

    public void setSapcing(int gapSize) {
        setLayout(LayoutFactory.createHorizontalFlowLayout(LayoutFactory.SerialAlignment.CENTER, gapSize));
    }
}
