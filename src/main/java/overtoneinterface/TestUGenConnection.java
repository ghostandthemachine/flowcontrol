/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

/**
 *
 * @author Jon
 */
public class TestUGenConnection implements IUGenConnection {

    private IUGen source;
    private int sourcePort;
    private IUGen target;
    private int targetPort;

    public TestUGenConnection(IUGen srcUgen, int srcId, IUGen tgtUgen, int tgtId) {
        source = srcUgen;
        sourcePort = srcId;
        target = tgtUgen;
        targetPort = tgtId;
    }

    @Override
    public IUGen getTarget() {
        return target;
    }

    @Override
    public int getTargetPortNumber() {
        return targetPort;
    }

    @Override
    public IUGen getSource() {
        return source;
    }

    @Override
    public int getSourcePortNumber() {
        return sourcePort;
    }
}
