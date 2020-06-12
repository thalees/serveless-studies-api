package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.ArticleDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.web.api.schemas.ArticleSchema;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;
import java.sql.*;

public class ArticleHandler implements ArticleDao {
    @Override
    public APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String studentId =  input.getPathParameters().get("student_id");

            ArticleSchema data = convertBody(input.getBody());

            String sql = String.format(
                    "INSERT INTO public.article (student_id, subject, link) " +
                            "VALUES ('%s', '%s', '%s')", studentId, data.subject, data.link
            );

            statement.executeUpdate(sql);
            conn.close();

            responseEvent.setStatusCode(201);

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            return responseEvent;
        }
    }

    @Override
    public APIGatewayProxyResponseEvent update(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String articleId = input.getPathParameters().get("article_id");

            ArticleSchema data = convertBody(input.getBody());

            String sql = String.format(
                    "UPDATE public.article SET subject = '%s', link = '%s' " +
                            "WHERE id = '%s'", data.subject, data.link, articleId
            );

            statement.executeUpdate(sql);
            conn.close();

            responseEvent.setStatusCode(201);

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            return responseEvent;
        }
    }

    @Override
    public APIGatewayProxyResponseEvent delete(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();
            String articleId = input.getPathParameters().get("article_id");

            String sql = String.format("DELETE FROM public.article WHERE id = '%s'", articleId);

            statement.executeUpdate(sql);
            conn.close();
            responseEvent.setStatusCode(204);

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            return responseEvent;
        }
    }

    private ArticleSchema convertBody(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, ArticleSchema.class);
    }
}
