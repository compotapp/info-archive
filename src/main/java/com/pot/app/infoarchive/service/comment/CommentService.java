package com.pot.app.infoarchive.service.comment;

import com.pot.app.infoarchive.domain.IdDto;
import com.pot.app.infoarchive.domain.comment.dto.CommentCreate;
import com.pot.app.infoarchive.domain.comment.dto.CommentDto;
import com.pot.app.infoarchive.domain.comment.dto.CommentUpdate;
import com.pot.app.infoarchive.repository.comment.dao.CommentDao;

import java.util.List;

public interface CommentService {

    CommentDto save(CommentCreate request);

    CommentDto getById(IdDto request);

    List<CommentDto> getAll();

    CommentDto update(CommentUpdate request);

    void deleteById(IdDto request);
}
