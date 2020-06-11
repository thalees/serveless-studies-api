package br.com.studiesMaterials.dao;

import br.com.studiesMaterials.web.api.schemas.PodcastPostSchema;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import java.util.UUID;

public interface PodcastDao {
    String findAll();
    APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent input);
    void update(PodcastPostSchema pramSchema);
    void delete(UUID podcastId);
}
