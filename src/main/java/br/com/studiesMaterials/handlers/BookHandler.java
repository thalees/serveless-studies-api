package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.BookDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.web.api.schemas.BookSchema;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.sql.*;

public class BookHandler implements BookDao {
    @Override
    public APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String studentId =  input.getPathParameters().get("student_id");

            BookSchema data = convertBody(input.getBody());

            String sql = String.format(
                "INSERT INTO public.book (student_id, subject, title, author, link) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s')", studentId, data.subject, data.title, data.author, data.link
            );

            statement.executeUpdate(sql);

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

            String bookId = input.getPathParameters().get("book_id");

            BookSchema data = convertBody(input.getBody());

            String sql = String.format(
                    "UPDATE public.book SET subject = '%s', title = '%s', author = '%s', link = '%s' " +
                    "WHERE id = '%s'",
                    data.subject, data.title, data.author, data.link, bookId
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
            String bookId = input.getPathParameters().get("book_id");

            String sql = String.format("DELETE FROM public.book WHERE id = '%s'", bookId);

            statement.executeUpdate(sql);

            responseEvent.setStatusCode(204);

            return responseEvent;
        } catch (SQLException error) {
            error.printStackTrace();
            responseEvent.setStatusCode(500);

            String body = String.format("{ 'message': %s}", error.getMessage());
            responseEvent.setBody(body);

            return responseEvent;
        }
    }

    private BookSchema convertBody(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, BookSchema.class);
    }
}

