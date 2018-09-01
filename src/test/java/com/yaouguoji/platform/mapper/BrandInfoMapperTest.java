package com.yaouguoji.platform.mapper;

import com.yaouguoji.platform.entity.BrandInfoEntity;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BrandInfoMapperTest {

    @Resource
    private BrandInfoMapper brandInfoMapper;

    @Test
    public void addBrandInfoTest() {
        BrandInfoEntity brandInfoEntity = new BrandInfoEntity();
        brandInfoEntity.setBrandName("H&M");
        brandInfoEntity.setBrandLogo("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1535765175&di=0199743b7f771770da0d2a7369dab307&src=http://www.epbiao.com/Public/upload/remote/2017/08/75571501738934.png");
        brandInfoEntity.setDeleted(0);
        int brandId = brandInfoMapper.addBrandInfo(brandInfoEntity);
        Assert.assertTrue(brandId > 0);
    }

    @Test
    public void updateAndSelectTest() {
        BrandInfoEntity brandInfoEntity = brandInfoMapper.findById(2);
        Assert.assertNotNull(brandInfoEntity);
        brandInfoEntity.setBrandName("测试品牌");
        int i = brandInfoMapper.updateBrandInfo(brandInfoEntity);
        Assert.assertEquals(1, i);
    }
}
