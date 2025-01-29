import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecommendationSystem {

    public static void main(String[] args) throws IOException, TasteException {
       DataModel model = new FileDataModel(new File("ratings.csv"));

        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        int k = 2; 
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(k, similarity, model);

        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        int userId = 1;
        List<RecommendedItem> recommendations = recommender.recommend(userId, 3);

        System.out.println("Recommendations for user " + userId + ":");
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("Item ID: " + recommendation.getItemID() + ", Predicted Rating: " + recommendation.getValue());
        }
    }
}
