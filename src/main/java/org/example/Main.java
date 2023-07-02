package org.example;

import java.util.List;

import static org.example.Calculyator.expr;
import static org.example.Calculyator.signAnalizator;

public class Main {
    public static void main(String[] args) {
        String str = "13+21-56234/3421";
        List<Calculyator.Sign> signList = signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        System.out.println(expr(bufferSign));
    }
}