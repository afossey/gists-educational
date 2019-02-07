package com.afossey.projects.educational.gists;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface GistRepository extends MongoRepository<GistEntity, String> { }
