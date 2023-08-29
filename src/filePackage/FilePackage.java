package filePackage;

import java.io.*;
import java.util.Calendar;
import java.util.Scanner;
import java.time.LocalDate;

public class FilePackage {
	public static String events(String eventFilter) throws Exception {
		LocalDate currentdate = LocalDate.now();
		Calendar calendar = Calendar.getInstance();
		int currentDay = currentdate.getDayOfMonth();
		String currentDayString = "" + currentDay;
		int currentYear = currentdate.getYear();
		int currentMonth = calendar.get(Calendar.MONTH) + 1;
		int i, count = 0, monthDays = 0, totalMonthDays = 0, totalDays = 0, alertDay = 7;
		String[] arr = new String[128];
		String monthName = "", eventDetail = "None", eventPerson = "None";

		// Day, Month, LeapYear calculation
		boolean isLeapYear;
		if (currentYear % 4 == 0) {
			isLeapYear = true;
		} else {
			isLeapYear = false;
		}

		switch (currentMonth) {
		case 1:
			monthName = "Jan";
			monthDays = 31;
			totalMonthDays = 0;
			break;
		case 2:
			monthName = "Feb";
			if (isLeapYear == true) {
				monthDays = 29;
				totalMonthDays = 31;
			} else {
				monthDays = 28;
				totalMonthDays = 31;
			}
			break;
		case 3:
			monthName = "Mar";
			monthDays = 31;
			if (isLeapYear == true) {
				totalMonthDays = 60;
			} else {
				totalMonthDays = 59;
			}
			break;
		case 4:
			monthName = "Apr";
			monthDays = 30;
			if (isLeapYear == true) {
				totalMonthDays = 91;
			} else {
				totalMonthDays = 90;
			}
			break;
		case 5:
			monthName = "May";
			monthDays = 31;
			if (isLeapYear == true) {
				totalMonthDays = 121;
			} else {
				totalMonthDays = 120;
			}
			break;
		case 6:
			monthName = "Jun";
			monthDays = 30;
			if (isLeapYear == true) {
				totalMonthDays = 152;
			} else {
				totalMonthDays = 151;
			}
			break;
		case 7:
			monthName = "Jul";
			monthDays = 31;
			if (isLeapYear == true) {
				totalMonthDays = 182;
			} else {
				totalMonthDays = 181;
			}
			break;
		case 8:
			monthName = "Aug";
			monthDays = 31;
			if (isLeapYear == true) {
				totalMonthDays = 213;
			} else {
				totalMonthDays = 212;
			}
			break;
		case 9:
			monthName = "Sep";
			monthDays = 30;
			if (isLeapYear == true) {
				totalMonthDays = 244;
			} else {
				totalMonthDays = 243;
			}
			break;
		case 10:
			monthName = "Oct";
			monthDays = 31;
			if (isLeapYear == true) {
				totalMonthDays = 274;
			} else {
				totalMonthDays = 273;
			}
			break;
		case 11:
			monthName = "Nov";
			monthDays = 30;
			if (isLeapYear == true) {
				totalMonthDays = 305;
			} else {
				totalMonthDays = 304;
			}
			break;
		case 12:
			monthName = "Dec";
			monthDays = 31;
			if (isLeapYear == true) {
				totalMonthDays = 335;
			} else {
				totalMonthDays = 334;
			}
			break;
		default:
			return "Error";
		}
		totalDays = (currentYear * 365) + (currentYear % 4) + totalMonthDays + currentDay + 5;

		// Checking for date match
		try {
			String filePath = "C:\\Users\\Niraj Giri\\Desktop\\Digital Assistance\\files\\event.csv";
			Scanner sc = new Scanner(new File(filePath));
			sc.useDelimiter(",");

			while (sc.hasNext()) {
				arr[count] = (String) sc.next().replace("\n", "");
				if ((count + 1) % 4 == 0) {
					if (arr[count - 1].equals(monthName) && arr[count].equals(currentDayString)) {
						eventDetail = arr[count - 3].replace("\n", "").replace("\r", "");
						eventPerson = arr[count - 2].replace("\n", "").replace("\r", "");
					}
				}
				count++;
			}
			sc.close();

			// Upcoming event date determining
			if (eventFilter.equals("nextEvent") || eventFilter.equals("nearEvent")) {
				String nextEvent = "", nextPerson = "", nextDate = "";
				int remDays = 0, dif = 0, min = 366, max = 0, minIndex = 0, maxIndex = 0, index = 0, currentDateToDays = totalMonthDays + currentDay;
				try {
					for (i = 3; i < arr.length;) {
						if (arr[i] == null) {
							break;
						}
						switch (arr[i - 1]) {
						case "Jan":
							totalMonthDays = 0 + Integer.parseInt(arr[i]);
							break;
						case "Feb":
							totalMonthDays = 31 + Integer.parseInt(arr[i]);
							break;
						case "Mar":
							totalMonthDays = 59 + Integer.parseInt(arr[i]);
							break;
						case "Apr":
							totalMonthDays = 90 + Integer.parseInt(arr[i]);
							break;
						case "May":
							totalMonthDays = 120 + Integer.parseInt(arr[i]);
							break;
						case "Jun":
							totalMonthDays = 151 + Integer.parseInt(arr[i]);
							break;
						case "Jul":
							totalMonthDays = 181 + Integer.parseInt(arr[i]);
							break;
						case "Aug":
							totalMonthDays = 212 + Integer.parseInt(arr[i]);
							break;
						case "Sep":
							totalMonthDays = 243 + Integer.parseInt(arr[i]);
							break;
						case "Oct":
							totalMonthDays = 273 + Integer.parseInt(arr[i]);
							break;
						case "Nov":
							totalMonthDays = 304 + Integer.parseInt(arr[i]);
							break;
						case "Dec":
							totalMonthDays = 334 + Integer.parseInt(arr[i]);
							break;
						default:
							return "Error";
						}
						dif = totalMonthDays - currentDateToDays;
						if (dif > 0) {
							if (dif < min) {
								min = dif;
								minIndex = i;
							}
						} else if (Math.abs(dif) > max) {
							max = Math.abs(dif);
							maxIndex = i;
						}
						i = i + 4;
					}
					if (minIndex == 0) {
						index = maxIndex;
						remDays = 366 - max;
					} else {
						index = minIndex;
						remDays = min;
					}
					nextEvent = arr[index-3].replace("\n", "").replace("\r", "");
					nextPerson = arr[index-2].replace("\n", "").replace("\r", "");
					nextDate = (arr[index] + " " + arr[index-1]).replace("\n", "").replace("\r", "");
	
					if (eventFilter.equals("nearEvent")) {
						if (min <= alertDay) {
							return nextEvent + "," + nextPerson + ",," + nextDate + ",,," + remDays;
						} else {
							return "None";
						}
					} else {
						return nextEvent + "," + nextPerson + ",," + nextDate;
					}
				} catch (Exception e) {
					return "Error";
				}
			} else {
				if (eventDetail.equals("None")) {
					return "None";
				} else {
					return eventDetail + "," + eventPerson;
				}
			}
		} catch (Exception e) {
			return "Error";
		}
	}
}
