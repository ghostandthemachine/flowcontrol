/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package overtoneinterface;

/**
 *
 * @author Jon
 */
public class TestUGenInfo implements IUGenInfo{

    String name;
    TestUGenParameter params;
    int rate;
    int nOutputs;
    int nInputs;
    
    public TestUGenInfo(String name, int rate, int ins, int outs) {

        this.name = name;
        this.rate = rate;
        this.nInputs = ins;
        this.nOutputs = outs;

        params = new TestUGenParameter(name, 500);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getRate() {
        return rate;
    }

    @Override
    public int numOutputs() {
        return nOutputs;
    }

    @Override
    public IUGenParameter getParameters() {
        return params;
    }

    @Override
    public String getDoc() {
        return "this is a test ugen dummy document";
    }

    @Override
    public IUGenConnection[] getInputs() {
        return null;
    }

    @Override
    public int numInputs() {
        return nInputs;
    }

}
