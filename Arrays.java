/*

1. Sort Colors(Dutch National Flag alg)
2. Maximum Sum Subarray of Size K (easy)
3. Single Number II
4. Majority Element N/2
5. Majority Element N/3
6. 287. Find the Duplicate Number
7. 239. Sliding Window Maximum
8. Find subarray with given sum | Set 1
9. Find subarray with given sum | Set 2 (Handles Negative Numbers)
10. 15. 3Sum
11. 42. Trapping Rain Water
12. 11. Container With Most Water
13. Minimum Number of Platforms Required for a Railway/Bus Station 
14. Reverse an array in groups of given size
15. 941. Valid Mountain Array
16. 16. 56. Merge Intervals
17. 986. Interval List Intersections
18. 238. Product of Array Except Self
19. Merge Sort || Count Inversions in an array
20. Median of two Sorted Arrays 
21. First Missing Positive
22. 1310. XOR Queries of a Subarray 
23. Power Set 
24. Maximum difference between two elements in the array such that smaller element appears before the larger element (Important)

*/
*-----------------------------------------------------------------------------------------------------------------------------------------------*
// 1. Sort Colors(Dutch National Flag alg)

    public void sortColors(int[] nums) {
        
      int low = 0,mid = 0, high;
      high = nums.length -1;
      while(mid<=high){  // impt <=
          if(nums[mid] == 0){
              swap(low,mid, nums);
              low++;
              mid++;
          }else if(nums[mid] == 1){
              mid++;
          }else{
              swap(mid,high, nums);
              high--;
          }  
      }
      
    }
    
    public void swap(int l, int r, int[] nums){
        int temp = nums[l];
        nums[l]=nums[r];
        nums[r]=temp;
    }


*-----------------------------------------------------------------------------------------------------------------------------------------------*
// 2. Maximum Sum Subarray of Size K (easy)

  public static int findMaxSumSubArray(int k, int[] arr) {
    int windowSum = 0, maxSum = 0;
    int windowStart = 0;
    for (int windowEnd = 0; windowEnd < arr.length; windowEnd++) {
      windowSum += arr[windowEnd]; // add the next element
      // slide the window, we don't need to slide if we've not hit the required window size of 'k'
      if (windowEnd >= k - 1) {
        maxSum = Math.max(maxSum, windowSum);
        windowSum -= arr[windowStart]; // subtract the element going out
        windowStart++; // slide the window ahead
      }
    }

    return maxSum;
  }
  
*-----------------------------------------------------------------------------------------------------------------------------------------------*
/* 3. Single Number II
 
Given a non-empty array of integers, every element appears three times except for one, which appears exactly once. Find that single one.

*/

    public int singleNumber(int[] nums) {
        int result = 0; 
        int x = 0, sum = 0; 
          
        for(int i=0; i<32; i++){ 
            sum = 0; 
            x = (1 << i);    // left shift 1 to ith position
            for(int j=0; j<nums.length; j++) { 
                if((nums[j] & x) == x) 
                  sum++; 
            } 
            if ((sum % 3) != 0) 
              result |= x; 
        } 
        return result; 
        
    }
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*

// 4. Majority Element N/2

	public static Integer getMajorityElement(int[] array) {

		if (array == null || array.length == 0) return null;
		
		Integer candidate = null;
		int count = 0;
		for (int i = 0; i < array.length; i++) {
			if (count == 0) {
				candidate = array[i];
				count = 1;
			} else {
				if (candidate == array[i]) {
					count++;
				} else {
					count--;
				}
			}
		}

		if (count == 0) {
			return null;
		}

		 
		count = 0;
		for (int i = 0; i < array.length; i++) {
			if (candidate == array[i]) {
				count++;
			}
		}
		return (count > array.length / 2) ? candidate : null;
	}
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*

