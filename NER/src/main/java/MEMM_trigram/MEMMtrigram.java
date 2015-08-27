package main.java.MEMM_trigram;



import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import main.java.memm.LogConditionalObjectiveFunction;
import main.java.memm.QNMinimizer;
import main.java.memm.Scorer;
import main.java.datastructures.Datum;

import main.java.features_extract.FeatureEngineer;
import main.java.features_extract.FeatureEngineertrigram;

public class MEMMtrigram {
	public static void main(String[] args) throws IOException {
		 
		 FeatureEngineertrigram featureEngineer = new FeatureEngineertrigram();

		 String resource = "/data/train";		
		 URL url = FeatureEngineer.class.getResource(resource);
		// featureEngineer.readfile(url);
		 List<Datum> list = featureEngineer.readfile(url);
		 List<Datum> train = featureEngineer.setFeaturesTrain(list);
		  System.out.println("Training data uploaded");
		resource = "/data/dev";
		url = FeatureEngineer.class.getResource(resource);
		
		 featureEngineer.readfile(url);
	     list =  featureEngineer.readfile(url);
		 List<Datum> test = featureEngineer.setFeaturesTest(list);
		 System.out.println("Testing data uploaded");
		 List<Datum> testData = runMEMM2(train, test);

		// print words + guess labels for development
	
			for (Datum datum : testData) {
			    System.out.println(datum.word + "\t" + datum.label + "\t"
						+ datum.guessLabel);
			}
		
	

		System.out.println();
		Scorer.score(testData);

	}


    public static List<Datum> runMEMM2(List<Datum> trainData, List<Datum> testDataWithMultiplePrevLabels) {

		
		LogConditionalObjectiveFunction obj = new LogConditionalObjectiveFunction(
				trainData);
		System.out.println("Solver initiatlized");
		double[] initial = new double[obj.domainDimension()];

		// restore the original test data from the source
		List<Datum> testData = new ArrayList<Datum>();
		testData.add(testDataWithMultiplePrevLabels.get(0));
		testData.add(testDataWithMultiplePrevLabels.get(1));
		for (int i = 3; i < testDataWithMultiplePrevLabels.size(); i += 2*(obj.labelIndex.size())) {
			testData.add(testDataWithMultiplePrevLabels.get(i));
			
		}
		System.out.println("Single set array from multiple created");
		QNMinimizer minimizer = new QNMinimizer(15);
		double[][] weights = obj.to2D(minimizer.minimize(obj, 1e-4, initial,
				-1, null));
		System.out.println("Training done");
		Viterbitrigram viterbi = new Viterbitrigram(obj.labelIndex, obj.featureIndex, weights);
		viterbi.decode(testData, testDataWithMultiplePrevLabels);

		return testData;
	}

	
    

}