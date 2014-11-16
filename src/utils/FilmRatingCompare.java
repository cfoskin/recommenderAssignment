package utils;

import java.util.Comparator;

import model.Film;

public class FilmRatingCompare implements Comparator<Film>{

	@Override
	public int compare(Film f1, Film f2) {
		return f1.getTotalRatingValue() - f2.getTotalRatingValue();		
	}

}