// 5. Majority Element N/3

    public int repeatedNumber(final List<Integer> a) {
        
        int count1 = 0, count2 = 0;
        int first = Integer.MAX_VALUE, second = Integer.MAX_VALUE;
        int N = a.size();
		// Step 1
        for(int i=0; i<N; i++){
            int current = a.get(i);
            
            if(first == current) count1++;
            else if(second == current) count2++;
            else if(count1 == 0){
                count1++;
                first = current;
            }
            else if(count2 == 0){
                count2++;
                second = current;
            }else{
                count1--;
                count2--;
            }
            
        }
		//Step 2 check if the candidates are majority
        count1 = count2 = 0;
        for(int i=0; i<N; i++){
            int current = a.get(i);
            
            if(first == current) count1++;
            else if(second == current) count2++;           
        }
        
        if(count1>N/3) return first;
        
        if(count2>N/3) return second;      
        
        return -1;
    }
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*6. 287. Find the Duplicate Number https://leetcode.com/articles/find-the-duplicate-number/

You must not modify the array (assume the array is read only).
You must use only constant, O(1) extra space.
Your runtime complexity should be less than O(n2). --> below is O(n) solution 
There is only one duplicate number in the array, but it could be repeated more than once

Floyd's Tortoise and Hare (Cycle Detection)
*/

  public int findDuplicate(int[] nums) {
    // Find the intersection point of the two runners.
    int tortoise = nums[0];
    int hare = nums[0];
    do {
      tortoise = nums[tortoise];
      hare = nums[nums[hare]];
    } while (tortoise != hare);

    // Find the "entrance" to the cycle.
    tortoise = nums[0];
    while (tortoise != hare) {
      tortoise = nums[tortoise];
      hare = nums[hare];
    }

    return hare;
  }

*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
7. 239. Sliding Window Maximum   https://leetcode.com/problems/sliding-window-maximum/discuss/65884/Java-O(n)-solution-using-deque-with-explanation

We scan the array from 0 to n-1, keep "promising" elements in the deque. The algorithm is amortized O(n) as each element is put and polled once.

At each i, we keep "promising" elements, which are potentially max number in window [i-(k-1),i] or any subsequent window. This means

 1. If an element in the deque and it is out of i-(k-1), we discard them. We just need to poll from the head, as we are using a deque and elements are ordered as the sequence in the array

 2. Now only those elements within [i-(k-1),i] are in the deque. We then discard elements smaller than a[i] from the tail. This is because if a[x] <a[i] and x<i, then a[x] has no chance to be the "max" in [i-(k-1),i], or any other subsequent window: a[i] would always be a better candidate.

 3. As a result elements in the deque are ordered in both sequence in array and their value. At each step the head of the deque is the max element in [i-(k-1),i]
*/

    public int[] maxSlidingWindow(int[] a, int k) {		
		if (a == null || k <= 0) {
			return new int[0];
		}
		int n = a.length;
		int[] r = new int[n-k+1];
		int ri = 0;
		// store index
		Deque<Integer> q = new ArrayDeque<>();
		for (int i = 0; i < a.length; i++) {
			// remove numbers out of range k
			while (!q.isEmpty() && q.peek() < i - k + 1) {
				q.poll();
			}
			// remove smaller numbers in k range as they are useless
			while (!q.isEmpty() && a[q.peekLast()] < a[i]) {
				q.pollLast();
			}
			// q contains index... r contains content
			q.offer(i);
			if (i >= k - 1) {
				r[ri++] = a[q.peek()];
			}
		}
		return r;
	}

*-----------------------------------------------------------------------------------------------------------------------------------------------*
// 8. Find subarray with given sum | Set 1

    int subArraySum(int arr[], int n, int sum){ 
        int curr_sum = arr[0], start = 0, i; 
  
        // Pick a starting point 
        for (i = 1; i <= n; i++) { 
            // If curr_sum exceeds the sum, 
            // then remove the starting elements 
            while (curr_sum > sum && start < i - 1) { 
                curr_sum = curr_sum - arr[start]; 
                start++; 
            } 
  
            // If curr_sum becomes equal to sum, then return true 
            if (curr_sum == sum) { 
                int p = i - 1; 
                System.out.println( 
                    "Sum found between indexes " + start 
                    + " and " + p); 
                return 1; 
            } 
  
            // Add this element to curr_sum 
            if (i < n) 
                curr_sum = curr_sum + arr[i]; 
        } 
  
        System.out.println("No subarray found"); 
        return 0; 
    } 

