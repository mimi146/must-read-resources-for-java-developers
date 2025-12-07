/**
 * Common Data Structures and Algorithms Patterns
 * Essential patterns for problem-solving and coding interviews
 */

import java.util.*;

public class DSAPatterns {
    
    // ============= PATTERN 1: Two Pointers =============
    static class TwoPointers {
        // Example: Remove duplicates from sorted array
        public static int removeDuplicates(int[] nums) {
            if (nums.length == 0) return 0;
            
            int i = 0;
            for (int j = 1; j < nums.length; j++) {
                if (nums[j] != nums[i]) {
                    i++;
                    nums[i] = nums[j];
                }
            }
            return i + 1;
        }
        
        // Example: Two sum in sorted array
        public static int[] twoSumSorted(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    return new int[]{left, right};
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
            return new int[]{-1, -1};
        }
        
        // Example: Check if string is palindrome
        public static boolean isPalindrome(String s) {
            int left = 0, right = s.length() - 1;
            
            while (left < right) {
                if (s.charAt(left) != s.charAt(right)) {
                    return false;
                }
                left++;
                right--;
            }
            return true;
        }
    }
    
    // ============= PATTERN 2: Sliding Window =============
    static class SlidingWindow {
        // Example: Maximum sum subarray of size k
        public static int maxSumSubarray(int[] arr, int k) {
            int maxSum = 0, windowSum = 0;
            
            // Calculate sum of first window
            for (int i = 0; i < k; i++) {
                windowSum += arr[i];
            }
            maxSum = windowSum;
            
            // Slide the window
            for (int i = k; i < arr.length; i++) {
                windowSum = windowSum - arr[i - k] + arr[i];
                maxSum = Math.max(maxSum, windowSum);
            }
            
            return maxSum;
        }
        
        // Example: Longest substring without repeating characters
        public static int lengthOfLongestSubstring(String s) {
            Map<Character, Integer> map = new HashMap<>();
            int maxLen = 0, start = 0;
            
            for (int end = 0; end < s.length(); end++) {
                char c = s.charAt(end);
                if (map.containsKey(c)) {
                    start = Math.max(start, map.get(c) + 1);
                }
                map.put(c, end);
                maxLen = Math.max(maxLen, end - start + 1);
            }
            
            return maxLen;
        }
    }
    
    // ============= PATTERN 3: Fast & Slow Pointers =============
    static class FastSlowPointers {
        static class ListNode {
            int val;
            ListNode next;
            
            ListNode(int val) {
                this.val = val;
            }
        }
        
        // Example: Detect cycle in linked list
        public static boolean hasCycle(ListNode head) {
            if (head == null) return false;
            
            ListNode slow = head, fast = head;
            
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
                
                if (slow == fast) {
                    return true;
                }
            }
            
            return false;
        }
        
        // Example: Find middle of linked list
        public static ListNode findMiddle(ListNode head) {
            ListNode slow = head, fast = head;
            
            while (fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            
            return slow;
        }
    }
    
    // ============= PATTERN 4: Merge Intervals =============
    static class MergeIntervals {
        static class Interval {
            int start, end;
            
            Interval(int start, int end) {
                this.start = start;
                this.end = end;
            }
            
            @Override
            public String toString() {
                return "[" + start + "," + end + "]";
            }
        }
        
        // Example: Merge overlapping intervals
        public static List<Interval> merge(List<Interval> intervals) {
            if (intervals.isEmpty()) return intervals;
            
            // Sort by start time
            intervals.sort((a, b) -> Integer.compare(a.start, b.start));
            
            List<Interval> merged = new ArrayList<>();
            Interval current = intervals.get(0);
            
            for (int i = 1; i < intervals.size(); i++) {
                Interval next = intervals.get(i);
                
                if (current.end >= next.start) {
                    // Overlapping intervals, merge them
                    current.end = Math.max(current.end, next.end);
                } else {
                    // No overlap, add current and move to next
                    merged.add(current);
                    current = next;
                }
            }
            merged.add(current);
            
            return merged;
        }
    }
    
