package com.frank.blog.service.impl;

import com.frank.blog.entity.Notice;
import com.frank.blog.mapper.NoticeMapper;
import com.frank.blog.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Frank
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> listNotice(Integer status)  {
        return noticeMapper.listNotice(status);
    }

    @Override
    public void insertNotice(Notice notice)  {
        noticeMapper.insert(notice);
    }

    @Override
    public void deleteNotice(Integer id)  {
        noticeMapper.deleteById(id);
    }

    @Override
    public void updateNotice(Notice notice)  {
        noticeMapper.update(notice);
    }

    @Override
    public Notice getNoticeById(Integer id)  {
        return noticeMapper.getNoticeById(id);
    }

}