*-----------------------------------------------------------------------------------------------------------------------------------------------*
//9. Find subarray with given sum | Set 2 (Handles Negative Numbers)   ----> Not able to remember the logic at first glance 

    public static void subArraySum(int[] arr, int n, int sum) { 
        //cur_sum to keep track of cummulative sum till that point 
        int cur_sum = 0; 
        int start = 0; 
        int end = -1; 
        HashMap<Integer, Integer> hashMap = new HashMap<>(); 
  
        for (int i = 0; i < n; i++) { 
            cur_sum = cur_sum + arr[i]; 
            //check whether cur_sum - sum = 0, if 0 it means 
            //the sub array is starting from index 0- so stop 
            if (cur_sum - sum == 0) { 
                start = 0; 
                end = i; 
                break; 
            } 
            //if hashMap already has the value, means we already  
            // have subarray with the sum - so stop 
            if (hashMap.containsKey(cur_sum - sum)) { 
                start = hashMap.get(cur_sum - sum) + 1; 
                end = i; 
                break; 
            } 
            //if value is not present then add to hashmap 
            hashMap.put(cur_sum, i); 
  
        } 
        // if end is -1 : means we have reached end without the sum 
        if (end == -1) { 
            System.out.println("No subarray with given sum exists"); 
        } else { 
            System.out.println("Sum found between indexes " 
                            + start + " to " + end); 
        } 
  
    } 
*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
10. 15. 3Sum

Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0? Find all unique triplets in the array which gives the sum of zero.

*/

    public List<List<Integer>> threeSum(int[] nums) {
        
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        Arrays.sort(nums);
        
        int n = nums.length;
        
        for(int i=0; i<n-2; i++){
            if (i == 0 || (i > 0 && nums[i] != nums[i-1])) {
                int start = i+1;
                int end = n-1;
                while(start<end){
                    int sum = nums[i] + nums[start] + nums[end];
                    if(sum==0){
                       result.add(Arrays.asList(nums[i], nums[start], nums[end]));
                        
                       int prev = nums[end];
                       while(end>=0 && prev == nums[end])
                         end--;
                    
                       prev = nums[start];
                       while(start<end && prev == nums[start])
                         start++;
                        
                    }else if (sum>0)
                        end--;
                    else
                        start++;   
                }
            }
            
        }
        
        return result;
        
    }

*-----------------------------------------------------------------------------------------------------------------------------------------------*
// 11. 42. Trapping Rain Water

	public int trap(int[] height) {
	  int n = height.length;
	  if (n <= 2) return 0;
	  // pre-compute
	  int[] leftMax = new int[n];
	  int[] rightMax = new int[n];
	  leftMax[0] = height[0]; // init
	  rightMax[n - 1] = height[n - 1];
	  for (int i = 1, j = n - 2; i < n; ++i, --j) {
		leftMax[i] = Math.max(leftMax[i - 1], height[i]);
		rightMax[j] = Math.max(rightMax[j + 1], height[j]);
	  }
	  // water
	  int totalWater = 0;
	  for (int k = 1; k < n - 1; ++k) { // do not consider the first and the last places
		int water = Math.min(leftMax[k - 1], rightMax[k + 1]) - height[k];
		totalWater += (water > 0) ? water : 0;
	  }
	  return totalWater;
	}

    ---------------------------------------------------
	
    public int trap(int[] height) {
        
        if(height == null || height.length==0 || height.length == 1 || height.length==2) return 0;
        
        int i = 0, j = height.length -1;
        int leftMax = 0, rightMax = 0, result = 0;
        
        while(i<=j){
            if(height[i]<height[j]){
                if(leftMax < height[i]){
                    leftMax = height[i];
                }else{
                    result += leftMax - height[i]; 
                }
                i++;
            }else{
                if(rightMax < height[j]){
                    rightMax = height[j];
                }else{
                    result += rightMax - height[j]; 
                }
                j--;      
            }
        }
        
        return result;
        
    }
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
12. 11. Container With Most Water

