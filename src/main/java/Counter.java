import huffman.Coder;
import org.apache.commons.math3.util.Precision;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Counter {
    private TreeMap<Character, Integer> sym = new TreeMap<>();
    private LinkedHashMap<Character, Double> symProb = new LinkedHashMap<>();
    private LinkedHashMap<Character, String> symCodes = new LinkedHashMap<>();
    private LinkedHashMap<Character, Integer> symCodesLenght = new LinkedHashMap<>();
    private LinkedHashMap<Character, Double> symPQ = new LinkedHashMap<>();
    private LinkedHashMap<Character, Double> symPlogP = new LinkedHashMap<>();
    private LinkedHashMap<Character, Integer> huffCodesLenght = new LinkedHashMap<>();
    private LinkedHashMap<Character, Double> huffPQ = new LinkedHashMap<>();

    private Coder huffman;
    private int symNum = 0;
    private String text;

    public Counter(String text) {
        this.text = text.toLowerCase();
        count();
        code();
        calculate();
        huffman = new Coder(sym);
    }

    private void count() {
        Pattern p = Pattern.compile("[a-zа-я ]");
        Matcher m = p.matcher(text);
        while (m.find()) {
            symNum++;
            char c = m.group().charAt(0);
            if (c == 'ъ') c = 'ь';
            if (c == 'ё') c = 'е';
            if (sym.containsKey(c)) {
                int count = sym.get(c);
                count++;
                sym.put(c, count);
            } else sym.put(c, 1);
        }

        sym.forEach((k, v) -> {
            double t = v / (double) symNum;
            symProb.put(k, Precision.round(t, 4));
        });
        symProb = symProb
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
    }

    private void code() {
        System.out.println("\nНачало кодирования:");
        Object[] keys = symProb.keySet().toArray();
        for (int i = 0; i < keys.length; i++) {
            StringBuilder output = new StringBuilder();
            searchTree(output, 0, keys.length - 1, i, keys);
            symCodes.put((Character) keys[i], output.toString());
        }
    }

    private void searchTree(StringBuilder output, int pos1, int pos2, int target, Object[] keys) {
        if (pos1 != pos2) {
            int center = findCenter(pos1, pos2, keys);
            System.out.println("Центральная буква - " + (char)keys[center]);
            if (target > center) {
                output.append('0');
                searchTree(output, center + 1, pos2, target, keys);
            } else {
                output.append('1');
                searchTree(output, pos1, center, target, keys);
            }
        } else System.out.println("\nЗакодирован символ - " + (char)keys[pos1] + "\n");
    }

    private int findCenter(int pos1, int pos2, Object[] keys) {
        LinkedHashMap<Integer, Double> diff = new LinkedHashMap<>();

        /*double sum = 0;
        for (int i = pos1; i <= pos2; i++){
            sum += symProb.get(keys[i]);
        }

        double subSum = 0;
        for (int i = pos1; i <= pos2; i++){
            subSum += symProb.get(keys[i]);
            if (subSum > sum/2.0 && i != pos1) return i-1;
        }
         */
        double sum1 = 0;
        for (int i = pos1; i < pos2; i++){
            sum1 += symProb.get(keys[i]);
            double sum2 = 0;
            for (int k = i+1; k <= pos2; k++){
                sum2 += symProb.get(keys[k]);
                diff.put(i, Math.abs(sum1-sum2));
            }
        }

        int max = pos1;

        for (int key : diff.keySet()){
            if (diff.get(key) < diff.get(max)) max = key;
        }

        return max;
    }

    public Coder getHuffmanCoder(){
        return huffman;
    }

    private void calculate(){
        symCodes.forEach( (k,v) -> symCodesLenght.put(k, v.length()));
        symProb.forEach( (k,v) -> symPQ.put(k, v*symCodesLenght.get(k)));
        symProb.forEach( (k,v) -> symPlogP.put(k, -v*(Math.log10(v)/Math.log10(2))));
    }

    public Map<Character, Double> getSymProb() {
        return symProb;
    }

    public Map<Character, String> getSymCodes() {
        return symCodes;
    }

    public Map<Character, Integer> getSym() {
        return sym;
    }

    public Map<Character, Integer> getSymCodesLenght() {
        return symCodesLenght;
    }

    public Map<Character, Double> getSymPQ() {
        return symPQ;
    }

    public Map<Character, Double> getSymPlogP() {
        return symPlogP;
    }

    public int getSymNum() {
        return symNum;
    }

    public double entropy() {
        double ent = 0;
        for (char key : symPlogP.keySet()) {
            ent += symPlogP.get(key);
        }
        return ent;
    }

    public double averageLenght(){
        double sum = 0;
        for (char key : symPQ.keySet()){
            sum += symPQ.get(key);
        }
        return sum;
    }

    public double getHuffmanInfo() {
        var a = huffman.getCodes();
        System.out.println(a);
        a.forEach( (k,v) -> huffCodesLenght.put(k, v.length()));
        symProb.forEach( (k,v) -> huffPQ.put(k, v*huffCodesLenght.get(k)));
        double sum = 0;
        for (char key : huffPQ.keySet()){
            sum += huffPQ.get(key);
        }
        return sum;
    }
}
