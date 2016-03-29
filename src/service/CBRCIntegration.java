package service;

import java.util.Random;

public class CBRCIntegration {

	public String feedback0(){
		Random r = new Random();
		int randNum = r.nextInt(4);
		String[] feedbacks = {"Feedback Level: 0\n"
				+ "\n"
				+ "Here is the solution to further assist you in your programming issue:\n\n"
				+ "#include <stdio.h>\n"
				+ "int main()\n"
				+ "{\n"
				+ "\tint x, y, z;\n"
				+ "\tx = 4;\n"
				+ "\ty = 1;\n"
				+ "\tz = x * y;\n"
				+ "\n"
				+ "\twhile (z <= 496)\n"
				+ "\t{\n"
				+ "\t\tprintf(\"%d \", z);\n"
				+ "\t\ty++;\n"
				+ "\t\tz = x*y;\n"
				+ "\t}\n"
				+ "\tgetch();\n"
				+ "\treturn 0;\n"
				+ "}\n",
				"Feedback Level: 1"
				+ "\n"
				+ "In line 16: remove the getch();.",
				"Feedback Level: 2"
				+ "\n"
				+ "You will just need to remove the getch() in your code.",
				"Feedback Level: 3"
				+ "\n"
				+ "The only way to solve this issue is to remove getch() in your code in line 16, there may be other solutions besides this one."};
		return feedbacks[randNum];
	}
}
