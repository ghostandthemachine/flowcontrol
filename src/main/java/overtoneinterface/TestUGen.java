/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package overtoneinterface;

import java.util.ArrayList;

/**
 *
 * @author Jon
 */
public class TestUGen implements IUGen {

    String name;
    int rate;
    int nOutputs;
    int nInputs;
    TestUGenParameter params;
    ArrayList<IUGenConnection> inputs = new ArrayList<IUGenConnection>();
    IUGenInfo info;
    private float defaultValue = 0;

    public TestUGen(String name, int rate, int ins, int outs) {
        this.name = name;
        this.rate = rate;
        this.nInputs = ins;
        this.nOutputs = outs;

        params = new TestUGenParameter(name, defaultValue);
    }

    public TestUGen(String name, int rate, int ins, int outs, IUGenConnection[] inputs) {
        this.name = name;
        this.rate = rate;
        this.nInputs = ins;
        this.nOutputs = outs;

        for ( int i = 0; i < inputs.length; i++) {
            this.inputs.add( inputs[i]);
        }

        params = new TestUGenParameter(name, defaultValue);
    }

    public TestUGen(IUGenInfo info) {
        this.name = info.getName();
        this.rate = info.getRate();
        this.nInputs = info.numInputs();
        this.nOutputs = info.numOutputs();

        params = new TestUGenParameter(name, defaultValue);
    }

    public void addUGenInput(TestUGenConnection input) {
        inputs.add(input);
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
    public IUGenConnection[] getConnections() {
        IUGenConnection[] array = new IUGenConnection[inputs.size()];
        inputs.toArray(array);
        return array;
    }

    @Override
    public int getNumConnections() {
        return inputs.size();
    }

    @Override
    public int numInputs() {
        return nInputs;
    }

    public IUGenInfo getInfo() {
        return info;
    }

    @Override
    public TestUGenParameter getParameters() {
        return params;
    }
}
