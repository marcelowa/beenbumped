import dao.PersonDao;
import entities.Person;


public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		createPerson();
	}
	
	
	public static void createPerson() {
		PersonDao dao = PersonDao.getInstance();
		Person p = new Person("marcelo.waisman@gmail.com", "Marcelo", "Waisman", "Ramat Gan", "Hachula", 16, "Appartment 12", 52255, "050-8789831", "", "Stam", "Agent Stam", "no phone1", "no phone2", "thenumberoftheinsura");
		dao.create(p);
	}
	
	public static void createUser() {
		
	}

}
