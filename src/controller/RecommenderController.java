package controller;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import utils.FilmRatingCompare;
import utils.FilmYearCompare;
import utils.Sort;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;
import model.Film;
import model.Member;
import model.Rating;


/**
 * @author Colum Foskin
 *  version 1.0
 *  This is the main controller for the recommender system, it is the link between the views and the models.
 */
public class RecommenderController {
	protected ArrayList<Member> members;
	private ArrayList<Film> films;
	protected Member loggedInMember;

	public RecommenderController()
	{
		members = new ArrayList<Member>();
		films = new ArrayList<Film>();
		loadXml();
	}

	public ArrayList getReccomendedFilms() {
		Member mostSimilarMember = this.loggedInMember.findMostSimilarMember(members);
		ArrayList<Film> reccomendedFilms = this.loggedInMember.findRecommendedFilms(mostSimilarMember);
		return reccomendedFilms;
	}

	/**
	 * @return
	 * this method returns a list of films for the user to choose one to
	 * rate. it checks if the user has already rated a film and if it has 
	 * then it does not display it in the list of films the user. users are only allowed to rate a film once
	 * so they cannot inflate ratings or deflate ratings.
	 */
	public ArrayList displayUnseenFilmsForRating() {
		ArrayList<Film>  unSeenFilms= new ArrayList<Film>();
		for(int i=0;i<films.size();i++)
		{
			Film film = films.get(i);
			if(!(this.loggedInMember.getMyFilms().contains(film)))
			{
				unSeenFilms.add(film);
			}
		}
		Sort sort = new Sort(new FilmYearCompare());
		sort.selectionSort(unSeenFilms);
		Collections.reverse(unSeenFilms);//displaying the sorted list from top down instead of bottom up.
		return unSeenFilms;		
	}

	/**
	 * @param film
	 * @param rating
	 * @return this method allows the user to rate a film and returns true if rating was successful.
	 */
	public boolean rateAFilm(Film film, Rating rating) {
		boolean result;
		film.addRating(rating);//this rating value can then be used to calculate total rating value for each film.
		boolean success =loggedInMember.addFilmRating(film, rating);
		if (success)
		{
			result = true; 
			saveXml();
		}
		else
		{
			result =false;
		}
		return result;
	}
	/**
	 * @param accountName
	 * @return
	 * method that searches for a member by account name and returns true if found
	 * this is to check if an account name is already taken.
	 */
	private Member searchForMember(String enteredName) 
	{
		Member m = null;
		for (int i=0 ; i<members.size(); i++) 
		{	
			Member temp = members.get(i);
			String accountName = temp.getAccountName();
			if (accountName.equals(enteredName))
			{	
				m = temp; 
			}
		}
		return m;
	}

	/**
	 * @param firstName
	 * @param lastName
	 * @param accountName
	 * @param passWord
	 * @return this method registers a new user once confirmed they are choosing a correct account name.
	 */
	public boolean register(String firstName, String lastName, String accountName, String passWord){
		boolean result;
		Member m = searchForMember(accountName);
		if(m != null)
		{
			result = false;
		}
		else
		{
			Member newMember = new Member(firstName, lastName, accountName, passWord);
			addMember(newMember);
			saveXml();	
			this.loggedInMember = newMember;
			result = true;
		}
		return result;
	}

	/**
	 * @param accountName
	 * @param password
	 * @return this method logs in a member once the account name and password are correct.
	 */
	public boolean logInMember(String accountName, String password)
	{
		boolean result;
		Member m = searchForMember(accountName);
		if(m != null && m.getPassWord().equals(password))
		{
			this.loggedInMember = m;
			result = true;
		}	
		else
		{
			result = false;
		}
		return result;
	}

	/**
	 * @param member
	 * takes in a member and adds that member to the arrayList of members
	 */
	private void addMember(Member member) {
		members.add(member);
	}

