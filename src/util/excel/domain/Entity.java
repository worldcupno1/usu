package util.excel.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Title:  <br>
 * Description: <br>
 * Date: 2019\4\26 0026 10:26<br>
 * Copyright (c)   <br>
 *
 * @author lvm
 */
@Data
public class Entity extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String flag;

    @ExcelProperty(index = 1)
    private Integer customerId;

    @ExcelProperty(index = 2)
    private String customerName;

    @ExcelProperty(index = 3)
    private Integer idType;

    @ExcelProperty(index = 4)
    private String idNo;

    @ExcelProperty(index = 5)
    private String accountNo;

    @ExcelProperty(index = 6)
    private BigDecimal payableAmount;

    @ExcelProperty(index = 7)
    private BigDecimal actualAmount;

    @ExcelProperty(index = 8)
    private String status;

    @ExcelProperty(index = 9)
    private String info;

    @ExcelProperty(index = 10)
    private String notes;

}
