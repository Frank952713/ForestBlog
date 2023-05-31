package com.frank.blog.controller.home;

import cn.hutool.http.HtmlUtil;
import com.frank.blog.service.ArticleService;
import com.frank.blog.service.CommentService;
import com.frank.blog.dto.JsonResult;
import com.frank.blog.entity.Article;
import com.frank.blog.entity.Comment;
import com.frank.blog.entity.User;
import com.frank.blog.enums.ArticleStatus;
import com.frank.blog.enums.Role;
import com.frank.blog.util.MyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * @author Frank
 */

@Controller
@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    /**
     * 添加评论
     *
     * @param request
     * @param comment
     */
    @RequestMapping(value = "/comment", method = {RequestMethod.POST})
    public JsonResult insertComment(HttpServletRequest request, Comment comment, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new JsonResult().fail("请先登录");
        }
        Article article = articleService.getArticleByStatusAndId(ArticleStatus.PUBLISH.getValue(), comment.getCommentArticleId());
        if (article == null) {
            return new JsonResult().fail("文章不存在");
        }


        //添加评论
        comment.setCommentUserId(user.getUserId());
        comment.setCommentCreateTime(new Date());
        comment.setCommentIp(MyUtils.getIpAddr(request));
        if (Objects.equals(user.getUserId(), article.getArticleUserId())) {
            comment.setCommentRole(Role.OWNER.getValue());
        } else {
            comment.setCommentRole(Role.VISITOR.getValue());
        }
        comment.setCommentAuthorAvatar(user.getUserAvatar());

        //过滤字符，防止XSS攻击
        comment.setCommentContent(HtmlUtil.escape(comment.getCommentContent()));

        comment.setCommentAuthorName(user.getUserNickname());
        comment.setCommentAuthorEmail(user.getUserEmail());
        comment.setCommentAuthorUrl(user.getUserUrl());
        try {
            commentService.insertComment(comment);
            //更新文章的评论数
            articleService.updateCommentCount(article.getArticleId());
        } catch (Exception e) {
            e.printStackTrace();
            return new JsonResult().fail();
        }
        return new JsonResult().ok();
    }


}
