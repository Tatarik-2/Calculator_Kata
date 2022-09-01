import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static {
        Tools.inputRomeNumbersHashMap();//получаем все римские симоволы
    }

    public static String calc(String input) {
        input = input.replaceAll("\\s", "");//удаляем все пробелы, если таковые были (хоть в ТЗ и не было)
        Tools.checkInputData(Tools.REGEXFORROMAN, input);//проверяем входные данные, к примеру, на наличие
        // римских цифр, т.к. по моим регуляркам, если проверка на Римские дадут тру, то и арабские тру

        Pattern pattern = Pattern.compile(Tools.REGEXFORROMAN);
        Matcher matcher = pattern.matcher(input);

        String firstSymbol = null,
                operationSymbol = null,
                secondSymbol = null;

        int firstNumber = 0,
                secondNumber = 0;
        while (matcher.find()) {
            firstSymbol = matcher.group(1);
            operationSymbol = matcher.group(2);
            secondSymbol = matcher.group(3);
        }

        boolean romeNumbersTrue = Tools.checkRomeNumbers(firstSymbol, secondSymbol);
        if (romeNumbersTrue) {
            Tools.reversToRomeNumbers();
            firstNumber = Tools.getValueOfRomeNumber(firstSymbol);
            secondNumber = Tools.getValueOfRomeNumber(secondSymbol);
        } else {
            boolean arabianNumbersTrue = Tools.checkInputData(Tools.REGEXFORARABIAN, input);//проверяем условия по арабским цифрам
            if (arabianNumbersTrue) {
                firstNumber = Integer.parseInt(firstSymbol);
                secondNumber = Integer.parseInt(secondSymbol);
            }
        }

        char operation = operationSymbol.charAt(0);
        int result = Tools.getResult(firstNumber, operation, secondNumber);

        if (romeNumbersTrue) {
            return Tools.getResultForRomain(result);
        } else {
            return String.valueOf(result);
        }
    }
}

class Tools {
    final static String REGEXFORROMAN = "(\\w{1,4})(\\D)(\\w{1,4})";
    final static String REGEXFORARABIAN = "(10|[1-9])(\\D)(10|[1-9])";

    private static final Map<String, Integer> romeNumbers = new HashMap<>();

    public static void inputRomeNumbersHashMap() {
        romeNumbers.put("I", 1);
        romeNumbers.put("II", 2);
        romeNumbers.put("III", 3);
        romeNumbers.put("IV", 4);
        romeNumbers.put("V", 5);
        romeNumbers.put("VI", 6);
        romeNumbers.put("VII", 7);
        romeNumbers.put("VIII", 8);
        romeNumbers.put("IX", 9);
        romeNumbers.put("X", 10);
    }

    private static Map<Integer, String> reversRomeNumbers = new HashMap<>();

    public static void reversToRomeNumbers() {
        reversRomeNumbers.put(0, "");
        reversRomeNumbers.put(1, "I");
        reversRomeNumbers.put(2, "II");
        reversRomeNumbers.put(3, "III");
        reversRomeNumbers.put(4, "IV");
        reversRomeNumbers.put(5, "V");
        reversRomeNumbers.put(6, "VI");
        reversRomeNumbers.put(7, "VII");
        reversRomeNumbers.put(8, "VIII");
        reversRomeNumbers.put(9, "IX");
        reversRomeNumbers.put(10, "X");
        reversRomeNumbers.put(20, "XX");
        reversRomeNumbers.put(30, "XXX");
        reversRomeNumbers.put(40, "XL");
        reversRomeNumbers.put(50, "L");
        reversRomeNumbers.put(60, "LX");
        reversRomeNumbers.put(70, "LXX");
        reversRomeNumbers.put(80, "LXXX");
        reversRomeNumbers.put(90, "XC");
        reversRomeNumbers.put(100, "C");
    }

    public static int getValueOfRomeNumber(String s) {
        if (romeNumbers.containsKey(s)) {
            return romeNumbers.get(s);
        } else throw new RuntimeException("Некорректные данные");
    }

    public static boolean checkInputData(String regEx, String inputText) {
        boolean a = Pattern.matches(regEx, inputText);
        if (a) {
            return a;
        } else {
            throw new RuntimeException("Введены некорректные данные. Пожалуйста, попробуйте еще раз");
        }
    }

    public static boolean checkRomeNumbers(String s1, String s2) {
        boolean b1 = romeNumbers.containsKey(s1);
        boolean b2 = romeNumbers.containsKey(s2);
        return b1 && b2;
    }

    public static int getResult(int i1, char i2, int i3) {
        int result = 0;
        switch (i2) {
            case '+':
                result = i1 + i3;
                break;
            case '-':
                result = i1 - i3;
                break;
            case '*':
                result = i1 * i3;
                break;
            case '/':
                result = i1 / i3;
                break;
            default:
                throw new RuntimeException("Введен некорректный символ");
        }
        return result;
    }

    public static String getResultForRomain(int res) {

        if (res <= 0) {
            throw new RuntimeException("В римской системе нет отрицательных чисел");
        } else {
            int i1 = Math.abs(res / 10 * 10);//десятые для перевода результата в римские цифры
            int i2 = Math.abs(res % 10);//остаток
            return reversRomeNumbers.get(i1).concat(reversRomeNumbers.get(i2));
        }
    }
}

class Test {
    public static void main(String[] args) {
//        System.out.println(Main.calc("1+2"));
//        System.out.println(Main.calc("V+VII"));
//        System.out.println(Main.calc("10+2"));
//        System.out.println(Main.calc("1+V"));
//        System.out.println(Main.calc("V-VI"));
//        System.out.println(Main.calc("1+2+3"));
//        System.out.println(Main.calc("1+-2"));
//        System.out.println(Main.calc("X*X"));
//        System.out.println(Main.calc("1=2"));
//        System.out.println(Main.calc("10/0"));

        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        System.out.println(Main.calc(text));
    }
}
