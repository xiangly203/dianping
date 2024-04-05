package org.example.dianping.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.dianping.entity.BlogComments;
import org.example.dianping.mapper.BlogCommentsMapper;
import org.example.dianping.service.IBlogCommentsService;
import org.springframework.stereotype.Service;


@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements IBlogCommentsService {

}
