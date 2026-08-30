class Solution {
    public String lexPalindromicPermutation(String s, String target) {
        int n = s.length();
        int[] freq = new int[26];

        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int odd = 0;
        char middle = 0;

        for (int i = 0; i < 26; i++) {
            if (freq[i] % 2 != 0) {
                odd++;
                middle = (char) ('a' + i);
            }
        }

        if (odd > 1) {
            return "";
        }

        int halfLen = n / 2;
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = freq[i] / 2;
        }

        char[] first = new char[halfLen];
        int i = 0;

        while (i < halfLen) {
            int c = target.charAt(i) - 'a';

            if (half[c] == 0) {
                break;
            }

            first[i] = target.charAt(i);
            half[c]--;
            i++;
        }

        if (i == halfLen) {
            String candidate = build(first, middle);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        if (i < halfLen) {
            int x = target.charAt(i) - 'a';

            for (int c = x + 1; c < 26; c++) {
                if (half[c] > 0) {
                    first[i] = (char) ('a' + c);
                    half[c]--;

                    fill(first, i + 1, half);

                    return build(first, middle);
                }
            }
        }

        for (int j = i - 1; j >= 0; j--) {
            int original = first[j] - 'a';
            half[original]++;

            int x = target.charAt(j) - 'a';

            for (int c = x + 1; c < 26; c++) {
                if (half[c] > 0) {
                    first[j] = (char) ('a' + c);
                    half[c]--;

                    fill(first, j + 1, half);

                    return build(first, middle);
                }
            }
        }

        return "";
    }

    private void fill(char[] first, int start, int[] half) {
        int pos = start;

        for (int c = 0; c < 26; c++) {
            while (half[c] > 0) {
                first[pos++] = (char) ('a' + c);
                half[c]--;
            }
        }
    }

    private String build(char[] first, char middle) {
        int n = first.length * 2 + (middle != 0 ? 1 : 0);
        char[] result = new char[n];

        for (int i = 0; i < first.length; i++) {
            result[i] = first[i];
        }

        int pos = first.length;

        if (middle != 0) {
            result[pos++] = middle;
        }

        for (int i = first.length - 1; i >= 0; i--) {
            result[pos++] = first[i];
        }

        return new String(result);
    }
}








    



  












    






     
























 
  
  



      


              



            


    

   




 

     





                
            
        

       
    

    
    

     
           
              
                
            
        
    

   
      
       

        
        
        

      

      
        
       

       
       
        

    

