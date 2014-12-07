package model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import edu.princeton.cs.introcs.StdOut;
import utils.FilmRatingCompare;
import utils.Sort;

/**
 * @author colum foskin
 * This class is the Member model where all functions relating to the Member are performed.
 */
public class Member {
	private String firstName;
	private String lastName;
	private String accountName;
	private String passWord;
	private Map <Film, Rating> ratedFilms;
	private ArrayList<Film> myFilms;//used this as it was more suitable then the map for displaying films in a list form.


	public Member(String name, String lastName , String accountName, String passWord){
		this.firstName = name;
		this.lastName = lastName;
		this.accountName = accountName;
		this.passWord = passWord;
		ratedFilms = new HashMap<>();
		myFilms = new ArrayList<Film>();
	}

	/**
	 * @param film
	 * @param rating
	 * @return this method takes in the film and rating from the recommenderController class
	 * and adds these to the member object. 
	 */
	public boolean addFilmRating(Film film, Rating rating)
	{
		if(rating == null || ratedFilms.containsKey(film))//not allowing the user to rate the same film twice.
		{
			return false;
		}
		else 
		{
			ratedFilms.put(film, rating);
			myFilms.add(film);
		}
		return true;
	}


	/**
	 * @param memberTwo
	 * @return this method checks the films that this member and another member
	 *  have seen and returns an array of these films. this array can then be used to 
	 *  calculate the dot product of two members to see how similar they are, this avoids 
	 *  checking irrelevant data. 
	 */
	public ArrayList<Film> checkFilmsInCommon (Member memberTwo)
	{
		ArrayList<Film> commonFilms = new ArrayList<Film>();
		ArrayList<Film> m1Films = getMyFilms();
		ArrayList<Film> m2Films = memberTwo.getMyFilms();
		for(int i=0; i< m1Films.size(); i++)
		{
			for(int j = 0; j< m2Films.size();j++)
			{
				if(m1Films.get(i).equals(m2Films.get(j)))
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

	/**
	 * @param memberOneKeys
	 * @param memberTwoKeys
	 * @return this method takes in the two arrays of ratings used in the findMostSimilarMember()
	 * method and calculates the similarity of the two members.
	 * 
	 */
	private int dotProduct(ArrayList<Integer> memberOneKeys,ArrayList<Integer> memberTwoKeys)
	{
		int dotProduct =0;
		for(int i =0; i< memberOneKeys.size(); i++)
		{ 
			dotProduct += (memberOneKeys.get(i) * memberTwoKeys.get(i));
		}
		return dotProduct;
	}

	/**
	 * @param members
	 * @return this method takes in the array of members passed in from the
	 * RecommenderController.getReccomendedFilms() method. 
	 */
	public Member findMostSimilarMember(ArrayList<Member> members)
	{
		int mostSimilar =0 ;
		Member tempMember = null;
		Member mostSimilarMember = null;
		for(Member m : members)
		{  		
			ArrayList<Integer> memberOneRatings = new ArrayList<Integer>();
			ArrayList<Integer> tempMemberRatings = new ArrayList<Integer>();
			int similarity = 0;
			if(!this.equals(m))// don't check against this.member.
			{
				tempMember = m;
				ArrayList<Film> commonFilms = this.checkFilmsInCommon(tempMember);//getting the array of films they have both rated
				for(int i =0; i< commonFilms.size(); i++)
				{//this loop retrieves the ratings that each member has given every film in the common films arraylist.
					Film film = commonFilms.get(i);
					int memberOneRating = this.getRatedFilms().get(film).getRatingValue();
					memberOneRatings.add(memberOneRating);
					int tempMemberRating =  tempMember.getRatingForfilm(film).getRatingValue();
					tempMemberRatings.add(tempMemberRating);
				}
				try {
					similarity = dotProduct(memberOneRatings, tempMemberRatings);//this is then used to calculate the similarity of two members.
				} catch (Exception e) {
				}
			}
			if(similarity > mostSimilar)
			{
				mostSimilar = similarity;
				mostSimilarMember = tempMember;
			}
		}
		return mostSimilarMember;
	}

	/**
	 * @param film
	 * @return I had to implement this method which is called in the findMostSimilarMember() above 
	 * as I was using a film object as a key and this was causing mapping problems when trying to retrieve the 
	 * tempMembers rating for the film.
	 */
	private Rating getRatingForfilm(Film film) {
		Object[] arrayOfRatedFilms = this.ratedFilms.keySet().toArray();
		Rating ratingOfFilm =  null;
		for(Object aFilm : arrayOfRatedFilms){
			int indexOfFilm = this.myFilms.indexOf(aFilm);
			ratingOfFilm = this.ratedFilms.get(this.myFilms.get(indexOfFilm));
		}
		return ratingOfFilm;
	}

	/**
	 * @param mostSimilarMember
	 * @return this method returns the arraylist of films that are recommended by only the 
	 * most similar member to this.member. this function is called in the getReccomendedFilms() method
	 * in the RecommenderController class.
	 */
	public ArrayList<Film> findRecommendedFilms(Member mostSimilarMember)
	{
		ArrayList<Film> recommendedFilms = new ArrayList<Film>();
		try {
			for(int i=0;i < mostSimilarMember.getMyFilms().size();i++)
			{
				Film film = mostSimilarMember.getMyFilms().get(i);
				if(!(this.getMyFilms().contains(film)))//check to see if i have already rated and seen this film.
				{
					recommendedFilms.add(film);//if not add it to the list of recommended films for this.member
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "You have seen all the films that member most similar to you has seen!");
		}
		return recommendedFilms;
	}
	/**
	 * @return
	 * returns the map of rated films
	 */
	public Map<Film, Rating> getRatedFilms() {
		return ratedFilms;
	}

	/**
	 * @return
	 * returns a sorted array of films that ive rated
	 */
	public ArrayList<Film> getMyFilms() {
		Sort sort = new Sort(new FilmRatingCompare());
		sort.selectionSort(myFilms);
		Collections.reverse(myFilms);
		return myFilms;
	}

	/**
	 * @return returns the members firstname
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @return returns the members last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @return returns the members account name
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * @return returns the users password
	 */
	public String getPassWord() {
		return passWord;
	}

	@Override
	public String toString() {
		return firstName + " " + lastName + "  "+accountName + "  number of fims rated: " + this.getMyFilms().size();
	}
}
