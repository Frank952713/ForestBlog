package com.frank.blog.service.impl;

import com.frank.blog.service.MenuService;
import com.frank.blog.entity.Menu;
import com.frank.blog.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Frank
 */
@Service
public class MenuServiceImpl implements MenuService {


    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> listMenu() {
        List<Menu> menuList = menuMapper.listMenu();
        return menuList;
    }

    @Override
    public Menu insertMenu(Menu menu) {
        menuMapper.insert(menu);
        return menu;
    }

    @Override
    public void deleteMenu(Integer id) {
        menuMapper.deleteById(id);
    }

    @Override
    public void updateMenu(Menu menu) {
        menuMapper.update(menu);
    }

    @Override
    public Menu getMenuById(Integer id) {
        return menuMapper.getMenuById(id);
    }
}
