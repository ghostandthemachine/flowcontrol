/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package overtoneinterface;

/**
 *
 * @author Jon
 */
public class TestUGenParameter implements IUGenParameter{
    private String name;
    private float defaultValue;

    public TestUGenParameter(String name, float value) {
        this.name = name;
        this.defaultValue = value;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public float getDefaultValue() {
        return defaultValue;
    }

}
