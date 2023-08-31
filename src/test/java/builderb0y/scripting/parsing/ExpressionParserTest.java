package builderb0y.scripting.parsing;

import org.junit.jupiter.api.Test;

import builderb0y.scripting.TestCommon;

/**
for anyone wondering, the reason why all the successful tests have
unnecessary spaces in them is to verify that spaces are ignored properly.
please do not use these tests as a style guide.
*/
public class ExpressionParserTest extends TestCommon {

	@Test
	public void testBasicSyntax() throws ScriptParsingException {
		assertFail("Unexpected end of input", "");
		assertSuccess(1, "1");
		assertSuccess(1, "1.0");
		assertFail("Expected fractional part of number", "1.");
		assertFail("Unknown prefix operator: .", ".1");
		assertFail("Unknown prefix operator: .", ".1.0");
		assertFail("Multiple radix points", "1.0.");
		assertFail("Multiple radix points", "1.0.0");
		assertSuccess(256, "2x1p8");
		assertSuccess(256.0D, "2x1.0p8");
		assertSuccess(1.0D / 256.0D, "2x1p-8");
		assertSuccess(1.0D / 256.0D, "2x1.0p-8");
		assertSuccess(2, "1 + 1");
		assertSuccess(6, "2 + 2 * 2");
		assertSuccess(6, "2 * 2 + 2");
		assertSuccess(8, "2 * 2 + 2 * 2");
		assertSuccess(8, "2 + 2 * 2 + 2");
		assertSuccess(6, "2 + 2 + 2");
		assertSuccess(8, "2 * 2 * 2");
		assertSuccess(8, "( 2 + 2 ) * 2");
		assertSuccess(8, "2 * ( 2 + 2 )");
		assertSuccess(4, "-2 ^ 2");
		assertSuccess(4, "- 2 ^ 2");
		assertSuccess(4, "int x = 2 ,, -x ^ 2");
		assertSuccess(4, "int x = 2 ,, - x ^ 2");
		assertSuccessExactType(Integer.MIN_VALUE, Integer.toString(Integer.MIN_VALUE));
		assertSuccessExactType(Long.MIN_VALUE, Long.toString(Long.MIN_VALUE));
		assertFail("3 / 4 cannot be represented exactly as an int. Try doing 3.0 / 4.0 instead", "3 / 4");
		assertFail("Unexpected end of input", "(");
		assertFail("Expected ')'", "(2");
		assertFail("Unexpected end of input", "(2 +");
		assertFail("Expected ')'", "(2 + 2");
		assertFail("Expected ')'", "((2 + 2)");
		assertFail("Unexpected character: )", ")");
		assertFail("Unexpected trailing character: )", "2)");
		assertFail("Unexpected trailing character: )", "+ 2)");
		assertFail("Unexpected trailing character: )", "2 + 2)");
		assertFail("Unexpected trailing character: )", "(2 + 2))");
		assertSuccess(256, "2 ^ 2 ^ 3");
		assertSuccess(1, "4 / 2 ^ 2");
		assertSuccess(-1, "int x = -1 int y = 4 x / y"); //assert rounding towards -∞.
		assertSuccess(5, "sqrt ( 3 ^ 2 + 4 ^ 2 )");
		assertSuccess(5, "`sqrt` ( 3 ^ 2 + 4 ^ 2 )");
		assertFail(
			"""
			Unknown variable: sqrt
			Candidates:
				Function sqrt: functionInvokeStatic: public static pure java/lang/Math.sqrt(D)D
			Actual form: sqrt""",
			"sqrt"
		);
		assertFail("Not a statement", "2 3");
		assertFail("Unreachable statement", "return(2) return(3)");
		assertFail("Not a statement", "int x = 2 ,, x ,, x");
	}

