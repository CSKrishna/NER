package main.java.MEMM_trigram;

import java.util.*;

import main.java.datastructures.Datum;
import main.java.datastructures.Index;


public class Viterbitrigram {

	private final Index labelIndex;
	private final Index featureIndex;
	private final double[][] weights;

	public Viterbitrigram(Index labelIndex, Index featureIndex, double[][] weights) {
		this.labelIndex = labelIndex;
		this.featureIndex = featureIndex;
		this.weights = weights;
	}

	public void decode(List<Datum> data, List<Datum> dataWithMultiplePrevLabels) {
		// load words from the data
		List<String> words = new ArrayList<String>();
		for (Datum datum : data) {
			words.add(datum.word);
		}

		int[][][] backpointers = new int[data.size()][numLabels()][numLabels()];
		double[][][] scores = new double[data.size()][numLabels()] [numLabels()];

		int prevLabel = labelIndex.indexOf(data.get(0).previousLabel);
		double[] localScores = computeScores(data.get(0).features);
		int prevprevLabel = labelIndex.indexOf(data.get(0).previouspreviousLabel);

		int position = 0;
		for (int currLabel1 = 0; currLabel1 < localScores.length; currLabel1++) {
			for (int currLabel2 = 0; currLabel2 < localScores.length; currLabel2++) {
			 backpointers[position][currLabel1][currLabel2] = prevprevLabel;
			 scores[position][currLabel1][currLabel2] = localScores[currLabel2];
			// System.out.println(localScores[currLabel2]);
		   }
		}
		position++;
		// for each previous label 
		for (int j = 0; j < numLabels(); j++) {
			Datum datum = dataWithMultiplePrevLabels.get(1 + j);
			String previousLabel = datum.previousLabel;
			prevLabel = labelIndex.indexOf(previousLabel);
			String previouspreviousLabel = datum.previouspreviousLabel;
			 prevprevLabel = labelIndex.indexOf(previouspreviousLabel);

			localScores = computeScores(datum.features);
			for (int currLabel = 0; currLabel < localScores.length; currLabel++) {
				double score = localScores[currLabel]
						+ scores[position - 1][prevprevLabel][prevLabel];
				
				if ( score >= scores[position][prevLabel][currLabel]) {
					backpointers[position][prevLabel][currLabel] = prevprevLabel;
					scores[position][prevLabel][currLabel] = score;
				}
			}
		}
		
		
		

		// for each position in data
		for (position = 2; position< data.size(); position++) {
			// equivalent position in dataWithMultiplePrevLabels
			int i = (position -1) * numLabels()*numLabels() - 1; 
		//	System.out.println(i);
			// for each previous label 
			for (int j = 0; j < numLabels()*numLabels(); j++) {
		//		for (int k = 0; i < numLabels(); k++) {
				 Datum datum = dataWithMultiplePrevLabels.get(i + j);
				 String previousLabel = datum.previousLabel;
				 prevLabel = labelIndex.indexOf(previousLabel);
				 String previouspreviousLabel = datum.previouspreviousLabel;
				 prevprevLabel = labelIndex.indexOf(previouspreviousLabel);

				
				//here we need to do the rejig
				localScores = computeScores(datum.features);
				
					for (int currLabel = 0; currLabel < localScores.length; currLabel++) {
					
						double score = localScores[currLabel]
								+ scores[position - 1][prevprevLabel][prevLabel];
						
					//	if (prevprevLabel == 0 ||  score > scores[position][prevLabel][currLabel]) {
							if ( score >= scores[position][prevLabel][currLabel]) {
							backpointers[position][prevLabel][currLabel] = prevprevLabel;
							scores[position][prevLabel][currLabel] = score;
						}
					}
				
				
		}
	}
		
		
		
		int bestLabel1 = 0;
		int bestLabel2 = 0;
		double bestScore = scores[data.size() - 1][0][0];

		for (int label1 = 0; label1 < scores[data.size() - 1].length; label1++) {
			for (int label2 = 0; label2 < scores[data.size() - 1].length; label2++) {
			if (scores[data.size() - 1][label1][label2] > bestScore) {
				bestLabel1 = label1;
				bestLabel2 = label2;
				bestScore = scores[data.size() - 1][label1][label2];
			}
		  }
		}
			
        //set the last 2 labels
		position = data.size() - 1;	
		Datum datum = data.get(position);
		datum.guessLabel = (String) labelIndex.get(bestLabel2);
		position--;
		datum = data.get(position);
		datum.guessLabel = (String) labelIndex.get(bestLabel1);

		
	    //set remaining labels using backpointers
		for (position = data.size() - 3; position >= 0; position--) {
			datum = data.get(position);
			int bestLabel = backpointers[position+ 2][bestLabel1][bestLabel2] ;
			datum.guessLabel = (String) labelIndex.get(bestLabel);
			bestLabel2 = bestLabel1;
			bestLabel1 = bestLabel;
	   	}
	
}
	private double[] computeScores(List<String> features) {

		double[] scores = new double[numLabels()];

		for (Object feature : features) {
			int f = featureIndex.indexOf(feature);
			if (f < 0) {
				continue;
			}
			for (int i = 0; i < scores.length; i++) {
				scores[i] += weights[i][f];
			}
		}

		return scores;
	}

	private int numLabels() {
		return labelIndex.size();
	}

}