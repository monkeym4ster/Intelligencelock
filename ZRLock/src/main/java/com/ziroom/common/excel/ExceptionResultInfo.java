package com.ziroom.common.excel;

/**
 * 自定义系统异常类
 */
public class ExceptionResultInfo extends Exception {

	/**
	 * TODO
	 */
	private static final long serialVersionUID = 2103772615043480942L;
	// 系统统一使用的结果类，包括了 提示信息类型和信息内容
	private ResultInfo resultInfo;

	public ExceptionResultInfo(ResultInfo resultInfo) {
		super(resultInfo.getMessage());
		this.resultInfo = resultInfo;
	}

	public ResultInfo getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(ResultInfo resultInfo) {
		this.resultInfo = resultInfo;
	}

}
