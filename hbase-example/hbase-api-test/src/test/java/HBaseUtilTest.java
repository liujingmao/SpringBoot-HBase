import java.io.IOException;

import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import com.imooc.bigdata.hbase.api.HBaseConn;
import com.imooc.bigdata.hbase.api.HBaseUtil;

/**
 * Created by jixin on 18-2-25.
 */
public class HBaseUtilTest {

  @Test
  public void createTable() {
    HBaseUtil.createTable("FileTable", new String[]{"fileInfo", "saveInfo"});
  }

  @Test
  public void addFileDetails() {
    HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "http://www.baidu.com/test", "file1.txt");
    HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "http://www.baidu.com/test2", "txt");
    HBaseUtil.putRow("FileTable", "rowkey1", "fileInfo", "http://www.baidu.com/test3", "1024");
    HBaseUtil.putRow("FileTable", "rowkey1", "saveInfo", "http://www.baidu.com/test4", "jixin");
    HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "http://www.baidu.com/test", "file2.jpg");
    HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "http://www.baidu.com/test2", "jpg");
    HBaseUtil.putRow("FileTable", "rowkey2", "fileInfo", "http://www.baidu.com/test3", "1024");
    HBaseUtil.putRow("FileTable", "rowkey2", "saveInfo", "http://www.baidu.com/test4", "jixin");

  }

  @Test
  public void getInfo() {
    try (Table table = HBaseConn.getTable("FileTable")) {
      Get get = new Get(Bytes.toBytes("rowkey1"));
      get.addColumn("fileInfo".getBytes(),"http://www.baidu.com/test".getBytes());
      System.out.println(table.get(get));
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  @Test
  public void getFileDetails() {
    Result result = HBaseUtil.getRow("FileTable", "rowkey1");
    if (result != null) {
      System.out.println("rowkey=" + Bytes.toString(result.getRow()));
      System.out.println("fileName=" + Bytes
          .toString(result.getValue(Bytes.toBytes("fileInfo"), Bytes.toBytes("name"))));
    }
  }

  @Test
  public void scanFileDetails() {
    ResultScanner scanner = HBaseUtil.getScanner("FileTable", "rowkey2", "rowkey2");
    if (scanner != null) {
      scanner.forEach(result -> {
        System.out.println("rowkey=" + Bytes.toString(result.getRow()));
        System.out.println("fileName=" + Bytes
            .toString(result.getValue(Bytes.toBytes("fileInfo"), Bytes.toBytes("name"))));
      });
      scanner.close();
    }
  }

  @Test
  public void deleteRow() {
    HBaseUtil.deleteRow("FileTable", "rowkey1");
  }

  @Test
  public void deleteTable() {
    HBaseUtil.deleteTable("FileTable");
  }
}
