package view;
import java.io.IOException;
import model.Film;
import model.Rating;
import controller.Reccomender;
import edu.princeton.cs.introcs.StdIn;
import edu.princeton.cs.introcs.StdOut;

public class UI {

	Reccomender rec = new Reccomender();
	public UI()throws IOException{

		int option = menu();
		while (option != 0) {
			switch (option) {
			case 1:
				register();		
				break;
			case 2:
				logIn();
				break;
			case 3:
				seeAllMembers();
				break;
			case 4:
				filmsOptions();
				break;
			case 5:
				this.rec.saveXml();
				break;
			case 6:
				this.rec.mapRatingToFilm();
				break;
			case 7:
				StdOut.print(this.rec.checkSimilarity());
				
				break;
			default:
				this.rec.saveXml();
				break;
			}
			option = menu();
		}
		StdOut.println("Exiting... bye");
	}

	private void listFilmsByRating() {
		String filmsList = this.rec.getFilmsSortedByRating();
		StdOut.print(filmsList);
	}

	private void listFilmsByYear() {
		String filmsList = this.rec.getFilmsSortedByYear();
		StdOut.print(filmsList);
	}
	
	private void listTopTenFilms() {
		String filmsList = this.rec.topTenFilms();
		StdOut.print(filmsList);
	}


	private void seeAllMembers() {
		String membersList = this.rec.getMembersAsString();
		StdOut.print(membersList);
	}

	private void rateAFilm() {
		StdOut.print("which film do you want to rate? Please enter id of your chosen film: " + "\n");
		listFilmsByRating();
		String filmId = StdIn.readString();
		Film film = this.rec.chooseFilm(filmId);
		if(!this.rec.getLoggedInMember().getFilmRatings().containsKey(film))
		{
			StdOut.print("please enter your rating for " + film.getTitle() + "\n");
			StdOut.print(" -5 = Terrible" + "\n" + " -3 = Didn't like it" + "\n" + " 0 = Haven't seen it" 
					+ "\n" + " 1 = OK" + "\n" + " 3 = Liked it" + "\n" + " 5 = Really liked it" + "\n");
			int rating = StdIn.readInt();
			Rating r = Rating.getRating(rating);
			//r.setM(this.rec.getLoggedInMember());
			film.addRating(r);
		//	this.rec.getLoggedInMember().addFilm(film);
			//StdOut.print(r.getM().getFirstName() + " " + r.getM().getLastName() + "  ");
			boolean success = this.rec.getLoggedInMember().addFilmRating(film, rating);
			if(!success )
			{
				StdOut.println("enter a valid rating from the list");
			}
			else
			{
				StdOut.println("you have rated the film: " + film.getTitle() + " and your rating is:  " + this.rec.getLoggedInMember().getAFilmRating(rating)+ "\n");
			}
		}
		else
		{
			StdOut.println("Sorry, you have already rated this film!");
		}
	}

	private void createFilm() {
		StdOut.println("Enter new Films title: ");
		String title  = StdIn.readString();
		StdOut.println("Enter the new films id: ");
		int id = StdIn.readInt();
		StdOut.println("Enter the new films genre: ");
		String genre = StdIn.readString();
		StdOut.println("Enter the new films release year: ");
		int year = StdIn.readInt();
		boolean success = this.rec.createFilm(id, title, year, genre);
		if(success)
		{
			StdOut.println("Congratulations! youve added: " + title + "" + genre + " to the library" + "\n");
		}
	}

	private void logIn() {
		StdOut.println("Please enter account name to log in: ");
		String accountName  = StdIn.readString();
		boolean success = this.rec.logIn(accountName);
		if(success){
			StdOut.println("Welcome "+ this.rec.getLoggedInMember().getFirstName() + " " + this.rec.getLoggedInMember().getLastName() + "\n");
			userOptions();
		}
		else{
			StdOut.println("you need to register to continue!!"+ "\n");
		}				
	}