	@Test
	public void testDescriptiveIdentifierErrorMessages() {
		assertFail(
			"""
			Unknown variable: exp
			Candidates:
				Function exp: functionInvokeStatic: public static pure java/lang/Math.exp(D)D
			Actual form: exp""",
			"exp"
		);
		assertFail(
			"""
			Unknown function or incorrect arguments: e
			Candidates:
				Variable e: ConstantInsnTree of type double (constant: 2.718281828459045 of type double)
			Actual form: e()""",
			"e ( )"
		);
		assertFail(
			"""
			Unknown field: getKey
			Candidates:
				Method interface java.util.Map$Entry extends java/lang/Object.getKey: methodInvoke: public abstract java/util/Map$Entry.getKey()Ljava/lang/Object; (interface)
			Actual form: LoadInsnTree of type java.util.Map$Entry (not constant).getKey""",
			"MapEntry entry = null ,, entry . getKey"
		);
		assertFail(
			"""
			Unknown method or incorrect arguments: key
			Candidates:
				Field interface java.util.Map$Entry extends java/lang/Object.key: fieldInvoke: public abstract java/util/Map$Entry.getKey()Ljava/lang/Object; (interface)
			Actual form: LoadInsnTree of type java.util.Map$Entry (not constant).key()""",
			"MapEntry entry = null ,, entry . key ( )"
		);
	}

	@Test
	public void testPrint() throws ScriptParsingException {
		evaluate("print ( 'a: ', 1, ', b: ' , 2 ) ,, 0");
		assertSuccess("$10", "'$$10'");
		assertSuccess("a: 1, b: 2", "'a: $1, b: $2'");
		assertSuccess("a: 1, b: 2", "'a: $.1, b: $.2'");
		assertSuccess("a: 1, b: 2", "'a: $(1), b: $(2)'");
		assertSuccess("a: 1, b: 2", "'a: $.(1), b: $.(2)'");
		assertSuccess("a: 1, b: 2", "int a = 1 ,, int b = 2 ,, 'a: $a, b: $b'");
		assertSuccess("2^2 = 4", "int square ( int x : x * x ) ,, int x = 2 ,, '$x^2 = $square ( x )'");
		assertSuccess("a 42 b", "class X(int x) 'a $.X.new(42).x b'");
		assertSuccess("a: 1, b: 2", "int a = 1 int b = 2 '$:a, $:b'");
		assertSuccess("box.value: 42", "class Box(int value) Box box = new(42) '$:.box.value'");
	}

	@Test
	public void testComments() throws ScriptParsingException {
		assertSuccess(2, "2 ;comment");
		assertSuccess(2, "2 ; comment");
		assertSuccess(2,
			"""
			int identity(int x: x)
			identity ( 3 ) ;comment
			2
			"""
		);
		assertSuccess(2,
			"""
			int identity(int x: x)
			identity ( 3 ) ; comment
			2
			"""
		);
		assertSuccess(2,
			"""
			int identity(int x: x)
			identity ( 3 )
			;comment
			2
			"""
		);
		assertSuccess(2,
			"""
			int identity(int x: x)
			identity ( 3 )
			; comment
			2
			"""
		);
		assertSuccess(2, ";;3;; 2");
		assertSuccess(2, ";; 3 ;; 2");
		assertSuccess(2, ";;;; 2");
		assertFail("Un-terminated multi-line comment", ";;");
		assertFail("Un-terminated multi-line comment", ";;comment");
		assertSuccess(2, ";() 2");
		assertSuccess(2, ";(comment) 2");
		assertSuccess(2, ";((comment)) 2");
		assertSuccess(2, ";((comment)(more comments)) 2");
		assertSuccess(2, ";(fakeFunction(fakeArgument)) 2");
		assertFail("Mismatched parentheses in comment", ";(");
		assertFail("Mismatched parentheses in comment", ";(comment");
		assertFail("Mismatched parentheses in comment", ";(()");
		assertFail("Mismatched parentheses in comment", ";((comment)");
	}
}