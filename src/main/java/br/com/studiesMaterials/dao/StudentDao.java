package br.com.studiesMaterials.dao;

import br.com.studiesMaterials.web.api.schemas.StudentPostSchema;

public interface StudentDao {
    String findAll();
    void create(StudentPostSchema paramSchema);
}
