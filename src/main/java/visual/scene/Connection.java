/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visual.scene;

import GroupNode.BorderPortTestNode;
import org.netbeans.api.visual.widget.ConnectionWidget;
import org.netbeans.api.visual.widget.Widget;
import visual.node.VisualNode;

/**
 *
 * @author Jon
 */
public class Connection {

    Widget source;
    Widget target;
    int sourcePort;
    int targetPort;
    ConnectionWidget connection;

    public Connection(VisualNode source, int sourcePort, VisualNode target, int targetPort, ConnectionWidget connection) {

        this.source = source;
        this.target = target;
        this.sourcePort = sourcePort;
        this.targetPort = targetPort;
        this.connection = connection;
    }

    Connection(BorderPortTestNode source, int sourcePort, BorderPortTestNode target, int targetPort, ConnectionWidget connection) {
        this.source = source;
        this.target = target;
        this.sourcePort = sourcePort;
        this.targetPort = targetPort;
        this.connection = connection;
    }

    public Widget getSource() {
        return source;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public Widget getTarget() {
        return target;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public ConnectionWidget getConnectionWidget() {
        return connection;
    }
}
