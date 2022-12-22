/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities;

import javax.swing.JLabel;

/**
 *
 * @author ADMIN
 */
public class RunText extends Thread {

    JLabel Name;

    public RunText(JLabel Name) {
        this.Name = Name;
    }

    @Override
    public void run() {
        while (true) {
            String str = Name.getText();
            char str1 = str.charAt(0);
            String str2 = str.substring(1);
            str = str2 + str1;
            Name.setText(str);
            try {
                sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

    }
}
