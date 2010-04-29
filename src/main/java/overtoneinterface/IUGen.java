/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

import compositeNodes.OvertoneNode;

/**
 *
 * @author Jon
 */
public interface IUGen extends OvertoneNode {

    int getRate();

    IUGenConnection[] getConnections();

    public TestUGenParameter getParameters();
}
