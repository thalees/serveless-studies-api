package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.CourseDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.web.api.schemas.CourseSchema;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.sql.*;


public class CourseHandler implements CourseDao {
    @Override
    public APIGatewayProxyResponseEvent create(APIGatewayProxyRequestEvent input) {
        APIGatewayProxyResponseEvent responseEvent = new APIGatewayProxyResponseEvent();
        try(Connection conn = DataBase.connection()) {
            assert conn != null;
            Statement statement = conn.createStatement();

            String studentId =  input.getPathParameters().get("student_id");

            CourseSchema data = convertBody(input.getBody());

            String sql = String.format(
                    "INSERT INTO public.course (student_id, name, platform, price) " +
                            "VALUES ('%s', '%s', '%s', '%s')", studentId, data.name, data.platform, data.price
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

            String courseId = input.getPathParameters().get("course_id");

            CourseSchema data = convertBody(input.getBody());

            String sql = String.format(
                    "UPDATE public.course SET name = '%s', platform = '%s', price = '%s' " +
                            "WHERE id = '%s'", data.name, data.platform, data.price, courseId
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
            String courseId = input.getPathParameters().get("course_id");

            String sql = String.format("DELETE FROM public.course WHERE id = '%s'", courseId);

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

    private CourseSchema convertBody(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, CourseSchema.class);
    }
}
