import java.util.ArrayList;

public class Iris {
    public ArrayList<Double> attrs;
    public String name;
    public Iris(String[] s){
        attrs = new ArrayList<>();
        for (int i = 0; i < s.length - 1; i++) {
            attrs.add(Double.parseDouble(s[i].replace(',', '.').strip()));
        }
        name = s[s.length - 1].strip();
    }
}