*/

    public int maxArea(int[] height) {
        
        if(height == null || height.length == 0) return 0;
        
        int maxArea = 0;
        int left = 0, right = height.length-1;
        
        while(left < right){
            int min = Math.min(height[left], height[right]);
            maxArea = Math.max(maxArea, min * (right-left));
            if(height[left] < height[right]){
                left++;
            }else{
                right--;
            }
        }
        
        return maxArea;
    }
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
13. Minimum Number of Platforms Required for a Railway/Bus Station  https://www.geeksforgeeks.org/minimum-number-platforms-required-railwaybus-station/
*/

    int findPlatform(int arr[], int dep[], int n) { 
        // Sort arrival and departure arrays 
        Arrays.sort(arr); 
        Arrays.sort(dep); 
  
        // plat_needed indicates number of platforms needed at a time 
        int plat_needed = 1, result = 1; 
        int i = 1, j = 0; 
  
        // Similar to merge in merge sort to process all events in sorted order 
        while (i < n && j < n) { 
  
            // If next event in sorted order is arrival, increment count of platforms needed 
            if (arr[i] <= dep[j]) { 
                plat_needed++; 
                i++; 
            } 
  
            // Else decrement count of platforms needed 
            else if (arr[i] > dep[j]) { 
                plat_needed--; 
                j++; 
            } 
  
            // Update result if needed 
            if (plat_needed > result) 
                result = plat_needed; 
        } 
  
        return result; 
    }
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
14. Reverse an array in groups of given size

Given an array, reverse every sub-array formed by consecutive k elements.

*/

    void reverse(int arr[], int n, int k){ 
	
        for (int i = 0; i < n; i += k) { 
            int left = i; 
      
            // to handle case when k is not multiple of n
			
            int right = Math.min(i + k - 1, n - 1); 
            int temp; 
              
            // reverse the sub-array [left, right] 
            while (left < right) 
            { 
                temp=arr[left]; 
                arr[left]=arr[right]; 
                arr[right]=temp; 
                left+=1; 
                right-=1; 
            } 
        } 
    } 
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
15. 941. Valid Mountain Array

Given an array A of integers, return true if and only if it is a valid mountain array.

Recall that A is a mountain array if and only if:


    1. A.length >= 3
	2. There exists some i with 0 < i < A.length - 1 such that:
		A[0] < A[1] < ... A[i-1] < A[i]
		A[i] > A[i+1] > ... > A[A.length - 1]

*/
    public boolean validMountainArray(int[] A) {
        
        int N = A.length;
        int i = 0;

        // walk up
        while (i+1 < N && A[i] < A[i+1])
            i++;

        // peak can't be first or last
        if (i == 0 || i == N-1)
            return false;

        // walk down
        while (i+1 < N && A[i] > A[i+1])
            i++;

        return i == N-1;
        
    }
	

*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*

16. 56. Merge Intervals

Given a collection of intervals, merge all overlapping intervals.

Example 1:

Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].

Example 2:

Input: intervals = [[1,4],[2,3]]
Output: [[1,4]]

*/
    public int[][] merge(int[][] intervals) {
        
        if(intervals.length <= 1) return intervals;
        
        List<int[]> result = new ArrayList<int[]>();
        
        Arrays.sort(intervals, (nums1, nums2) -> nums1[0] - nums2[0]);
        
        for(int[] curInterval: intervals){
            
            if(result.size() == 0 || result.get(result.size()-1)[1] < curInterval[0]){
                result.add(curInterval);
            }else{
                result.get(result.size()-1)[1] = Math.max(result.get(result.size()-1)[1],curInterval[1]); // Using Max for coveing up Example 2 test case.
            }
            
        }
        
        return result.toArray(new int[result.size()][]);
    }
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
17. 986. Interval List Intersections

Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.

