package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.stmt.ExpressionStmt;

public class AutoGrader {

	public boolean testUserInputHandling(String filePath) throws IOException {
		System.out.println("Starting testUserInputHandling with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		boolean hasScannerUsage = false;
		boolean hasArithmeticOperation = false;

		// Log the usage of Scanner
		System.out.println("------ Scanner Usage ------");
		for (ExpressionStmt expr : cu.findAll(ExpressionStmt.class)) {
			if (expr.toString().contains("new Scanner(System.in)")) {
				hasScannerUsage = true;
				System.out.println("✓ Found usage of Scanner for input.");
			}
		}

		// Log the arithmetic operation (multiplying the input integer by 2) and ignore
		// comments
		System.out.println("------ Arithmetic Operation ------");

		// Loop through all the expressions
		for (ExpressionStmt expr : cu.findAll(ExpressionStmt.class)) {
			boolean isCommented = false;

			// Check if the expression is inside a comment
			for (Comment comment : cu.getComments()) {
				if (comment.getContent().contains(expr.toString())) {
					isCommented = true;
					break;
				}
			}

			// Only check non-commented expressions for the arithmetic operation
			if (!isCommented && expr.toString().contains("* 2")) {
				hasArithmeticOperation = true;
				System.out.println("✓ Found arithmetic operation: Multiplying input by 2.");
			}
		}

		// Check if both Scanner usage and arithmetic operation are present
		System.out.println("Has Scanner usage: " + hasScannerUsage);
		System.out.println("Has arithmetic operation: " + hasArithmeticOperation);

		boolean result = hasScannerUsage && hasArithmeticOperation;
		System.out.println("Test result: " + result);

		return result;
	}

}
