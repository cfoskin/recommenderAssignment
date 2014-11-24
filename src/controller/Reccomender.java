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
	private ArrayList<Member> members;
	private ArrayList<Film> films;
	private Member loggedInMember;


	public Reccomender()
	{
		members = new ArrayList<Member>();
		films = new ArrayList<Film>();
		loadXml();
	}

	private int dotProduct(ArrayList<Integer> memberOneKeys,ArrayList<Integer> memberTwoKeys)
	{
		int dotProduct =0;
		for(int i =0; i< memberOneKeys.size(); i++)
		{ 

			dotProduct += (memberOneKeys.get(i) * memberTwoKeys.get(i));
		}
		return dotProduct;
	}

	private ArrayList<Film> checkFilmsInCommon (Member memberOne, Member memberTwo)
	{
		ArrayList<Film> commonFilms = new ArrayList<Film>();
		ArrayList<Film> m1Films = memberOne.getMyFilms();
		ArrayList<Film> m2Films = memberTwo.getMyFilms();
		for(int i=0; i< m1Films.size(); i++)
		{
			for(int j = 0; j< m2Films.size();j++)
			{
				if(m1Films.get(i) == m2Films.get(j))
				{
					Film film = m1Films.get(i);
					if(!(commonFilms.contains(film)))
					{
						commonFilms.add(film);
					}
				}
			}
		}
		return commonFilms;
	}

	private Member findMostSimilarMember()
	{
		int mostSimilar =0 ;
		Member memberOne = this.getLoggedInMember();
		Member tempMember = null;
		Member mostSimilarMember = null;
		for(Member m : members)
		{  		
			ArrayList<Integer> memberOneKeys = new ArrayList<Integer>();
			ArrayList<Integer> tempMemberKeys = new ArrayList<Integer>();
			int similarity = 0;
			if(memberOne != m)
			{
				tempMember = m;
				ArrayList<Film> commonFilms = checkFilmsInCommon(memberOne, tempMember);
				for(int i =0; i< commonFilms.size(); i++)
				{
					Film film = commonFilms.get(i);
					int m1Rating = memberOne.getRatedFilms().get(film).getRatingValue();
					memberOneKeys.add(m1Rating);
					int m2Rating =  tempMember.getRatedFilms().get(film).getRatingValue();
					tempMemberKeys.add(m2Rating);
				}
				similarity = dotProduct(memberOneKeys, tempMemberKeys);
			}
			if(similarity >= mostSimilar)
			{
				mostSimilar = similarity;
				mostSimilarMember = tempMember;
			}
		}
		StdOut.println("the most similar is: " + mostSimilarMember.getFirstName() + 
				" "  + mostSimilarMember.getLastName() +  " and the similarity is : "+ mostSimilar + "\n");
		return mostSimilarMember;
	}

	private ArrayList<Film> findReccomendedFilms()
	{
		Member member = this.loggedInMember;
		Member mostSimilarMember = findMostSimilarMember();
		ArrayList<Film> reccomendedFilms = new ArrayList<Film>();
		for(int i=0;i < mostSimilarMember.getMyFilms().size();i++)
		{
			Film film = mostSimilarMember.getMyFilms().get(i);
			if(!(member.getMyFilms().contains(film)))
			{
				reccomendedFilms.add(film);
			}
		}
		if(reccomendedFilms.isEmpty())
		{
			StdOut.print("You have seen all the films " + mostSimilarMember.getFirstName() + " has seen! " );
		}
		else
		{
			StdOut.print("These are the films reccommended by " + mostSimilarMember.getFirstName()+ reccomendedFilms + "\n");
		}
		return reccomendedFilms;
	}

	public String getReccomendedFilms() {
		
		ArrayList<Film> unseenFilms = findReccomendedFilms();
		String result = "";
		for(int i=0;i<unseenFilms.size();i++)
		{
			Film film = unseenFilms.get(i);
			result += film + "\n";
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
		File f = new File("myMembers.xml");
		XStream xtr = new XStream(new StaxDriver());
		members = (ArrayList<Member>) xtr.fromXML(f);

		File fa = new File("myFilms.xml");
		XStream xtream = new XStream(new StaxDriver());
		films = (ArrayList<Film>) xtream.fromXML(fa);
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
