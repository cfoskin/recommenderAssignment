package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import model.Film;
import model.Member;
import model.Rating;
import org.junit.Before;
import org.junit.Test;

/**
 * @author colum foskin
 * this Junit test class tests the Member model.
 *
 */
public class MemberTest {
	private Member memberOne;
	private Member memberTwo;
	private Member memberThree;

	private Film filmOne;
	private Film filmTwo;
	private Film filmThree;
	private Rating ratingOne;
	private Rating ratingTwo;

	@Before
	public void setUp() throws Exception {
		memberOne = new Member("Colum", "Foskin", "foskin", "pass");
		memberTwo = new Member("John", "Foskin", "foskin", "pass");
	    memberThree = new Member("Homer", "Simpson", "homer", "pass");
		filmOne = new Film(1,"Interstellar",2014, "Scifi");
		filmTwo = new Film(2,"Maleficent",2014, "Fantasy");
		filmThree= new Film(3,"Lion king",2014, "Fantasy");
		ratingOne = Rating.REALLY_LIKED_IT;
		ratingTwo = Rating.TERRIBLE;
	}

	@Test
	public void testMember()
	{
		assertNotNull(memberOne);
		assertNotNull(memberTwo);
		assertTrue(memberOne.getFirstName().equals("Colum"));
		assertTrue(memberOne.getAccountName().equals("foskin"));
		assertTrue(memberOne.getLastName().equals("Foskin"));
		assertTrue(memberOne.getPassWord().equals("pass"));
		assertFalse(memberOne.getFirstName().equals("John"));
		assertFalse(memberOne.getPassWord().equals("wrongPassword"));
	}

	@Test
	public void testGetMyFilms() 
	{
		memberOne.addFilmRating(filmOne, ratingOne);
		memberOne.addFilmRating(filmTwo, ratingOne);
		assertEquals(2, memberOne.getMyFilms().size());
		assertTrue(memberOne.getMyFilms().get(1).equals(filmTwo));
	}

	@Test
	public void testRatings()
	{
		memberOne.addFilmRating(filmOne, ratingOne);
		memberOne.addFilmRating(filmOne, ratingTwo);
		assertFalse(memberOne.getRatedFilms().get(filmOne).equals(ratingTwo));
		assertTrue(memberOne.getRatedFilms().get(filmOne).equals(ratingOne));
	}

	@Test
	public void testGetRatedFilms()
	{
		memberOne.addFilmRating(filmOne, ratingOne);
		memberOne.addFilmRating(filmTwo, ratingTwo);
		assertEquals(ratingOne,memberOne.getRatedFilms().get(filmOne));
		assertEquals(ratingTwo,memberOne.getRatedFilms().get(filmTwo));
		assertNotEquals(ratingOne, ratingTwo);
		assertEquals(2, memberOne.getRatedFilms().size());
	}

	@Test
	public void testCheckFilmsInCommon()
	{
		memberOne.addFilmRating(filmOne, ratingOne);
		memberOne.addFilmRating(filmTwo, ratingOne);
		memberOne.addFilmRating(filmThree, ratingOne);
		memberTwo.addFilmRating(filmOne, ratingOne);
		ArrayList<Film> commonFilms = memberOne.checkFilmsInCommon(memberTwo);
		assertTrue(commonFilms.contains(filmOne));
		assertFalse(commonFilms.contains(filmTwo));
		assertEquals(1, commonFilms.size());
		assertNotNull(commonFilms.size());
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
		assertTrue(mostSimilar.getFirstName().equals("Colum"));
		assertNotNull(mostSimilar);
	}
	@Test
	public void testfindReccomendedFilms()
	{
		ArrayList<Member> members = new ArrayList<Member>();
		members.add(memberOne);
		members.add(memberTwo);
		members.add(memberThree);
		memberOne.addFilmRating(filmOne, ratingOne);
		memberOne.addFilmRating(filmTwo, ratingOne);
		memberOne.addFilmRating(filmThree, ratingOne);
		memberTwo.addFilmRating(filmOne, ratingTwo);
		memberTwo.addFilmRating(filmTwo, ratingTwo);
		memberThree.addFilmRating(filmOne, ratingOne);
		memberThree.addFilmRating(filmTwo, ratingOne);
		Member mostSimilar = memberThree.findMostSimilarMember(members);
		
		assertTrue(mostSimilar.equals(memberOne));
		assertFalse(mostSimilar.equals(memberTwo));
	
		assertTrue(memberThree.findRecommendedFilms(mostSimilar).contains(filmThree));
		assertEquals(1, memberThree.findRecommendedFilms(mostSimilar).size());
		assertTrue(memberThree.findRecommendedFilms(mostSimilar).get(0).getTitle().equals("Lion king"));
		assertFalse(memberThree.findRecommendedFilms(mostSimilar).get(0).getTitle().equals("Interstellar"));
		assertFalse(memberThree.findRecommendedFilms(mostSimilar).isEmpty());
	}
}

















