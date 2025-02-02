import java.util.* ;

/*
 * Objective: Implement a function to check if a linked list has a cycle. 
Details: Create a LinkedList class with a Node inner class. Use the two-pointer technique (Floyd’s Tortoise and Hare) to detect a cycle.
    • Functions to Implement:
        ◦ boolean hasCycle(Node head): Returns true if there's a cycle in the linked list.
 */

class ListNode{
    int val;
    ListNode next;

    public ListNode(int val, ListNode next){
        this.val = val;
        this.next = next;
    }

    public boolean hasCycle(ListNode head){
        ListNode tortoise = head, hare = head;

        //iterating both pointers
        while(tortoise != null || hare != null){
            if(tortoise != null && tortoise.next != null) tortoise = tortoise.next;
            else tortoise = null;

            if(hare != null && hare.next != null && hare.next.next != null) hare = hare.next.next;
            else hare = null;

            if(tortoise != null && tortoise == hare) return true;
        }

        return false;
    }
}

public class task6 {


    public static void main(String[] args) {
        ListNode n5 = new ListNode(5, null);
        ListNode n4 = new ListNode(4, n5);
        ListNode n3 = new ListNode(3, n4);
        ListNode n2 = new ListNode(2, n3);
        ListNode head = new ListNode(1, n2);

        //adding cycle
        n5.next = head;

        if(head.hasCycle(head)) System.out.println("Cycle detected");
        else System.out.println("no cycle detected");
        
    }
    
}
