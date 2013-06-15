import dao.PersonDao;
import dao.UserDao;
import entities.Person;
import entities.User;
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		updateUser();
		//createUser();
		//createPerson();
		//getPerson(2);
		//updatePerson(3);
	}
	
	public static void createUser() {
		UserDao dao = UserDao.getInstance();
		User user = new User();
		user.setUsername("myusername2");
		user.setPassword("mypassword2");
		user.setEmail("a mail address2");
		user.setFirstName("a first name2");
		user.setLastName("a last name2");
		user.setCity("the city name ");
		user.setStreetName("the street name");
		//TODO handle better integers, it crushes the application if non integer string is passed here
		user.setHouseNumber(1001);
		user.setAddressDetails("some address details");
		//TODO handle better integers, it crushes the application if non integer string is passed here
		user.setZipcode(20100);
		user.setPhone1("+972-50-8789831");
		user.setPhone2("+972-54-9771333");
		user.setInsuranceCompany("insurance company name");
		user.setInsuranceAgentName("insurance agent name");
		user.setInsurancePhone1("the phone 1");
		user.setInsurancePhone2("the phone 2");
		user.setInsuranceNumber("insurance number");
		
		dao.save(user);
	}
	
	public static void updateUser() {
		UserDao dao = UserDao.getInstance();
		User user = new User();
		user.setUserId(1);
		user.setPersonId(15);
		user.setUsername("completley2");
		user.setPassword("diffrent2");
		user.setEmail("no address2");
		user.setFirstName("no first name2");
		user.setLastName("no last name2");
		user.setCity("no city name2");
		user.setStreetName("the street name");
		//TODO handle better integers, it crushes the application if non integer string is passed here
		user.setHouseNumber(1001);
		user.setAddressDetails("some address details");
		//TODO handle better integers, it crushes the application if non integer string is passed here
		user.setZipcode(20100);
		user.setPhone1("+972-50-8789831");
		user.setPhone2("+972-54-9771333");
		user.setInsuranceCompany("insurance company name");
		user.setInsuranceAgentName("insurance agent name");
		user.setInsurancePhone1("the phone 1");
		user.setInsurancePhone2("the phone 2");
		user.setInsuranceNumber("insurance number");
		
		dao.save(user);
	}
	
	public static void createPerson() {
		PersonDao dao = PersonDao.getInstance();
		Person p = new Person("marcelo.waisman@gmail.commmm", "Marcelowaisman", "Waismandwdq", "Ramat Gan", "Hachula", 16, "Appartment 12", 52255, "050-8789831", "", "Stam", "Agent Stam", "no phone1", "no phone2", "thenumberoftheinsura");
		dao.save(p);
	}
	
	public static boolean updatePerson(int id) {
		PersonDao dao = PersonDao.getInstance();
		Person p = dao.getById(id);
		p.setFirstName("stamhreh xxx");
		p.setLastName("setLastName!!");
		boolean result = dao.save(p);
		return result;
	}
	
	public static Person getPerson(int id) {
		PersonDao dao = PersonDao.getInstance();
		Person p = dao.getById(id);
		System.out.println(p.getPersonId());
		System.out.println(p.getEmail());
		System.out.println(p.getFirstName());
		System.out.println(p.getLastName());
		System.out.println(p.getPhone1());
		return p;
	}

}
