/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

import compositeNodes.OvertoneNodeInfo;

/**
 *
 * @author Jon
 */
public interface IUGenInfo extends OvertoneNodeInfo {

    int getRate();

    IUGenParameter getParameters();

    IUGenConnection[] getInputs();
}


