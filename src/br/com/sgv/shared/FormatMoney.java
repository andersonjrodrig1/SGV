/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.shared;

import br.com.sgv.enumerator.CalcTypeEnum;
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
    
    public static String verifyDecimal(String money, int typeCalc) {
        String[] splitMoney = money.split("\\.");
        
        if (typeCalc == CalcTypeEnum.UNITY.value) {
            if (splitMoney.length == 1) {
                money = money.concat(".00");
            } else if (splitMoney[splitMoney.length -1].length() == 1) {
                money = money.concat("0");
            }
        } else {
            if (splitMoney.length == 1) {
                money = money.concat(".000");
            } else if (splitMoney[1].length() == 1) {
                money = money.concat("00");
            } else if (splitMoney[1].length() == 2) {
                money = money.concat("0");
            } 
        }
        
        return money;
    }
    
    public static String verifyAmountDecimal(String amount) {
        if (!amount.isEmpty()) {
            amount = amount.replaceAll(",", "").replaceAll("\\.", "");
            int convertValue = Integer.valueOf(amount);
            amount = String.valueOf(convertValue);

            if (amount.length() == 1) {
                amount = "0,00".concat(amount);
            } else if (amount.length() == 2) {
                amount = "0,0".concat(amount);
            } else if (amount.length() == 3) {
                amount = "0,".concat(amount);
            } else {
                String g = amount.substring(amount.length() -3, amount.length());
                String kg = amount.substring(0, amount.length() -3);
                amount = kg.concat(",").concat(g);
            }
        } else {
            return "0,000";
        }
        
        return amount;
    }
    
    public static boolean verifyCodeChar(KeyEvent event) {
        Object objKeyChar = event.getKeyChar();        
        char letterChar = 0;
        
        if (event.getKeyChar() != '\uffff') {            
            if (event.getKeyChar() != '\b') {
                if (objKeyChar instanceof String)
                    return false;
                else
                    letterChar = (char)objKeyChar;
            }
        }
        
        switch(letterChar) {
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
                return true;
        }
        
        switch(event.getKeyCode()) {
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
            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_ESCAPE:
            case KeyEvent.VK_BACK_SPACE:
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_CONTROL:
            case KeyEvent.VK_WINDOWS:
            case KeyEvent.VK_AT:
            case KeyEvent.VK_PRINTSCREEN:
            case KeyEvent.VK_DELETE:
            case KeyEvent.VK_PAGE_UP:
            case KeyEvent.VK_PAGE_DOWN:
            case KeyEvent.VK_HOME:
            case KeyEvent.VK_END:
            case KeyEvent.VK_UP:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_SHIFT:
                return true;
        }
        
        return false;
    }
}
