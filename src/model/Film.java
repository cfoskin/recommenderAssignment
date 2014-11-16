package model;

import java.util.ArrayList;
import java.util.Comparator;

public class Film {//implements Comparator<Film>, Comparable<Film>{

	private int id;
	private String title;
	private int year;
	private String genre;
	private ArrayList<Rating> ratings;
	private int totalRatingValue;
	private int numOfViewers;

	public Film()
	{
	}

	public Film (int id, String title, int year, String genre)
	{
		this.title = title;
		this.id = id;
		this.genre = genre;
		this.year = year;
		ratings = new ArrayList<Rating>();
		this.totalRatingValue= 0;
		this.numOfViewers = 0;
	}
	
//	@Override
//	public int compare(Film f1, Film f2) {
//		return f1.year - f2.year;		
//	}
//	
//	@Override
//	public int compareTo(Film f){
//		return (this.title).compareTo(f.title);
//	}
//	
//	public int compare2(Film f1, Film f2) {
//		return f1.totalRatingValue - f2.totalRatingValue;		
//	}
//	
//	public int compareTo2(Film f){
//		return (this.title).compareTo(f.title);
//	}
//	
	public void addRating(Rating rating)
	{
		ratings.add(rating); 
		totalRatingValue += rating.getRatingValue();
		numOfViewers++;
	}

//	private String ratingsAsString()
//	{
//		String result ="";
//		for(int i=0;i<ratings.size();i++)
//		{
//			result += ratings.get(i).getRating(i);
//		}
//		return result;
//	}
//	
	public int getNumOfViewers() {
		return numOfViewers;
	}

	public void setNumOfViewers(int numOfViewers) {
		this.numOfViewers = numOfViewers;
	}

	public int getTotalRatingValue()
	{
		return totalRatingValue;
	}

	public void setTotalRatingValue(int totalRatingValue) {
		this.totalRatingValue = totalRatingValue;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(ArrayList<Rating> ratings) {
		this.ratings = ratings;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public String toString() {
		return "Film id= " + id + ", title=" + title + ", year=" + year
				+ ", genre=" + genre
				+ ", totalRatingValue=" + totalRatingValue + ", numOfViewers="
				+ numOfViewers + "]";
	}
}
