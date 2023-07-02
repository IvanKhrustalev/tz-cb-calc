package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Calculyator {


    public static class Sign {
        SignType signType;
        String value;

        public Sign(SignType signType, String value) {
            this.signType = signType;
            this.value = value;
        }

        public Sign(SignType signType, Character value) {
            this.signType = signType;
            this.value = value.toString();
        }

        @Override
        public String toString() {
            return "Sign{" +
                    "signType=" + signType +
                    ", value='" + value + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sign sign = (Sign) o;
            return signType == sign.signType && Objects.equals(value, sign.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(signType, value);
        }
    }
    public static class BufferSign {
        private int pos;
        public List<Sign> signList;

        public BufferSign() {}

        public BufferSign(List<Sign> signList) {
            this.signList = signList;
        }

        public Sign next() {
           return signList.get(pos++);
        }
        public void back() {
            pos--;
        }
        public int getPos() {
            return pos;
        }
    }

    public static List<Sign> signAnalizator(String str) {
        List<Sign> signList = new ArrayList<>();
        int pos = 0;
        while (pos < str.length()) {
            char c = str.charAt(pos);
            switch (c) {
                case '(':
                    signList.add(new Sign(SignType.LEFT_BRACKET, c));
                    pos++;
                    continue;
                case ')':
                    signList.add(new Sign(SignType.RIGHT_BRACKET, c));
                    pos++;
                    continue;
                case '+':
                    signList.add(new Sign(SignType.PLUS, c));
                    pos++;
                    continue;
                case '-':
                    signList.add(new Sign(SignType.MINUS, c));
                    pos++;
                    continue;
                case '*':
                    signList.add(new Sign(SignType.MULT, c));
                    pos++;
                    continue;
                case '/':
                    signList.add(new Sign(SignType.DEV, c));
                    pos++;
                    continue;
                default:
                    if (c <= '9' && c >= '0') {
                        StringBuilder sb = new StringBuilder();
                        do {
                            sb.append(c);
                            pos++;
                            if (pos >= str.length()) {
                                break;
                            }
                            c = str.charAt(pos);
                        } while (c <= '9' && c >= '0');
                        signList.add(new Sign(SignType.NUMBER, sb.toString()));
                    } else {
                        if (c != ' ') {
                            throw new RuntimeException("Неверный символ " + c);
                        }
                        pos++;
                    }
            }
        }
        signList.add(new Sign(SignType.END_LINE, ""));
        return signList;
    }

    public static double expr(BufferSign bufferSign) {
        Sign sign = bufferSign.next();
        if (sign.signType == SignType.END_LINE) {
            return 0;
        } else {
            bufferSign.back();
            return plusMinus(bufferSign);
        }
    }
    public static double delMult(BufferSign bufferSign) {
        double val = factor(bufferSign);
        while (true) {
            Sign sign = bufferSign.next();
            switch (sign.signType) {
                case MULT :
                    val *= factor(bufferSign);
                    break;
            case DEV :
                    val /= factor(bufferSign);
                    break;
                default :
                    bufferSign.back();
                    return val;
                }
        }
    }
    public static double plusMinus(BufferSign bufferSign) {
        double val = (double) delMult(bufferSign);
        while (true) {
            Sign sign = bufferSign.next();
            switch (sign.signType) {
                case PLUS:
                    val += delMult(bufferSign);
                    break;
                case MINUS :
                    val -= delMult(bufferSign);
                    break;
                case END_LINE:
                case RIGHT_BRACKET :
                    bufferSign.back();
                    return val;
                default : throw new RuntimeException("Метод: plusMinus. Неверный символ " + sign.value + " на позиции " + bufferSign.getPos());
            }
        }
    }
    public static double factor(BufferSign bufferSign) {
        Sign sign = bufferSign.next();
        switch (sign.signType) {
            case NUMBER :
                return Integer.parseInt(sign.value);
            case LEFT_BRACKET:
                double val = plusMinus(bufferSign);
                sign = bufferSign.next();
                if (sign.signType != SignType.RIGHT_BRACKET) {
                    throw new RuntimeException("Метод: factor1. Неверный символ " + sign.value + " на позиции " + bufferSign.getPos());
            }
                return val;
            default : throw new RuntimeException(" Метод: factor2. Неверный символ " + sign.value + " на позиции " + bufferSign.getPos());
        }
    }
}
