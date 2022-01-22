package jfsd.lesson5;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

	private static final ArrayList<Integer> expenses = initExpenses();
	
	public static void main(String[] args) {
		System.out.println("\n**************************************\n");
		System.out.println("\tWelcome to TheDesk \n");
		System.out.println("**************************************");
		
		optionsSelection();
	}

	private static ArrayList<Integer> initExpenses() {
		ArrayList<Integer> expenses = new ArrayList<Integer>();

		expenses.add(1000);
		expenses.add(2300);
		expenses.add(45000);
		expenses.add(32000);
		expenses.add(110);

		return expenses;
	}

	private static void optionsSelection() {
		String[] arr = {"1. I wish to review my expenditure",
				"2. I wish to add my expenditure",
				"3. I wish to delete my expenditure",
				"4. I wish to sort the expenditures",
				"5. I wish to search for a particular expenditure",
				"6. Close the application"
		};

		for(int i=0; i<arr.length;i++){
			System.out.println(arr[i]);
		}

		Scanner sc = new Scanner(System.in);

		try {
			System.out.print("\nEnter your choice: ");
			int options =  sc.nextInt();
			System.out.println();
			switch (options){
				case 1:
					System.out.println("Your saved expenses are listed below:");
					System.out.println(expenses);
					break;
				case 2:
					System.out.print("Enter the value to add your expense: ");
					int value = sc.nextInt();
					expenses.add(value);
					System.out.println("\nThe expense was added.");
					System.out.println(expenses);	
					break;
				case 3:
					System.out.println("You are about the delete all your expenses!\nConfirm again by selecting the same option...");
					int con_choice = sc.nextInt();
					System.out.println();
					if(con_choice==options)
						deleteAllExpenses();
					else
						System.out.println("Oops... try again!");
					break;
				case 4:
					sortExpenses(expenses);
					break;
				case 5:
					System.out.print("Enter the expense you want to search: ");
					int expenseKey = sc.nextInt();
					System.out.println();
					searchExpenses(expenses, expenseKey);
					break;
				case 6:
					closeApp();
					return;
				default:
					throw new InputMismatchException();
			}
			System.out.println();
			optionsSelection();
		}
		catch(InputMismatchException e) {
			System.out.println("\nYou have made an invalid choice!\n");
			optionsSelection();
		}
		catch(Exception e) {
			System.out.println("\nFatal error occurred ["+e.getMessage()+"]");
			e.printStackTrace();
		}
		finally {
			sc.close();
		}
	}

	private static void deleteAllExpenses() {
		if(expenses != null && !expenses.isEmpty()) {
			expenses.clear();
			System.out.println("All expenses were erased!");
			System.out.println(expenses);
		}
		else
			System.out.println("No expenses found!");
	}

	private static void searchExpenses(ArrayList<Integer> arrayList, int expenseKey) {
		int last = arrayList.size() - 1;

		int i = recursiveBinarySearch(arrayList, 0, last, expenseKey);
		if(i > -1)
			System.out.println("Record found in position "+i+".");
		else
			System.out.println("Could not find a record with value "+expenseKey+".");
	} 

	private static void sortExpenses(ArrayList<Integer> arrList) {
		mergeSort(arrList, 0, arrList.size()-1, "ASC");
		System.out.println("The expenses have been sorted.\n"+arrList);
	}

	private static void closeApp() {
		System.out.println("Closing application..."
				+ ".\nThank you!");
	}

	private static int recursiveBinarySearch(ArrayList<Integer> arrList, int first, int last, int key){
		if (last>=first){  
			int mid = first + (last - first)/2;  
			if (arrList.get(mid) == key)
				return mid;

			if (arrList.get(mid) > key)
				return recursiveBinarySearch(arrList, first, mid-1, key);		
			else
				return recursiveBinarySearch(arrList, mid+1, last, key);
		}  
		return -1;  
	}

	private static void mergeSort(ArrayList<Integer> arrList, int l, int r, String direction) {
		if (l < r) {
			int m = (l+r)/2;

			mergeSort(arrList, l, m, direction);
			mergeSort(arrList , m+1, r, direction);

			merge(arrList, l, m, r, direction);
		}
	}

	private static void merge(ArrayList<Integer> arrList, int l, int m, int r, String direction)
	{
		int n1 = m - l + 1;
		int n2 = r - m;

		int L[] = new int [n1];
		int R[] = new int [n2];

		for (int i=0; i<n1; ++i)
			L[i] = arrList.get(l + i);
		for (int j=0; j<n2; ++j)
			R[j] = arrList.get(m + 1+ j);

		int i = 0;
		int j = 0;
		int k = l;
		while (i < n1 && j < n2) {
			boolean comparisonResult = direction == "ASC" ? L[i] <= R[j] : L[i] >= R[j];			
			if (comparisonResult) {
				arrList.set(k, L[i]);
				i++;
			}
			else {
				arrList.set(k, R[j]);
				j++;
			}
			k++;
		}

		while (i < n1)
		{
			arrList.set(k, L[i]);
			i++;
			k++;
		}

		while (j < n2)
		{
			arrList.set(k, R[j]);
			j++;
			k++;
		}
	}
}
