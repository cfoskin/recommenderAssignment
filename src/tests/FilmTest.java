package tests;

import static org.junit.Assert.*;
import model.Film;
import model.Rating;

import org.junit.Before;
import org.junit.Test;
/**
 * @author colum foskin
 * this Junit test class tests the Film Model
 *
 */
public class FilmTest {
	private Film filmOne;
	private Film filmTwo;
	private Rating ratingOne;
	private Rating ratingTwo;

	@Before
	public void setUp() throws Exception {
		filmOne = new Film(1,"Interstellar",2014, "Scifi");
		filmTwo = new Film(2,"Maleficent",2014, "Fantasy");
		ratingOne = Rating.REALLY_LIKED_IT;
		ratingTwo = Rating.TERRIBLE;
	}
	@Test
	public void testFilm() {
		assertNotNull(filmOne);
		assertNotNull(filmTwo);
		}
	
	@Test
	public void testGetTotalRatingValue()
	{
		filmOne.addRating(ratingOne);
		assertEquals(5,filmOne.getTotalRatingValue());
		filmOne.addRating(ratingOne);
		assertEquals(10,filmOne.getTotalRatingValue());
		filmOne.addRating(ratingTwo);
		assertEquals(5,filmOne.getTotalRatingValue());
	} 

	@Test
	public void testAddRating()
	{
		filmOne.addRating(ratingOne);
		filmOne.addRating(ratingOne);
		assertEquals(10,filmOne.getTotalRatingValue());
		assertNotNull(filmOne.getTotalRatingValue());
	} 
	
	@Test
	public void testGetNumOfViewers()
	{
		filmOne.addRating(ratingOne);
		assertEquals(1,filmOne.getNumOfViewers());
		filmOne.addRating(ratingTwo);
        assertEquals(2, filmOne.getNumOfViewers());
		assertNotNull(filmOne.getNumOfViewers());
	} 
	
	@Test
	public void testGetTitle()
	{
		assertEquals("Interstellar",filmOne.getTitle());
		assertEquals("Maleficent",filmTwo.getTitle());
	} 
	
	@Test
	public void testGetId()
	{
		assertEquals(1,filmOne.getId());
		assertEquals(2,filmTwo.getId());
	} 
	@Test
	public void testGetGenre()
	{
		assertEquals("Scifi",filmOne.getGenre());
		assertEquals("Fantasy",filmTwo.getGenre());
	} 
	
	@Test
	public void testGetYear()
	{
		assertEquals(2014,filmOne.getYear());
		assertEquals(2014,filmTwo.getYear());
	} 
	
//	@Test
//	public void testGetRatings()
//	{
//		filmOne.addRating(ratingOne);
//		filmOne.addRating(ratingTwo);
//		filmTwo.addRating(ratingOne);
//		filmTwo.addRating(ratingTwo);
//		assertEquals(2,filmOne.getRatings().size());
//		assertEquals(2,filmTwo.getRatings().size());
//		assertFalse(filmOne.getRatings().size() == 0);
//	}

}
