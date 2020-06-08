package br.com.studiesMaterials.dao;

import br.com.studiesMaterials.web.api.schemas.PodcastPostSchema;

import java.util.UUID;

public interface PodcastDao {
    String findAll();
    void create(PodcastPostSchema paramSchema);
    void update(PodcastPostSchema pramSchema);
    void delete(UUID podcastId);
}
