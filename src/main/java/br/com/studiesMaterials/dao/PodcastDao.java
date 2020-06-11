package br.com.studiesMaterials.dao;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

public interface PodcastDao {
    APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent input);
    APIGatewayProxyResponseEvent update(APIGatewayProxyRequestEvent input);
    APIGatewayProxyResponseEvent delete(APIGatewayProxyRequestEvent input);
}
