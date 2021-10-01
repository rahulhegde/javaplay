package functionInterface;

@FunctionalInterface
interface ConvertDataTypeHelper <F, T> {
	T Convert(F from);
}