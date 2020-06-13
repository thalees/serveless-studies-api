package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.StudentDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.web.api.schemas.*;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentHandler implements StudentDao {
    @Override
    public APIGatewayProxyResponseEvent findAll(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        final List<StudentResponse> students = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM public.student";

            ResultSet result = statement.executeQuery(sql);
            conn.close();

            while (result.next()) {
                StudentResponse student = new StudentResponse(
                        result.getString("id"),
                        result.getString("subject")
                );
                students.add(student);
            }
            responseEvent.setStatusCode(200);
            responseEvent.setBody(serializerResponse(students));

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);
            return responseEvent;
        }
    }

    @Override
    public APIGatewayProxyResponseEvent findAllBooks(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        final List<BookResponse> books = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            String studentId =  input.getPathParameters().get("student_id");

            Statement statement = conn.createStatement();
            String sql = String.format("SELECT * FROM public.book WHERE student_id = '%s'", studentId);
            ResultSet result = statement.executeQuery(sql);
            conn.close();

            while (result.next()) {
                BookResponse book = new BookResponse(
                        result.getString("id"),
                        result.getString("subject"),
                        result.getString("title"),
                        result.getString("author"),
                        result.getString("link")
                );
                books.add(book);
            }

            responseEvent.setStatusCode(200);
            responseEvent.setBody(serializerResponse(books));

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            return responseEvent;
        }
    }

    @Override
    public APIGatewayProxyResponseEvent findAllPodcasts(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        final List<PodcastResponse> podcasts = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            String studentId =  input.getPathParameters().get("student_id");

            Statement statement = conn.createStatement();
            String sql = String.format("SELECT * FROM public.podcast WHERE student_id = '%s'", studentId);
            ResultSet result = statement.executeQuery(sql);
            conn.close();

            while (result.next()) {
                PodcastResponse podcast = new PodcastResponse(
                        result.getString("id"),
                        result.getString("subject"),
                        result.getString("time"),
                        result.getString("link")
                );
                podcasts.add(podcast);
            }

            responseEvent.setStatusCode(200);
            responseEvent.setBody(serializerResponse(podcasts));

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            return responseEvent;
        }
    }

    @Override
    public APIGatewayProxyResponseEvent findAllCourses(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        final List<CourseResponse> courses = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            String studentId =  input.getPathParameters().get("student_id");

            Statement statement = conn.createStatement();
            String sql = String.format("SELECT * FROM public.course WHERE student_id = '%s'", studentId);
            ResultSet result = statement.executeQuery(sql);
            conn.close();

            while (result.next()) {
                CourseResponse course = new CourseResponse(
                        result.getString("id"),
                        result.getString("name"),
                        result.getString("platform"),
                        result.getString("price")
                );
                courses.add(course);
            }

            responseEvent.setStatusCode(200);
            responseEvent.setBody(serializerResponse(courses));

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            return responseEvent;
        }
    }

    @Override
    public APIGatewayProxyResponseEvent findAllArticles(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        final List<ArticleResponse> articles = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            String studentId =  input.getPathParameters().get("student_id");

            Statement statement = conn.createStatement();
            String sql = String.format("SELECT * FROM public.article WHERE student_id = '%s'", studentId);
            ResultSet result = statement.executeQuery(sql);
            conn.close();

            while (result.next()) {
                ArticleResponse article = new ArticleResponse(
                        result.getString("id"),
                        result.getString("subject"),
                        result.getString("link")
                );
                articles.add(article);
            }

            responseEvent.setStatusCode(200);
            responseEvent.setBody(serializerResponse(articles));

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            return responseEvent;
        }
    }

    @Override
    public APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            StudentSchema data = convertBody(input.getBody());

            String sql = String.format(
                    "INSERT INTO public.student (username) " +
                            "VALUES ('%s')", data.username
            );
            statement.executeQuery(sql);

            responseEvent.setStatusCode(201);

        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);
        }
        return responseEvent;
    }

   private static String serializerResponse(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private StudentSchema convertBody(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, StudentSchema.class);
    }

}
