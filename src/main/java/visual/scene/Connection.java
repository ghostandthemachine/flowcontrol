/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual.scene;

import org.netbeans.api.visual.widget.ConnectionWidget;
import visual.node.VisualNode;

/**
 *
 * @author Jon
 */
public class Connection {

    VisualNode source;
    VisualNode target;
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

    public VisualNode getSource() {
        return source;
    }

    public int getSourcePort() {
        return sourcePort;
    }

    public VisualNode getTarget() {
        return target;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public ConnectionWidget getConnectionWidget() {
        return connection;
    }

}
