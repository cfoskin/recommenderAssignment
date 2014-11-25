package controller;
import java.io.File;
import java.util.ArrayList;

import utils.FilmRatingCompare;
import utils.FilmYearCompare;
import utils.Sort;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdOut;
import model.Film;
import model.Member;


public class Reccomender {
	public ArrayList<Member> members;
	private ArrayList<Film> films;
	public Member loggedInMember;


	public Reccomender()
	{
		members = new ArrayList<Member>();
		films = new ArrayList<Film>();
		loadXml();
	}

	public String getReccomendedFilms() {
		Member mostSimilarMember = this.loggedInMember.findMostSimilarMember(members);
		ArrayList<Film> reccomendedFilms = this.loggedInMember.findRecommendedFilms(mostSimilarMember);
		String result = "";
		if(reccomendedFilms.isEmpty())
		{
			result = "You have seen all the films " + mostSimilarMember.getFirstName() + " has seen! " +"\n";
		}
		else
		{
			result = "These are the films that are reccommended by " + mostSimilarMember.getFirstName()+"\n";
			Sort sort = new Sort(new FilmRatingCompare());
			sort.selectionSort(reccomendedFilms);
			for(int i=0;i<reccomendedFilms.size();i++)
			{
				Film film = reccomendedFilms.get(i);
				result += film + "\n";
			}
		}
		return result;		
	}

	public String getUnSeenFilmsAsString() {
		String result = "";
		for(int i=0;i<films.size();i++)
		{
			Film film = films.get(i);
			if(!(this.loggedInMember.getMyFilms().contains(film)))
			{
				result += film + "\n";
			}
		}
		return result;		
	}

	/**
	 * @param accountName
	 * @return
	 * method that searches for a member by account name and returns true if found
	 */
	public Member searchForMember(String enteredName) 
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
			result = true;
		}
		return result;
	}

	public boolean logIn(String accountName)
	{
		boolean result = false;
		Member m = searchForMember(accountName);
		if(m != null)
		{
			this.loggedInMember = m;
			result = true;
		}
		return result;
	}

	public boolean checkPassWord(String accountName, String passWord)
	{
		boolean result = logIn(accountName);
		if(result == true && this.loggedInMember.getPassWord().equals(passWord))
		{
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

	public void deleteMember()
	{
		members.remove(loggedInMember);
		saveXml();	
	}

	public void deleteFilm(Film film)
	{
		films.remove(film);
		for(int i=0; i<members.size();i++)
		{
			if(members.get(i).getMyFilms().contains(film))
			{
				members.get(i).getMyFilms().remove(film);
				members.get(i).getRatedFilms().remove(film);
			}
		}
		saveXml();	
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
	 * @param filmId
	 * @return
	 */
	public int convertToInt(String filmId)
	{
		int filmIdAsInt;
		if(filmId.charAt(0) == '-')
		{
			filmId = filmId.substring(1);
			filmIdAsInt = new Integer(filmId) * -1;
		}
		else
		{
			filmIdAsInt = new Integer(filmId);
		}
		return filmIdAsInt;
	}

	/**
	 * @param id
	 * @return
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
	 * @return
	 */
	public Film chooseFilm(String filmId)
	{	
		int filmIdAsInt =convertToInt(filmId);
		Film film = searchForFilm(filmIdAsInt);
		return film;
	}

	/**
	 * method to create a new film and add it to the system
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

	private ArrayList<Film> sortFilmsByRating()
	{		
		Sort sort = new Sort(new FilmRatingCompare());
		sort.selectionSort(films);
		return films;
	}

	private ArrayList<Film> sortFilmsByYear()
	{		
		Sort sort = new Sort(new FilmYearCompare());
		sort.selectionSort(films);
		return films;
	}

	public String getFilmsSortedByRating() {
		sortFilmsByRating();
		String result = "";
		for(int i=0;i<films.size();i++)
		{
			result += films.get(i)+ "\n";
		}
		return result;
	}

	public String getFilmsSortedByYear() {
		sortFilmsByYear();
		String result = "";
		for(int i=0;i<films.size();i++)
		{
			result += films.get(i)+ "\n";
		}
		return result;
	}

	public String getMembersAsString() {
		String result = "";
		for(int i=0;i<members.size();i++)
		{
			result += members.get(i)+ "\n";
		}
		return result;	
	}

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

	@SuppressWarnings("unchecked")
	public void loadXml()
	{
		try {
			File f = new File("myMembers.xml");
			XStream xtr = new XStream(new StaxDriver());
			members = (ArrayList<Member>) xtr.fromXML(f);
		} catch (Exception e1) {
			StdOut.print("No members file matey");
		}

		try {
			File fa = new File("myFilms.xml");
			XStream xtream = new XStream(new StaxDriver());
			films = (ArrayList<Film>) xtream.fromXML(fa);
		} catch (Exception e) {
			StdOut.print("No films file matey");
		}
	}

	public void setLoggedInMember(Member loggedInMember) {
		this.loggedInMember = loggedInMember;
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
}