    // ============= PATTERN 5: Cyclic Sort =============
    static class CyclicSort {
        // Example: Find missing number in array [0, n]
        public static int findMissingNumber(int[] nums) {
            int i = 0;
            while (i < nums.length) {
                if (nums[i] < nums.length && nums[i] != i) {
                    // Swap to correct position
                    int temp = nums[nums[i]];
                    nums[nums[i]] = nums[i];
                    nums[i] = temp;
                } else {
                    i++;
                }
            }
            
            // Find the missing number
            for (i = 0; i < nums.length; i++) {
                if (nums[i] != i) {
                    return i;
                }
            }
            
            return nums.length;
        }
    }
    
    // ============= PATTERN 6: In-place Reversal of LinkedList =============
    static class LinkedListReversal {
        static class ListNode {
            int val;
            ListNode next;
            
            ListNode(int val) {
                this.val = val;
            }
        }
        
        // Example: Reverse a linked list
        public static ListNode reverse(ListNode head) {
            ListNode prev = null, current = head;
            
            while (current != null) {
                ListNode next = current.next;
                current.next = prev;
                prev = current;
                current = next;
            }
            
            return prev;
        }
        
        // Example: Reverse sublist from position m to n
        public static ListNode reverseBetween(ListNode head, int m, int n) {
            if (head == null) return null;
            
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode prev = dummy;
            
            // Move to position m-1
            for (int i = 0; i < m - 1; i++) {
                prev = prev.next;
            }
            
            // Reverse from m to n
            ListNode current = prev.next;
            for (int i = 0; i < n - m; i++) {
                ListNode next = current.next;
                current.next = next.next;
                next.next = prev.next;
                prev.next = next;
            }
            
            return dummy.next;
        }
    }
    
    // ============= PATTERN 7: Tree BFS (Breadth-First Search) =============
    static class TreeBFS {
        static class TreeNode {
            int val;
            TreeNode left, right;
            
            TreeNode(int val) {
                this.val = val;
            }
        }
        
        // Example: Level order traversal
        public static List<List<Integer>> levelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) return result;
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                List<Integer> currentLevel = new ArrayList<>();
                
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    currentLevel.add(node.val);
                    
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
                