Return the intersection of these two interval lists.

(Formally, a closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.  
The intersection of two closed intervals is a set of real numbers that is either empty, or can be represented as a closed interval. 
For example, the intersection of [1, 3] and [2, 4] is [2, 3].)

*/


class Solution {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        
        int i = 0, j = 0;
        List<int[]> result = new ArrayList<>();
        
        while(i<A.length && j<B.length){
            
            int lo = Math.max( A[i][0] , B[j][0]);
            int hi = Math.min( A[i][1] , B[j][1]);
            
            if(lo <= hi)
                result.add(new int[]{lo, hi});
            
            if(A[i][1] < B[j][1])
                i++;
            else
                j++;

        }
        
        return result.toArray(new int[result.size()][]);
        
    }
}

*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
18. 238. Product of Array Except Self

Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to the product of all the elements of nums except nums[i].

Example:

Input:  [1,2,3,4]
Output: [24,12,8,6]
Constraint: It's guaranteed that the product of the elements of any prefix or suffix of the array (including the whole array) fits in a 32 bit integer.

Note: Please solve it without division and in O(n).

Follow up:
Could you solve it with constant space complexity? (The output array does not count as extra space for the purpose of space complexity analysis.)

*/

class Solution {
    public int[] productExceptSelf(int[] nums) {
        
        int n = nums.length;
        int[] L = new int[n];
        int[] R = new int[n];
        
        L[0] = R[n-1] = 1;
        
        for(int i=1; i < n; i++){
            L[i] = L[i-1] * nums[i-1];
        }
        
        for(int i=n-2; i >= 0; i--){
            R[i] = R[i+1] * nums[i+1];
        }
        
         for(int i=0; i < n; i++){
            L[i] = L[i] * R[i];
        }       

        return L;
        
    }
}
/*

Given numbers [2, 3, 4, 5], regarding the third number 4, the product of array except 4 is 2*3*5 which consists of two parts: left 2*3 and right 5. The product is left*right. We can get lefts and rights:

Numbers:     2    3    4     5
Lefts:            2  2*3 2*3*4
Rights:  3*4*5  4*5    5      
Let’s fill the empty with 1:

Numbers:     2    3    4     5
Lefts:       1    2  2*3 2*3*4
Rights:  3*4*5  4*5    5     1
We can calculate lefts and rights in 2 loops. The time complexity is O(n).

We store lefts in result array. If we allocate a new array for rights. The space complexity is O(n). To make it O(1), we just need to store it in a variable which is right in @lycjava3’s code.

Clear code with comments:

public class Solution {
    public int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];
        // Calculate lefts and store in res.
        int left = 1;
        for (int i = 0; i < n; i++) {
            if (i > 0)
                left = left * nums[i - 1];
            res[i] = left;
        }
        // Calculate rights and the product from the end of the array.
        int right = 1;
        for (int i = n - 1; i >= 0; i--) {
            if (i < n - 1)
                right = right * nums[i + 1];
            res[i] *= right;
        }
        return res;
    }
}

*/
*-----------------------------------------------------------------------------------------------------------------------------------------------*
// 19. Merge Sort || Count Inversions in an array 

    void merge(int arr[], int l, int m, int r){ 
 
        /* Create temp arrays */
        int L[] = Arrays.copyOfRange(arr, l, m + 1);  
        int R[] = Arrays.copyOfRange(arr, m + 1, r + 1); 
 
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0; 
  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < L.length && j < R.length) { 
            if (L[i] <= R[j]) { 
                arr[k] = L[i]; 
                i++; 
            } 
            else { 
                arr[k] = R[j]; 
                j++; 
            } 
            k++; 
        } 
        
        /* Copy remaining elements of L[] if any */
        while (i < L.length) 
            arr[k++] = L[i++]; 
        /* Copy remaining elements of R[] if any */
        while (j < R.length) 
            arr[k++] = R[j++]; 
    }
    
    
    void sort(int arr[], int l, int r) { 
        if (l < r) { 
            // Find the middle point 
            int m = (l + r) / 2; 
            // Sort first and second halves 
            sort(arr, l, m); 
            sort(arr, m + 1, r); 
            // Merge the sorted halves 
            merge(arr, l, m, r); 
        } 
    }
