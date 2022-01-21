package lamda;
import java.awt.Button;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LambdaPlay 
{
	int memberVariable;
	static int staticVariable = 100;

	public LambdaPlay() {
	}

	public LambdaPlay(FunctionalInterfaceAddNumbers localVariableLamdaTest) {
		localVariableLamdaTest.sum(1, 2);
	}
	
	public void simplePublicScopePrint() {
		System.out.println("simplePublicScopePrint");
	}

	private void simplePrivateScopePrint() {
		System.out.println("simplePrivateScopePrint");
	}
	
	public void TestLamdaPlay() {
		System.out.println("** Entering Lambda Test ***");

		//LamdaPlay_ListenerLamda();
		
		// add two numbers using lambda
		LamdaPlay_AddUtility();
		
		// convert string to int using static method reference	
		LamdaPlay_StringIntConversion();
		
		LambdaPlay_AccessingVariables(2);
		
		LambdaPlay_AnonymousObjectAccessingDefaultInterfaceMethod();
		
		LambdaPlay_AccessingVariables_OnArgument((a,b) -> {
			int c = a + b;
			System.out.println("LambdaPlay_AccessingVariables_OnArgument - memberVariable: " + this.memberVariable);
			System.out.println("LambdaPlay_AccessingVariables_OnArgument - staticVariable: " + staticVariable);
			System.out.println("LambdaPlay_AccessingVariables_OnArgument - sum: " + c );
			return c;
		});
		
//		LambdaPlay_ConsumerObject();
//		LambdaPlay_ListPrint();
	}
	
	void LamdaPlay_AddUtility() {
		// Lambda expressions can be stored in variables if 
		// the variable's type is an interface which has only one method.
		FunctionalInterfaceAddNumbers myFunctionObject = (a, b) ->  a + b;
		System.out.println("FunctionalInterfaceAddNumbers: sum(2,3) is " + myFunctionObject.sum(2, 3));

		HelperClass helperClass = new HelperClass();
		FunctionalInterfaceAddNumbers myFunctionObject1 = helperClass::sum;
		System.out.println("FunctionalInterfaceAddNumbers using object method reference, sum(2,3) is " + myFunctionObject1.sum(2, 3));
	}
	
	void LamdaPlay_StringIntConversion() {
		// Lambda expressions can be stored in variables if 
		// the variable's type is an interface which has only one method.
		FunctionalInterfaceConvertStringToInteger myFunctionObject1 = (from) -> Integer.valueOf(from);
		System.out.println("FunctionalInterfaceConvertStringToInteger using lambda: " + myFunctionObject1.Convert("2"));
		
		FunctionalInterfaceConvertStringToInteger myFunctionObject2 = Integer::valueOf;
		System.out.println("FunctionalInterfaceConvertStringToInteger using static method reference: " + myFunctionObject2.Convert("2"));

		HelperClass helperClass = new HelperClass();
		FunctionalInterfaceConvertStringToInteger myFunctionObject3 = helperClass::convertStringToInt;
		System.out.println("FunctionalInterfaceConvertStringToInteger using object method reference: " + myFunctionObject3.Convert("2"));
	}
	
	void LambdaPlay_AccessingVariables(int methodArg1) {
		// this localVariable should be final or effective final (value not changed after lamda definition)
		// this is also true if an 
		int localVariable = 10;
		memberVariable = 20;
		staticVariable = 30;
		
		FunctionalInterfaceAddNumbers staticAndMemberClassLamdaTest = (a, b) -> { return a + memberVariable + staticVariable + b; };
		System.out.println("FunctionalInterfaceAddNumbers - accessing member + static variable: " + staticAndMemberClassLamdaTest.sum(1, 2));
		
		// compilation failure - local 
		FunctionalInterfaceAddNumbers localVariableLamdaTest = (a, b) -> { return a + localVariable + methodArg1 + b; };
		// compilation failure un-comment
		// localVariable = 20;
		// methodArg1 = 20; 
		System.out.println("FunctionalInterfaceAddNumbers - accessing local variable: " + localVariableLamdaTest.sum(1, 2));
		
		
		FunctionalInterfaceAddNumbers accessMethodLambda = (a, b) -> { 
			this.simplePrivateScopePrint();
			this.simplePublicScopePrint();
			return a + b; 
		};
		System.out.println("FunctionalInterfaceAddNumbers - accessing local variable: " + accessMethodLambda.sum(1, 2));
	}

	void LambdaPlay_AccessingVariables_OnArgument (FunctionalInterfaceAddNumbers localVariableLamdaTest) {
		localVariableLamdaTest.sum(100, 200);
	}

	
	void LambdaPlay_AnonymousObjectAccessingDefaultInterfaceMethod( ) {
		
		// accessing interface default or extension methods using Anonymous Object
		// same is not possible using Lamda (functional implementation)
		FunctionalInterfaceAddNumbers adder = new FunctionalInterfaceAddNumbers() {
			
			@Override
			public int sum(int a, int b) {
				this.printer();
				return a + b;
			}
		};		
		System.out.println("FunctionalInterfaceAddNumbers - accessing local variable: " + adder.sum(1, 2));
	}

		
	
	void LambdaPlay_ConsumerObject() {
		// in-built Consumer Function
		Consumer<Integer> consumerFunctionObject = (n) -> { System.out.println("integer using Consumer Function: " + n); };
		consumerFunctionObject.accept(100);
	}
	
	void LambdaPlay_ListPrint() {
		 List<String> list=new ArrayList<String>();  
	       list.add("Rick");         
	       list.add("Negan");       
	       list.add("Daryl");         
	       list.add("Glenn");         
	       list.add("Carl");                
	       
	       // lambda expression
	       list.forEach((names)->System.out.println("lambda expression: " + names)); 
	       
	       // method reference ?
	       list.forEach(System.out::println);
	}
	
	void LamdaPlay_ListenerLamda( ) {
		Frame frame=new Frame("ActionListener - Java8");  

		Button b=new Button("Click Here");  
		b.setBounds(50,100,80,50);  

		// lamda for event listener action
		b.addActionListener((e) -> { System.out.println("hello world 1");} );  
		
		// traditional way of listener action registration
		b.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("hello world 2");				
			}
		});
		
		frame.add(b);

		frame.setSize(200,200);  
		frame.setLayout(null);  
		frame.setVisible(true);   
	}
}