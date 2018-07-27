/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.sgv.shared;

/**
 *
 * @author ander
 */
public class FormatMoney {
    
    public static String formatMoney(String money) {
        String moneyFormat = "";
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
                isVerify = true;
        }
        
        return isVerify;
    }
}
