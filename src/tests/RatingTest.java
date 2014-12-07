package tests;

import static org.junit.Assert.*;
import model.Rating;

import org.junit.Before;
import org.junit.Test;

/**
 * @author colum foskin
 * This Junit test class tests the member model
 *
 */
public class RatingTest {
	Rating ratingOne;
	Rating ratingTwo;
	Rating ratingThree;

	@Before
	public void setUp() throws Exception {
		ratingOne = Rating.REALLY_LIKED_IT;
		ratingTwo =Rating.TERRIBLE;
		ratingThree =Rating.OK;
	}

	@Test
	public void test() {
		assertEquals(5, ratingOne.getRatingValue());
		assertEquals(-5, ratingTwo.getRatingValue());
		assertEquals(1, ratingThree.getRatingValue());
		}

}
