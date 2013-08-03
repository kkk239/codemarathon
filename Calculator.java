package codemarathon;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Vector;

class Unit {
	String unit;
	String unit_s;
	float meters;
}

public class Calculator {

	static String[][] UNIT = { { "mile", "miles" }, { "yard", "yards" },
			{ "inch", "inches" }, { "foot", "feet" }, { "fath", "faths" },
			{ "furlong", "furlongs" } };

	static Vector<Unit> units = new Vector<Unit>();

	static String newline = System.getProperty("line.separator");

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			File fout = new File("output.txt");
			if (fout.exists()) {
				fout.delete();
				fout.createNewFile();
			}
			BufferedWriter bw = new BufferedWriter(new FileWriter(fout));
			bw.write("kkk239@msn.com" + newline);

			DecimalFormat df = new DecimalFormat("0.00");
			File fin = new File("input.txt");
			BufferedReader br = new BufferedReader(new FileReader(fin));
			String line = br.readLine();
			while (line != null) {
				if (line.contains("=")) {
					readUnit(line);
				} else if (line.trim().length() > 0) {
					bw.write(newline + df.format(calculator(line)) + " m");
				}
				line = br.readLine();
			}
			br.close();
			bw.flush();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	static void readUnit(String line) {
		String[] tmp = line.split("=");
		String[] firstparam = tmp[0].trim().split(" ");
		String[] secondparam = tmp[1].trim().split(" ");
		for (String[] u : UNIT) {
			if (u[0].equals(firstparam[1])) {
				Unit _u = new Unit();
				_u.unit = u[0];
				_u.unit_s = u[1];
				_u.meters = Float.parseFloat(secondparam[0]);
				units.addElement(_u);
				return;
			}
		}
	}

	static float calculator(String line) {
		float result = .0f;
		if (line.contains("+")) {
			String[] params = line.split("[+]");
			for (String param : params) {
				result += calculator(param);
			}
		} else if (line.contains("-")) {
			String[] params = line.trim().split("-");
			result += (calculator(params[0]) * 2);
			for (String param : params) {
				result -= calculator(param);
			}
		} else {
			String[] params = line.trim().split(" ");
			for (Unit u : units) {
				if (params[1].equals(u.unit) || params[1].equals(u.unit_s)) {
					result = Float.parseFloat(params[0]) * u.meters;
				}
			}
		}
		return result;
	}

}