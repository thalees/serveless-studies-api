package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.ArticleDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.domain.Article;
import br.com.studiesMaterials.web.api.schemas.ArticlePostSchema;
import br.com.studiesMaterials.web.api.schemas.ArticlePutSchema;
import com.google.gson.Gson;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ArticleHandler implements ArticleDao {
    @Override
    public String findAll() {
        final List<Article> articles = new ArrayList<>();

        try(Connection conn = DataBase.connection()){
            assert conn != null;

            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM public.article"
            );

            while(rs.next()){
                Article article = new Article(
                        rs.getString("id"),
                        rs.getString("student_id"),
                        rs.getString("subject"),
                        rs.getString("link")
                );
                articles.add(article);
            }
            return serializerResponse(articles);
        } catch (SQLException error){
            error.printStackTrace();
        }
        return null;
    }

    public void create(ArticlePostSchema data) {
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String sql = String.format(
                    "INSERT INTO public.article (subject, link) " +
                            "VALUES ('%s', '%s')", data.subject, data.link
            );

            statement.executeQuery(sql);
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    public void update(ArticlePutSchema data, UUID id){
        try(Connection conn = DataBase.connection()){
            assert conn != null;
            Statement statement = conn.createStatement();
            String sql = String.format("UPDATE public.course SET ");

            if(data.subject!=null)
                sql.concat("subject=" + data.subject);
            if(data.link!=null)
                sql.concat("link=" + data.link);

            sql.concat(" WHERE id = " + id);

            statement.executeQuery(sql);
        }
        catch (SQLException error){
            error.printStackTrace();
        }
    }

    public void delete(UUID id){
        try(Connection conn = DataBase.connection()){
            assert conn != null;
            Statement statement = conn.createStatement();

            String sql = String.format(
                    "DELETE FROM public.article" +
                            "WHERE id = %s", id);

            statement.executeQuery(sql);
        }
        catch (SQLException error){
            error.printStackTrace();
        }
    }

    private String serializerResponse(List<Article> articles) {
        Gson gson = new Gson();
        return gson.toJson(articles);
    }
}
