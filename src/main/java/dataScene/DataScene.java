/*
 * OverFlow Data scene
 * Holds nodes and connections as well as provides and interface
 * for creating, maintaining, and removing nodes and connections
 * from the scene.
 * 
 */
package dataScene;

import java.util.ArrayList;
import data.DataConnection;
import data.DataNode;
import visual.scene.VisualScene;

/**
 *
 * @author Jon Rose
 */
public class DataScene {

    ArrayList<DataConnection> connections = new ArrayList<DataConnection>();
    ArrayList<DataNode> nodes = new ArrayList<DataNode>();
    private VisualScene scene;

    public DataScene(VisualScene s) {
        scene = s;
    }

    public ArrayList getConnections() {
        return connections;
    }

    public void addNode(DataNode node) {
        nodes.add(node);
    }

    public void removeNode(DataNode node) {
        nodes.remove(node);
    }

    /**
     * Connect two nodes with no ids
     * Defaults to connect port 0 to 0.
     * 
     * @param source node
     * @param target node
     */
    public void connect(DataNode source, DataNode target) {
        source.addDataConnection(new DataConnection(source, 0, target, 0));
        connections.add(new DataConnection(source, 0, target, 0));
    }

    /**
     *
     * @param source node
     * @param source node port id
     * @param target node
     * @param target node port id
     */
    public void connect(DataNode source, int sourcePort, DataNode target, int targetPort) {
        source.addDataConnection(new DataConnection(source, sourcePort, target, targetPort));
        connections.add(new DataConnection(source, sourcePort, target, targetPort));
    }

    public void removeConnection(DataConnection connection) {
        connections.remove(connection);
    }

    public DataNode getNode(int i) {
        return nodes.get(i);
    }

    public void setNodes(ArrayList<DataNode> nodes) {
        this.nodes = nodes;
    }

    public VisualScene getModelScene() {
        return scene;
    }

    public void removeConnection(DataConnection[] connections) {
        if (connections != null) {
            for (int i = 0; i < connections.length; i++) {
                this.removeConnection(connections[i]);
            }
        }
    }

    public DataConnection[] getConnections(DataNode node) {
        ArrayList<DataConnection> returnConnections = new ArrayList<DataConnection>();
        DataConnection[] conns = new DataConnection[0];
        if (nodes.contains(node)) {
            for (int i = 0; i < nodes.size(); i++) {
                DataConnection connection = connections.get(i);
                //if the node is either a source or target somewhere
                if (connection.getSource().equals(node) || connection.getTarget().equals(node)) {
                    returnConnections.add(connection);
                }
            }
        }
        conns = returnConnections.toArray(conns);
        return conns;
    }
}
