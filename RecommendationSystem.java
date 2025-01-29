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
        // Load data from a file (replace "ratings.csv" with your actual data file)
        DataModel model = new FileDataModel(new File("ratings.csv"));

        // Calculate user similarity using Pearson Correlation
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // Find the k-nearest neighbors
        int k = 2; // Number of neighbors
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(k, similarity, model);

        // Create a recommender
        Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // Get recommendations for a specific user (replace 1 with the actual user ID)
        int userId = 1;
        List<RecommendedItem> recommendations = recommender.recommend(userId, 3); // Recommend 3 items

        // Print recommendations
        System.out.println("Recommendations for user " + userId + ":");
        for (RecommendedItem recommendation : recommendations) {
            System.out.println("Item ID: " + recommendation.getItemID() + ", Predicted Rating: " + recommendation.getValue());
        }
    }
}