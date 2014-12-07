package tests;

import static org.junit.Assert.*;
import java.util.ArrayList;
import model.Film;
import model.Member;
import model.Rating;
import org.junit.Before;
import org.junit.Test;
import controller.RecommenderController;

/**
 * @author colum foskin
 * this J unit class tests the Recommender Controller
 *
 */
public class RecControllerTest {
	private Member memberOne;
	private Member memberTwo;
	private Member memberThree;
	private Film filmOne;
	private Film filmTwo;
	private Film filmThree;
	private Rating ratingOne;
	private Rating ratingTwo;
	private RecommenderController rec;

	@Before
	public void setUp() throws Exception {
		rec = new RecommenderController();
		memberOne = new Member("memberone", "lastname", "accName", "pass");
		memberTwo = new Member("memberTwo", "lastname", "accName", "pass");
		memberThree = new Member("Homer", "Simpson", "homer", "pass");
		filmOne = new Film(1,"Interstellar",2014, "Scifi");
		filmTwo = new Film(2,"Maleficent",2014, "Fantasy");
		filmThree= new Film(3,"Lion king",2014, "Fantasy");
		ratingOne = Rating.REALLY_LIKED_IT;
		ratingTwo = Rating.DIDNT_LIKE_IT;
	}

	@Test
	public void getMembers() 
	{
		ArrayList<Member> members = this.rec.getMembers();
		members.add(memberOne);
		assertTrue(this.rec.getMembers().get(members.size()-1).equals(memberOne));
		members.remove(memberOne);
	}

	@Test
	public void getFilms() 
	{	
		Film filmA = new Film(260,"filma",2014, "Scifi");
		ArrayList<Film> films = this.rec.getFilms();
		films.add(filmA);
		assertTrue(this.rec.getFilms().get(films.size()-1).equals(filmA));
		films.remove(filmA);
	}

	@Test
	public void getAndSetLoggedInMember() 
	{	
		this.rec.setLoggedInMember(memberOne);
		assertTrue(this.rec.getLoggedInMember().equals(memberOne));
		assertFalse(this.rec.getLoggedInMember().equals(memberTwo));
	}

	@Test
	public void testFindMostSimilarMember()
	{
		ArrayList<Member> members = new ArrayList<Member>();
		members.add(memberOne);
		members.add(memberTwo);
		members.add(memberThree);
		memberOne.addFilmRating(filmOne, ratingOne);
		memberOne.addFilmRating(filmTwo, ratingOne);
		memberTwo.addFilmRating(filmOne, ratingTwo);
		memberTwo.addFilmRating(filmTwo, ratingTwo);
		memberThree.addFilmRating(filmOne, ratingOne);
		memberThree.addFilmRating(filmTwo, ratingOne);
		Member mostSimilar = memberThree.findMostSimilarMember(members);
		assertTrue(mostSimilar.equals(memberOne));
		assertFalse(mostSimilar.equals(memberTwo));
		assertTrue(mostSimilar.getFirstName().equals("memberone"));
		assertNotNull(mostSimilar);
	}
	@Test
	public void testdisplayUnseenFilmsForRating() 
	{
		memberOne.addFilmRating(filmOne, ratingOne);
		memberOne.addFilmRating(filmTwo, ratingOne);
		memberTwo.addFilmRating(filmOne, ratingTwo);
		memberTwo.addFilmRating(filmTwo, ratingTwo);
		memberThree.addFilmRating(filmOne, ratingOne);
		memberThree.addFilmRating(filmTwo, ratingOne);
		this.rec.setLoggedInMember(memberOne);
		assertTrue(this.rec.displayUnseenFilmsForRating().contains(filmThree));
		assertFalse(this.rec.displayUnseenFilmsForRating().contains(filmTwo));
	}

	@Test
	public void testRateAFilm() 
	{
		this.rec.setLoggedInMember(memberOne);
		this.rec.rateAFilm(filmOne, ratingOne);
		assertTrue(memberOne.getRatedFilms().containsKey(filmOne));
		assertFalse(memberOne.getRatedFilms().containsKey(filmTwo));
	}

	@Test
	public void testRegister() 
	{
		this.rec.register("memberone", "lastname", "accName", "pass");
		int newestMembersIndex = (this.rec.getMembers().size()-1);
		assertTrue(this.rec.getMembers().get(newestMembersIndex).getFirstName().equals("memberone"));

		assertFalse(this.rec.register("memberone", "lastname", "accName", "pass"));
		//trying to register again with the same acc name which should return false
	}

	@Test
	public void testlogInMember() 
	{
		this.rec.register("memberone", "lastname", "accName", "pass");
		assertTrue(this.rec.logInMember("accName", "pass"));
		assertFalse(this.rec.logInMember("accName", "wrongpassword"));
	}

	@Test
	public void testDeleteMember() 
	{
		ArrayList<Member> members = this.rec.getMembers();
		members.add(memberOne);
		members.add(memberTwo);
		members.add(memberThree);
		this.rec.deleteMember(memberOne);
		this.rec.deleteMember(memberTwo);
		this.rec.deleteMember(memberThree);
		assertFalse(members.contains(memberOne));
		assertFalse(members.contains(memberTwo));
		assertFalse(members.contains(memberThree));
	}


	@Test
	public void testDeleteFilm() 
	{
		Film filmB = new Film(500,"film b",2014, "Fantasy");
		ArrayList<Film> films = this.rec.getFilms();
		films.add(filmB);
		memberTwo.addFilmRating(filmB, ratingOne);
		assertTrue(this.rec.deleteFilm(filmB));
	}

	@Test
	public void testSearchForFilm() 
	{
		Film filmA = new Film(200,"filma",2014, "Scifi");
		Film filmB = new Film(201,"film b",2014, "Fantasy");
		ArrayList<Film> films = this.rec.getFilms();
		films.add(filmA);
		films.add(filmB);
		Film foundFilm = this.rec.searchForFilm(200);
		assertTrue(foundFilm.equals(filmA));
		assertFalse(foundFilm.equals(filmB));
		this.rec.deleteFilm(filmA);
		this.rec.deleteFilm(filmB);
	}
	@Test
	public void testCreateFilm() 
	{
		Film filmA = new Film(260,"filma",2014, "Scifi");
		assertTrue(this.rec.createFilm(260,"filma",2014, "Scifi"));
		this.rec.deleteFilm(filmA);
	}

	@Test
	public void testSortFilmsByRating() 
	{
		ArrayList<Film> films = this.rec.getFilms();
		this.rec.sortFilmsByRating();//sort them first
		int lowestValue =films.get(films.size()-1).getTotalRatingValue();
		int highestValue = films.get(0).getTotalRatingValue();
		assertTrue(lowestValue < highestValue);
		assertFalse(highestValue < lowestValue);
	}

	@Test
	public void testSortFilmsByYear() 
	{
		ArrayList<Film> films = this.rec.getFilms();
		this.rec.sortFilmsByYear();//sort them first
		int oldestYear =films.get(films.size()-1).getYear();
		int highestYear = films.get(0).getYear();
		assertTrue(highestYear > oldestYear);
		assertFalse(oldestYear > highestYear);

	}

}











