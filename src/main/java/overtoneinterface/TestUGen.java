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
    ArrayList<TestUGenInput> inputs = new ArrayList<TestUGenInput>();
    TestUGenInfo info;
    private float defaultValue = 0;

    public TestUGen(String name, int rate, int ins, int outs) {
        this.name = name;
        this.rate = rate;
        this.nInputs = ins;
        this.nOutputs = outs;

        params = new TestUGenParameter(name, defaultValue);
        info = new TestUGenInfo(this);
        for (int i = 0; i < nInputs; i++) {
            inputs.add(0, new TestUGenInput(this, i));
        }
    }

    public void addUGenInput(TestUGenInput input){
        inputs.add(input);
        nInputs++;
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
    public IUGenInput[] getInputs() {
        IUGenInput[] array = new IUGenInput[inputs.size()];
        inputs.toArray(array);
        return array;
    }

    @Override
    public int numInputs() {
        return inputs.size();
    }

    public TestUGenInfo getInfo() {
        return info;
    }

    @Override
    public TestUGenParameter getParameters() {
        return params;
    }
}
