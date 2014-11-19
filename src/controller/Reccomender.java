package controller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import utils.FilmRatingCompare;
import utils.FilmYearCompare;
import utils.Sort;

import com.opencsv.CSVReader;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.StaxDriver;

import edu.princeton.cs.introcs.Out;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;
import model.Film;
import model.Rating;
import model.Member;


public class Reccomender {
	private ArrayList<Member> members;
	private ArrayList<Film> films;
	private Member loggedInMember;


	public Reccomender() throws IOException 
	{
		members = new ArrayList<Member>();
		films = new ArrayList<Film>();
		//		readFilms();
		//		readMembers();
		loadXml();
	}

	public void mapRatingToFilm()
	{
		for(int i=0; i< members.size();i++)
		{
			for(int j=0; j<films.size();j++)
			{
				Member member = members.get(i);
				Film film = films.get(j);
				int ratingKey =  member.getMyRatingsKeys().get(j);
				Rating r = Rating.getRating(ratingKey);
				film.addRating(r);
				member.addFilmRating(film, ratingKey);
				//member.addFilm(film);
			}
		}
	}

	private int dotProduct(ArrayList<Integer> m1Keys,ArrayList<Integer> m2Keys)
	{
		int dotProduct =0;
		for(int j =0; j< m1Keys.size(); j++)
		{
			dotProduct += (m1Keys.get(j) * m2Keys.get(j));
		}
		return dotProduct;
	}

	public int checkSimilarity()
	{
		int similarity = 0;
		Member memberOne = this.getLoggedInMember();
		ArrayList<Integer> m1keys = memberOne.getMyRatingsKeys();
		StdOut.println("which member do u want to compare against?");
		for(int j=0;j<members.size();j++)
		{
			StdOut.println(members.get(j).getAccountName()+ "\n");
		}
		String name = StdIn.readString();
		Member memberTwo = searchForMember(name);
		ArrayList<Integer> m2keys =memberTwo.getMyRatingsKeys();
		similarity = dotProduct(m1keys,m2keys);
		StdOut.print("similarity between " + memberOne.getFirstName() + " " + memberTwo.getFirstName() + "is: " +similarity);
		return similarity;
	}
	
	public void allSimilarites()
	{
		int similarity = 0;
		Member memberOne = this.getLoggedInMember();
		ArrayList<Integer> m1keys = memberOne.getMyRatingsKeys();
		for(Member m : members)
		{  
			if(memberOne != m)
			{
				similarity =0;
				ArrayList<Integer> member2keys =m.getMyRatingsKeys();
				similarity = dotProduct(m1keys,member2keys);
				StdOut.println("Similarity for " + memberOne.getFirstName() + " and "+ m.getAccountName() + " is : "+ similarity + "\n");
			}
		}
	}

	public String getUnSeenFilmsAsString() {
		String result = "";
		for(int i=0;i<films.size();i++)
		{
			Film film = films.get(i);
			if(!(this.loggedInMember.getMyFilms().contains(film)))
			{
				//	StdOut.print(film.getTitle()+ "\n");
				result += film + "\n";//still showing all films even seen ones.
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


	public boolean register(String firstName, String lastName, String accountName){
		boolean result;
		Member m = searchForMember(accountName);
		if(m != null)
		{
			result = false;
		}
		else
		{
			Member newMember = new Member(firstName, lastName, accountName);
			addMember(newMember);
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


	/**
	 * @param member
	 * takes in a member and adds that member to the arrayList of members
	 */
	private void addMember(Member member) {
		members.add(member);
		saveXml();	
	}

	public void deleteMember()
	{
		members.remove(loggedInMember);
		saveXml();	
	}

	public void deleteFilm(Film film)
	{
		films.remove(film);
		saveXml();	
	}
	/**
	 * @param film
	 * method to take in a film and add it to the films array
	 */
	private Film addFilm(Film film) {
		films.add(film);
		for(int i=0; i<members.size();i++)
		{
			members.get(i).addUnseenFilm(film);
		}
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

	public String topTenFilms() {
		String result = "";
		Film film;
		for(int i=0;i<films.size();i++)
		{
			for(int j= i+1; j<films.size();j++)
			{
				Film firstFilm = films.get(i);
				Film nextFilm = films.get(j);

				if(nextFilm.getTotalRatingValue()>firstFilm.getTotalRatingValue())
				{
					film =nextFilm;
					result += film + "\n";
				}
			}
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

	/**
	 * @throws IOException
	 * method that reads in the fim data from a .csv file
	 */

	/**
	 * @throws IOException
	 * methods that reads in the members data from a .csv file
	 */
	private  void readMembers() throws IOException  {
		CSVReader reader = new CSVReader(new FileReader("src/ratings_fx.csv"));
		List<String []> nextLine = reader.readAll();
		for(int i = 0;i < nextLine.size();i = i + 2)
		{
			String[] memberInfo = nextLine.get(i);
			String[] ratingsInfo = nextLine.get(i+1);
			Member newMember = new Member(memberInfo[1], memberInfo[0], memberInfo[2], ratingsInfo);
			members.add(newMember);
		}
	}

	private  void readFilms() throws IOException  {
		CSVReader filmReader = new CSVReader(new FileReader("src/films_fx.csv"));
		List<String []> nextFilmLine = filmReader.readAll();
		for(int i = 0;i < nextFilmLine.size();i++)
		{
			String[] filmInfo = nextFilmLine.get(i);
			String filmIdAsString = filmInfo[0];
			int filmIdAsInt = Integer.parseInt(filmIdAsString);
			String filmYearAsString = filmInfo[2];
			int filmYearAsInt = Integer.parseInt(filmYearAsString);
			Film film = new Film(filmIdAsInt,filmInfo[1],filmYearAsInt,filmInfo[3]);
			films.add(film);
		}
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

	public void loadXml()
	{
		File f = new File("myMembers.xml");
		XStream xtr = new XStream(new StaxDriver());
		members = (ArrayList<Member>) xtr.fromXML(f);

		File fa = new File("myFilms.xml");
		XStream xtream = new XStream(new StaxDriver());
		films = (ArrayList<Film>) xtream.fromXML(fa);
	}

	//
	//	public void listTopRatedFilms()
	//	{
	//		int highestRating = 0;
	//		int tempHighestRating = 0;
	//		for(int i=0; i<films.size();i++)
	//		{
	//			tempHighestRating = films.get(i).getTotalRatingValue();
	//			if(tempHighestRating > highestRating)
	//			{
	//				highestRating = tempHighestRating;
	//			}
	//		}
	//	}

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
