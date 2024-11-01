package ru.vladuss.serviceorientedproject.testDel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainForLeetCode {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        String s1;
        s1 = bufferedReader.readLine();
        System.out.println(s1);
        
    }
}

//class SolutionLeetCode_AddDigits_258 {
//    public int addDigits(int num) {
//        if (num == 0) {
//            return num;
//        } else if (num % 9 == 0) {
//            return 9;
//        } else {
//            return num % 9;
//        }
//    }
//}

//class SolutionLeetCode_ContaincsDuplicate_217 {
//    public boolean containsDuplicate(int[] nums) {
//
//        int[] newNums = new int[]{nums.length};
//        newNums = Arrays.stream(nums).distinct().toArray();
//        if (nums.length == newNums.length) {
//            return false;
//        } else {
//            return true;
//        }
//
//    }
//}

//class Solution {
//    public ListNode deleteDuplicates(ListNode head) {
//        if (head == null || head.next == null) {
//            return head;
//        }
//
//        ListNode listNode = head;
//
//        while (listNode.next != null) {
//            if (listNode.val == listNode.next.val) {
//                listNode.next = listNode.next.next;
//            } else {
//                listNode = listNode.next;
//            }
//        }
//
//        return head;
//    }
//}


//class SolutionLeetCode_ReverseLinkedList_206 {
//    public ListNode reverseList(ListNode head) {
//        ListNode output = recursive(head);
//
//        return output;
//    }
//
//    static ListNode recursive(ListNode head) {
//        if (head == null || head.next == null) {
//            return head;
//        }
//
//        ListNode newHead = recursive(head.next);
//        head.next.next = head;
//        head.next = null;
//        return newHead;
//    }
//}
//
//
class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}


//class Solution {
//    public int reverse(int x) {
//        if (x == 0) {
//            return 0;
//        }
//
//        String s = String.valueOf(x);
//        StringBuilder newS = new StringBuilder(s);
//        int n = newS.length();
//
//        if (s.charAt(0) == '-') {
//            newS.deleteCharAt(0);
//            newS.reverse();
//            newS.insert(0, '-');
//        } else if (newS.charAt(n-1) == '0') {
//            newS.reverse();
//            for (int i = 1; i < newS.length()-1; i++) {
//                if (newS.charAt(i-1) == '0') {
//                    newS.deleteCharAt(i-1);
//                    if (newS.charAt(i) == '0') {
//                        break;
//                    }
//                }
//            }
//        } else {
//            newS.reverse();
//        }
//
//        long output = Long.parseLong(String.valueOf(newS));;
//
//        if (output > Integer.MAX_VALUE || output < Integer.MIN_VALUE) {
//            return 0;
//        }
//
//        return (int) output;
//    }
//}