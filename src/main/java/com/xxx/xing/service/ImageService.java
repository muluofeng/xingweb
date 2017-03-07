package com.xxx.xing.service;

import com.xxx.xing.dao.ImageDAO;
import com.xxx.xing.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author xing
 * @Created by 2017-03-03 上午10:09.
 */
@Service
public class ImageService {
    @Autowired
    ImageDAO imageDAO;

    public Image save(Image image) {
        return imageDAO.save(image);
    }

    public Page<Image> serach(String search, Pageable pageable) {
        if (search == null || "".equals(search)) {
            return imageDAO.findAll(pageable);
        }
        return imageDAO.findByNameLike("%" + search + "%", pageable);
    }
}
