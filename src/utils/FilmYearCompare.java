package utils;
import java.util.Comparator;

import model.Film;

public class FilmYearCompare implements Comparator<Film> {

	@Override
	public int compare(Film f1, Film f2) {
		return f1.getYear() - f2.getYear();
	}
}
