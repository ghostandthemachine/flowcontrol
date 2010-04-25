/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

/**
 *
 * @author Jon
 */
public interface IUGen {

    String getName();

    int getRate();

    int numOutputs();

    int numInputs();

    IUGenConnection[] getConnections();

    int getNumConnections();

    public TestUGenParameter getParameters();
}
