package com.xxx.xing.web.admin;

import com.xxx.xing.common.Result;
import com.xxx.xing.common.util.ImageUtil;
import com.xxx.xing.configuration.image.ImageConfig;
import com.xxx.xing.entity.Image;
import com.xxx.xing.service.ImageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @author xing
 * @Created by 2017-03-03 上午10:10.
 */
@Controller
@RequestMapping("/admin/image")
public class ImageController {

    @Autowired
    ImageConfig imageConfig;

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index() {
        return "admin/image/index";
    }

    @RequestMapping("/getList")
    @ResponseBody
    public Map<String, Object> getList(int draw, int start, int length,@RequestParam(required = false,name = "search[value]") String searchWord){
        Map  map=new HashMap<String,Object>();
        if(start!=0){
            start=start/length;
        }
        Sort sort=new Sort(Sort.Direction.ASC,"id");
        PageRequest request=new PageRequest(start,length,sort);
        Page<Image> imagePage= imageService.serach(searchWord,request);

        map.put("draw",draw);
        map.put("recordsTotal", imagePage.getTotalPages()); //数据库里总共记录数
        map.put("recordsFiltered",imagePage.getTotalElements());//返回的是过滤后的记录数
        map.put("data", imagePage.getContent());
        map.put("title","图片列表");
        return map;
    }


    @RequestMapping("/upload")
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("w") int width, @RequestParam("h") int height, HttpServletRequest request, HttpServletResponse response) {
        //判断文件类型
        String fileName = file.getOriginalFilename();
        String type = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()) : null;
        if (!"GIF".equals(type.toUpperCase()) && !"PNG".equals(type.toUpperCase()) && !"JPG".equals(type.toUpperCase())) {
            //不是图片类型，或者文件类型为空
            return new Result(false, "文件类型不正确");
        }
        String datePath = DateFormatUtils.format(new Date(), "yyyy/MM/dd");
        String uploadPath = imageConfig.getUploadDir();
        String imageId = RandomStringUtils.randomAlphanumeric(15);
        String trueFileName = imageId+fileName ;
        File fileDir = new File(uploadPath, "/" + datePath);

        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(fileDir, trueFileName)));
                out.write(file.getBytes());
                out.flush();
                //图片处理
                ImageUtil.convertImage(file.getInputStream(),fileDir+"/"+imageId+file.getName()+"-s."+type);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //保存到数据库
            Image image = new Image();
            image.setUrl(imageConfig.getDomainName() + "/upload/" + datePath + "/" + trueFileName);
            image.setWidth(width);
            image.setHeight(height);
            image.setName(imageId);
            image.setLastModified(new Date());
            imageService.save(image);


        }
        return new Result(true, "文件上传成功");
    }


}
