/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

/**
 *
 * @author Jon
 */
public interface IUGenConnection {

    IUGen getTarget();

    int getTargetPortNumber();

    IUGen getSource();

    int getSourcePortNumber();
}
