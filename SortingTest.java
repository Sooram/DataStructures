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
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
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
	{ // modification from the book 'Data Abstraction & problem solving with Java'
		
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
	{ // modification from the book 'Data Abstraction & problem solving with Java'
		
		int unsortedIndex, sortedIndex = 0;
		
		for(unsortedIndex = 1; unsortedIndex < value.length; ++unsortedIndex) {
			int nextItem = value[unsortedIndex];
			
			while((sortedIndex > 0) && (value[sortedIndex-1] > nextItem)) {
			// find the right place to insert 'nextItem'
				value[sortedIndex] = value[sortedIndex-1];
				sortedIndex--;
			}
			value[sortedIndex] = nextItem;			
		}
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
		BuildMaxHeap(value);
		
		int last = value.length-1;
		int tempItem;
		while(last >= 2) {
			
			tempItem = value[last];
			value[last] = value[0];
			value[0] = tempItem;
			
			--last;
			
			MaxHeapify(value, 0, last);
		}
		
		return (value);
	}
	
	private static void BuildMaxHeap(int[] value) 
	{
		int i;
		int last = value.length-1;
		for(i = value.length/2; i >= 0; i--) {
			MaxHeapify(value, i, last);
		}
	}
	
	private static void MaxHeapify(int[] value, int curr, int last)
	{
		int left = 2 * curr + 1;
		int right = 2 * curr + 2;
		int larger;
		
		if(left <= last && value[left] >= value[right]) {
			larger = left;
		}
		else if(right <= last && value[right] > value[left]){
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
			
			MaxHeapify(value, larger, last);
		}
	}
		

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{ // modification from the book 'Data Abstraction & problem solving with Java'
		
		int mid = value.length/2;
		DoMergeSort(Arrays.copyOfRange(value, 0, mid));
		DoMergeSort(Arrays.copyOfRange(value, mid+1, value.length-1));
		Merge(value, mid);
		
		return (value);
	}
	
	private static int[] Merge(int[] value, int mid) 
	{ // modification from the book 'Data Abstraction & problem solving with Java'
		// initialize the local indexes to indicate the subarrays
		int first1 = 0;
		int last1 = mid;
		int first2 = mid+1;
		int last2 = value.length-1;
		
		int[] tempArray = new int[value.length];
		
		int i = 0;		
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
		
		for(i=0; i<value.length; i++) {
			value[i] = tempArray[i];
		}
		
		return value;
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{ // modification from the book 'Data Abstraction & problem solving with Java'
		int pivotIndex = Partition(value);
		DoQuickSort(Arrays.copyOfRange(value, 0, pivotIndex));
		DoQuickSort(Arrays.copyOfRange(value, pivotIndex+1, value.length-1));
		
		return (value);
	}

	private static void ChoosePivot(int[] value)
	{
		// choose pivot and exchange it with the first element
		Random randomNum = new Random();
		int i = randomNum.nextInt(value.length-1);
		int temp;
		temp = value[0];
		value[0] = value[i];
		value[i] = temp;
	}
	
	private static int Partition(int[] value) 
	{ // modification from the book 'Data Abstraction & problem solving with Java'
		
		ChoosePivot(value);
		int pivot = value[0];
		
		int firstSecEnd = 0;
		int unknownSecFirst;
		int tempItem;
		
		for (unknownSecFirst = 1; unknownSecFirst <= value.length; ++unknownSecFirst) {
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
		value[0] = value[firstSecEnd];
		value[firstSecEnd] = pivot;
		
		
		return firstSecEnd;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		int i;
		List<String> valueStr = new ArrayList<>();
		for(i=0; i<value.length; i++) {
			// convert int[] to String[]
			valueStr.add(String.format("%10s",  String.valueOf(value[i])).replace(' ', '0'));
		}
		
		List<List<String>> bucket = new LinkedList<>();
		MakeBucket(bucket);
		
		int j;
		char digit;
		for(j=10; j>0; j--) {
			ClearBucket(bucket);
			for(i=0; i<value.length; i++) {
				if(j > valueStr.get(i).length()) {
					bucket.get(0).add(valueStr.get(i));
				}
				else {
					digit = valueStr.get(i).charAt(j-1);
					bucket.get(digit-48).add(valueStr.get(i));
				}
			} // end for i
			
			valueStr.clear();
			for(i=0; i<10; i++) {
				valueStr.addAll(bucket.get(i));
			}
		} // end for j
		
		for(i=0; i<value.length; i++) {
			// convert list to int[]
			value[i] = Integer.valueOf(valueStr.get(i));
		}
		return (value);
	}
	
	private static void MakeBucket(List<List<String>> bucket) 
	{	
		List<String> l0 = new LinkedList<>();
		List<String> l1 = new LinkedList<>();
		List<String> l2 = new LinkedList<>();
		List<String> l3 = new LinkedList<>();
		List<String> l4 = new LinkedList<>();
		List<String> l5 = new LinkedList<>();
		List<String> l6 = new LinkedList<>();
		List<String> l7 = new LinkedList<>();
		List<String> l8 = new LinkedList<>();
		List<String> l9 = new LinkedList<>();
		
		bucket.add(l0);
		bucket.add(l1);
		bucket.add(l2);
		bucket.add(l3);
		bucket.add(l4);
		bucket.add(l5);
		bucket.add(l6);
		bucket.add(l7);
		bucket.add(l8);
		bucket.add(l9);
	
	}
	
	private static void ClearBucket(List<List<String>> bucket) 
	{
		int i;
		for(i=0; i<10; i++) {
			bucket.get(i).clear();
		}
	}
}
