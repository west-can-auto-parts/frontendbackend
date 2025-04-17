package com.example.demo21.service.Implementation;

import com.example.demo21.dto.ReviewResponse;
import com.example.demo21.service.ReviewService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Value("${google.api.key}")
    private String googleApiKey;

    private final RestTemplate restTemplate = new RestTemplate();
    List<String> placeIds=List.of( "ChIJXZVYH0V2hlQRwqIES3MCCW8",
            "ChIJWwgPaQR1hlQR-tVqwNPYGd0",
            "ChIJJZigK8F3hlQRJ_cidF9CiO8",
            "ChIJx3GbAQ54hlQRWU30sDuRUYc",
            "ChIJI7ic-HfZhVQR4jC5mgbhbcU",
            "ChIJq7uEvDLWhVQROVLbABtN0XY",
            "ChIJC18IobJ4hlQRTK8hkKt-0DU",
            "ChIJlYpCBdbPhVQRjJfSqwfwATY",
            "ChIJPUXdnpfEhVQRgcXhwURuUdE",
            "ChIJhX6_k8GPC0ERo_WXGG5RTCc");

    @Override
    public List<ReviewResponse> getAllTopRecentReviews() {

        List<ReviewResponse> allFilteredReviews = new ArrayList<>();
        long cutoffEpoch = LocalDateTime.now().minusMonths(4).toEpochSecond(ZoneOffset.UTC);

        for (String placeId : placeIds) {
            String url = String.format(
                    "https://maps.googleapis.com/maps/api/place/details/json?place_id=%s&fields=reviews&key=%s",
                    placeId, googleApiKey
            );

            try {
                ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

                Map<String, Object> result = (Map<String, Object>) response.getBody().get("result");
                if (result == null || !result.containsKey("reviews")) continue;

                List<Map<String, Object>> reviews = (List<Map<String, Object>>) result.get("reviews");

                for (Map<String, Object> review : reviews) {
                    Integer rating = (Integer) review.get("rating");
                    System.out.println("Rating number: "+rating);
                    Long time = ((Number) review.get("time")).longValue();
                    System.out.println("Review Time: "+time);
                    System.out.println("Code time "+cutoffEpoch);
                    if (rating != null && rating==5 && time >= cutoffEpoch) {
                        String name = (String) review.get("author_name");
                        String authorUrl = (String) review.get("author_url");
                        String text = (String) review.get("text");
                        String profilePhotoUrl = (String) review.get("profile_photo_url");
                        ReviewResponse rr = new ReviewResponse();
                        rr.setName(name);
                        rr.setRating(rating);
                        rr.setUrl(authorUrl);
                        rr.setDescription(text);
                        rr.setPhotoUrl(profilePhotoUrl);
                        allFilteredReviews.add(rr);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error fetching reviews for place ID: " + placeId);
                e.printStackTrace();
            }
        }

        return allFilteredReviews;
    }
}
