# Fraud Detection using Deep Learning models on Google Cloud ML Engine

This repository contains a Python package to submit a Deep Learning model training job to Cloud ML Engine. The code base itself has been cloned from the [Google Cloud Platform templates](https://github.com/GoogleCloudPlatform/cloudml-samples/tree/master/cloudml-template/examples/census-classification) and repurposed for the fraud detection use case.

The model adopts the [Deep & Wide modelling architecture](https://ai.googleblog.com/2016/06/wide-deep-learning-better-together-with.html): most categorical features with low cardinality of the underlying category sets are not passed through hidden activations. These constitute the wide aspect and the model only learns the weights to assign to these features. On the other hand, the model learns dense embeddings for features with high cardinality of their underlying category sets. These dense embeddings are then passed through a series of hidden activations. This enables the model to map these highly sparse features into a lower dimensional space, each dimension of which encodes different aspects of the propensity for fraud. Thus, the architecture combines the best of a simple linear classifier and a deep learning classifier to train high performance models.


The code itself is structured so as to provide the following functionality:
* Encode meta-data information so that the model can read from the source - either local files or from Google Cloud Storage - and parse the data
* Ability to add embedding layers, transform features, and create new cross features
* Wide & Deep model construction using canned estimators or alternately construct a custom model
* Submit a training job locally or over Cloud ML Engine


### Repository Structure

The code-base is organized as follows:
1. trainer directory contains the following python scripts - metadata.py, input.py, model.py, task.py -  to adapt the data, specify the training configuration and hyperparameters values, and the script to train and validate the model
    
2. config.yaml contains the specs for running a distributed training job including:
   1) number of parameter servers,
   2) number of workers
   3) concomitant hardware specs 
3. config_hyperparam.yaml file, in addition to the specs for the cluster, also specifies the training regime for hyperparameters, including:
   1) hyperparameters to tune
   2) range of values to explore for each hyperparameter
   3) number of trials to run
   4) level of parallelism (applicable only for grid/random search)
   5) tuning algorithm - Bayesian Optimization, Grid Search, Random Search      
4. local_run.sh is the shell script to lauch a training job locally    
5. distributed_run.sh is the shell script to launch a model training job over Google Cloud MLE. It references config.yaml to specify the cluster configuration    
6. distributed_run_hyperparm_tuning.sh is the shell script to launch a set of training jobs either in parallel or in sequence so as to produce a model that is optimized over both trainable and hyperparameters. The script references config_hyperparams.yaml to specify the hyperparameter tuning regime. 

### Trainer Template Modules

|File Name| Purpose| Do You Need to Change?
|:---|:---|:---
|[metadata.py](template/trainer/metadata.py)|Defines: 1) task type, 2) input data header, 3) numeric and categorical feature names, 4) target feature name (and labels, for a classification task), and 5) unused feature names. | **Yes**, as you will need to specify the metadata of your dataset. **This might be the only module to change!**
|[input.py](template/trainer/input.py)| Includes: 1) data input functions to read data from csv and tfrecords files, 2) parsing functions to convert csv and tf.example to tensors, 3) function to implement your features custom  processing and creation functionality, and 4) prediction functions (for serving the model) that accepts CSV, JSON, and tf.example instances. | **Maybe**, if you want to implement any custom pre-processing and feature creation during reading data.
|[featurizer.py](template/trainer/featurizer.py)| Creates: 1) tensorflow feature_column(s) based on the dataset metadata (and other extended feature columns, e.g. bucketisation, crossing, embedding, etc.), and 2) deep and wide feature column lists. | **Maybe**, if you want to change your feature_column(s) and/or change how deep and wide columns are defined (see next section). 
|[model.py](template/trainer/model.py)|Includes: 1) function to create DNNLinearCombinedRegressor, 2) DNNLinearCombinedClassifier, and 2) function to implement for a custom estimator model_fn.|**No, unless** you want to change something in the estimator, e.g., activation functions, optimizers, etc., or to implement a custom estimator. 
|[task.py](template/trainer/task.py) |Includes: 1 experiment function that executes the model training and evaluation, 2) initialise and parse task arguments (hyper parameters), and 3) Entry point to the trainer. | **No, unless** you want to add/remove parameters, or change parameter default values.
