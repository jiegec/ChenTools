package com.chenfuzi.chentoolslib;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ChenTools {
	public static final String[] m = { "请选择你想要的工具。",
			"1.已知二次函数过三点(x1,y1)(x2,y2)(x3,y3)求二次函数解析式。",
			"2.已知二次函数顶点(x,y)，经过点(x0,y0)。", "3.已知二次函数过两点(x1,y1)(x2,y2)，对称轴x=z。",
			"4.输入一组数据，得出众数、中位数、平均数、方差、标准差、极差。", "5.求一个表达式的值（只能输入+-*/()和数字）。",
			"6.已知一次函数过(x1,y1),(x2,y2),求一次函数解析式", "7.输入一个二次函数解析式，输出二次函数的信息" };
	public static String version = "2014.01.07.8";

	public interface InputOutput {
		void writeToConsole(String str);

		String getInput() throws InterruptedException;

		void inputError();

		void wantNumber();

		void wantText();
	}

	public static InputOutput io;

	@SuppressWarnings("resource")
	public static String httpDownload(String httpUrl) {
		URL url = null;
		try {
			url = new URL(httpUrl);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "";
		}

		try {
			URLConnection conn = url.openConnection();
			InputStream inStream = conn.getInputStream();
			Scanner s = new Scanner(inStream).useDelimiter("\\A");
			return s.hasNext() ? s.next() : "";
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	private static Double getDouble() throws InterruptedException {
		String d;
		Double ret;
		io.wantNumber();
		while (true) {
			d = io.getInput();
			try {
				ret = Double.valueOf(d);
			} catch (Exception e) {
				io.inputError();
				continue;
			}
			break;
		}
		io.writeToConsole(d);
		return ret;
	}

	private static Integer getInteger() throws InterruptedException {
		String d;
		Integer ret;
		io.wantNumber();
		while (true) {
			d = io.getInput();
			try {
				ret = Integer.valueOf(d);
			} catch (Exception e) {
				io.inputError();
				continue;
			}
			break;
		}
		io.writeToConsole(d);
		return ret;
	}

	public static void work(int tool) {
		switch (tool) {
		case 1:
			tool1();
			break;
		case 2:
			tool2();
			break;
		case 3:
			tool3();
			break;
		case 4:
			tool4();
			break;
		case 5:
			tool5();
			break;
		case 6:
			tool6();
			break;
		case 7:
			tool7();
			break;
		default:
			break;
		}
	}

	private static void format1(Double a, Double b, Double c) {
		io.writeToConsole("\ny=ax^2+bx+c");
		io.writeToConsole("\na=" + String.valueOf(a));
		io.writeToConsole("\nb=" + String.valueOf(b));
		io.writeToConsole("\nc=" + String.valueOf(c));
	}

	private static void format2(Double k, Double b) {
		io.writeToConsole("\ny=kx+b");
		io.writeToConsole("\nk=" + String.valueOf(k));
		io.writeToConsole("\nb=" + String.valueOf(b));
	}

	private static void tool1() {
		try {
			Double x1, x2, x3, y1, y2, y3, a, b, c;
			io.writeToConsole("\nx1=");
			x1 = getDouble();
			io.writeToConsole("\ny1=");
			y1 = getDouble();
			io.writeToConsole("\nx2=");
			x2 = getDouble();
			io.writeToConsole("\ny2=");
			y2 = getDouble();
			io.writeToConsole("\nx3=");
			x3 = getDouble();
			io.writeToConsole("\ny3=");
			y3 = getDouble();
			a = -(y1 * (x2 - x3) + y2 * (x3 - x1) + y3 * (x1 - x2)) / (x1 - x2)
					/ (x2 - x3) / (x3 - x1);
			b = (y1 * (x2 * x2 - x3 * x3) + y2 * (x3 * x3 - x1 * x1) + y3
					* (x1 * x1 - x2 * x2))
					/ (x1 - x2) / (x2 - x3) / (x3 - x1);
			c = (y1 * x2 * x3 * (x2 - x3) + y2 * x3 * x1 * (x3 - x1) + y3 * x1
					* x2 * (x1 - x2))
					/ (x2 * x3 * (x2 - x3) + x3 * x1 * (x3 - x1) + x1 * x2
							* (x1 - x2));
			format1(a, b, c);
		} catch (InterruptedException e) {

		}

	}

	private static void tool2() {
		try {
			Double x, y, x0, y0, a, b, c;
			io.writeToConsole("\nx=");
			x = getDouble();
			io.writeToConsole("\ny=");
			y = getDouble();
			io.writeToConsole("\nx0=");
			x0 = getDouble();
			io.writeToConsole("\ny0=");
			y0 = getDouble();
			a = (y0 - y * y) / (x - x0) / (x - x0);
			b = -2 * a * x;
			c = a * x * x + y;
			format1(a, b, c);
		} catch (InterruptedException e) {

		}

	}

	private static void tool3() {
		try {
			Double x1, y1, x2, y2, z, a, b, c;
			io.writeToConsole("\nx1=");
			x1 = getDouble();
			io.writeToConsole("\ny1=");
			y1 = getDouble();
			io.writeToConsole("\nx2=");
			x2 = getDouble();
			io.writeToConsole("\ny2=");
			y2 = getDouble();
			io.writeToConsole("\nz=");
			z = getDouble();
			a = (y1 - y2) / (x1 - x2) / (x1 + x2 - 2 * z * (x1 - x2));
			b = -2 * z * a;
			c = (y1 * x2 * x2 - y2 * x1 * x1 - b * x1 * x2 * (x2 - x1))
					/ (x2 * x2 - x1 * x1);
			format1(a, b, c);
		} catch (InterruptedException e) {

		}

	}

	@SuppressWarnings("unchecked")
	private static void tool4() {
		try {
			Integer num = 1, i;
			io.writeToConsole("\n请输入数据个数");
			do {
				num = getInteger();
			} while (num.intValue() <= 0);
			ArrayList<Double> nums = new ArrayList<Double>(num);
			Double sum = 0.0;
			Double fangcha = 0.0;
			io.writeToConsole("\n请输入数据");
			for (i = 0; i < num - 1; i++) {
				nums.add(getDouble());
				io.writeToConsole(",");
				sum += nums.get(i);
			}
			nums.add(getDouble());
			sum += nums.get(i);
			ArrayList<Double> temp = (ArrayList<Double>) nums.clone();
			io.writeToConsole("\n平均数：");
			io.writeToConsole(String.valueOf((sum / num)));
			for (i = 0; i < num; i++) {
				fangcha += (nums.get(i) - sum / num)
						* (nums.get(i) - sum / num);
			}
			io.writeToConsole("\n方差：");
			io.writeToConsole(String.valueOf((fangcha / num)));
			io.writeToConsole("\n标准差：");
			io.writeToConsole(String.valueOf(Math.sqrt(Double.valueOf(fangcha
					/ num))));
			Collections.sort(temp);
			Map<Double, Integer> m = new HashMap<Double, Integer>();
			if (num == 1) {
				io.writeToConsole("\n:众数：" + String.valueOf(nums.get(i)));
			} else {
				Double prev = temp.get(0), cur;
				Integer curnum = 1;
				for (i = 1; i < temp.size(); i++) {
					cur = temp.get(i);
					if (cur.equals(prev)) {
						curnum++;
					} else {
						m.put(prev, curnum);
						prev = cur;
						curnum = 1;
					}
				}
				m.put(prev, curnum);
				ArrayList<Entry<Double, Integer>> l = new ArrayList<Entry<Double, Integer>>(
						m.entrySet());
				Collections.sort(l,
						new Comparator<Map.Entry<Double, Integer>>() {
							public int compare(Map.Entry<Double, Integer> o1,
									Map.Entry<Double, Integer> o2) {
								return (o2.getValue().intValue() - o1
										.getValue().intValue());
							}
						});

				Integer maxtime = l.get(0).getValue();
				for (i = 1; i < l.size(); i++) {
					if (l.get(i).getValue() != maxtime) {
						break;
					}
				}
				io.writeToConsole("\n众数：");
				Integer j;
				for (j = 0; j < i - 1; j++) {
					io.writeToConsole(String.valueOf(l.get(j).getKey()));
					io.writeToConsole(",");
				}
				io.writeToConsole(String.valueOf(l.get(j).getKey()));
			}
			io.writeToConsole("\n中位数：");
			if (num % 2 == 1) {
				io.writeToConsole(String.valueOf(nums.get((num + 1) / 2 - 1)));
			} else {
				io.writeToConsole(String.valueOf((nums.get(num / 2 - 1) + nums
						.get(num / 2)) / 2));
			}
			io.writeToConsole("\n极差：");
			io.writeToConsole(String.valueOf(temp.get(num - 1) - temp.get(0)));
		} catch (InterruptedException e) {

		}

	}

	private static void tool5() {
		io.writeToConsole("\n输入表达式：");
		String input;
		io.wantText();
		try {
			while (true) {
				input = io.getInput();
				Character c;
				Integer i;
				for (i = 0; i < input.length(); i++) {
					c = input.charAt(i);
					if (!((c >= '0' && c <= '9') || c == ' ' || c == '+'
							|| c == '-' || c == '*' || c == '/' || c == '(' || c == ')')) {
						io.inputError();
						break;
					}
				}
				if (i == input.length()) {
					break;
				}
			}
			io.writeToConsole(input);
			io.writeToConsole("\n结果为："
					+ String.valueOf(StringToArithmetic
							.stringToArithmetic(input)));
		} catch (InterruptedException e) {

		}

	}

	private static void tool6() {
		try {
			Double x1, x2, y1, y2, k, b;
			io.writeToConsole("\nx1=");
			x1 = getDouble();
			io.writeToConsole("\ny1=");
			y1 = getDouble();
			io.writeToConsole("\nx2=");
			x2 = getDouble();
			io.writeToConsole("\ny2=");
			y2 = getDouble();
			k = (y2 - y1) / (x2 - x1);
			b = (x1 * y2 - x2 * y1) / (x1 - x2);
			format2(k, b);
		} catch (InterruptedException e) {

		}
	}

	private static void tool7() {
		try {
			Double a, b, c;
			io.writeToConsole("\na=");
			a = getDouble();
			io.writeToConsole("\nb=");
			b = getDouble();
			io.writeToConsole("\nc=");
			c = getDouble();
			if (a > 0) {
				io.writeToConsole("\n开口向上");
			}
			if (a < 0) {
				io.writeToConsole("\n开口向下");
			}
			if (b * b - 4 * a * c > 0) {
				io.writeToConsole("\n2个不相同的实数根");
			}
			if (b * b - 4 * a * c < 0) {
				io.writeToConsole("\n无实数根");
			}
			if (b * b - 4 * a * c == 0) {
				io.writeToConsole("\n2个相同的实数根");
			}
			io.writeToConsole("\n对称轴：y=" + String.valueOf(-b / 2 / a));
		} catch (InterruptedException e) {

		}
	}
}
