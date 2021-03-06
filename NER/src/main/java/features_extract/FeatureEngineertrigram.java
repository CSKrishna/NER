package main.java.features_extract;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import main.java.datastructures.In;
import main.java.datastructures.Datum;
import main.java.datastructures.Index;


public class FeatureEngineertrigram {
	public List<Datum> readfile (URL url) throws IOException  {
		In in = new In(url);
		List<Datum> data = new ArrayList<Datum>();
		while (in.hasNextLine()) {
			String[] line_split = in.readLine().split("\t");
			if (line_split.length < 2) continue;
			Datum datum = new Datum(line_split[0],line_split[1]);
			data.add(datum);			
		}
		return data;
	}
	
	
	
	public List<Datum> setFeaturesTest(List<Datum> List) {
		Index labelindex = new Index();
		//Bag<String> labellist = new Bag<String>();
		List<String> labellist = new ArrayList<String>();
		int size = List.size();
		String[] words = new String[size];
		List<Datum> ListwithFeatures = new ArrayList<Datum>();
		int i = 0;
		for (Datum datum: List) {
			words[i++] = datum.word;
			if (!labelindex.haskey(datum.label)) {
				labelindex.add(datum.label);
				labellist.add(datum.label);
			}
		}
		
		
		i = 0;
		for ( Datum datum: List) {
			if (i == 0){
			 String prev_label = "O";
			 String prev_prev_label = "O";
			 Datum newdatum = new Datum(datum.word, datum.label);
			 newdatum.features = ComputeFeatures.computetrigram(words, prev_label,prev_prev_label, i);
			 newdatum.previousLabel = prev_label;
			 newdatum.previouspreviousLabel = prev_prev_label;
			 ListwithFeatures.add(newdatum);
			}
			
			else if (i ==1) {
				String prev_prev_label = "O";
				for (String prev_label: labellist) {
					Datum newdatum = new Datum(datum.word, datum.label);
					 newdatum.features = ComputeFeatures.computetrigram(words, prev_label,prev_prev_label, i);
					 newdatum.previousLabel = prev_label;
					 newdatum.previouspreviousLabel = prev_prev_label;
					 ListwithFeatures.add(newdatum);
				}
			}	
				
			else 			
			{
				for (String prev_prev_label: labellist) {
				  for (String prev_label: labellist) {
					Datum newdatum = new Datum(datum.word, datum.label);
					 newdatum.features = ComputeFeatures.computetrigram(words, prev_label, prev_prev_label, i);
					 newdatum.previousLabel = prev_label;
					 newdatum.previouspreviousLabel = prev_prev_label;
					 ListwithFeatures.add(newdatum);
     				}
	    		}	
			}
		 i++;	
		}
		
		return ListwithFeatures;
	}
		

	public List<Datum> setFeaturesTrain(List<Datum> List) {
			
			int size = List.size();
			String[] words = new String[size];
			List<Datum> ListwithFeatures = new ArrayList<Datum>();
			int i = 0;
			for (Datum datum: List) {
				words[i++] = datum.word;
			}
			
			i =0;
			String prev_label = "O";
			String prev_prev_label = "O";
			
			for ( Datum datum: List) {
				 Datum newdatum = new Datum(datum.word, datum.label);
				 newdatum.features = ComputeFeatures.computetrigram(words, prev_label, prev_prev_label, i);
				 newdatum.previousLabel = prev_label;
				 newdatum.previouspreviousLabel = prev_prev_label;
				 ListwithFeatures.add(newdatum);
				 
				 prev_prev_label = prev_label;
				 prev_label = datum.label;
				 i++;
			}
			
			return ListwithFeatures;
		}

	public static void main(String[] args) throws IOException  {
		 String resource = "/data/dev";
		 FeatureEngineertrigram featureEngineer = new FeatureEngineertrigram();
		 URL url = FeatureEngineertrigram.class.getResource(resource);
		 List<Datum> list  =  featureEngineer.readfile(url);
		 
		
		 List<Datum> test = featureEngineer.setFeaturesTest(list);
		 int i=0;
		 for (Datum data: test) {
			 if (i < 7) {
		
			 System.out.println(data.word + "----" + data.previousLabel);
			 System.out.println(data.word + "----" + data.previouspreviousLabel);
			 }
			 i++;
		 }
		 
       
   }
}