                result.add(currentLevel);
            }
            
            return result;
        }
        
        // Example: Zigzag level order traversal
        public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            if (root == null) return result;
            
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);
            boolean leftToRight = true;
            
            while (!queue.isEmpty()) {
                int levelSize = queue.size();
                LinkedList<Integer> currentLevel = new LinkedList<>();
                
                for (int i = 0; i < levelSize; i++) {
                    TreeNode node = queue.poll();
                    
                    if (leftToRight) {
                        currentLevel.addLast(node.val);
                    } else {
                        currentLevel.addFirst(node.val);
                    }
                    
                    if (node.left != null) queue.offer(node.left);
                    if (node.right != null) queue.offer(node.right);
                }
                
                result.add(currentLevel);
                leftToRight = !leftToRight;
            }
            
            return result;
        }
    }
    
    // ============= PATTERN 8: Tree DFS (Depth-First Search) =============
    static class TreeDFS {
        static class TreeNode {
            int val;
            TreeNode left, right;
            
            TreeNode(int val) {
                this.val = val;
            }
        }
        
        // Example: Find all paths from root to leaf
        public static List<List<Integer>> findPaths(TreeNode root) {
            List<List<Integer>> result = new ArrayList<>();
            dfs(root, new ArrayList<>(), result);
            return result;
        }
        
        private static void dfs(TreeNode node, List<Integer> path, List<List<Integer>> result) {
            if (node == null) return;
            
            path.add(node.val);
            
            // If leaf node, add path to result
            if (node.left == null && node.right == null) {
                result.add(new ArrayList<>(path));
            } else {
                dfs(node.left, path, result);
                dfs(node.right, path, result);
            }
            
            path.remove(path.size() - 1); // Backtrack
        }
        
        // Example: Path sum - check if path exists with given sum
        public static boolean hasPathSum(TreeNode root, int sum) {
            if (root == null) return false;
            
            if (root.left == null && root.right == null) {
                return sum == root.val;
            }
            
            return hasPathSum(root.left, sum - root.val) || 
                   hasPathSum(root.right, sum - root.val);
        }
    }
    
    // ============= PATTERN 9: Top K Elements =============
    static class TopKElements {
        // Example: Find K largest elements
        public static List<Integer> findKLargest(int[] nums, int k) {
            PriorityQueue<Integer> minHeap = new PriorityQueue<>();
            
            for (int num : nums) {
                minHeap.offer(num);
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }
            
            return new ArrayList<>(minHeap);
        }
        
        // Example: K most frequent elements
        public static List<Integer> topKFrequent(int[] nums, int k) {
            Map<Integer, Integer> freqMap = new HashMap<>();
            for (int num : nums) {
                freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
            }
            
            PriorityQueue<Map.Entry<Integer, Integer>> minHeap = 
                new PriorityQueue<>((a, b) -> a.getValue() - b.getValue());
            
            for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
                minHeap.offer(entry);
                if (minHeap.size() > k) {
                    minHeap.poll();
                }
            }
            
            List<Integer> result = new ArrayList<>();
            while (!minHeap.isEmpty()) {
                result.add(minHeap.poll().getKey());
            }
            
            return result;
        }
    }
    
    // ============= PATTERN 10: Modified Binary Search =============
    static class ModifiedBinarySearch {
        // Example: Search in rotated sorted array
        public static int searchRotated(int[] nums, int target) {
            int left = 0, right = nums.length - 1;
            
            while (left <= right) {
                int mid = left + (right - left) / 2;
                
                if (nums[mid] == target) {
                    return mid;
                }
                
                // Left half is sorted
                if (nums[left] <= nums[mid]) {
                    if (target >= nums[left] && target < nums[mid]) {
                        right = mid - 1;
                    } else {
                        left = mid + 1;
                    }
                }
                // Right half is sorted
                else {
                    if (target > nums[mid] && target <= nums[right]) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
            
            return -1;
        }
        
        // Example: Find peak element
        public static int findPeakElement(int[] nums) {
            int left = 0, right = nums.length - 1;
            
            while (left < right) {
                int mid = left + (right - left) / 2;
                
                if (nums[mid] > nums[mid + 1]) {
                    right = mid;
                } else {
                    left = mid + 1;
                }
            }
            
            return left;
        }
    }
    
    // ============= PATTERN 11: Backtracking =============
    static class Backtracking {
        // Example: Generate all permutations
        public static List<List<Integer>> permute(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            backtrack(nums, new ArrayList<>(), result, new boolean[nums.length]);
            return result;
        }
        
        private static void backtrack(int[] nums, List<Integer> current, 
                                     List<List<Integer>> result, boolean[] used) {
            if (current.size() == nums.length) {
                result.add(new ArrayList<>(current));
                return;
            }
            
            for (int i = 0; i < nums.length; i++) {
                if (used[i]) continue;
                
                current.add(nums[i]);
                used[i] = true;
                backtrack(nums, current, result, used);
                current.remove(current.size() - 1);
                used[i] = false;
            }
        }
        
        // Example: Generate all subsets
        public static List<List<Integer>> subsets(int[] nums) {
            List<List<Integer>> result = new ArrayList<>();
            generateSubsets(nums, 0, new ArrayList<>(), result);
            return result;
        }
        
        private static void generateSubsets(int[] nums, int index, 
                                          List<Integer> current, List<List<Integer>> result) {
            result.add(new ArrayList<>(current));
            
            for (int i = index; i < nums.length; i++) {
                current.add(nums[i]);
                generateSubsets(nums, i + 1, current, result);
                current.remove(current.size() - 1);
            }
        }
    }
    
    // ============= PATTERN 12: Dynamic Programming =============
    static class DynamicProgramming {
        // Example: Fibonacci with memoization
        public static int fibonacci(int n) {
            Map<Integer, Integer> memo = new HashMap<>();
            return fibHelper(n, memo);
        }
        
        private static int fibHelper(int n, Map<Integer, Integer> memo) {
            if (n <= 1) return n;
            if (memo.containsKey(n)) return memo.get(n);
            
            int result = fibHelper(n - 1, memo) + fibHelper(n - 2, memo);
            memo.put(n, result);
            return result;
        }
        
        // Example: Longest common subsequence
        public static int longestCommonSubsequence(String text1, String text2) {
            int m = text1.length(), n = text2.length();
            int[][] dp = new int[m + 1][n + 1];
            
            for (int i = 1; i <= m; i++) {
                for (int j = 1; j <= n; j++) {
                    if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                        dp[i][j] = dp[i - 1][j - 1] + 1;
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
            
            return dp[m][n];
        }
        
        // Example: 0/1 Knapsack
        public static int knapsack(int[] weights, int[] values, int capacity) {
            int n = weights.length;
            int[][] dp = new int[n + 1][capacity + 1];
            
            for (int i = 1; i <= n; i++) {
                for (int w = 1; w <= capacity; w++) {
                    if (weights[i - 1] <= w) {
                        dp[i][w] = Math.max(
                            dp[i - 1][w],
                            values[i - 1] + dp[i - 1][w - weights[i - 1]]
                        );
                    } else {
                        dp[i][w] = dp[i - 1][w];
                    }
                }
            }
            
            return dp[n][capacity];
        }
    }
    
    // ============= DEMONSTRATION =============
    public static void main(String[] args) {
        System.out.println("=== DSA Patterns ===\n");
        
        // Pattern 1: Two Pointers
        System.out.println("1. Two Pointers Pattern:");
        int[] sorted = {1, 2, 3, 4, 6};
        int[] result = TwoPointers.twoSumSorted(sorted, 6);
        System.out.println("   Two Sum: indices " + result[0] + ", " + result[1]);
        System.out.println("   Is 'racecar' palindrome? " + TwoPointers.isPalindrome("racecar"));
        
        // Pattern 2: Sliding Window
        System.out.println("\n2. Sliding Window Pattern:");
        int[] arr = {2, 1, 5, 1, 3, 2};
        System.out.println("   Max sum of subarray (k=3): " + SlidingWindow.maxSumSubarray(arr, 3));
        System.out.println("   Longest substring without repeating: " + 
                         SlidingWindow.lengthOfLongestSubstring("abcabcbb"));
        
        // Pattern 4: Merge Intervals
        System.out.println("\n4. Merge Intervals Pattern:");
        List<MergeIntervals.Interval> intervals = new ArrayList<>();
        intervals.add(new MergeIntervals.Interval(1, 3));
        intervals.add(new MergeIntervals.Interval(2, 6));
        intervals.add(new MergeIntervals.Interval(8, 10));
        intervals.add(new MergeIntervals.Interval(15, 18));
        System.out.println("   Merged intervals: " + MergeIntervals.merge(intervals));
        
        // Pattern 5: Cyclic Sort
        System.out.println("\n5. Cyclic Sort Pattern:");
        int[] nums = {3, 0, 1};
        System.out.println("   Missing number: " + CyclicSort.findMissingNumber(nums));
        
        // Pattern 9: Top K Elements
        System.out.println("\n9. Top K Elements Pattern:");
        int[] numbers = {3, 1, 5, 12, 2, 11};
        System.out.println("   Top 3 largest: " + TopKElements.findKLargest(numbers, 3));
        
        // Pattern 10: Modified Binary Search
        System.out.println("\n10. Modified Binary Search Pattern:");
        int[] rotated = {4, 5, 6, 7, 0, 1, 2};
        System.out.println("   Search 0 in rotated array: index " + 
                         ModifiedBinarySearch.searchRotated(rotated, 0));
        
        // Pattern 11: Backtracking
        System.out.println("\n11. Backtracking Pattern:");
        int[] permuteNums = {1, 2, 3};
        System.out.println("   Permutations of [1,2,3]: " + Backtracking.permute(permuteNums));
        System.out.println("   Subsets of [1,2,3]: " + Backtracking.subsets(permuteNums));
        
        // Pattern 12: Dynamic Programming
        System.out.println("\n12. Dynamic Programming Pattern:");
        System.out.println("   Fibonacci(10): " + DynamicProgramming.fibonacci(10));
        System.out.println("   LCS of 'abcde' and 'ace': " + 
                         DynamicProgramming.longestCommonSubsequence("abcde", "ace"));
        
        System.out.println("\n=== All Patterns Demonstrated ===");
    }
}
