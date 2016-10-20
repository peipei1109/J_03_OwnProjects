package com.recommenderSystem.rs.slopOne;

import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.MemoryDiffStorage;
import org.apache.mahout.cf.taste.impl.recommender.slopeone.SlopeOneRecommender;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.slopeone.*;

public class SlopeOne {
    public static void main(String[] args) throws IOException, TasteException {
        DataModel dataModel = new FileDataModel(new File("data/intro.csv"));

        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        double score = evaluator.evaluate(new SlopeOneNoWeighting(), null,
                dataModel, 0.7, 1.0);
        System.out.println(score);
    }

}

class SlopeOneNoWeighting implements RecommenderBuilder {
    @SuppressWarnings("deprecation")
	public Recommender buildRecommender(DataModel model) throws TasteException {
        DiffStorage diffStorage = new MemoryDiffStorage(model,
                Weighting.UNWEIGHTED, Long.MAX_VALUE);
        return new SlopeOneRecommender(model, Weighting.UNWEIGHTED,
                Weighting.UNWEIGHTED, diffStorage);
    }
}
