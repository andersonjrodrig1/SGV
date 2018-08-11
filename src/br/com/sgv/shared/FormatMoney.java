/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.shared;

import java.awt.event.KeyEvent;

/**
 *
 * @author ander
 */
public class FormatMoney {
    
    public static String formatMoney(String money) {
        String moneyFormat;
        String moneyAux = money.replace(",", "").replaceAll("\\.", "");
        moneyAux = String.valueOf(Long.valueOf(moneyAux));
        
        if (moneyAux.length() == 1)
            moneyFormat = "0,0".concat(moneyAux);
        else if (moneyAux.length() == 2)
            moneyFormat = "0,".concat(moneyAux);
        else {
            int textSize = moneyAux.length() - 2;
            int countPoint = (textSize % 3 == 0) ? textSize / 3 - 1 : textSize / 3;
            int sizeAux = textSize - 3;
            
            moneyFormat = ",".concat(moneyAux.substring(moneyAux.length() -2, moneyAux.length()));
            
            for (int i = 0; i < countPoint; i++) {
                moneyFormat = ".".concat(moneyAux.substring(sizeAux, textSize)).concat(moneyFormat);
                textSize -= 3;
                sizeAux -= 3;
            }
            
            if (textSize > 0) {
                moneyFormat = moneyAux.substring(0, textSize).concat(moneyFormat);
            }
        }
        
        return moneyFormat;
    }
    
    public static String verifyDecimalMoney(String money) {
        String[] splitMoney = money.split("\\.");
        
        if (splitMoney.length == 1) {
            money = money.concat(".00");
        } else if (splitMoney[splitMoney.length -1].length() == 1) {
            money = money.concat("0");
        }
        
        return money;
    }
    
    public static boolean verifyCodeChar(char letter) {
        boolean isVerify = false;
        
        switch(letter) {
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case '0':
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_F1:
            case KeyEvent.VK_F2:
            case KeyEvent.VK_F3:
            case KeyEvent.VK_F4:
            case KeyEvent.VK_F5:
            case KeyEvent.VK_F6:
            case KeyEvent.VK_F7:
            case KeyEvent.VK_F8:
            case KeyEvent.VK_F9:
            case KeyEvent.VK_F10:
            case KeyEvent.VK_F11:                     
            case KeyEvent.VK_F12:
            case KeyEvent.VK_SPACE:
                isVerify = true;
        }
        
        return isVerify;
    }
}
