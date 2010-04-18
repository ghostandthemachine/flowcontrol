/*
 * OverFlow Data scene
 * Holds nodes and connections as well as provides and interface
 * for creating, maintaining, and removing nodes and connections
 * from the scene.
 * 
 */
package ofDataScene;

import java.util.ArrayList;
import ofdata.DataConnection;
import ofdata.DataNode;
import ofvisual.scene.ModelScene;

/**
 *
 * @author Jon Rose
 */
public class DataScene {

    ArrayList<DataConnection> connections = new ArrayList<DataConnection>();
    ArrayList<DataNode> nodes = new ArrayList<DataNode>();
    private ModelScene scene;

    public DataScene(ModelScene s) {
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
    public void connect(DataNode n1, DataNode n2) {
        n1.addDataConnection(new DataConnection(n1, 0, n2, 0));
        connections.add(new DataConnection(n1, 0, n2, 0));
    }

    /**
     *
     * @param source node
     * @param source node port id
     * @param target node
     * @param target node port id
     */
    public void connect(DataNode n1, int n1ID, DataNode n2, int n2ID) {
        n1.addDataConnection(new DataConnection(n1, n1ID, n2, n2ID));
        connections.add(new DataConnection(n1, n1ID, n2, n2ID));
    }

    public DataNode getNode(int i) {
        return nodes.get(i);
    }

    public void setNodes(ArrayList<DataNode> nodes) {
        this.nodes = nodes;
    }

    public ModelScene getModelScene() {
        return scene;
    }
}
