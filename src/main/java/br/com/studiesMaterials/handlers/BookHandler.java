package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.BookDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.domain.Book;
import br.com.studiesMaterials.web.api.schemas.BookPostSchema;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookHandler implements BookDao {
    @Override
    public String findAll() {
        final List<Book> books = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM public.book"
            );

            while (rs.next()) {
                Book book = new Book(
                    rs.getString("id"),
                    rs.getString("student_id"),
                    rs.getString("subject"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("link")
                );
                books.add(book);
            }

            return serializerResponse(books);
            } catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }

    public void create(BookPostSchema data) {
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String sql = String.format(
                "INSERT INTO public.book (subject, title, author, link) " +
                "VALUES ('%s', '%s', '%s', '%s')", data.subject, data.title, data.author, data.link
            );

            statement.executeQuery(sql);
        } catch (SQLException error) {
            error.printStackTrace();
        }
    }

    private String serializerResponse(List<Book> books) {
        Gson gson = new Gson();
        ObjectMapper mapper = new ObjectMapper();
        return gson.toJson(books);
    }
}

