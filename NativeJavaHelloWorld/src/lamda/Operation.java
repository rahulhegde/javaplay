package lamda;

import java.util.function.DoubleBinaryOperator;

public enum Operation {
    PLUS("+", (x, y) -> {
        // Lambda expression has no access to instance variable 
    	// https://stackoverflow.com/questions/67823427/why-cant-the-instance-member-be-accessed-from-the-lambda-of-the-enum-constructo
    	//System.out.println(symbol);
        return x + y;
    }),
    MINUS("-", (x, y) -> x - y),
    TIMES("*", (x, y) -> x * y),
    DIVIDE("/", (x, y) -> x / y);

    Operation(String symbol, DoubleBinaryOperator op) {
        this.symbol = symbol;
        this.op = op;
    }

    public String getSymbol() {
        return symbol;
    }

    protected final String symbol;
    private final DoubleBinaryOperator op;

    public double apply(double x, double y) {
        return op.applyAsDouble(x, y);
    }
}