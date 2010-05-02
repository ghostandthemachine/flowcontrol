/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compositeNodes;

/**
 *
 * @author Jon
 */
public interface OvertoneNode {

    String getName();

    int numOutputs();

    int numInputs();

    int getNumConnections();
}
