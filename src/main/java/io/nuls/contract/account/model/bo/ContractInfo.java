package io.nuls.contract.account.model.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.nuls.contract.account.model.vo.ContractModeInfo;
import io.nuls.contract.model.ContractMethod;
import io.nuls.contract.model.ContractResultInfo;

import java.math.BigInteger;
import java.util.List;

public class ContractInfo {

    private String contractAddress;

    private String creater;

    private String createTxHash;

    private String alias;

    private long blockHeight;

    private boolean success;

    private BigInteger balance;

    private String errorMsg;

    private boolean isNrc20;//是否支持NRC20协议(0-否、1-是)

    private boolean isDirectPayable;

    private int status; // -1,执行失败，0未认证 1正在审核 2通过验证 3 已删除

    private long certificationTime;

    private long createTime;

    private String remark;

    private int txCount;

    private String deleteHash;

    private List<ContractMethod> methods;

    //以下字段，为NRC20合约特有
    private String tokenName;

    private String symbol;

    private int decimals;

    private String totalSupply;

    private int transferCount;

    private List<String> owners;

    private ContractResultInfo resultInfo;

    @JsonIgnore
    private boolean isNew;

    public ContractModeInfo toContractModeInfo(){
        ContractModeInfo contractModeInfo =new ContractModeInfo();
        contractModeInfo.setAlias(this.getAlias());
        contractModeInfo.setBalance(this.getBalance());
        contractModeInfo.setBlockHeight(this.getBlockHeight());
        contractModeInfo.setCertificationTime(this.getCertificationTime());
        contractModeInfo.setContractAddress(this.getContractAddress());
        contractModeInfo.setCreater(this.getCreater());
        contractModeInfo.setCreateTime(this.getCreateTime());
        contractModeInfo.setCreateTxHash(this.getCreateTxHash());
        contractModeInfo.setDecimals(this.getDecimals());
        contractModeInfo.setDeleteHash(this.getDeleteHash());
        contractModeInfo.setDirectPayable(this.isDirectPayable);
        contractModeInfo.setErrorMsg(this.getErrorMsg());
        contractModeInfo.setMethods(this.getMethods());
        contractModeInfo.setNrc20(this.isNrc20);
        contractModeInfo.setOwners(this.getOwners());
        contractModeInfo.setRemark(this.getRemark());
        contractModeInfo.setResultInfo(this.getResultInfo());
        contractModeInfo.setStatus(this.getStatus());
        contractModeInfo.setSuccess(this.isSuccess());
        contractModeInfo.setSymbol(this.getSymbol());
        contractModeInfo.setTokenName(this.getTokenName());
        contractModeInfo.setTotalSupply(this.getTotalSupply());
        contractModeInfo.setTransferCount(this.getTransferCount());
        contractModeInfo.setTxCount(this.getTxCount());
        return contractModeInfo;
    }


    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTxHash() {
        return createTxHash;
    }

    public void setCreateTxHash(String createTxHash) {
        this.createTxHash = createTxHash;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public long getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(long blockHeight) {
        this.blockHeight = blockHeight;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public boolean isDirectPayable() {
        return isDirectPayable;
    }

    public void setDirectPayable(boolean directPayable) {
        isDirectPayable = directPayable;
    }

    public boolean isNrc20() {
        return isNrc20;
    }

    public void setNrc20(boolean nrc20) {
        isNrc20 = nrc20;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getCertificationTime() {
        return certificationTime;
    }

    public void setCertificationTime(long certificationTime) {
        this.certificationTime = certificationTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getTxCount() {
        return txCount;
    }

    public void setTxCount(int txCount) {
        this.txCount = txCount;
    }

    public String getDeleteHash() {
        return deleteHash;
    }

    public void setDeleteHash(String deleteHash) {
        this.deleteHash = deleteHash;
    }

    public List<ContractMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<ContractMethod> methods) {
        this.methods = methods;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int getDecimals() {
        return decimals;
    }

    public void setDecimals(int decimals) {
        this.decimals = decimals;
    }

    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    public int getTransferCount() {
        return transferCount;
    }

    public void setTransferCount(int transferCount) {
        this.transferCount = transferCount;
    }

    public List<String> getOwners() {
        return owners;
    }

    public void setOwners(List<String> owners) {
        this.owners = owners;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public ContractResultInfo getResultInfo() {
        return resultInfo;
    }

    public void setResultInfo(ContractResultInfo resultInfo) {
        this.resultInfo = resultInfo;
    }
}