	private void register() {
		StdOut.println("Welcome to the registration menu: ");
		StdOut.println("Enter new members first name: ");
		String firstName  = StdIn.readString();
		StdOut.println("Enter new members last name: ");
		String lastName  = StdIn.readString();
		StdOut.println("Enter new members account name: ");
		String accountName  = StdIn.readString();

		boolean success = this.rec.register(firstName, lastName, accountName);

		if(success){
			StdOut.println("Welcome  " + firstName +  "!" + "your account has been created" + "\n");
			StdOut.println("would you like to log in? - y/n ");
			String reply = StdIn.readString();
			if(reply.equals("y"))
			{
				logIn();
			}
			else 
				StdOut.println("OK - GoodBye!" + "\n");
		}
		else{
			StdOut.print("Sorry this account name is already in use, please choose a different one." + "\n");
		}
	}

	private void viewMyRatedFilms() {
		if(this.rec.getLoggedInMember().getFilmRatings().isEmpty())
		{
			StdOut.println(this.rec.getLoggedInMember().getFirstName() + ", You have not rated any Films yet!" + "\n");
		}
		else
		{
			StdOut.println(this.rec.getLoggedInMember().getFirstName() + " , your rated films are : " + 
					this.rec.getLoggedInMember().getFilmRatings().keySet() + "\n");
		}
	}

//	private void checkOutMyFilms(){
//		StdOut.print("which film do you want to view your rating for? Please enter id of your chosen film: " + "\n");
//		listFilms();
//		String filmId = StdIn.readString();
//		Film film = this.rec.chooseFilm(filmId);
//		if(this.rec.getLoggedInMember().getFilmRatings().containsKey(film))
//		{
//			Rating rating = null;
//			for(int i=0;i<film.getRatings().size();i++)
//			{
//				Rating r = film.getRatings().get(i);
//				if(r == this.rec.getLoggedInMember().getFilmRatings().get(film));
//				rating  =r;
//			}
//			StdOut.println("you have rated the film: " + film.getTitle() + " and your rating is:  " + rating + "\n");
//		}
//		else
//		{
//			StdOut.println("Sorry, you havent rated this film yet!");
//		}
//	}

	private void filmsOptions(){
		int option = filmsMenu();

		while (option != 0) {
			switch (option) {
			case 1:
				listFilmsByRating();
				break;
			case 2:
				listFilmsByYear();
				break;
			default:
				break;
			}
			option = filmsMenu();
		}
	}

	private int filmsMenu() {
		StdOut.println(" Welcome to menu." + "\n"
				+ "Please select an option" + "\n"
				+ "=========================>>>>");
		StdOut.println("1) list all films sorted by rating");
		StdOut.println("2) list all films sorted by year");

		StdOut.println("0) Exit" + "\n");
		int option = StdIn.readInt();
		return option;
	}

	private void userOptions(){
		int option = userMenu();

		while (option != 0) {
			switch (option) {
			case 0:
				this.rec.setLoggedInMember(null);
				break;
			case 1:
				rateAFilm();
				break;
			case 2:
				createFilm();
				break;
			case 3:
				viewMyRatedFilms();
				break;
			default: 
				this.rec.setLoggedInMember(null);
				break;
			}
			option = userMenu();
		}
	}

	private int userMenu() {
		StdOut.println(" Welcome to menu." + "\n"
				+ "Please select an option" + "\n"
				+ "=========================>>>>");
		StdOut.println("1) Rate a Film");
		StdOut.println("2) Add a film to the library"); 
		StdOut.println("3) List all the films i have rated");
		StdOut.println("0) Log out" + "\n");
		int option = StdIn.readInt();
		return option;
	}

	private int menu() {
		StdOut.println(" Welcome to menu." + "\n"
				+ "Please select an option" + "\n"
				+ "=========================>>>>");
		StdOut.println("1) register");
		StdOut.println("2) log in");
		StdOut.println("3) list all members");
		StdOut.println("4) Films lists");
		StdOut.println("5) Save all");
		StdOut.println("6) Map user ratings to films");
		StdOut.println("7) check similiarity");
		StdOut.println("0) Exit ==>>" + "\n");
		int option = StdIn.readInt();
		return option;
	}
}