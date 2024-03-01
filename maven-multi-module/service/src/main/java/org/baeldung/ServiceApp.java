package org.baeldung;

/**
 * Hello world!
 *
 */
public class ServiceApp {

	/**
	 * 1. docker run -d --name sonarqube -p 9000:9000 sonarqube:9.9-community
	 * 2. create user and toker to be used
	 * 3. mvn clean verify sonar:sonar -Dsonar.token= -Dsonar.login= -Dsonar.password= -Dsonar.url=localhost:9000
	 */
	public static void main(String[] args) {
		CoreApp sa = new CoreApp();
		System.out.println(sa.checkStringv11("Hello World!"));
	}
}
