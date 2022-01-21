package mockitodemo;

/**
 * Hello world!
 *
 */
public class AppContainer 
{	
	private App app;

	int getProperty() {
		return app.getProperty();
	}
	
	int multiple(int i, int j) {
		return app.multiple(i, j);
	}
	
	int addition (int i, int j) {
		return app.addition(i, j);
	}
}