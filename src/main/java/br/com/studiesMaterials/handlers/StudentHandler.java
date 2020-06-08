package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.StudentDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.domain.Student;
import br.com.studiesMaterials.web.api.schemas.StudentPostSchema;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentHandler implements StudentDao {
    @Override
    public String findAll() {
        final List<Student> students = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM public.student"
            );

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("id"),
                        rs.getString("subject")
                );
                students.add(student);
            }

            return serializerResponse(students);
        } catch (SQLException error) {
            error.printStackTrace();
        }
        return null;
    }

    @Override
    public void create(StudentPostSchema paramSchema) {

    }

    private String serializerResponse(List<Student> books) {
        Gson gson = new Gson();
        return gson.toJson(books);
    }
}