--------------------------------------------------------------------------------

    int merge(int arr[], int l, int m, int r){ 
 
        /* Create temp arrays */
        int L[] = Arrays.copyOfRange(arr, l, m + 1);  
        int R[] = Arrays.copyOfRange(arr, m + 1, r + 1); 
 
        // Initial indexes of first and second subarrays 
        int i = 0, j = 0, swaps =0; 
  
        // Initial index of merged subarry array 
        int k = l; 
        while (i < L.length && j < R.length) { 
            if (L[i] <= R[j]) { 
                arr[k] = L[i]; 
                i++; 
            } 
            else { 
                arr[k] = R[j];
                swaps += (m + 1) - (l + i);  // important point to remember
                j++; 
            } 
            k++; 
        } 
        
        /* Copy remaining elements of L[] if any */
        while (i < L.length) 
            arr[k++] = L[i++]; 
        /* Copy remaining elements of R[] if any */
        while (j < R.length) 
            arr[k++] = R[j++];
        
        return swaps;
    }
    
    
    int sort(int arr[], int l, int r) {
    	int count = 0;
        if (l < r) { 
            // Find the middle point 
            int m = (l + r) / 2; 
            // Sort first and second halves 
            count+= sort(arr, l, m); 
            count+= sort(arr, m + 1, r); 
            // Merge the sorted halves 
            count+= merge(arr, l, m, r); 
        } 
        return count;
    }
   
*-----------------------------------------------------------------------------------------------------------------------------------------------*	

/*
20. Median of two Sorted Arrays

x1 x2|x3 x4 x5 x6
y1 y2 y3 y4 y5 y6|y7 y8 

x2 <= y7 and y6<=x3 , proves the that mid point is perfect we can calculate median

even: Avg(Math.max(x2,y6) + Math.min(x3,y7))
odd: Math.max(x2,y6)

*/ 

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        
        if(nums1.length > nums2.length) 
            return findMedianSortedArrays(nums2,nums1);
        
        int x = nums1.length;
        int y = nums2.length;
        int low = 0;
        int high = x;
        
        while(low<=high){
            int partX =  (low+high)/2;
            int partY =  (x+y+1)/2 - partX;
            
            int xLeft  = partX == 0 ? Integer.MIN_VALUE : nums1[partX-1];
            int xRight = partX == x ? Integer.MAX_VALUE : nums1[partX];
            int yLeft  = partY == 0 ? Integer.MIN_VALUE : nums2[partY-1];
            int yRight = partY == y ? Integer.MAX_VALUE : nums2[partY];
            
            if(xLeft<=yRight && yLeft<=xRight){
               if((x+y)%2==0){
                   return ((double)Math.max(xLeft,yLeft) + Math.min(xRight,yRight))/2;
               }else{
                   return Math.max(xLeft,yLeft);
               } 
            }else if(xLeft>yRight){
                high = partX -1;
            }else{
                low = partX+1;
            }
        }
        return 0;
    }  
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*
// 21. First Missing Positive 

public class Solution {
    public int firstMissingPositive(int[] A) {
        int i = 0;
        while(i < A.length){
            if(A[i] == i+1 || A[i] <= 0 || A[i] > A.length) i++;
            else if(A[A[i]-1] != A[i]) swap(A, i, A[i]-1);
            else i++;
        }
        i = 0;
        while(i < A.length && A[i] == i+1) i++;
        return i+1;
    }
    
