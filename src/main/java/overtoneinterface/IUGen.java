/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

import overtoneinterface.IUGenInput;

/**
 *
 * @author Jon
 */
public interface IUGen {

    String getName();

    int getRate();

    int numOutputs();

    IUGenInput[] getInputs();
}
