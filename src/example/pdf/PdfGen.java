package example.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class PdfGen {

    public static void main(String[] args) throws IOException, DocumentException {
        //直接用代码生成
//        genByCode();

        //通过模板生成，需要注意的是生成模板的软件要用Acrobat Pro DC ，用福昕的话会有问题，数字显示不出来，不知怎么回事
        genByTemple();

    }

    /**
     * 模板文件 生成pdf
     *
     * @throws DocumentException
     * @throws IOException
     */
    private static void genByTemple() throws DocumentException, IOException {
        //模板路径
        String templatePath = "G:\\logs\\pdf模板.pdf";
        //生成的新文件路径
        String newPDFPath = "G:\\logs\\gen.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            //输出流
            out = new FileOutputStream(newPDFPath);
            //读取pdf模板
            reader = new PdfReader(templatePath);
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            String[] str = {"3500000000009772", "-中文", "《br》", "1994-00-00","300",
                     "河北省1231唐山市 ","校验码","确认码"};
            int i = 0;
            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                System.out.println(name + "=" + str[i]);
                System.out.println(form.setField(name, str[i++]));
            }
            //如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.setFormFlattening(true);
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(
                    new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
            doc.close();

        } catch (IOException e) {
            System.out.println(1);
        } catch (DocumentException e) {
            System.out.println(2);
        }
    }


    /**
     * 代码生成pdf
     *
     * @throws DocumentException
     * @throws IOException
     */
    private static void genByCode() throws DocumentException, IOException {
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
        Font titleFont = new Font(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED),16);

        // 4.添加一个内容段落
        Paragraph p1 = new Paragraph("福建省交通违法罚没缴款信息", titleFont);
        p1.setAlignment(Element.ALIGN_CENTER);
        document.add(p1);
        //插入横线
//        Image img = Image.getInstance("C:\\Users\\Administrator\\Documents\\Snagit\\line1_pdf.png");
//        img.setAbsolutePosition(30, 100);

        //其他内容
        Paragraph p2 = new Paragraph("                        处罚决定书号：       回显", chineseFont);
        document.add(p2);
        Paragraph p3 = new Paragraph("                        执收单位：           回显", chineseFont);
        document.add(p3);
        Paragraph p4 = new Paragraph("                        缴款日期：           回显", chineseFont);
        document.add(p4);
        Paragraph p5 = new Paragraph("                        缴款人：             回显", chineseFont);
        document.add(p5);
        Paragraph p6 = new Paragraph("                        缴费金额：           回显", chineseFont);
        document.add(p6);
        Paragraph p7 = new Paragraph("                        缴款书号：           回显", chineseFont);
        document.add(p7);
        Paragraph p8 = new Paragraph("                        票面信息校验码：     回显", chineseFont);
        document.add(p8);
        Paragraph p9 = new Paragraph("                        缴款信息确认码：     回显", chineseFont);
        document.add(p9);

        PdfPTable table = new PdfPTable(1);
        // 宽度100%填充
        table.setWidthPercentage(80);
        // 前间距
        table.setSpacingBefore(30f);
        // 后间距
        table.setSpacingAfter(30f);

        ArrayList<PdfPRow> listRow = table.getRows();

        PdfPCell cells1[]= new PdfPCell[1];
        PdfPRow row1 = new PdfPRow(cells1);
        //单元格
        //单元格内容
        cells1[0] = new PdfPCell(new Paragraph("如需纸质发票，请至指定代开票据网点办理。指定网点可在http://www.ggjfw.com或"
                + "APP\"福建交通罚没\"上查询", chineseFont));
        //水平居中
        cells1[0].setHorizontalAlignment(Element.ALIGN_CENTER);
        //垂直居中
        cells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);

        listRow.add(row1);
        document.add(table);


//        Paragraph p10 = new Paragraph("如需纸质发票，请至指定代开票据网点办理。指定网点可在http://www.ggjfw.com或"
//                + "APP\"福建交通罚没\"上查询", chineseFont);
//        document.add(p10);

        // 5.关闭文档
        document.close();
    }

}