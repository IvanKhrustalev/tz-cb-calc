package org.example;

import java.util.List;

import static org.example.Calculyator.expr;
import static org.example.Calculyator.signAnalizator;

public class Main {
    public static void main(String[] args) {
        String str = "2+2*2/5";
        List<Calculyator.Sign> signList = signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        System.out.println(expr(bufferSign));
    }
}