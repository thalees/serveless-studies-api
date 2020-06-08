package br.com.studiesMaterials.handlers;

import br.com.studiesMaterials.dao.CourseDao;
import br.com.studiesMaterials.db.DataBase;
import br.com.studiesMaterials.domain.Course;
import br.com.studiesMaterials.web.api.schemas.CoursePostSchema;
import br.com.studiesMaterials.web.api.schemas.CoursePutSchema;
import com.google.gson.Gson;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CourseHandler implements CourseDao {
    @Override
    public String FindAll() {
        final List<Course> courses = new ArrayList<>();

        try(Connection conn = DataBase.connection()){
            assert conn != null;
            Statement statement = conn.createStatement();

            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM public.course"
            );

            while(rs.next()){
                Course course = new Course(
                        rs.getString("id"),
                        rs.getString("student_id"),
                        rs.getString("name"),
                        rs.getString("platform"),
                        rs.getString("price")
                );
                courses.add(course);
            }

            return serializerResponse(courses);

        } catch (SQLException error){
            error.printStackTrace();
        }
        return null;
    }

    public void create(CoursePostSchema data){
        try(Connection conn = DataBase.connection()){
            assert conn != null;
            Statement statement = conn.createStatement();

            String sql = String.format(
                    "INSERT INTO public.course (name, platform, price)" +
                            "VALUES ('%s','%s','%f')", data.name,data.platform,data.price);

            statement.executeQuery(sql);
        }
        catch (SQLException error){
            error.printStackTrace();
        }
    }

    public void update(CoursePutSchema data, UUID id){
        try(Connection conn = DataBase.connection()){
            assert conn != null;
            Statement statement = conn.createStatement();
            String sql = String.format("UPDATE public.course SET ");

            if(data.name!=null)
                sql.concat("name=" + data.name);
            if(data.platform!=null)
                sql.concat("platform=" + data.platform);
            if(data.price!=null)
                sql.concat("price=" + data.price);

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
                    "DELETE FROM public.course" +
                            "WHERE id = %s", id);

            statement.executeQuery(sql);
        }
        catch (SQLException error){
            error.printStackTrace();
        }
    }

    private String serializerResponse(List<Course> courses) {
        Gson gson = new Gson();
        return gson.toJson(courses);
    }

}
