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

    String getName();

    int getRate();

    int numOutputs();

    int numInputs();

    IUGenParameter getParameters();

    String getDoc();

    IUGenInput[] getInputs();
}