	/**
	 * @param member
	 * this method deletes a member from the system
	 */
	public void deleteMember(Member member)
	{
		members.remove(member);
		saveXml();	
	}

	/**
	 * @param film
	 * @return this method deletes a film from the system if it exists.
	 * it also removes the film and rating for the film from each member.
	 */
	public boolean deleteFilm(Film film)
	{
		boolean result;
		if(films.contains(film))
		{
			films.remove(film);
			result = true;
			for(int i=0; i<members.size();i++)
			{
				if(members.get(i).getMyFilms().contains(film))
				{
					members.get(i).getMyFilms().remove(film);
					members.get(i).getRatedFilms().remove(film);
				}
			}
		}
		else
		{
			result = false;
		}
		saveXml();
		return result;	
	}
	/**
	 * @param film
	 * method to take in a film and add it to the films array
	 */
	private Film addFilm(Film film) {
		films.add(film);
		saveXml();
		return film;
	}

	/**
	 * @param id
	 * @return this method is used to ensure the id given is valid and not already used
	 */
	public Film searchForFilm(int id)
	{
		Film film = null;
		for(int i=0;i<films.size();i++)
		{			

			Film temp = films.get(i);
			int filmId = temp.getId();
			if(filmId == id)
			{
				film = temp;
			}
		}
		return film;
	}

	/**
	 * @param id
	 * @param title
	 * @param year
	 * @param genre
	 * @return method to create a new film and add it to the system
	 * returns true if successful.
	 */
	public boolean createFilm(int id, String title, int year, String genre)
	{
		boolean result;
		Film film = new Film(id, title, year, genre);
		if(addFilm(film) == null)
		{
			result = false;
		}
		else
		{
			return true;
		}
		return result;
	}

	/**
	 * @return this method sorts the films in the system by rating value (Highest first)
	 */
	public ArrayList<Film> sortFilmsByRating()
	{		
		Sort sort = new Sort(new FilmRatingCompare());
		sort.selectionSort(films);
		Collections.reverse(films);//display highest on top of the list
		return films;
	}

	/**
	 * @return this method sorts the films in the system by newest first.
	 */
	public ArrayList<Film> sortFilmsByYear()
	{		
		Sort sort = new Sort(new FilmYearCompare());
		sort.selectionSort(films);
		Collections.reverse(films);//display newest on top of the list
		return films;
	}

	/**
	 * this method saves the arrays of members and films in the system to xml file
	 */
	public void saveXml()
	{
		Out out = new Out("myMembers.xml");
		XStream xtr = new XStream(new StaxDriver());
		out.println(xtr.toXML(members));
		out.close();

		Out o = new Out("myFilms.xml");
		XStream xtream = new XStream(new StaxDriver());
		o.println(xtream.toXML(films));
		o.close();
	}

	/**
	 * this method loads the arrays of members and films in to the system from an xml file
	 */
	@SuppressWarnings("unchecked")
	public void loadXml()
	{
		try {
			File f = new File("myMembers.xml");
			XStream xtr = new XStream(new StaxDriver());
			members = (ArrayList<Member>) xtr.fromXML(f);
		} catch (Exception e1) {
			StdOut.print("No members file matey  ");
		}

		try {
			File fa = new File("myFilms.xml");
			XStream xtream = new XStream(new StaxDriver());
			films = (ArrayList<Film>) xtream.fromXML(fa);
		} catch (Exception e) {
			StdOut.print("  No films file matey");
		}
	}
	/**
	 * @return
	 */
	public ArrayList<Member> getMembers() {
		return members;
	}

	/**
	 * @return
	 */
	public ArrayList<Film> getFilms() {
		return films;
	}

	/**
	 * @return
	 */
	public Member getLoggedInMember() {
		return loggedInMember;
	}
	/**
	 * @param loggedInMember
	 */
	public void setLoggedInMember(Member loggedInMember) {
		this.loggedInMember = loggedInMember;
	}
}
