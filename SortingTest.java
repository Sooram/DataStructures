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
	{
		// TODO : Bubble Sort 를 구현하라.
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
	{
		// TODO : Insertion Sort 를 구현하라.
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
		// TODO : Heap Sort 를 구현하라.
		
		
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
		// TODO : Merge Sort 를 구현하라.
		int mid = value.length/2;
		DoMergeSort(Arrays.copyOfRange(value, 0, mid));
		DoMergeSort(Arrays.copyOfRange(value, mid+1, value.length-1));
		Merge(value, mid);
		
		return (value);
	}
	
	private static int[] Merge(int[] value, int mid) {
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
	{
		// TODO : Quick Sort 를 구현하라.
		return (value);
	}

	private static void choosePivot(int[] value)
	{
		// choose pivot and exchange it with the first element
		Random randomNum = Random();
		int i = randomNum.nextInt(value.length-1);
		int temp;
		temp = value[0];
		value[0] = value[i];
		value[i] = temp;
	}
	
	private static int Partition(int[] value) 
	{
		choosePivot(value);
		int pivot = value[0];
		
		int lastS1 = 0;
		
		
		return lastS1;
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
		// TODO : Radix Sort 를 구현하라.
		
		return (value);
	}
}

