/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ofdata;

import ofAtom.Atom;

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
    DataNode srcParent;
    DataNode tgtParent;

    public DataConnection(DataNode src, int srcid, DataNode tgt, int tgtid) {
        srcParent = src;
        tgtParent = tgt;
        srcId = srcid;
        tgtId = tgtid;
    }

    /**
     * update the connection by getting the source nodes' output Atom at
     * the specified ID (output number) and set the target nodes' input
     */
    public void update() {
        tgtParent.setInput(tgtId, srcParent.getOutput(srcId));
        tgtParent.update();
    }
}
