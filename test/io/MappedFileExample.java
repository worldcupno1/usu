package io;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

/**内存映射文件例子,涉及大文件处理的时候使用
 * Created by lvm on 2016/7/13.
 */
public class MappedFileExample {
    private static Logger log = LogManager.getLogger(TestCase.class);
    static int length = 0x8FFFFFF; // 128 MB
    public static void main(String[] args) throws Exception {
        log.info("Started writing");
        MappedByteBuffer out =
                new RandomAccessFile("test.dat", "rw").getChannel()
                        .map(FileChannel.MapMode.READ_WRITE, 0, length);
        for(int i = 0; i < length; i++)
            out.put((byte)'x');
        log.info("Finished writing");
        for(int i = length/2; i < length/2 + 6; i++)
            System.out.print((char)out.get(i));

    }
}
