/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

/**
 *
 * @author Jon
 */
public interface IUGenInfo {

    int getRate();

    IUGenParameter getParameters();

    IUGenConnection[] getInputs();

    String getName();

    int numOutputs();

    int numInputs();

    String getDoc();

}


