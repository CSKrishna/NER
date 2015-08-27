package main.java.memm;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import main.java.datastructures.Datum;

import main.java.features_extract.FeatureEngineer;

public class MEMM2 {
	public static void main(String[] args) throws IOException {
		 
		 FeatureEngineer featureEngineer = new FeatureEngineer();

		 String resource = "/data/train";		
		 URL url = FeatureEngineer.class.getResource(resource);
		// featureEngineer.readfile(url);
		 List<Datum> list = featureEngineer.readfile(url);
		 List<Datum> train = featureEngineer.setFeaturesTrain(list);
		
		resource = "/data/dev";
		url = FeatureEngineer.class.getResource(resource);
		
		 featureEngineer.readfile(url);
	     list =  featureEngineer.readfile(url);
		 List<Datum> test = featureEngineer.setFeaturesTest(list);

		 List<Datum> testData = runMEMM(train, test);

		// print words + guess labels for development
		
			for (Datum datum : testData) {
			    System.out.println(datum.word + "\t" + datum.label + "\t"
						+ datum.guessLabel);
			}
		
	

		System.out.println();
		Scorer.score(testData);

	}


    public static List<Datum> runMEMM(List<Datum> trainData, List<Datum> testDataWithMultiplePrevLabels) {

		LogConditionalObjectiveFunction obj = new LogConditionalObjectiveFunction(
				trainData);
		double[] initial = new double[obj.domainDimension()];

		// restore the original test data from the source
		List<Datum> testData = new ArrayList<Datum>();
		testData.add(testDataWithMultiplePrevLabels.get(0));
		for (int i = 1; i < testDataWithMultiplePrevLabels.size(); i += obj.labelIndex.size()) {
			testData.add(testDataWithMultiplePrevLabels.get(i));
			
		}

		QNMinimizer minimizer = new QNMinimizer(15);
		double[][] weights = obj.to2D(minimizer.minimize(obj, 1e-4, initial,
				-1, null));

		Viterbi viterbi = new Viterbi(obj.labelIndex, obj.featureIndex, weights);
		viterbi.decode(testData, testDataWithMultiplePrevLabels);

		return testData;
	}

	 

}
