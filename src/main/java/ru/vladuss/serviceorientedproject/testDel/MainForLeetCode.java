package ru.vladuss.serviceorientedproject.testDel;

public class MainForLeetCode {
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.reverse(123));
        System.out.println(solution.reverse(-123));
        System.out.println(solution.reverse(120));
        System.out.println(solution.reverse(901000));
        System.out.println(solution.reverse(10));
        System.out.println(solution.reverse(1534236469));
        System.out.println(solution.reverse(-2147483648));

    }
}

class Solution {
    public int reverse(int x) {
        if (x == 0) {
            return 0;
        }

        String s = String.valueOf(x);
        StringBuilder newS = new StringBuilder(s);
        int n = newS.length();

        if (s.charAt(0) == '-') {
            newS.deleteCharAt(0);
            newS.reverse();
            newS.insert(0, '-');
        } else if (newS.charAt(n-1) == '0') {
            newS.reverse();
            for (int i = 1; i < newS.length()-1; i++) {
                if (newS.charAt(i-1) == '0') {
                    newS.deleteCharAt(i-1);
                    if (newS.charAt(i) == '0') {
                        break;
                    }
                }
            }
        } else {
            newS.reverse();
        }

        long output = Long.parseLong(String.valueOf(newS));;

        if (output > Integer.MAX_VALUE || output < Integer.MIN_VALUE) {
            return 0;
        }

        return (int) output;
    }
}