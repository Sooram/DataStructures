import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						int[] tempArray = new int[value.length];
						newvalue = DoMergeSort(newvalue, tempArray, 0, newvalue.length-1);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue, 0, newvalue.length-1);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{ // copy from 'Data Abstraction & problem solving with Java'
		
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
		boolean sorted = false;
		int pass, index;
		
		for(pass = 1; (pass < value.length) && !sorted; ++pass) {
			sorted = true;
			for(index = 0; index < value.length-pass; ++index) {
				int nextIndex = index + 1;
				if(value[index] > value[nextIndex]) {
					int temp = value[index];
					value[index] = value[nextIndex];
					value[nextIndex] = temp;
					sorted = false;
				}
			}
		}
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{ // copy from 'Data Abstraction & problem solving with Java'
		
		int unsortedIndex, insertPosition;
		
		for(unsortedIndex = 1; unsortedIndex < value.length; ++unsortedIndex) {
			int nextItem = value[unsortedIndex];
			insertPosition = unsortedIndex;
			
			while((insertPosition > 0) && (value[insertPosition-1] > nextItem)) {
			// find the right place to insert 'nextItem'
				value[insertPosition] = value[insertPosition-1];
				insertPosition--;
			}
			value[insertPosition] = nextItem;			
		}
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{ // copy from 'Introduction to algorithms'
		buildMaxHeap(value);
		
		int last = value.length-1;
		int tempItem;
		while(last >= 1) {
			// swap the last element with the first element(the biggest value)
			tempItem = value[last];
			value[last] = value[0];
			value[0] = tempItem;
			
			--last;
			// make the remained array have max heap property
			maxHeapify(value, 0, last);
		}
			
		return (value);
	}
	
	private static void buildMaxHeap(int[] value) 
	{ // copy from 'Introduction to algorithms'
		int i;
		int last = value.length-1;
		for(i = value.length/2; i >= 0; i--) {
			maxHeapify(value, i, last);
		}
	}
	
	private static void maxHeapify(int[] value, int curr, int last)
	{ // copy from 'Introduction to algorithms'
		int left = 2 * curr + 1;
		int right = 2 * curr + 2;
		int larger;
		
		if(left <= last && right <= last && value[left] >= value[right]) {
			larger = left;
		}
		else if(left <= last && right <= last && value[right] > value[left]){
			larger = right;
		}
		else {
			larger = curr;
		}
		
		int tempItem;
		if(value[curr] < value[larger]) {
			
			tempItem = value[curr];
			value[curr] = value[larger];
			value[larger] = tempItem;
			
			maxHeapify(value, larger, last);
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value, int[] tempArray, int first, int last)
	{ // copy from 'Data Abstraction & problem solving with Java'
		
		if(first < last) {
			int mid = (first + last)/2;
			DoMergeSort(value, tempArray, first, mid);
			DoMergeSort(value, tempArray,  mid+1, last);
			merge(value, tempArray, first, mid, last);
		}
		
		return (value);
	}
	
	private static int[] merge(int[] value, int[] tempArray, int first, int mid, int last) 
	{ // copy from 'Data Abstraction & problem solving with Java'
		// initialize the local indexes to indicate the subarrays
		int first1 = first;
		int last1 = mid;
		int first2 = mid+1;
		int last2 = last;
		
//		int[] tempArray = new int[value.length];
		
		int i = first;		
		while(first1 <= last1 && first2 <= last2) {
			if(value[first1] <= value[first2]) {
				tempArray[i] = value[first1];
				first1++;
			}
			else {
				tempArray[i] = value[first2]; 
				first2++;
			}
			i++;
		}
		
		while(first1 <= last1) {
			tempArray[i] = value[first1];
			first1++;
			i++;
		}
		while(first2 <= last2) {
			tempArray[i] = value[first2];
			first2++;
			i++;
		}
		
		for(i=first; i<=last; ++i) {
			value[i] = tempArray[i];
		}
		
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value, int first, int last)
	{ // copy from 'Data Abstraction & problem solving with Java'
		int pivotIndex;  
		if(first < last) {
			pivotIndex = partition(value, first, last);
			DoQuickSort(value, first, pivotIndex-1);
			DoQuickSort(value, pivotIndex+1, last);
		}
		return (value);
	}

	private static void choosePivot(int[] value, int first, int last)
	{ // modification from 'Data Abstraction & problem solving with Java'
		// choose pivot and exchange it with the first element
		Random randomNum = new Random();
		int i = randomNum.nextInt(last-first+1) + first;
		int temp;
		temp = value[first];
		value[first] = value[i];
		value[i] = temp;
	}
	
	private static int partition(int[] value, int first, int last) 
	{ // modification from 'Data Abstraction & problem solving with Java'
		
		choosePivot(value, first, last);
		int pivot = value[first];
		
		int firstSecEnd = first;
		int unknownSecFirst;
		int tempItem;
		
		for (unknownSecFirst = first+1; unknownSecFirst <= last; ++unknownSecFirst) {
			if(value[unknownSecFirst] < pivot) { // item from unknown belongs in the first section
				// make 'firstSecEnd' be the index of the first item of the second section
				// by increasing it by one
				++firstSecEnd;
				
				// exchange item from unknown with the last item of the first section
				tempItem = value[unknownSecFirst];
				value[unknownSecFirst] = value[firstSecEnd];
				value[firstSecEnd] = tempItem;
			}
			// else item from unknown belongs in the second section
			// which is already handled by '++unknownSecFirst'
		} // end for
		
		// place pivot in proper position and mark its location
		tempItem = value[first];
		value[first] = value[firstSecEnd];
		value[firstSecEnd] = tempItem;	
		
		return firstSecEnd;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{ // modification from: https://gist.githubusercontent.com/yeison/5606963	
		int[] negValues = new int[value.length];
		int[] posValues = new int[value.length];
		int negCount = 0, posCount = 0;
		
		// divide to negative value and positive value
		int i;
		for(i=0; i<value.length; i++) {
			if(value[i] < 0) {
				negValues[negCount] = value[i];
				negCount++;
			}
			else {
				posValues[posCount] = value[i];
				posCount++;
			}
		}

		for(int place=1; place <= 1000000000; place *= 10){
            // Use counting sort at each digit's place
            negValues = countingSort(Arrays.copyOfRange(negValues, 0, negCount), place);
            posValues = countingSort(Arrays.copyOfRange(posValues, 0, posCount), place);
        }
        
		// copy the sorted array back to 'value'
        for(i=0; i<negCount; i++) {
        	value[i] = negValues[negCount-i-1];
        }
        for(i=negCount; i<value.length; i++) {
        	value[i] = posValues[i-negCount];
        }

		return (value);
	}
	
	   private static int[] countingSort(int[] input, int place)
	   { // modification from: https://gist.githubusercontent.com/yeison/5606963	
	        int[] out = new int[input.length];
	        int[] count = new int[10];

	        for(int i=0; i < input.length; i++){
	        	// according to the digit, count up the value of indexed matching to it
	        	int digit = getDigit(input[i], place);
	            count[digit] += 1;
	        }

	        for(int i=1; i < count.length; i++){
	        	// calculate accumulated number of elements in each index
	            count[i] += count[i-1];
	        }

	        for(int i = input.length-1; i >= 0; i--){
	        	// find the right place of input[i] using 'count' 
	        	// and put it into 'out'
	           	int digit = getDigit(input[i], place);
	            out[count[digit]-1] = input[i];
	            count[digit]--;
	        }

	        return out;

	    }
	    
	    private static int getDigit(int value, int digitPlace)
	    {
	        return Math.abs((value/digitPlace ) % 10);
	    }

}
