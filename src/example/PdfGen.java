package example;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class PdfGen {

    public static void main(String[] args) throws IOException, DocumentException {

        // 1.新建document对象
        Document document = new Document();

        //如果是服务器返回字节数组，不用生成本地文件的话，ByteArrayOutputStream
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
////        PdfWriter writer = PdfWriter.getInstance(document, baos);
////        //中间设置完，最后输出
////        return baos.toByteArray();



        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("G:\\logs\\test.pdf"));

        // 3.打开文档
        document.open();
        //中文字体,解决中文不能显示问题
        BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        Font chineseFont = new Font(bfChinese);

        // 4.添加一个内容段落
        Paragraph p1 = new Paragraph("福建省交通违法罚没缴款信息", chineseFont);
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);
        //其他内容
        Paragraph p2 = new Paragraph("处罚决定书号：回显", chineseFont);
        document.add(p2);
        Paragraph p3 = new Paragraph("执收单位：回显", chineseFont);
        document.add(p3);
        Paragraph p4 = new Paragraph("缴款日期：回显", chineseFont);
        document.add(p4);
        Paragraph p5 = new Paragraph("缴款人：回显", chineseFont);
        document.add(p5);
        Paragraph p6 = new Paragraph("缴费金额：回显", chineseFont);
        document.add(p6);
        Paragraph p7 = new Paragraph("缴款书号：回显", chineseFont);
        document.add(p7);
        Paragraph p8 = new Paragraph("票面信息校验码：回显", chineseFont);
        document.add(p8);
        Paragraph p9 = new Paragraph("缴款信息确认码：回显", chineseFont);
        document.add(p9);
        Paragraph p10 = new Paragraph("如需纸质发票，请至指定代开票据网点办理。指定网点可在http://www.ggjfw.com或"
                + "APP\"福建交通罚没\"上查询", chineseFont);
        document.add(p10);

        // 5.关闭文档
        document.close();

    }

}