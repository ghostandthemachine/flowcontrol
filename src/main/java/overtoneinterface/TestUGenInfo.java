/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

/**
 *
 * @author Jon
 */
public class TestUGenInfo implements IUGenInfo {

    String name;
    TestUGenParameter[] params = new TestUGenParameter[3];
    int rate;
    int nOutputs;
    int nInputs;

    public TestUGenInfo(String name, int rate, int ins, int outs) {

        this.name = name;
        this.rate = rate;
        this.nInputs = ins;
        this.nOutputs = outs;

        TestUGenParameter param = new TestUGenParameter(name, 500);
        params[0] = param;
        params[1] = param;
        params[2] = param;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public IUGenParameter[] getParameters() {
        return params;
    }

    @Override
    public String getDoc() {
        return "this is a test ugen dummy document";
    }


    public int numInputs() {
        return params.length;
    }

    @Override
    public int[] getRates() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
