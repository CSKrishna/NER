package main.java.features_extract;

import java.util.ArrayList;
import java.util.List;



public class ComputeFeatures {
	
	public static List<String> compute(String[] words, String prev_label, int position) {
	//	Bag<String> features = new Bag<String>();
		List<String> features = new ArrayList<String>();
		String current_word = words[position];
		// Add feature functions here
		 features.add("word=" + current_word);
	     features.add("prevLabel=" + prev_label);
	     features.add("word=" + current_word + ", prevLabel=" + prev_label);
	     
	     /**
	     
	     //if word begins with uppercase
			if (Character.isUpperCase(current_word.charAt(0))) {
			features.add("case=Title");
		}
			
		//if word begins with upper case, previous word also begins with upper case
			if (position > 0) {
				String prev = words[position - 1];
				if (Character.isUpperCase(current_word.charAt(0))) {
					features.add("case=Title, previous word=" + prev);
					features.add("case=Title, previous word=" + prev + "prevLabel=" + prev_label);
					if (position < (size -1)) {
						String next = words[position+1];
						features.add("case=Title, next word=" + next);
						if (Character.isUpperCase(next.charAt(0))) {
						features.add("case=Title, following case=Title, " + "prevLabel=" + prev_label );
							features.add("case=Title, following case=Title");
						//	features.add("case=Title, following case=Title, previous word=" + prev);
						//	features.add("case=Title, following case=Title, following word=" + next);
						//	features.add("case=Title, following case=Title, previous word=" + prev + "prevLabel=" + prev_label);
							
						}
					}
					
				
					if (position < (size -1)) {
						String next = words[position+1];
						features.add("word=" + current_word + "next word= " + next);
					}
				
					
					
				
					
				
					if (Character.isUpperCase(prev.charAt(0))) {
						features.add("case=Title, previous case=Title, " + "prevLabel=" + prev_label );
						features.add("case=Title, previous case=Title");
						
					}
				}
			}
			
			
			//if all lower case
			boolean b = true;
			for (int i = 0; i < current_word.length(); i++){
				if (Character.isUpperCase(current_word.charAt(i))) {
					b = false;
					break;
				}
			}
			if (b) features.add("case=lower");
			
			//if all upper
			 b = true;
			for (int i = 0; i < current_word.length(); i++){
				if (!Character.isUpperCase(current_word.charAt(i))) {
					b = false;
					break;
				}
			}
			if (b) features.add("case=upper");
			
			  //if word begins with uppercase and not start of sentence
			if (position > 0) {
				String prev = words[position - 1];
			if (Character.isUpperCase(current_word.charAt(0)) && (!prev.equals(".")) ) {
			features.add("case=Title and not start of a sentence");
			features.add("case=Title, not start of sentence , prevLabel=" + prev_label);
			features.add("case=Title, not start of sentence , prevLabel=" + prev_label  + " ,prevword=" + prev);
			//features.add("case=Title, not start of sentence , prevword=" + prev);
		}
			//String prev = words[position - 1];
			if (Character.isUpperCase(current_word.charAt(0)) && (prev.equals(".")) ) {
			features.add("case=Title and start of a sentence");
		}
			//features.add("word=" + current_word + ", prevLabel=" + prev_label + " ,prevword=" + prev);
			
			
			}
			
			**/
		return features;
	}

	public static List<String> computetrigram(String[] words, String prev_label, String prev_prev_label, int position) {
		//	Bag<String> features = new Bag<String>();
			List<String> features = new ArrayList<String>();
			String current_word = words[position];
			// Add feature functions here
			int size = words.length;
			 features.add("word=" + current_word);
		     features.add("prevLabel=" + prev_label);
		     features.add("prevprevLabel=" + prev_prev_label);
		    features.add("word=" + current_word + ", prevLabel=" + prev_label);
		    features.add("word=" + current_word + ", prevLabel=" + prev_label + ", prevprevLabel=" + prev_prev_label);
		  /**
		  //   features.add("word=" + current_word + ", prevLabel=" + prev_label + ", prevprevLabel=" + prev_prev_label);
		     
		    //if word begins with uppercase
		 			if (Character.isUpperCase(current_word.charAt(0))) {
		 			features.add("case=Title");
		 		}
		 			
		 		//if word begins with upper case, previous word also begins with upper case
		 			if (position > 0) {
		 				String prev = words[position - 1];
		 				if (Character.isUpperCase(current_word.charAt(0))) {
		 					features.add("case=Title, previous word=" + prev);
		 					features.add("case=Title, previous word=" + prev + "prevLabel=" + prev_label);
		 					if (position < (size -1)) {
		 						String next = words[position+1];
		 						features.add("case=Title, next word=" + next);
		 						if (Character.isUpperCase(next.charAt(0))) {
		 						features.add("case=Title, following case=Title, " + "prevLabel=" + prev_label );
		 						features.add("case=Title, following case=Title, " + "prevLabel=" + prev_label + "prevprevLabel=" + prev_prev_label);
		 							features.add("case=Title, following case=Title");
		 						//	features.add("case=Title, following case=Title, previous word=" + prev);
		 						//	features.add("case=Title, following case=Title, following word=" + next);
		 						//	features.add("case=Title, following case=Title, previous word=" + prev + "prevLabel=" + prev_label);
		 							
		 						}
		 					}
		 					
		 				
		 					if (position < (size -1)) {
		 						String next = words[position+1];
		 						features.add("word=" + current_word + "next word= " + next);
		 					}
		 				
		 					
		 					
		 				
		 					
		 				
		 					if (Character.isUpperCase(prev.charAt(0))) {
		 						features.add("case=Title, previous case=Title, " + "prevLabel=" + prev_label );
		 						features.add("case=Title, previous case=Title, " + "prevLabel=" + prev_label  + "prevprevLabel=" + prev_prev_label);
		 						features.add("case=Title, previous case=Title");
		 						
		 					}
		 				}
		 			}
		 			
		 			
		 			//if all lower case
		 			boolean b = true;
		 			for (int i = 0; i < current_word.length(); i++){
		 				if (Character.isUpperCase(current_word.charAt(i))) {
		 					b = false;
		 					break;
		 				}
		 			}
		 			if (b) features.add("case=lower");
		 			
		 			//if all upper
		 			 b = true;
		 			for (int i = 0; i < current_word.length(); i++){
		 				if (!Character.isUpperCase(current_word.charAt(i))) {
		 					b = false;
		 					break;
		 				}
		 			}
		 			if (b) features.add("case=upper");
		 			
		 			  //if word begins with uppercase and not start of sentence
		 			if (position > 0) {
		 				String prev = words[position - 1];
		 			if (Character.isUpperCase(current_word.charAt(0)) && (!prev.equals(".")) ) {
		 			features.add("case=Title and not start of a sentence");
		 			features.add("case=Title, not start of sentence , prevLabel=" + prev_label);
		 			features.add("case=Title, not start of sentence , prevLabel=" + prev_label +  " ,prevprevlabel=" + prev_prev_label);
		 			features.add("case=Title, not start of sentence , prevLabel=" + prev_label  + " ,prevword=" + prev);
		 			//features.add("case=Title, not start of sentence , prevword=" + prev);
		 		}
		 			//String prev = words[position - 1];
		 			if (Character.isUpperCase(current_word.charAt(0)) && (prev.equals(".")) ) {
		 			features.add("case=Title and start of a sentence");
		 		}
		 			//features.add("word=" + current_word + ", prevLabel=" + prev_label + " ,prevword=" + prev);
		 			
		 		
		 			}
		 		**/
				
			return features;
		}
	
	
	
}
