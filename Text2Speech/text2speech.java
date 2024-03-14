package Text2Speech;

import java.io.*;
import javax.sound.sampled.*;
import java.util.Locale;
import java.util.Scanner;

class text2speech {
  public final static String path2folder = "C:\\Users\\Dimitris T\\Desktop\\Projects\\StandaloneProjects\\Text2Speech\\Files";

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Give me a sentence");
    String sentence = scanner.nextLine();
    SoundOut(sentence);
  }

  public static void SoundOut(String sentence) {
    sentence = turnNumbersToString(sentence);
    ;
    for (int i = 0; i < sentence.length(); i++) {
      char c = sentence.charAt(i);
      if (Character.isLetter(c)) {
        String path = path2folder + "\\" + Character.toUpperCase(c) + ".wav";
        try {
          Clip clip = AudioSystem.getClip();
          clip.open(AudioSystem.getAudioInputStream(new File(path)));
          clip.start();

          // Add a delay to ensure the clip plays for a sufficient duration
          Thread.sleep(clip.getMicrosecondLength() / 1000);

        } catch (Exception exc) {
          exc.printStackTrace(System.out);
        }
      }
    }
  }



  public static String turnNumbersToString(String sentence) {
    sentence = sentence.replace("1", "one");
    sentence = sentence.replace("2", "two");
    sentence = sentence.replace("3", "three");
    sentence = sentence.replace("4", "four");
    sentence = sentence.replace("5", "five");
    sentence = sentence.replace("6", "six");
    sentence = sentence.replace("7", "seven");
    sentence = sentence.replace("8", "eight");
    sentence = sentence.replace("9", "nine");
    return sentence;
  }
}
