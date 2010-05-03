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

    int[] getRates();

    IUGenParameter[] getParameters();

    String getName();

    String getDoc();

}


