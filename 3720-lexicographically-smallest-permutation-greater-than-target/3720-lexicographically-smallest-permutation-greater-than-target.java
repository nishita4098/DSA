class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int n = s.length();
        int[] cnt = new int[26];

        for (char c : s.toCharArray()) {
            cnt[c - 'a']++;
        }

        char[] ans = new char[n];
        int i = 0;

        // Match target as long as possible
        while (i < n) {
            int x = target.charAt(i) - 'a';

            if (cnt[x] == 0) {
                break;
            }

            ans[i] = target.charAt(i);
            cnt[x]--;
            i++;
        }

        // Try making current position greater
        if (i < n) {
            int x = target.charAt(i) - 'a';

            for (int c = x + 1; c < 26; c++) {
                if (cnt[c] > 0) {
                    ans[i] = (char) ('a' + c);
                    cnt[c]--;

                    fillRemaining(ans, i + 1, cnt);
                    return new String(ans);
                }
            }
        }

        // Backtrack to find the rightmost position
        // where we can place a larger character
        for (int j = i - 1; j >= 0; j--) {
            int x = target.charAt(j) - 'a';

            // Restore character at position j
            cnt[x]++;

            for (int c = x + 1; c < 26; c++) {
                if (cnt[c] > 0) {
                    ans[j] = (char) ('a' + c);
                    cnt[c]--;

                    fillRemaining(ans, j + 1, cnt);
                    return new String(ans);
                }
            }
        }

        return "";
    }

    private void fillRemaining(char[] ans, int start, int[] cnt) {
        int pos = start;

        for (int c = 0; c < 26; c++) {
            while (cnt[c] > 0) {
                ans[pos++] = (char) ('a' + c);
                cnt[c]--;
            }
        }
    }
}