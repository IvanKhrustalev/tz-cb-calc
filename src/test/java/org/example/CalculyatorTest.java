package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculyatorTest {

    @Test
     void signAnalizator() {
        String input = "(3 + 4) * 2 - 5";
        List<Calculyator.Sign> expected = new ArrayList<>();
        expected.add(new Calculyator.Sign(SignType.LEFT_BRACKET, '('));
        expected.add(new Calculyator.Sign(SignType.NUMBER, "3"));
        expected.add(new Calculyator.Sign(SignType.PLUS, '+'));
        expected.add(new Calculyator.Sign(SignType.NUMBER, "4"));
        expected.add(new Calculyator.Sign(SignType.RIGHT_BRACKET, ')'));
        expected.add(new Calculyator.Sign(SignType.MULT, '*'));
        expected.add(new Calculyator.Sign(SignType.NUMBER, "2"));
        expected.add(new Calculyator.Sign(SignType.MINUS, '-'));
        expected.add(new Calculyator.Sign(SignType.NUMBER, "5"));
        expected.add(new Calculyator.Sign(SignType.END_LINE, ""));

        List<Calculyator.Sign> actual = Calculyator.signAnalizator(input);

        assertEquals(actual, expected);
    }


    @Test
    void expr() {
        String str = "2+3-4*5/2";
        List<Calculyator.Sign> signList = Calculyator.signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        double expect = -5;
        double result = Calculyator.expr(bufferSign);
        assertEquals(expect, result);
    }

    @Test
    void exprShouldReturnZero() {
        Calculyator.Sign sign = new Calculyator.Sign(SignType.END_LINE, "");
        List<Calculyator.Sign> signListNumber = new ArrayList<>();
        signListNumber.add(sign);;
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signListNumber);
        double result = Calculyator.expr(bufferSign);
        assertEquals(0, result);
    }

    @Test
    void delMult() {
        String str = "6*3/8";
        List<Calculyator.Sign> signList = Calculyator.signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        double expect = 2.25;
        double result = Calculyator.delMult(bufferSign);
        assertEquals(expect, result);
    }

    @Test
    void plusMinus() {
        String str = "1+5-2";
        List<Calculyator.Sign> signList = Calculyator.signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        double expect = 4;
        double result = Calculyator.plusMinus(bufferSign);
        assertEquals(expect, result);
    }
    @Test
    void plusMinusWithMultipleOperations() {
        String str = "1+5-2*3";
        List<Calculyator.Sign> signList = Calculyator.signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        double expect = 0;
        double result = Calculyator.plusMinus(bufferSign);
        assertEquals(expect, result);
    }
    @Test
    void plusMinusIncorrectSymbol() {
        String str = "1+5-$2";
        List<Calculyator.Sign> signList = Calculyator.signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        assertThrows(RuntimeException.class, () -> Calculyator.plusMinus(bufferSign));
    }

    @Test
    void factorNumber() {
        Calculyator.Sign sign = new Calculyator.Sign(SignType.NUMBER, "42");
        List<Calculyator.Sign> signListNumber = new ArrayList<>();
        signListNumber.add(sign);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signListNumber);
        double result = Calculyator.factor(bufferSign);
        assertEquals(42, result);
    }
    @Test
    void factorWithBracket() {
        String str = "(2+3)*4";
        List<Calculyator.Sign> signList = Calculyator.signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        double expect = 5;
        double result = Calculyator.factor(bufferSign);
        assertEquals(expect, result);
    }
    @Test
    void factorIncorrectSymbol() {
        String str = "#4-!2";
        List<Calculyator.Sign> signList = Calculyator.signAnalizator(str);
        Calculyator.BufferSign bufferSign = new Calculyator.BufferSign(signList);
        assertThrows(RuntimeException.class, () -> Calculyator.factor(bufferSign));
    }
}