    private void swap(int[] A, int i, int j){
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }
}

/*

A[A[i]-1] != A[i]
Almost the same, but like [3,2,3,4], when i = 0, A[0] = 3, which should be put on position i = 2 where also A[2] = 3. 
Thus there is no need to do swap and go to else statement i++ for next check or it will go into endless loop

*/

*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
22. 1310. XOR Queries of a Subarray 
Given the array arr of positive integers and the array queries where queries[i] = [Li, Ri], for each query i compute the XOR of elements from Li to Ri (that is, arr[Li] xor arr[Li+1] xor ... xor arr[Ri] ). Return an array containing the result for the given queries.
 
Example 1:

Input: arr = [1,3,4,8], queries = [[0,1],[1,2],[0,3],[3,3]]
Output: [2,7,14,8]

Explanation: 
The binary representation of the elements in the array are:
1 = 0001 
3 = 0011 
4 = 0100 
8 = 1000 
The XOR values for queries are:
[0,1] = 1 xor 3 = 2 
[1,2] = 3 xor 4 = 7 
[0,3] = 1 xor 3 xor 4 xor 8 = 14 
[3,3] = 8


*/

    public int[] xorQueries(int[] A, int[][] queries) {
        int[] res = new int[queries.length], q;
        for (int i = 1; i < A.length; ++i)
            A[i] ^= A[i - 1];
        for (int i = 0; i < queries.length; ++i) {
            q = queries[i];
            res[i] = q[0] > 0 ? A[q[0] - 1] ^ A[q[1]] : A[q[1]];
        }
        return res;
    }
	
	
/* 
Sol : Reference https://leetcode.com/problems/xor-queries-of-a-subarray/discuss/470787/JavaC%2B%2BPython-Straight-Forward-Solution

In-place calculate the prefix XOR of input A.

For each query [i, j],
if i == 0, query result = A[j]
if i != 0, query result = A[i - 1] ^ A[j]

*/

*-----------------------------------------------------------------------------------------------------------------------------------------------*
/*
23. Power Set 
Ref: https://www.geeksforgeeks.org/power-set/
https://www.techiedelight.com/generate-power-set-given-set/

Set  = [a,b,c]
power_set_size = pow(2, 3) = 8
Run for binary counter = 000 to 111

Value of Counter            Subset
    000                    -> Empty set
    001                    -> a
    010                    -> b
    011                    -> ab
    100                    -> c
    101                    -> ac
    110                    -> bc
    111                    -> abc
	
*/

	public static void findPowerSet(int[] S){
		// N stores total number of subsets
		int N = (int) Math.pow(2, S.length);

		// generate each subset one by one
		for (int i = 0; i < N; i++) {
			// check every bit of i
			for (int j = 0; j < S.length; j++) {
				// if j'th bit of i is set, print S[j]
				if ((i & (1 << j)) != 0) {
					System.out.print(S[j] + " ");
				}
			}
			System.out.println();
		}
	}
	
*-----------------------------------------------------------------------------------------------------------------------------------------------*
// 24. Maximum difference between two elements in the array such that smaller element appears before the larger element (Important)

class Main{
	// Function to calculate 
	public static int diff(int[] A){
		
		int diff = Integer.MIN_VALUE;
		int n = A.length;
		int max_so_far = A[n-1];

		// traverse the array from right and keep track the maximum element
		for (int i = n - 2; i >= 0; i--)
		{
			// update max if current element is greater than the max element
			if (A[i] > max_so_far) {
				max_so_far = A[i];
			}
			// if the current element is less than the maximum element,
			// then update the difference if required
			else {
				diff = Integer.max(diff, max_so_far - A[i]);
			}
		}

		// return difference
		return diff;
	}

	public static void main(String[] args)
	{
		int[] A = { 2, 7, 9, 5, 1, 3, 5 };

		System.out.print("The maximum difference is " + diff(A));
	}
}

*-----------------------------------------------------------------------------------------------------------------------------------------------*