/**
 * Code examples for Algorithms and Data Structure section
 * Demonstrates Big O complexity concepts with practical examples
 */
public class AlgorithmsDataStructure {
    
    // O(1) - Constant Time: Accessing array element
    public static int constantTime(int[] arr, int index) {
        return arr[index];
    }
    
    // O(n) - Linear Time: Finding element in array
    public static boolean linearSearch(int[] arr, int target) {
        for (int num : arr) {
            if (num == target) {
                return true;
            }
        }
        return false;
    }
    
    // O(log n) - Logarithmic Time: Binary search
    public static int binarySearch(int[] sortedArr, int target) {
        int left = 0;
        int right = sortedArr.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            if (sortedArr[mid] == target) {
                return mid;
            } else if (sortedArr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    
    // O(n^2) - Quadratic Time: Bubble sort
    public static void bubbleSort(int[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }
    
    // Example: Stack implementation
    static class Stack<T> {
        private java.util.ArrayList<T> items;
        
        public Stack() {
            items = new java.util.ArrayList<>();
        }
        
        public void push(T item) {
            items.add(item);
        }
        
        public T pop() {
            if (isEmpty()) {
                throw new java.util.EmptyStackException();
            }
            return items.remove(items.size() - 1);
        }
        
        public boolean isEmpty() {
            return items.isEmpty();
        }
    }
    
    public static void main(String[] args) {
        // Test constant time access
        int[] arr = {10, 20, 30, 40, 50};
        System.out.println("Element at index 2: " + constantTime(arr, 2));
        
        // Test linear search
        System.out.println("Found 30? " + linearSearch(arr, 30));
        
        // Test binary search
        System.out.println("Binary search for 40: index " + binarySearch(arr, 40));
        
        // Test bubble sort
        int[] unsorted = {64, 34, 25, 12, 22, 11, 90};
        bubbleSort(unsorted);
        System.out.print("Sorted array: ");
        for (int num : unsorted) {
            System.out.print(num + " ");
        }
        System.out.println();
        
        // Test stack
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        System.out.println("Popped: " + stack.pop());
    }
}
