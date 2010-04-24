/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package visual.test;

import overtoneinterface.TestUGen;

/**
 *
 * @author Jon
 */
public class UGenTest {

    TestUGen[] ugenInfo = new TestUGen[10];

    public UGenTest() {

        for(int i = 0; i < 9; i++) {
            ugenInfo[i] = new TestUGen("test", 500, 2, 2);
        }
    }
}
