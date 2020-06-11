package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.StudentDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.domain.Student;
import br.com.studiesMaterials.web.api.schemas.StudentSchema;
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
        final List<Student> students = new ArrayList<>();

        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM public.student";

            ResultSet result = statement.executeQuery(sql);
            conn.close();

            while (result.next()) {
                Student student = new Student(
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

   private String serializerResponse(List<Student> students) {
        Gson gson = new Gson();
        return gson.toJson(students);
    }

    private StudentSchema convertBody(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, StudentSchema.class);
    }
}
