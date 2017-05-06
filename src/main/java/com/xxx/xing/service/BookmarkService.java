package com.xxx.xing.service;

import com.xxx.xing.common.util.ArraysUtil;
import com.xxx.xing.dao.BookmarkDAO;
import com.xxx.xing.entity.Bookmark;
import com.xxx.xing.solr.BookmarkSolr;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xing
 * @Created by 2017-04-20 下午2:15.
 */
@Service
public class BookmarkService {
    private static Log log= LogFactory.getLog(BookmarkService.class);
    @Autowired
    BookmarkDAO bookmarkDAO;
    @Autowired
    BookmarkSolr bookmarkSolr;
    public List<Bookmark> findByUserId(String userId) {
        return bookmarkDAO.findByUserId(userId);
    }

    /**
     * 根据当前用户上传的书签进行更新书签：新增的书签，删除的书签
     *
     * @param userid    用户id
     * @param uploadList 一个包含书签URl的list
     */
    public void update(String userid, List<String> uploadList) {
        List<String> existList = new ArrayList<>();  //存在书签的所有url
        List<String> ids = new ArrayList<>();
        Map<String, String> map = new HashMap<>();  //key:url ,value:userid
        List<Bookmark> existBookmarks = this.findByUserId(userid);
        for (Bookmark b : existBookmarks) {
            existList.add(b.getUrl());
            map.put(b.getUrl(), Integer.toString(b.getId()));
        }
        Map<String, List> listMap = ArraysUtil.getdiff(uploadList, existList);


        /**
         *  新增
         */
        //solr新增
        List addList = listMap.get("a");
        Map<String, Bookmark> bookmarkMap=new HashMap<>();
        if(addList.size()>0){
            bookmarkMap = bookmarkSolr.getBookmarkByUrl((String[]) addList.toArray(new String[addList.size()]), userid);
            //数据库新增
            save(bookmarkMap.values());
            //solr新增
            bookmarkSolr.createIndexs(bookmarkMap);
        }



        //solr删除
        List<Integer> deleteIdsInteger=new ArrayList();
        List<String>  deleteIdsString=new ArrayList();
        if(listMap.get("b").size()>0){
            for( Object url:listMap.get("b")){
                deleteIdsInteger.add(Integer.parseInt(map.get(url.toString())));
                deleteIdsString.add(map.get(url.toString()));
            }
            bookmarkSolr.deleteIndexByIds(deleteIdsString);
            //数据库删除
            deleteByIds(deleteIdsInteger);
        }

        log.info("更新成功：用户id:"+userid+"  新增书签:"+bookmarkMap.size()+" 删除书签:"+deleteIdsInteger.size());
    }

    public List<Bookmark> save(Collection<Bookmark> bookmarks) {
        return bookmarkDAO.save(bookmarks);
    }

    public  void deleteByIds(List<Integer> ids){
         bookmarkDAO.deleteByIds(Bookmark.class,ids);
    }
}
