package br.com.studiesMaterials.dao;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;


public interface StudentDao {
    APIGatewayProxyResponseEvent findAllStudents(APIGatewayProxyRequestEvent input);
    APIGatewayProxyResponseEvent findAllBooks(APIGatewayProxyRequestEvent  input);
    APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent  input);
}
