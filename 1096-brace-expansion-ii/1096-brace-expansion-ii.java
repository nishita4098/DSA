import java.util.*;

class Solution {
    int index = 0;

    public List<String> braceExpansionII(String expression) {
        Set<String> result = solve(expression);
        List<String> ans = new ArrayList<>(result);

        Collections.sort(ans);
        return ans;
    }

    private Set<String> solve(String s) {
        Set<String> result = new HashSet<>();
        Set<String> current = new HashSet<>();
        current.add("");

        while (index < s.length() && s.charAt(index) != '}') {
            char ch = s.charAt(index);

            if (ch == ',') {
                result.addAll(current);
                current = new HashSet<>();
                current.add("");
                index++;
            } 
            else if (ch == '{') {
                index++;

                Set<String> nested = solve(s);

                Set<String> combined = new HashSet<>();

                for (String a : current) {
                    for (String b : nested) {
                        combined.add(a + b);
                    }
                }

                current = combined;
                index++; // Skip '}'
            } 
            else {
                String letter = String.valueOf(ch);

                Set<String> combined = new HashSet<>();

                for (String a : current) {
                    combined.add(a + letter);
                }

                current = combined;
                index++;
            }
        }

        result.addAll(current);
        return result;
    }
}