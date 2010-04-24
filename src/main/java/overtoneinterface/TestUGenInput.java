/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package overtoneinterface;

/**
 *
 * @author Jon
 */
public class TestUGenInput implements IUGenInput{
    private final IUGen ugen;
    private final int portID;

    public TestUGenInput(IUGen ugen, int id) {
        this.ugen = ugen;
        this.portID = id;
    }

    @Override
    public IUGen getUGen() {
       return ugen;
    }

    @Override
    public int getPortNumber() {
       return portID;
    }

}
