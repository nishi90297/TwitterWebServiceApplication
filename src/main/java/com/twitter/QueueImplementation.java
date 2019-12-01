//
//import java.util.*;
//public class QueueImplementation {
//    public static class MyQueue<T> {
//        Stack<T> stackNewestOnTop = new Stack<T>();
//        Stack<T> stackOldestOnTop = new Stack<T>();
//
//        public void enqueue(T value) { // Push onto newest stack
//            stackOldestOnTop.push(value);
//        }
//
//        public T peek() {
//        	int old_size=stackOldestOnTop.size();
//            for(int i=0;i<old_size;i++) {
//                stackNewestOnTop.push(stackOldestOnTop.pop());
//            }
//            T x= stackNewestOnTop.lastElement();
//            
//            int new_size=stackNewestOnTop.size();
//            for(int i=0;i<new_size;i++) {
//                stackOldestOnTop.push(stackNewestOnTop.pop());
//            }
//            return x;
//        }
//
//        public T dequeue() {
//            
//        	int old_size=stackOldestOnTop.size();
//            for(int i=0;i<old_size;i++) {
//                stackNewestOnTop.push(stackOldestOnTop.pop());
//            }
//            T x= stackNewestOnTop.pop();
//            
//            int new_size=stackNewestOnTop.size();
//            for(int i=0;i<new_size;i++) {
//                stackOldestOnTop.push(stackNewestOnTop.pop());
//            }
//            return x;
//        }
//    }
//
//    
//    public static void main(String[] args) {
//        MyQueue<Integer> queue = new MyQueue<Integer>();
//        
//        Scanner scan = new Scanner(System.in);
//        int n = scan.nextInt();
//        
//        for (int i = 0; i < n; i++) {
//            int operation = scan.nextInt();
//            if (operation == 1) { // enqueue
//                queue.enqueue(scan.nextInt());
//            } else if (operation == 2) { // dequeue
//                queue.dequeue();
//            } else if (operation == 3) { // print/peek
//                System.out.println(queue.peek());
//            }
//        }
//        scan.close();
//    }
//}
//
