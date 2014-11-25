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
	private String passWord;
	private Map <Film, Rating> ratedFilms;
	private ArrayList<Film> myFilms;

	public Member(String name, String lastName , String accountName, String passWord){
		this.firstName = name;
		this.lastName = lastName;
		this.accountName = accountName;
		this.passWord = passWord;
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
	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public ArrayList<Film> checkFilmsInCommon (Member memberTwo)
	{
		ArrayList<Film> commonFilms = new ArrayList<Film>();
		ArrayList<Film> m1Films = getMyFilms();
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

	public Member findMostSimilarMember(ArrayList<Member> members)
	{
		int mostSimilar =0 ;
		Member tempMember = null;
		Member mostSimilarMember = null;
		for(Member m : members)
		{  		
			ArrayList<Integer> memberOneKeys = new ArrayList<Integer>();
			ArrayList<Integer> tempMemberKeys = new ArrayList<Integer>();
			int similarity = 0;
			if(!this.equals(m))
			{
				tempMember = m;
				ArrayList<Film> commonFilms = this.checkFilmsInCommon(tempMember);
				for(int i =0; i< commonFilms.size(); i++)
				{
					Film film = commonFilms.get(i);
					int m1Rating = this.getRatedFilms().get(film).getRatingValue();
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
		return mostSimilarMember;
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

	public ArrayList<Film> findRecommendedFilms(Member mostSimilarMember)
	{
		ArrayList<Film> recommendedFilms = new ArrayList<Film>();
		for(int i=0;i < mostSimilarMember.getMyFilms().size();i++)
		{
			Film film = mostSimilarMember.getMyFilms().get(i);
			if(!(this.getMyFilms().contains(film)))
			{
				recommendedFilms.add(film);
			}
		}
		return recommendedFilms;
	}
	
	@Override
	public String toString() {
		return "Member [firstName=" + firstName + ", lastName=" + lastName
				+ ", accountName=" + accountName + ", passWord=" + passWord
				+ ", myFilms=" + myFilms.size() + "]";
	}
}
