import dao.PersonDao;
import entities.Person;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//createPerson();
		//getPerson(2);
		updatePerson(3);
	}
	
	
	public static void createPerson() {
		PersonDao dao = PersonDao.getInstance();
		Person p = new Person("marcelo.waisman@gmail.com", "Marcelo", "Waisman", "Ramat Gan", "Hachula", 16, "Appartment 12", 52255, "050-8789831", "", "Stam", "Agent Stam", "no phone1", "no phone2", "thenumberoftheinsura");
		dao.create(p);
	}
	
	public static boolean updatePerson(int id) {
		PersonDao dao = PersonDao.getInstance();
		Person p = dao.get(id);
		p.setFirstName("stamhreh");
		p.setLastName("setLastName runn!!");
		boolean result = dao.update(p);
		return result;
	}
	
	public static Person getPerson(int id) {
		PersonDao dao = PersonDao.getInstance();
		Person p = dao.get(id);
		System.out.println(p.getPersonId());
		System.out.println(p.getEmail());
		System.out.println(p.getFirstName());
		System.out.println(p.getLastName());
		System.out.println(p.getPhone1());
		return p;
	}

}
