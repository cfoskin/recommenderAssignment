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

	public Member(String name, String lastName , String accountName){
		this.firstName = name;
		this.lastName = lastName;
		this.accountName = accountName;
		ratedFilms = new HashMap<>();
		myFilms = new ArrayList<Film>();
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
		return true;
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

	public Map<Film, Rating> ratedFilms() {

		return ratedFilms;
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
				+ ", accountName=" + accountName + ", myFilms=" + myFilms.size() + "ratedFilms=" + ratedFilms.size()
				;
	}


}
