package com.jigdraw.draw.service.impl;

import android.content.Context;

import com.jigdraw.draw.dao.ImageDao;
import com.jigdraw.draw.dao.impl.ImageDaoImpl;
import com.jigdraw.draw.model.ImageEntity;
import com.jigdraw.draw.service.ImageService;

/**
 * Default implementation for {@link com.jigdraw.draw.service.ImageService}
 *
 * @author Jay Paulynice
 */
public class ImageServiceImpl implements ImageService {
    private ImageDao dao;

    public ImageServiceImpl(Context context) {
        dao = new ImageDaoImpl(context);
    }

    @Override
    public long insert(ImageEntity entity) {
        return dao.create(entity);
    }

    @Override
    public ImageEntity query(long id) {
        return dao.find(id);
    }

    @Override
    public boolean update(ImageEntity entity) {
        return dao.update(entity) > 0;
    }

    @Override
    public boolean delete(long id) {
        return dao.delete(id) > 0;
    }
}