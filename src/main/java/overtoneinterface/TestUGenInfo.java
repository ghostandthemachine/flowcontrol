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

    TestUGen ugen;
    String name;
    TestUGenParameter params;
    int rate;
    int nOutputs;
    int nInputs;
    
    public TestUGenInfo(IUGen ugen) {

        this.ugen = (TestUGen) ugen;
        this.name = ugen.getName();
        this.rate = ugen.getRate();
        this.nInputs = ugen.numInputs();
        this.nOutputs = ugen.numOutputs();

        params = ugen.getParameters();
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
    public IUGenInput[] getInputs() {
        return ugen.getInputs();
    }

    @Override
    public int numInputs() {
        return nInputs;
    }

}
