package model;

import java.util.ArrayList;

/**
 * @author colum foskin
 * This class is the film model where all functions relating to the film are performed.
 */
public class Film {

	private int id;
	private String title;
	private int year;
	private String genre;
	private ArrayList<Rating> ratings;
	private int totalRatingValue;//this field is used to sort films by the rating value
	private int numOfViewers;//this field is updated each time the film is rated

	/**
	 * @param id
	 * @param title
	 * @param year
	 * @param genre
	 * constructing a film object
	 */
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

	/**
	 * @param rating
	 * This method adds a rating to the film object and re calculates
	 *  the total rating value based on this rating.
	 */
	public void addRating(Rating rating)
	{
		ratings.add(rating); 
		totalRatingValue += rating.getRatingValue();
		numOfViewers++;
	}

	/**
	 * @return
	 * returns number of viewers for the film.
	 */
	public int getNumOfViewers() {
		return numOfViewers;
	}

	/**
	 * @return
	 * returns the total rating value for each film
	 */
	public int getTotalRatingValue()
	{
		return totalRatingValue;
	}

	/**
	 * @return
	 * returns the title of the film
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return
	 * returns the films id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return
	 * returns the Arraylist of ratings for the film
	 */
	public ArrayList<Rating> getRatings() {
		return ratings;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 * I had to implement this equals method as each time i re loaded my xml it would
	 * not recognize that i had already rated a film and therefore left me rate it a second time 
	 * which i did not want to allow.
	 */
	public boolean equals(Object obj){
		return this.id == ((Film) obj).getId();
	}

	/**
	 * @return
	 * returns the genre for the film
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @return
	 * returns the year of the film
	 */
	public int getYear() {
		return year;
	}

	@Override
	public String toString() {
		return title + ", year: " + year
				+ ", genre: " + genre
				+ ", total Rating so far: " + totalRatingValue + ", num of viewings: "
				+ numOfViewers;
	}
}
