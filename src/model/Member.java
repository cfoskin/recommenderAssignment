package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utils.FilmYearCompare;
import utils.Sort;

public class Member {
	private String firstName;
	private String lastName;
	private String accountName;
	private Map <Film, Rating> ratedFilms;
	private ArrayList<Film> myFilms;
	private ArrayList<Film> unSeenFilms;
	private ArrayList<Integer> myRatingsKeys;

	public Member(String name, String lastName, String accountName){
		this.firstName = name;
		this.lastName = lastName;
		this.accountName = accountName;
		myRatingsKeys = new ArrayList<Integer>();
		ratedFilms = new HashMap<>();
		myFilms = new ArrayList<Film>();
		unSeenFilms = new ArrayList<Film>();
	}

	public Member(String name, String account, String lastName, String[] ratings)
	{
		this.ratedFilms = new HashMap<>();
		myRatingsKeys = new ArrayList<Integer>();
		myFilms = new ArrayList<Film>();
		unSeenFilms = new ArrayList<Film>();
		this.firstName = name;
		this.lastName = lastName;
		this.accountName = account;
		this.addRatings(ratings);
	}

	private void addRatings(String[] ratings)
	{
		for(int i = 0;i < ratings.length;i++){
			String ratingAsString = ratings[i];
			int ratingAsInt = new Integer(ratingAsString);
			Rating newRating = Rating.getRating(ratingAsInt);
			newRating.setM(this);
			myRatingsKeys.add(ratingAsInt);
		}
	}

	public boolean addFilmRating(Film film, int rating)
	{
		Rating newRating = Rating.getRating(rating);
		if(newRating == null || ratedFilms.containsKey(film))
		{
			return false;
		}
		else 
		{
			ratedFilms.put(film, newRating);
			myFilms.add(film);
		}
		if(!myRatingsKeys.contains(rating))
		{
			myRatingsKeys.add(rating);
		}	
		return true;
	}

	
	public void addUnseenFilm(Film film)
	{
		unSeenFilms.add(film);
	}

	public Rating getAFilmRating(int rating)
	{
		Rating aRating = Rating.getRating(rating);	
		return aRating;
	}

	
	public Map<Film, Rating> getRatedFilms() {
		return ratedFilms;
	}

	public void setRatedFilms(Map<Film, Rating> ratedFilms) {
		this.ratedFilms = ratedFilms;
	}

	public ArrayList<Film> getMyFilms() {
		Sort sort = new Sort(new FilmYearCompare());
		sort.selectionSort(myFilms);
		return myFilms;
	}

	public void setMyFilms(ArrayList<Film> myFilms) {
		this.myFilms = myFilms;
	}
	
	public ArrayList<Film> getUnSeenFilms() {
		return unSeenFilms;
	}

	public void setUnSeenFilms(ArrayList<Film> unSeenFilms) {
		this.unSeenFilms = unSeenFilms;
	}

	public Map<Film, Rating> ratedFilms() {

		return ratedFilms;
	}

	public ArrayList<Integer> getMyRatingsKeys() {
		return myRatingsKeys;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstName = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	
	@Override
	public String toString() {
		return "Member firstName=" + firstName + ", lastName=" + lastName
				+ ", accountName=" + accountName + ", myFilms=" + myFilms.size() + ", unSeenFilms=" + unSeenFilms+ "\n"
				+ ", myRatingsKeys=" + myRatingsKeys.size() + ", ratedFilms=" + ratedFilms.size()
				;
	}


}
