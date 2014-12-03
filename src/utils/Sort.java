package utils;
import java.util.ArrayList;
import java.util.Comparator;

import model.Film;
public class Sort {
	private Comparator<Film> comparator;
	
	public Sort(Comparator<Film> c ) 
	{
          this.comparator = c;
	}
	/**
	 * @param items The array to be sorted
	 * This method uses the selection sort algorithm to sort the array.  It calls the 
	 * findPosOfLargest method to determine the position of the largest element in the
	 * array.Each iteration over the array reduces the size of the array by 1 until 
	 * the array is sorted.
	 */
	public void selectionSort(ArrayList<Film> items)
	   {
	      for (int i = items.size(); i > 0; i--)
	      {
	         int posLargest = findPosOfLargest(items, i);
	         swapMember(items, posLargest, i-1);
	      }
	  }

	 /**
	 * @param items The array whose elements to search for the largest element
	 * @param size The position of the largest element in the array.
	 * @return returns the position of the largest element in the Array.
	 */
	private int findPosOfLargest(ArrayList<Film> items, int size)
	    {
	       int largestPosSoFar = 0;
	       for (int i = 1; i < size; i++)
	       {
	    	   
	    	   if(this.comparator.compare(items.get(i), items.get(largestPosSoFar)) > 0){
	    		   largestPosSoFar = i;
	    	   }

	       }
	       return largestPosSoFar;
	   }
	 
	/**
	 * @param items The array whose elements will be swapped
	 * @param i the first element to be swapped with the second
	 * @param j the second element to be swapped with the first
	 * */
	private void swapMember(ArrayList<Film> items,int i, int j)
	{
		Film temp = items.get(i);
		items.set(i, items.get(j) );
		items.set(j, temp) ;
	}

}
