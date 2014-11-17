package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Member {
	private ArrayList<Integer> myRatingsKeys;
	private String firstName;
	private String lastName;
	private String accountName;
	private Map <Film, Rating> filmRatings;
	private ArrayList<Film> myFilms;

	public Member(String name, String lastName, String accountName){
		this.firstName = name;
		this.lastName = lastName;
		this.accountName = accountName;
		myRatingsKeys = new ArrayList<Integer>();
		filmRatings = new HashMap<>();
	}

	public Member(String name, String account, String lastName, String[] ratings)
	{
		this.filmRatings = new HashMap<>();
		myRatingsKeys = new ArrayList<Integer>();
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
			myRatingsKeys.add(ratingAsInt);
		}
	}

	public boolean addFilmRating(Film film, int rating)
	{
		Rating newRating = Rating.getRating(rating);
		if(newRating == null || filmRatings.containsKey(film))
		{
			return false;
		}
		else 
		{
			filmRatings.put(film, newRating);
		}
		if(!myRatingsKeys.contains(rating))
		{
			myRatingsKeys.add(rating);
		}	
		return true;
	}
	
	public void addFilm(Film film)
	{
		myFilms.add(film);
	}
	
	public Rating getAFilmRating(int rating)
	{
		Rating aRating = Rating.getRating(rating);	
		return aRating;
	}

	public Map<Film, Rating> getFilmRatings() {
		
		return filmRatings;
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
	public ArrayList<Film> getMyFilms() {
		return myFilms;
	}

	public void setMyFilms(ArrayList<Film> myFilms) {
		this.myFilms = myFilms;
	}
	@Override
	public String toString() {
		return "Member, firstName="
				+ firstName + ", lastName=" + lastName + ", accountName="
				+ accountName + ", filmRatings="
				+ filmRatings + "myRatingsKeys=" + myRatingsKeys;
	}


}
