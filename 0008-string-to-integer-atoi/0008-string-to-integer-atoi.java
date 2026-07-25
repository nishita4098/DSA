class Solution {

    public int myAtoi(String s) {

        int i = 0;
        int n = s.length();

        // Step 1: Skip leading spaces
        while (i < n && s.charAt(i) == ' ') {
            i++;
        }

        // If string is empty after spaces
        if (i == n) {
            return 0;
        }

        // Step 2: Check sign
        int sign = 1;

        if (s.charAt(i) == '-') {
            sign = -1;
            i++;
        } else if (s.charAt(i) == '+') {
            i++;
        }

        int result = 0;

        // Step 3: Read digits
        while (i < n && Character.isDigit(s.charAt(i))) {

            int digit = s.charAt(i) - '0';

            // Step 4: Check overflow
            if (result > Integer.MAX_VALUE / 10 ||
               (result == Integer.MAX_VALUE / 10 &&
                digit > Integer.MAX_VALUE % 10)) {

                return sign == 1 ?
                        Integer.MAX_VALUE :
                        Integer.MIN_VALUE;
            }

            result = result * 10 + digit;
            i++;
        }

        return sign * result;
    }
}