package qrcode;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;

import java.io.File;

/**
 * Title:  二维码例子<br>
 * Description: <br>
 * Date: 2020/11/2 14:53<br>
 * Copyright (c)   <br>
 *
 * @author lvm
 */
@Log4j2
public class TestCase {

    /**
     * 识别二维码
     */
    @Test
    public void e4() {
        String decode = QrCodeUtil.decode(FileUtil.file("f:/qrcode.jpg"));
        log.info(decode);
    }

    /**
     * 生成二维码
     */
    @Test
    public void example() {
        // 生成指定url对应的二维码到文件，宽和高都是300像素
        QrCodeUtil.generate("0371-458937", 300, 300, FileUtil.file("f:/qrcode.jpg"));
        log.info("file length= " + FileUtil.size(new File("f:/qrcode.jpg")));
    }

    /**
     * 自定义参数
     */
    @Test
    public void e2() {
        QrConfig config = new QrConfig(300, 300);
        // 设置边距，即二维码和背景之间的边距
        config.setMargin(3);
        // 设置前景色，即二维码颜色（青色）
        config.setForeColor(java.awt.Color.CYAN.getRGB());
        // 设置背景色（灰色）
        config.setBackColor(java.awt.Color.GRAY.getRGB());
        // 生成二维码到文件，也可以到流
        QrCodeUtil.generate("http://hutool.cn/", config, FileUtil.file("f:/qrcode.jpg"));

        //附带logo小图标
        QrCodeUtil.generate(//
                "http://hutool.cn/", //二维码内容
                QrConfig.create().setImg("e:/logo_small.jpg"), //附带logo
                FileUtil.file("e:/qrcodeWithLogo.jpg")//写出到的文件
        );
    }

    /**
     * 调整纠错级别很多时候，二维码无法识别，这时就要调整纠错级别。纠错级别使用zxing的ErrorCorrectionLevel枚举封装，
     * 包括：L、M、Q、H几个参数，由低到高。低级别的像素块更大，可以远距离识别，但是遮挡就会造成无法识别。
     * 高级别则相反，像素块小，允许遮挡一定范围，但是像素块更密集。
     */
    @Test
    public void e3() {
        QrConfig config = new QrConfig();
        // 高纠错级别
        config.setErrorCorrection(ErrorCorrectionLevel.H);
        QrCodeUtil.generate("https://hutool.cn/", config, FileUtil.file("e:/qrcodeCustom.jpg"));
    }

}
