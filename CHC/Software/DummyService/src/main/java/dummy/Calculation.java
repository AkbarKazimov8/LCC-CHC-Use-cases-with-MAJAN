/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummy;

import lombok.Getter;
import lombok.Setter;

public class Calculation {

    @Getter
    @Setter
    private int op1;
    @Getter
    @Setter
    private int op2;
    @Getter
    @Setter
    private String func;
    @Getter
    @Setter
    private String actionID;
    @Getter
    @Setter
    private String ajanHost;
    @Getter
    @Setter
    private int ajanPort;

    public Calculation() {

    }

    public Calculation(int op1, int op2, String func, String actionID, String ajanHost, int ajanPort) {
        this.op1 = op1;
        this.op2 = op2;
        this.func = func;
        this.actionID = actionID;
        this.ajanHost = ajanHost;
        this.ajanPort = ajanPort;
    }

}
