package model;

import java.util.HashMap;
import java.util.Map;

import edu.princeton.cs.introcs.StdOut;

public enum Rating {
	TERRIBLE(-5),
	DIDNT_LIKE_IT(-3),
	HAVENT_SEEN_IT(0),
	OK(1), 
	LIKED_IT(3), 
	REALLY_LIKED_IT(5);

	private int rating;
	private static Map<Integer, Rating> ratingsMap;;

	static {
		ratingsMap = new HashMap<>();
		ratingsMap.put(-5, TERRIBLE);
		ratingsMap.put(-3, DIDNT_LIKE_IT);
		ratingsMap.put(0, HAVENT_SEEN_IT);
		ratingsMap.put(1, OK);
		ratingsMap.put(3, LIKED_IT);
		ratingsMap.put(5, REALLY_LIKED_IT);
	}

	private Rating(int rating){
		this.rating = rating;
	}

	public int getRatingValue() {
		return this.rating;
	}

	public static Rating getRating(int key) {
		return ratingsMap.get(key);
	}
	
}