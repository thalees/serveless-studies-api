package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.PodcastDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.domain.Podcast;
import br.com.studiesMaterials.web.api.schemas.PodcastPostSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    public void create(PodcastPostSchema paramSchema) {
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String sql = String.format(
                    "INSERT INTO public.podcast (student_Id, subject, time, link) " +
                            "VALUES ('%s', '%s', '%s', '%s')", paramSchema.subject, paramSchema.user_id, paramSchema.time, paramSchema.link
            );

            statement.executeQuery(sql);
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    @Override
    public void update(PodcastPostSchema pramSchema) {

    }

    @Override
    public void delete(UUID podcastId) {

    }

    private String serializerResponse(List<Podcast> podcasts) {
        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        return gson.toJson(podcasts);
    }
}
