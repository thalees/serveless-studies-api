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
    public String findAll() {
        final List<Podcast> podcasts = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM public.podcast"
            );

            while (rs.next()) {
                Podcast podcast = new Podcast(
                        rs.getString("id"),
                        rs.getString("student_id"),
                        rs.getString("subject"),
                        rs.getInt("time"),
                        rs.getString("link")
                );
                podcasts.add(podcast);
            }

            return serializerResponse(podcasts);
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }

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
    public void update(PodcastPostSchema paramSchema) {
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String sql = String.format(
                    "UPDATE public.podcast SET (student_Id = '%s', subject = '%s', time = '%s', link = '%s') " +
                            "WHERE (id = '%s')", paramSchema.student_id, paramSchema.subject, paramSchema.time,
                                                                      paramSchema.link, paramSchema.id
            );

            statement.executeQuery(sql);
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void delete(UUID podcastId) {
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String sql = String.format(
                    "DELETE public.podcast WHERE (id = '%s')", podcastId
            );

            statement.executeQuery(sql);
        } catch (SQLException error) {
            error.printStackTrace();
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
