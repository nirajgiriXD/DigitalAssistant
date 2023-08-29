package main.resources;

import filePackage.*;
import automation.*;
import games.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.net.URL;
import java.net.URLConnection;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;
import java.awt.Desktop;

// Aashish Pd Pandey: Module
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import com.sun.speech.freetts.VoiceManager;
import com.sun.speech.freetts.Voice;


public class DigitalAssistance {

	// text to speech
	public void speak(String word) {
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		Voice voice;
		voice = VoiceManager.getInstance().getVoice("kevin16");
		if (voice != null) {
			voice.allocate();
		}
		try {
			voice.setRate(144);
			voice.setPitch(55);
			voice.setVolume(1);
			voice.setPitchShift(3);
			voice.setDurationStretch(1);
			voice.speak(word);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isConnected() {
		try {
	         URL url = new URL("http://www.google.com");
	         URLConnection connection = url.openConnection();
	         connection.connect();
	         return true;
	      } catch (MalformedURLException e) {
	    	  return false;
	      } catch (IOException e) {
	         return false;
	      }
	}
	
	public static void BotOutput(String message, String note) {
		String botName = "Chappie";
		Configuration config = new Configuration();
		config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		config.setDictionaryPath("res\\dic.dic");
		config.setLanguageModelPath("res\\lm.lm");
		DigitalAssistance bot = new DigitalAssistance();
		System.out.println("[ " + botName + " ] :\t" + message + " " + note);
		bot.speak(message);
	}
	
	public static void Greetings(String userName, String greet) {
		String var;
		int currentHour = LocalDateTime.now().getHour();
		
		if (currentHour >= 4 && currentHour < 12) {
			if (userName.equals("NULL")) {
				var = "Good morning";
			}
			else {
				var = "Good morning " + userName;
			}
		} else if (currentHour >= 12 && currentHour < 18) {
			if(userName.equals("NULL")) {
				var = "Good afternoon";
			}
			else {
				var ="Good afternoon " + userName;
			}
		} else if (currentHour >= 18 && currentHour < 22) {
			if(userName.equals("NULL")) {
				var = "Good evening";
			}
			else {
				var = "Good evening " + userName;
			}
		} else {
			if(userName.equals("NULL")) {
				var = greet;
			}
			else {
				var = greet + " " + userName;
			}
		}
		BotOutput(var, "");
	}

	public static void main(String[] st) {
		
		// Speech configuration
		Configuration config = new Configuration();
		config.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		config.setDictionaryPath("res\\dic.dic");
		config.setLanguageModelPath("res\\lm.lm");
		DigitalAssistance bot = new DigitalAssistance();

		// Program Variables
		String botName = "Chappie", userName = "", task, url;
		String event, person, eventDate, outputString;
		boolean isVoiceMode = false;
		int remDays = 0;

		// Greeting to the user
		bot.speak("beep");
		Greetings(userName, "Hello");
		
		try {
			outputString = FilePackage.events("currentEvent");
			if (outputString.equals("Error")) {
				;
			} else if(outputString.equals("None")) {
				if (outputString.equals("Error")) {
					;
				} else {
					outputString = FilePackage.events("nearEvent");
					event = outputString.substring(0, outputString.indexOf(","));
					person = outputString.substring(outputString.indexOf(",")+1, outputString.indexOf(",,"));
					eventDate = outputString.substring(outputString.indexOf(",,")+2, outputString.indexOf(",,,"));
					remDays = Integer.parseInt(outputString.substring(outputString.indexOf(",,,")+3, outputString.length()));
					if (event.equals("Birthday")) {
						BotOutput(person + "'s " + event + " is on " + eventDate + " after " + remDays, "");
					} else {
						BotOutput(event + " on " + eventDate + " after " + remDays, "(" + person + ")");
					}
				}
			} else {
				event = outputString.substring(0, outputString.indexOf(","));
				person = outputString.substring(outputString.indexOf(",")+1, outputString.length());
				if(event.equals("Birthday")) {
					BotOutput("Today is " + person + "'s " + event, "");
				} else {
					BotOutput("Today is " + event, "(" + person + ")");
				}
			}
		} catch (Exception e1) {
			;
		}

		try {

			// For clearing console messages
			Logger cmRootLogger = Logger.getLogger("default.config");
			cmRootLogger.setLevel(java.util.logging.Level.OFF);
			String conFile = System.getProperty("java.util.logging.config.file");
			if (conFile == null) {
				System.setProperty("java.util.logging.config.file", "ignoreAllSphinx4LoggingOutput");
			}

			// For windows interaction
			Desktop desktop = Desktop.getDesktop();
			File dto = null;
			
			//Calendar
			String[] monthName = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "Octuber", "November", "December"};
			String[] dayName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
			String command;
			Calendar calendar = Calendar.getInstance();
			Date date = new Date();
			Scanner sc = new Scanner(System.in);
			int monthNumber  = calendar.get(Calendar.MONTH);

			// Microphone is set
			LiveSpeechRecognizer speech = new LiveSpeechRecognizer(config);
			speech.startRecognition(true);

			SpeechResult speechresult = null;

			System.out.print("[ User ] :\t");
			while ((speechresult = speech.getResult()) != null) {
				
				if (isVoiceMode==true) {
					command = speechresult.getHypothesis();
					System.out.println(command);
				} else {
					command = sc.nextLine();
				}

				// Available Task
				// Edge, Chrome, Firefox, Youtube, Gmail, CMD, Date, Day, Time, Terminate, Random Number, 
				// Dice, Open folder, Check Internet Connection
				
				// To add task
				// Remindar, calculator
				
				// COMMAND MATCHING STARTS FROM HERE
				
				// Check Internet
				if (command.equalsIgnoreCase("CHECK INTERNET") || command.equalsIgnoreCase("CHECK NET") || command.equalsIgnoreCase("CHECK CONNECTION")) {
					if (isConnected()) {
						BotOutput("System is online", "(You are all set to go)");
					} else {
						BotOutput("System is not online", "(Welcome to jurassic age)");
					}
				}
				// Hi -> HEllo
				else if (command.equalsIgnoreCase("HI")) {
					Greetings(userName, "Hello");
				}
				// Hello -> Hi
				else if (command.equalsIgnoreCase("HELLO")) {
					Greetings(userName, "Hi");
				}
				// Change operating mode
				else if (command.equalsIgnoreCase("CHANGE MODE")) {
					if (isVoiceMode==true) {
						isVoiceMode = false;
					} else {
						isVoiceMode = true;
					}
				}
				// Open Google
				else if (command.equalsIgnoreCase("OPEN GOOGLE") || command.equalsIgnoreCase("OPEN CHROME")) {
					BotOutput("Opening Google Chrome", "");
					Runtime.getRuntime().exec("cmd.exe /c start chrome.exe");
				} 
				// Open Firefox
				else if (command.equalsIgnoreCase("OPEN FIREFOX") || command.equalsIgnoreCase("OPEN MOZILLA FIREFOX")) {
					BotOutput("Opening Mozilla Firefox", "");
					Runtime.getRuntime().exec("cmd.exe /c start firefox.exe");
				} 
				// Open Edge
				else if (command.equalsIgnoreCase("OPEN EDGE") || command.equalsIgnoreCase("OPEN MICROSOFT EDGE")) {
					BotOutput("Opening Microsoft Edge", "");
					Runtime.getRuntime().exec("cmd.exe /c start msedge.exe");
				}
				// Open YouTube
				else if (command.equalsIgnoreCase("OPEN YOUTUBE")) {
					BotOutput("Opening Youtube", "");
					Runtime.getRuntime().exec("cmd.exe /c start chrome.exe https://www.youtube.com");
				}
				// Open Mail
				else if (command.equalsIgnoreCase("OPEN MAIL") || command.equalsIgnoreCase("OPEN EMAIL") || command.equalsIgnoreCase("OPEN GMAIL")) {
					BotOutput("Opening Mail", "");
					Runtime.getRuntime().exec("cmd.exe /c start chrome.exe https://mail.google.com");
				}
				// Open Notepad
				else if (command.equalsIgnoreCase("OPEN NOTEPAD")) {
					BotOutput("Opening Notepad", "");
					Runtime.getRuntime().exec("cmd.exe /c start notepad.exe");
				}
				// Open Paint
				else if (command.equalsIgnoreCase("OPEN PAINT")) {
					BotOutput("Opening Paint", "");
					Runtime.getRuntime().exec("cmd.exe /c start mspaint.exe");
				}
				// Open Excel
				else if (command.equalsIgnoreCase("OPEN EXCEL")) {
					BotOutput("Opening Microsoft Excel", "");
					Runtime.getRuntime().exec("cmd.exe /c start excel.exe");
				}
				// Open PowerPoint
				else if (command.equalsIgnoreCase("OPEN POWERPOINT")) {
					BotOutput("Opening Microsoft Powerpoint", "");
					Runtime.getRuntime().exec("cmd.exe /c start powerpnt.exe");
				}
				// Open Word
				else if (command.equalsIgnoreCase("OPEN WORD") || command.equalsIgnoreCase("OPEN WORDS")) {
					BotOutput("Opening Microsoft Words", "");
					Runtime.getRuntime().exec("cmd.exe /c start winword.exe");
				}
				// Open Microsoft Store
				else if(command.equalsIgnoreCase("OPEN STORE") || command.equalsIgnoreCase("OPEN MICROSOFT STORE")) {
					BotOutput("Opening Microsoft Store", "");
					Runtime.getRuntime().exec("cmd /c start ms-windows-store:");
				}
				// Open Songs
				else if(command.equalsIgnoreCase("OPEN MUSIC") || command.equalsIgnoreCase("OPEN SONG") || command.equalsIgnoreCase("OPEN SONGS")) {
					BotOutput("Opening Music Player", "");
					Runtime.getRuntime().exec("cmd /c start vlc.exe");
				}
				// Play Music
				else if(command.equalsIgnoreCase("PLAY MUSIC") || command.equalsIgnoreCase("PLAY SONG") || command.equalsIgnoreCase("PLAY SONGS")) {
					BotOutput("Playing Music", "");
					Runtime.getRuntime().exec("cmd /c vlc D:\\Songs --random");
				}
				// Play Nepali Music
				else if(command.equalsIgnoreCase("PLAY NEPALI MUSIC") || command.equalsIgnoreCase("PLAY NEPALI SONG") || command.equalsIgnoreCase("PLAY NEPALI SONGS")
						|| command.equalsIgnoreCase("PLAY KOLLYHOOD MUSIC") || command.equalsIgnoreCase("PLAY KOLLYHOOD SONG") || command.equalsIgnoreCase("PLAY KOLLYHOOD SONGS")) {
					BotOutput("Playing Nepali Music", "");
					Runtime.getRuntime().exec("cmd /c vlc \"D:\\Songs\\Nepali Songs\" --random");
				}
				// Play English Music
				else if(command.equalsIgnoreCase("PLAY ENGLISH MUSIC") || command.equalsIgnoreCase("PLAY ENGLISH SONG") || command.equalsIgnoreCase("PLAY ENGLISH SONGS")
						|| command.equalsIgnoreCase("PLAY HOLLYHOOD MUSIC") || command.equalsIgnoreCase("PLAY HOLLYHOOD SONG") || command.equalsIgnoreCase("PLAY HOLLYHOOD SONGS")) {
					BotOutput("Playing English Music", "");
					Runtime.getRuntime().exec("cmd /c vlc \"D:\\Songs\\English Songs\" --random");
				}
				// Play Hindi Music
				else if(command.equalsIgnoreCase("PLAY HINDI MUSIC") || command.equalsIgnoreCase("PLAY HINDI SONG") || command.equalsIgnoreCase("PLAY HINDI SONGS")
						|| command.equalsIgnoreCase("PLAY BOLLYHOOD MUSIC") || command.equalsIgnoreCase("PLAY BOLLYHOOD SONG") || command.equalsIgnoreCase("PLAY BOLLYHOOD SONGS")) {
					BotOutput("Playing Hindi Music", "");
					Runtime.getRuntime().exec("cmd /c vlc \"D:\\Songs\\Hindi Songs\" --random");
				}
				// Play Arabic Music
				else if(command.equalsIgnoreCase("PLAY ARABIC MUSIC") || command.equalsIgnoreCase("PLAY ARABIC SONG") || command.equalsIgnoreCase("PLAY ARABIC SONGS")) {
					BotOutput("Playing Arabic Music", "");
					Runtime.getRuntime().exec("cmd /c vlc \"D:\\Songs\\Arabic Songs\" --random");
				}
				// Open desktop folder
				else if(command.equalsIgnoreCase("OPEN DESKTOP")) {
					BotOutput("Opening Desktop Folder", "");
					dto = new File("c:/Users/Niraj Giri/Desktop");
					desktop.open((dto));
				}
				// Open documents folder
				else if(command.equalsIgnoreCase("OPEN DOCUMENTS") || command.equalsIgnoreCase("OPEN DOCUMENT")) {
					BotOutput("Opening Documents Folder", "");
					dto = new File("c:/Users/Niraj Giri/Documents");
					desktop.open((dto));
				}
				// Open downloads folder
				else if(command.equalsIgnoreCase("OPEN DOWNLOADS") || command.equalsIgnoreCase("OPEN DOWNLOAD")) {
					BotOutput("Opening Downloads Folder", "");
					dto = new File("c:/Users/Niraj Giri/Downloads");
					desktop.open((dto));
				}
				// Open CMD
				else if(command.equalsIgnoreCase("OPEN CMD") || command.equalsIgnoreCase("OPEN TERMINAL")) {
					BotOutput("Opening cmd", "");
					Runtime.getRuntime().exec("cmd /c start cmd.exe");
				}
				
				
				//  Close Google
				else if (command.equalsIgnoreCase("CLOSE GOOGLE") || command.equalsIgnoreCase("CLOSE CHROME")) {
					BotOutput("Closing Google Chrome", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM chrome.exe");
				} 
				// Close Firefox
				else if (command.equalsIgnoreCase("CLOSE FIREFOX") || command.equalsIgnoreCase("CLOSE MOZILLA FIREFOX")) {
					BotOutput("Closing Mozilla Firefox", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM firefox.exe");
				} 
				// Close Edge
				else if (command.equalsIgnoreCase("CLOSE EDGE") || command.equalsIgnoreCase("CLOSE MICROSOFT EDGE")) {
					BotOutput("Closing Microsoft Edge", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM msedge.exe");
				}
				// Close Notepad
				else if (command.equalsIgnoreCase("CLOSE NOTEPAD")) {
					BotOutput("Closing Notepad", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM notepad.exe");
				}
				// Close Paint
				else if (command.equalsIgnoreCase("CLOSE PAINT")) {
					BotOutput("Closing Paint", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM mspaint.exe");
				}
				// Close Excel
				else if (command.equalsIgnoreCase("CLOSE EXCEL")) {
					BotOutput("Closing Microsoft Excel", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM excel.exe");
				}
				// Close PowerPoint
				else if (command.equalsIgnoreCase("CLOSE POWERPOINT")) {
					BotOutput("Closing Microsoft Powerpoint", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM powerpnt.exe");
				}
				// Close Word
				else if (command.equalsIgnoreCase("CLOSE WORD") || command.equalsIgnoreCase("OPEN WORDS")) {
					BotOutput("Closing Microsoft Words", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM winword.exe");
				}
				// Close CMD
				else if(command.equalsIgnoreCase("CLOSE CMD") || command.equalsIgnoreCase("CLOSE TERMINAL")) {
					BotOutput("Closing cmd", "");
					Runtime.getRuntime().exec("cmd.exe /c TASKKILL /IM cmd.exe");
				}
				// Close Music
				else if(command.equalsIgnoreCase("CLOSE MUSIC") || command.equalsIgnoreCase("CLOSE SONG") || command.equalsIgnoreCase("CLOSE SONGS")) {
					BotOutput("Closing Music", "");
					Runtime.getRuntime().exec("cmd /c TASKKILL /IM vlc.exe");
				}
				
				
				// Date and Time 
				else if (command.equalsIgnoreCase("CURRENT DATE") || command.equalsIgnoreCase("TODAY'S DATE")) {
					BotOutput((date.getYear()+1900) + " " + monthName[monthNumber] + " " + date.getDate(), "");
				} else if (command.equalsIgnoreCase("DAY")) {
					BotOutput(dayName[date.getDay()], "");
				} else if (command.equalsIgnoreCase("CURRENT TIME")) {
					if (LocalDateTime.now().getHour()>12) {
						BotOutput((LocalDateTime.now().getHour()%12) + " " + LocalDateTime.now().getMinute(), "(hh:mm)");
					} else {
						BotOutput(LocalDateTime.now().getHour() + " " + LocalDateTime.now().getMinute(), "(hh:mm)");
					}
				}
				// Date and Event
				else if(command.equalsIgnoreCase("TODAY'S EVENT")) {
					outputString = FilePackage.events("currentEvent");
					if (outputString.equals("Error")) {
						BotOutput(outputString, "(Could not retrive event info)");
					} else if(outputString.equals("None")) {
						BotOutput(outputString, "(No event nearby)");
					} else {
						event = outputString.substring(0, outputString.indexOf(","));
						person = outputString.substring(outputString.indexOf(",")+1, outputString.length());
						if(event.equals("Birthday")) {
							BotOutput("Today is " + person + "'s " + event, "");
						} else {
							BotOutput("Today is " + event, "(" + person + ")");
						}
					}
				} else if(command.equalsIgnoreCase("UPCOMING EVENT")) {
					outputString = FilePackage.events("nextEvent");
					if (outputString.equals("Error")) {
						BotOutput(outputString, "(Could not retrive event info)");
					} else {
						event = outputString.substring(0, outputString.indexOf(","));
						person = outputString.substring(outputString.indexOf(",")+1, outputString.indexOf(",,"));
						eventDate = outputString.substring(outputString.indexOf(",,")+2, outputString.length());
						if(event.equals("Birthday")) {
							BotOutput(person + "'s " + event + " is on " + eventDate, "");
						} else {
							BotOutput(event + " on " + eventDate, "(" + person + ")");
						}
					}
				}
				// Weather
				else if(command.equalsIgnoreCase("TODAY'S WEATHER") || command.equalsIgnoreCase("CURRENT TEMPERATURE") || command.equalsIgnoreCase("CURRENT WEATHER")) {
					BotOutput(Automation.getWeather(), "");
				}
				// Location
				else if(command.equalsIgnoreCase("CURRENT LOCATION") || command.equalsIgnoreCase("WHAT'S MY LOCATION")) {
					BotOutput("Getting Location Info", "(This might take a while...)");
					BotOutput(Automation.getLocation(), "");
				}
				// Play game
				else if(command.equalsIgnoreCase("PLAY GAME")) {
					int max = 1, min = 0, rani = (int)(Math.random()*(max-min+1)+min);
					String outputStr = "";
					int outputInt = 0;
					// Paper, Scissor and Stone game
					if(rani==0) {
						BotOutput("Would you like to play \"Paper, Scissor and Stone\" game?", "");
						System.out.print("[ User ] :\t");
						if(isVoiceMode==true) {
							speechresult = speech.getResult();
							command = speechresult.getHypothesis();
							command = speech.getResult().getHypothesis();
							System.out.println(command);
						} else {
							command = sc.nextLine();
						}
						if(command.equalsIgnoreCase("YES") || command.equalsIgnoreCase("OK") || command.equalsIgnoreCase("SURE")) {
							BotOutput("On my count", "");
							BotOutput("3", "");
							BotOutput("2", "");
							BotOutput("1", "");
							outputStr = Games.PaperScissorStone();
							System.out.print("[ User ] :\t");
							if(isVoiceMode==true) {
								speechresult = null;
								speechresult = speech.getResult();
								command = speechresult.getHypothesis();
								System.out.println(command);
							} else {
								command = sc.nextLine();
							}
							System.out.println("[ " + botName + " ] :\t" + outputStr);
							if(command.equalsIgnoreCase("PAPER") || command.equalsIgnoreCase("SCISSOR") || command.equalsIgnoreCase("STONE")) {
								if(command.equalsIgnoreCase("PAPER") && outputStr.equals("scissor")) {
									BotOutput("I Won", "");
								} else if(command.equalsIgnoreCase("SCISSOR") && outputStr.equals("stone")) {
									BotOutput("I Won", "");
								} else if(command.equalsIgnoreCase("STONE") && outputStr.equals("paper")) {
									BotOutput("I Won", "");
								}else if(command.equalsIgnoreCase("PAPER") && outputStr.equals("stone")) {
									BotOutput("You Won", "");
								} else if(command.equalsIgnoreCase("SCISSOR") && outputStr.equals("paper")) {
									BotOutput("You Won", ""); 
								} else if(command.equalsIgnoreCase("STONE") && outputStr.equals("scissor")) {
									BotOutput("You Won", ""); 
								} else {
									BotOutput("Draw", "");
								}
							} else {
								BotOutput("You can only choose paper, scissor or stone", "");
							}
						} else {
							BotOutput("Terminating game", "");
						}
						
					} else if (rani==1) {
						BotOutput("Would you like to play \"Odd or Even\" game?", "");
						System.out.print("[ User ] :\t");
						if(isVoiceMode==true) {
							speechresult = speech.getResult();
							command = speechresult.getHypothesis();
							command = speech.getResult().getHypothesis();
							System.out.println(command);
						} else {
							command = sc.nextLine();
						}
						if(command.equalsIgnoreCase("YES") || command.equalsIgnoreCase("OK") || command.equalsIgnoreCase("SURE")) {
							BotOutput("Make Your Guess", "");
							outputInt = Games.OddOrEven();
							System.out.print("[ User ] :\t");
							if(isVoiceMode==true) {
								speechresult = null;
								speechresult = speech.getResult();
								command = speechresult.getHypothesis();
								System.out.println(command);
							} else {
								command = sc.nextLine();
							}
							System.out.println("[ " + botName + " ] :\t" + outputInt);
							if (outputInt%2==0 && command.equalsIgnoreCase("EVEN")) {
								BotOutput("You Won", "");
							} else if (outputInt%2!=0 && command.equalsIgnoreCase("ODD")) {
								BotOutput("You Won", "");
							} else {
								BotOutput("Better luck next time", "");
							}
						} else {
							BotOutput("Terminating game", "");
						}
					}
				}
				// Close Digital Assistance
				else if (command.equalsIgnoreCase("TERMINATE") || command.equalsIgnoreCase("EXIT") || command.equalsIgnoreCase("CLOSE")) {
					BotOutput("Terminating program", "");
					System.exit(0);
				} 
				// Shutdown Machine
				else if (command.equalsIgnoreCase("SHUTDOWN COMPUTER")) {
					BotOutput("Shutting down windows", "(Make sure your files are saved)");
					Runtime.getRuntime().exec("cmd.exe /c shutdown /s");
					System.exit(0);
					// Remaining
				}
				// Default
				else {
					System.out.println("[ " + botName + " ] :\tI didn't get that");
				}
				System.out.print("[ User ] :\t");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}