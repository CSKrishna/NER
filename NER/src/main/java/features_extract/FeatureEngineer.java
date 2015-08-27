package main.java.features_extract;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import main.java.datastructures.Datum;
import main.java.datastructures.In;
import main.java.datastructures.Index;


public class FeatureEngineer {
	
	
	
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
			 Datum newdatum = new Datum(datum.word, datum.label);
			 newdatum.features = ComputeFeatures.compute(words, prev_label, i);
			 newdatum.previousLabel = prev_label;
			 ListwithFeatures.add(newdatum);
			}
			else {
				for (String prev_label: labellist) {
					Datum newdatum = new Datum(datum.word, datum.label);
					 newdatum.features = ComputeFeatures.compute(words, prev_label, i);
					 newdatum.previousLabel = prev_label;
					 ListwithFeatures.add(newdatum);
					
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
			
			for ( Datum datum: List) {
				 Datum newdatum = new Datum(datum.word, datum.label);
				 newdatum.features = ComputeFeatures.compute(words, prev_label, i);
				 newdatum.previousLabel = prev_label;
				 ListwithFeatures.add(newdatum);
				 prev_label = datum.label;
				 i++;
			}
			
			return ListwithFeatures;
		}

	
}
