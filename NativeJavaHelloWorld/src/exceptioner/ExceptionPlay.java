package exceptioner;

import java.util.logging.Logger;

public class ExceptionPlay {
	private static final Logger logger = Logger.getLogger(ExceptionPlay.class.toString());

	// need to define all exceptions in the method definition that the method throws
	private void checkedExceptionThrower() throws FilenameTooLongException {
		throw new FilenameTooLongException("corrupt.file", 102);
	}


	private void callerCheckedException() {
		// compiler enforced to add try-catch block other 
		try {
			checkedExceptionThrower();
		} catch (FilenameTooLongException e) {
			logger.info(e.getDescription());
		}
	}

	private void runtimeExceptionThrower() {
		throw new ResourceExhaustedException(202);
	}

	private void callerRuntimeException() {
		runtimeExceptionThrower();
	}

	private void runtimeErrorThrower() {
		throw new OutOfMemoryError();
	}

	private void callerErrorException() {
		runtimeErrorThrower();
	}

	public void exceptionPlay() {
		// Checked Exception - Recoverable Error - SQLEXception/IOException/Network Exception
		// Forced by compiler to be handled using try-catch or adding throws to method definition to force client to handle.
		// recoverable like SQLEXception - retry, IOEXception - Take a new file name
		callerCheckedException();

		// Runtime Exception - Programming Error - DivideByZero, NullPointer
		// Not forced by compiler to be handled using try-catch or adding throws to method definition
		try {
			callerRuntimeException();
		} catch(ResourceExhaustedException ex) {
			// handled to move ahead with the test case
			logger.info("ResourceExhaustedException exception handled to move with the next test");
		}

		// error - third category - used by JVM indicating system resource exception, class not found
		try {
			callerErrorException();
		} catch (OutOfMemoryError ex){
			logger.info("OutOfMemoryError exception handled to move with the next test");
		}
	}
}
