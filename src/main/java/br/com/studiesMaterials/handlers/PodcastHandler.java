package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.PodcastDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.domain.Podcast;
import br.com.studiesMaterials.web.api.schemas.PodcastPostSchema;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PodcastHandler implements PodcastDao{

    @Override
    public APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String studentId =  input.getPathParameters().get("student_id");

            PodcastPostSchema data = convertBody(input.getBody());

            String sql = String.format(
                    "INSERT INTO public.podcast (student_Id, subject, time, link) " +
                            "VALUES ('%s', '%s', '%s', '%s')", studentId, data.subject,
                                                               data.time, data.link
            );

            statement.executeQuery(sql);
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

            String podcastId = input.getPathParameters().get("podcast_id");
            PodcastPostSchema data = convertBody(input.getBody());

            String sql = String.format(
                    "UPDATE public.podcast SET student_Id = '%s', subject = '%s', time = '%s', link = '%s' " +
                            "WHERE id = '%s'", data.student_id, data.subject, data.time,
                    data.link, podcastId
            );

            statement.executeQuery(sql);
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

            String podcastId = input.getPathParameters().get("podcast_id");

            String sql = String.format("DELETE public.podcast WHERE id = '%s'", podcastId);

            statement.executeQuery(sql);
            responseEvent.setStatusCode(204);

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            return  responseEvent;
        }
    }

    private PodcastPostSchema convertBody(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, PodcastPostSchema.class);
    }

    private String serializerResponse(List<Podcast> podcasts) {
        Gson gson = new Gson();
        return gson.toJson(podcasts);
    }
}
