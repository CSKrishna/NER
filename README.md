# NLP_NER
Java code for a trigram Maximum Entropy Markov model (MEMM) for Named Entity Recognition (NER).

This extends the base code for NER based on the MEMM model by Stanford's Prof Jurafsky and Prof Manning.

The base code has been taken from  http://www.mohamedaly.info/teaching/cmp-462-spring-2013. The base code only allows for the features to be conditioned on the previous word's label. This has been extended to allow for the label to be guessed to be conditioned on the previous two labels in addition to the usual context - all the other words and their observables featues including their positions in the sequence.

So we can now add featues such as:
features.add("word=" + current_word + ", prevLabel=" + prev_label + ", prevprevLabel=" + prev_prev_label);

The main changes are to the Viterbi algorithm to handle the additional dependancy and the code for feature extraction and feature engineering.

The code now has two classes: MEMM2 that trains the bigram MEMM and MEMMtrigram that runs the trigram MEMM.

The training data is in the file 'train' and the testing data in 'dev' as usual.

Features can be added under the Class ComputeFeatures under the static method 'compute' (for the bigram case) or the static method 'computetrigram' (for the trigram case).

We note that the trigram model increases the F1 score to 0.68 from 0.63 by adding

features.add("word=" + current_word + ", prevLabel=" + prev_label + ", prevprevLabel=" + prev_prev_label);

to the base feature set:
       	features.add("word=" + current_word);
	features.add("prevLabel=" + prev_label);
	features.add("word=" + current_word + ", prevLabel=" + prev_label);
