package main.java.datastructures;

import java.util.*;



public class Datum {

  public final String word;
  public final String label;
  public List<String> features;
  public String guessLabel;
  public String previousLabel;
  public String previouspreviousLabel;
  
  public Datum(String word, String label) {
    this.word = word;
    this.label = label;
  }
}