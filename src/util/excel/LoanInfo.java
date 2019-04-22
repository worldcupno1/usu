package util.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.math.BigDecimal;
import java.util.Date;

public class LoanInfo extends BaseRowModel {
    @ExcelProperty(index = 0)
    private String bankLoanId;

    @ExcelProperty(index = 1)
    private Long customerId;

    @ExcelProperty(index = 2, format = "yyyy/MM/dd")
    private Date loanDate;

    @ExcelProperty(index = 3)
    private BigDecimal quota;

    @ExcelProperty(index = 4)
    private String bankInterestRate;

    @ExcelProperty(index = 5)
    private Integer loanTerm;

    @ExcelProperty(index = 6, format = "yyyy/MM/dd")
    private Date loanEndDate;

    @ExcelProperty(index = 7)
    private BigDecimal interestPerMonth;

    @ExcelProperty(value = {"一级表头", "二级表头"})
    private BigDecimal sax;
    /**
     * 通过 @ExcelProperty 注解与 index 变量可以标注成员变量所映射的列
     * 作为Excel的模型对象,需要setter方法
     */
    public String getBankLoanId() {
        return bankLoanId;
    }

    public void setBankLoanId(String bankLoanId) {
        this.bankLoanId = bankLoanId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public BigDecimal getQuota() {
        return quota;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public String getBankInterestRate() {
        return bankInterestRate;
    }

    public void setBankInterestRate(String bankInterestRate) {
        this.bankInterestRate = bankInterestRate;
    }

    public Integer getLoanTerm() {
        return loanTerm;
    }

    public void setLoanTerm(Integer loanTerm) {
        this.loanTerm = loanTerm;
    }

    public Date getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(Date loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public BigDecimal getInterestPerMonth() {
        return interestPerMonth;
    }

    public void setInterestPerMonth(BigDecimal interestPerMonth) {
        this.interestPerMonth = interestPerMonth;
    }

    public BigDecimal getSax() {
        return sax;
    }

    public void setSax(BigDecimal sax) {
        this.sax = sax;
    }
}