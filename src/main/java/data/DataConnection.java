/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import atom.Atom;

/**
 *
 * @author Jon
 */
public class DataConnection {

    int connectionId;
    int srcId;
    int tgtId;
    Atom lastValue;
    DataNode currentObject;
    DataNode source;
    DataNode target;

    public DataConnection(DataNode src, int srcid, DataNode tgt, int tgtid) {
        source = src;
        target = tgt;
        srcId = srcid;
        tgtId = tgtid;
    }

    /**
     * update the connection by getting the source nodes' output Atom at
     * the specified ID (output number) and set the target nodes' input
     */
    public void update() {
        target.setInput(tgtId, source.getOutput(srcId));
        target.update();
    }

    /**
     *  return the source data node
     */
    public DataNode getSource() {
        return source;
    }

    /**
     *  return the target data node
     */
    public DataNode getTarget() {
        return target;
    }
}
