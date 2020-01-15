import org.jasypt.util.text.BasicTextEncryptor;

public class Temp {

	public static void main(String[] args) {
		BasicTextEncryptor passwordEncryptor = new BasicTextEncryptor();
		passwordEncryptor.setPassword("abc123");
		String encryptedPassword = passwordEncryptor.encrypt("Postgres.1234");
		System.out.println(encryptedPassword);

	}

}
