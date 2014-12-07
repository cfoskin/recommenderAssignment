package model;

/**
 * @author Colum foskin
 * this enum is Rating model where all functions relating to the Rating are performed.
 *
 */
public enum Rating {
	TERRIBLE(-5),
	DIDNT_LIKE_IT(-3),
	HAVENT_SEEN_IT(0),
	OK(1), 
	LIKED_IT(3), 
	REALLY_LIKED_IT(5);

	private int rating;

	/**
	 * @param rating
	 */
	private Rating(int rating){
		this.rating = rating;
	}

	/**
	 * @return
	 */
	public int getRatingValue() {
		return this.rating;
	}
}